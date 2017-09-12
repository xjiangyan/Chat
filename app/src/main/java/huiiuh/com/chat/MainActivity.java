package huiiuh.com.chat;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
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
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                EMClient.getInstance().login("jjh", "520399", new EMCallBack() {
                    @Override
                    public void onSuccess() {
                        Message message = Message.obtain();
                        message.what = 0;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onError(int code, String error) {
                        Message message = Message.obtain();
                        message.what = 1;
                        mHandler.sendMessage(message);
                    }

                    @Override
                    public void onProgress(int progress, String status) {
                        Message message = Message.obtain();
                        message.what = 2;
                        mHandler.sendMessage(message);
                    }
                });
            }
        }).start();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
