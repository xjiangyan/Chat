package huiiuh.com.chat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hyphenate.chat.EMClient;

public class MainActivity extends AppCompatActivity {

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

    private void gotoMainOrLogin() {
        if (EMClient.getInstance().isLoggedInBefore()) {
            setContentView(R.layout.activity_main);
        } else {
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
