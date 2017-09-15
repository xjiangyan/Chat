package huiiuh.com.chat.control.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;

import huiiuh.com.chat.R;
import huiiuh.com.chat.control.activity.LoginAndRegister;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Me_Fragment extends Fragment {

    private View mChat_view;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    Toast.makeText(getContext(), "退出成功", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), LoginAndRegister.class);
                    startActivity(intent);
                    getActivity().finish();
                    break;
                case 1:
                    Toast.makeText(getContext(), "退出异常", Toast.LENGTH_SHORT).show();

                    break;
            }
        }
    };
    private Button mBut_logout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mChat_view = View.inflate(getContext(), R.layout.me_view, null);
        init();

        return mChat_view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initdata();

    }

    private void initdata() {
        mBut_logout.setText("退出登录(" + EMClient.getInstance().getCurrentUser() + ")");
        mBut_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        EMClient.getInstance().logout(false, new EMCallBack() {
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

                            }
                        });
                    }
                }).start();

            }
        });
    }

    private void init() {
        mBut_logout = (Button) mChat_view.findViewById(R.id.but_logout);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }
}
