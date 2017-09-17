package huiiuh.com.chat.control.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hyphenate.easeui.ui.EaseContactListFragment;

import huiiuh.com.chat.AppConstant;
import huiiuh.com.chat.R;
import huiiuh.com.chat.Util.SpUtil2;
import huiiuh.com.chat.control.activity.AddContactActivity;
import huiiuh.com.chat.control.activity.InviteActivity;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Contact_Fragment extends EaseContactListFragment implements View.OnClickListener {

    private LinearLayout mLine_group;
    private LinearLayout mLine_invite;
    private ImageView mIv_contact_red;
    private BroadcastReceiver InviteReciver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            mIv_contact_red.setVisibility(View.VISIBLE);
            SpUtil2.getInstance().save(SpUtil2.IS_NEW_INVITE, true);
        }
    };
    private LocalBroadcastManager mLoca;

    @Override
    protected void initView() {
        super.initView();
        titleBar.setRightImageResource(R.drawable.em_add);
        View headview = View.inflate(getContext(), R.layout.contacter, null);
        listView.addHeaderView(headview);
        findview();
    }


    private void findview() {
        mLine_group = (LinearLayout) getActivity().findViewById(R.id.line_group);
        mLine_invite = (LinearLayout) getActivity().findViewById(R.id.line_invite);
        mIv_contact_red = (ImageView) getActivity().findViewById(R.id.iv_contact_red);
    }

    @Override
    protected void setUpView() {
        super.setUpView();
        titleBar.setTitle("通讯录");
        mLine_group.setOnClickListener(this);
        mLine_invite.setOnClickListener(this);
        titleBar.setRightLayoutClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddContactActivity.class);
                startActivity(intent);
            }
        });
        boolean IS_NEW_INVITE = SpUtil2.getInstance().getBoolean(SpUtil2.IS_NEW_INVITE, false);
        mIv_contact_red.setVisibility(IS_NEW_INVITE ? View.VISIBLE : View.INVISIBLE);
        mLoca = LocalBroadcastManager.getInstance(getContext());
        mLoca.registerReceiver(InviteReciver, new IntentFilter(AppConstant.CONTACT_INVITE_CHANGED));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.line_group:
                Toast.makeText(getContext(), "点击了群组", Toast.LENGTH_SHORT).show();

                break;
            case R.id.line_invite:
                Toast.makeText(getContext(), "点击了好友邀请", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(), InviteActivity.class);
                startActivity(intent);
                mIv_contact_red.setVisibility(View.INVISIBLE);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mLoca.unregisterReceiver(InviteReciver);
    }
}
