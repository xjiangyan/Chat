package huiiuh.com.chat.control.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

import java.util.ArrayList;

import huiiuh.com.chat.R;
import huiiuh.com.chat.control.fragment.Chat_Fragment;
import huiiuh.com.chat.control.fragment.Contact_Fragment;
import huiiuh.com.chat.control.fragment.Discover_Fragment;
import huiiuh.com.chat.control.fragment.Me_Fragment;
import huiiuh.com.chat.model.Model;
import huiiuh.com.chat.model.bean.UserInfo;


public class MainActivity extends FragmentActivity {
    private FrameLayout frameLayout;
    private RadioGroup rgMain;
    private RadioButton rbMessage;
    private RadioButton rb_contact;
    private RadioButton rb_discover;
    private RadioButton rb_me;
    private ArrayList<Fragment> mFragmentlist;


    private int position = 0;
    private FragmentTransaction mFt;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {

                case 0:
                    Toast.makeText(MainActivity.this, "登录成功！", Toast.LENGTH_SHORT).show();

                    break;
                case 1:
                    Toast.makeText(MainActivity.this, "登录失败！", Toast.LENGTH_SHORT).show();

                    break;
                case 2:
                    Toast.makeText(MainActivity.this, "正在登录！", Toast.LENGTH_SHORT).show();

                    break;

            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gotoMainOrLogin();

    }

    private void findViews() {
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        rgMain = (RadioGroup) findViewById(R.id.rg_main);


    }


    private void init() {
        mFragmentlist = new ArrayList<>();
        mFragmentlist.add(new Chat_Fragment());
        mFragmentlist.add(new Contact_Fragment());
        mFragmentlist.add(new Discover_Fragment());
        mFragmentlist.add(new Me_Fragment());

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction mFt2 = fm.beginTransaction();

        rgMain.check(R.id.rb_message);
        mFt2.add(R.id.frameLayout, mFragmentlist.get(0)).commit();
        rgMain.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FragmentManager fm2 = getSupportFragmentManager();
                mFt = fm2.beginTransaction();
                hideFragments();
                switch (checkedId) {
                    case R.id.rb_message:
                        position = 0;
                        break;
                    case R.id.rb_contact:
                        position = 1;
                        break;
                    case R.id.rb_discover:
                        position = 2;
                        break;
                    case R.id.rb_me:
                        position = 3;
                        break;

                    default:
                        position = 0;
                        break;
                }
                switchfragment(position);

                Log.d("MainActivity", "position:" + position);
            }
        });


    }

    private void switchfragment(int position) {

        if (!mFragmentlist.get(position).isAdded()) {
            mFt.add(R.id.frameLayout, mFragmentlist.get(position));
            Log.d("MainActivity", "添加了");
        }
        mFt.show(mFragmentlist.get(position));
        mFt.commit();
    }

    private void hideFragments() {
        for (int i = 0; i < mFragmentlist.size(); i++) {
            mFt.hide(mFragmentlist.get(i));
            Log.d("MainActivity", "隐藏了");
        }
    }


    private void gotoMainOrLogin() {
        if (EMClient.getInstance().isLoggedInBefore()) {

            UserInfo accout = Model.getInstance().getUserAccountDao().getAccountByHxId(EMClient.getInstance().getCurrentUser());

            Log.d("MainActivity", "当前登录用户" + EMClient.getInstance().getCurrentUser());
            if (accout == null) {
                Toast.makeText(this, "还没登录过", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplication(), LoginAndRegister.class);
                startActivity(intent);
                finish();
            } else {
                setContentView(R.layout.activity_main);
                Model.getInstance().loginSucess(new huiiuh.com.chat.model.bean.UserInfo(EMClient.getInstance().getCurrentUser()));

                findViews();
                init();
            }


        } else {
            Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(getApplication(), LoginAndRegister.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);

    }
}
