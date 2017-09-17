package huiiuh.com.chat.control.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;
import com.hyphenate.exceptions.HyphenateException;

import huiiuh.com.chat.R;
import huiiuh.com.chat.model.Model;
import huiiuh.com.chat.model.bean.UserInfo;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTv_selectcontact;
    private TextView mEm_default_avatarname;
    private ImageView mEm_default_avatarpic;
    private Button mBt_addcontact;
    private EditText mEt_inputcontact;
    private RelativeLayout mRela_contactinfo;
    private String mUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        findview();
        initlistener();
    }

    private void initlistener() {
        mTv_selectcontact.setOnClickListener(this);
        mBt_addcontact.setOnClickListener(this);
    }

    private void findview() {
        mRela_contactinfo = (RelativeLayout) findViewById(R.id.rela_contactinfo);
        mTv_selectcontact = (TextView) findViewById(R.id.tv_selectcontact);
        mEm_default_avatarname = (TextView) findViewById(R.id.em_default_avatarname);
        mEm_default_avatarpic = (ImageView) findViewById(R.id.em_default_avatarpic);
        mBt_addcontact = (Button) findViewById(R.id.bt_addcontact);
        mEt_inputcontact = (EditText) findViewById(R.id.et_inputcontact);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_selectcontact:
                selectcontact();
                break;
            case R.id.bt_addcontact:
                addcontact();
                break;
        }
    }

    private void addcontact() {
        Model.getInstance().getGlobalThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    EMClient.getInstance().contactManager().addContact(mUsername, EMClient.getInstance().getCurrentUser() + "请求添加好友");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "好友邀请发送成功！", Toast.LENGTH_SHORT).show();

                        }
                    });
                } catch (final HyphenateException e) {
                    e.printStackTrace();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(AddContactActivity.this, "好友邀请发送失败！" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }
            }
        });
    }

    private void selectcontact() {
        mUsername = mEt_inputcontact.getText().toString().trim();
        if (TextUtils.isEmpty(mUsername)) {
            Toast.makeText(this, "请输入要查询的用户名！", Toast.LENGTH_SHORT).show();
        } else {

            Model.getInstance().getGlobalThreadPool().execute(new Runnable() {

                @Override
                public void run() {
                    //假设存在并且查询到了 （环信不提供查询好友的功能，在这直接默认存在）
                    final UserInfo mUserInfo = new UserInfo(mUsername);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mEm_default_avatarname.setText(mUserInfo.getHxid());
                            mRela_contactinfo.setVisibility(View.VISIBLE);
                        }
                    });

                }
            });

        }
    }
}
