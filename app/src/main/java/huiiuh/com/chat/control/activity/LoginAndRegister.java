package huiiuh.com.chat.control.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.connect.auth.QQToken;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import huiiuh.com.chat.AppConstant;
import huiiuh.com.chat.R;
import huiiuh.com.chat.Util.SpUtil;
import huiiuh.com.chat.model.Model;

public class LoginAndRegister extends AppCompatActivity implements View.OnClickListener {
    LinearLayout line;
    Button login;
    ImageView qq_login;

    TextView openidTextView;
    TextView nicknameTextView;
    ImageView userimage;
    Button loginButton;
    ImageView userlogo;
    private Tencent mTencent;
    public static QQAuth mQQAuth;
    public static String mAppid;
    public static String openidString;
    public static String nicknameString;
    public static String TAG = "MainActivity";
    Bitmap bitmap = null;

    private TextView register;
    private EditText username;
    private EditText password;
    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 0) {
                JSONObject response = (JSONObject) msg.obj;
                if (response.has("nickname")) {
                    try {
                        nicknameString = response.getString("nickname");

                        Toast.makeText(getApplicationContext(), nicknameString,
                                Toast.LENGTH_LONG).show();

                        SpUtil.putString(getApplicationContext(), "nickname",
                                nicknameString);
                        SpUtil.putBooelan(getApplicationContext(), "islogined",
                                true);
                        String imageresouse = response
                                .getString("figureurl_qq_2");
                        //                        Toast.makeText(getApplicationContext(),
                        //                                imageresouse, Toast.LENGTH_LONG).show();
                        SpUtil.putString(getApplicationContext(), "imageresouse",
                                imageresouse);
                        Log.d(TAG, "图片地址为：" + imageresouse);
                        Log.e(TAG, "--" + nicknameString);
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            finish();
            Intent intent = new Intent(getApplication(), MainActivity.class);
            startActivity(intent);
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_and_register);

        init();
    }

    private void init() {

        line = (LinearLayout) findViewById(R.id.linearlayout_login);
        line.setBackgroundColor(Color.rgb(228, 224, 223));

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        login.setOnClickListener(this);
        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);
        // login.setBackgroundColor(Color.rgb(0,191,255));
        qq_login = (ImageView) findViewById(R.id.login_qq);
        qq_login.setOnClickListener(this);


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_qq:
                Toast.makeText(getApplicationContext(), "qq登录", Toast.LENGTH_SHORT).show();

                login_qq();
                break;
            case R.id.login:
                login();
                break;
            case R.id.register:
                register();
                break;
        }
    }


    private void login() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("拼命登录中。。");
        mProgressDialog.show();

        if (TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())) {

            Toast.makeText(LoginAndRegister.this, "请输入完整", Toast.LENGTH_SHORT).show();
            username.setError("请输入完整");
        } else {
            EMClient.getInstance().login(username.getText().toString(), password.getText().toString(), new EMCallBack() {


                @Override
                public void onSuccess() {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAndRegister.this, "登录成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                    //                    SpUtil.putBooelan(LoginAndRegister.this, "islogined", true);
                    //                    SpUtil.putString(LoginAndRegister.this, "nickname", username.getText().toString());
                    //                    SpUtil.putBooelan(LoginAndRegister.this, "huanxindenglu", true);

                    Model.getInstance().getUserAccountDao().addAccount(new huiiuh.com.chat.model.bean.UserInfo(username.getText().toString()));
                    Intent intent = new Intent(LoginAndRegister.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }

                @Override
                public void onError(int i, String s) {
                    if (mProgressDialog != null) {
                        mProgressDialog.dismiss();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAndRegister.this, "登录失败,用户名或密码不正确", Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onProgress(int i, String s) {


                }
            });
        }
    }

    private void register() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().createAccount(username.getText().toString(), password.getText().toString());

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAndRegister.this, "注册成功", Toast.LENGTH_SHORT).show();
                        }
                    });

                } catch (final HyphenateException e) {

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(LoginAndRegister.this, "注册失败" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    });
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private void login_qq() {
        // TODO Auto-generated method stub
        // 这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = AppConstant.APP_ID;
        // 第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid, getApplicationContext());
        /**
         * 通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO
         * 是一个String类型的字符串，表示一些权限 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE =
         * “get_user_info,add_t”；所有权限用“all”
         * 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类
         */
        mTencent.login(this, "all", new BaseUiListener());

    }


    private class BaseUiListener implements IUiListener {

        public void onCancel() {
            // TODO Auto-generated method stub

        }

        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();

            try {
                // 获得的数据是JSON格式的，获得你想获得的内容
                // 如果你不知道你能获得什么，看一下下面的LOG
                Log.e(TAG, "-------------" + response.toString());
                openidString = ((JSONObject) response).getString("openid");
                // openidTextView.setText(openidString);
                Log.e(TAG, "-------------" + openidString);
                // access_token= ((JSONObject)
                // response).getString("access_token"); //expires_in =
                // ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /**
             * 到此已经获得OpneID以及其他你想获得的内容了
             * QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             * sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             * 如何得到这个UserInfo类呢？
             */
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
            // 这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON

            info.getUserInfo(new IUiListener() {

                public void onComplete(final Object response) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "---------------111111");
                    Message msg = new Message();
                    msg.obj = response;
                    msg.what = 0;
                    mHandler.sendMessage(msg);
                    Log.e(TAG, "-----111---" + response.toString());
                    /**
                     * 由于图片需要下载所以这里使用了线程，如果是想获得其他文字信息直接 在mHandler里进行操作
                     *
                     */
                    //                    new Thread() {
                    //
                    //                        @Override
                    //                        public void run() {
                    //                            // TODO Auto-generated method stub
                    //                            JSONObject json = (JSONObject) response;
                    //                            try {
                    ////                                bitmap = Util.getbitmap(json
                    ////                                        .getString("figureurl_qq_2"));
                    //                                String imageresouse = json
                    //                                        .getString("figureurl_qq_2");
                    //                                Toast.makeText(getApplicationContext(),
                    //                                        imageresouse, Toast.LENGTH_LONG).show();
                    //                                // SpUtil.putstring(getApplicationContext(),
                    //                                // "userlogo", imageresouse);
                    //                            } catch (JSONException e) {
                    //                                // TODO Auto-generated catch block
                    //                                e.printStackTrace();
                    //                            }
                    //                            Message msg = new Message();
                    //                            msg.obj = bitmap;
                    //                            msg.what = 1;
                    //                            mHandler.sendMessage(msg);
                    //                        }
                    //                    }.start();
                    mTencent.logout(getApplicationContext());
                    SpUtil.putBooelan(getApplicationContext(), "qqdenglu", true);
                }

                public void onCancel() {
                    Log.e(TAG, "--------------111112");
                    // TODO Auto-generated method stub
                }

                public void onError(UiError arg0) {
                    // TODO Auto-generated method stub
                    Log.e(TAG, "-111113" + ":" + arg0);
                }

            });

        }

        public void onError(UiError arg0) {
            // TODO Auto-generated method stub

        }
    }
}
