package huiiuh.com.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hyphenate.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

import huiiuh.com.chat.Util.SpUtil;

/**
 * @author Admin
 * @version $Rev$
 * @des ${TODO}
 * @updateAuthor $Author$
 * @updateDes ${TODO}
 */
public class Welcome_Activity extends Activity {

    private ViewPager mViewPager;
    private List<ImageView> mImagelist;
    private ImageView mPoint;
    private ImageView[] mImagedos;
    private Button mGotomain;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SpUtil.getBooelan(getApplicationContext(), "isused", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.welcome_view);
            mGotomain = (Button) findViewById(R.id.btn_gotomainactivity);
            mGotomain.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplication(), MainActivity.class);
                    startActivity(intent);
                    finish();
                    SpUtil.putBooelan(getApplicationContext(), "isused", true);
                }
            });
            initviewpager();
            initpoint();

        }
    }

    /**
     * 初始化viewpager下的小圆点
     */
    private void initpoint() {
        int width = new DensityUtil().dip2px(getApplicationContext(), 10);
        mImagedos = new ImageView[mImagelist.size()];
        LinearLayout linear = (LinearLayout) findViewById(R.id.line_point);
        for (int i = 0; i < mImagelist.size(); i++) {
            mImagedos[i] = new ImageView(getApplicationContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(width, width);
            params.leftMargin = 10;
            mImagedos[i].setLayoutParams(params);
            if (i == 0) {

                mImagedos[i].setBackgroundResource(R.drawable.dot_selected);
            } else {
                mImagedos[i].setBackgroundResource(R.drawable.dot_unselected);

            }
            linear.addView(mImagedos[i]);
        }
    }

    /**
     * 给viewpager设置图片
     */
    private void initviewpager() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
        final int[] imageresouse = new int[]{
                R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5
        };
        mImagelist = new ArrayList<>();
        for (int i = 0; i < imageresouse.length; i++) {
            ImageView image = new ImageView(getApplicationContext());
            image.setScaleType(ImageView.ScaleType.FIT_XY);
            image.setImageResource(imageresouse[i]);
            mImagelist.add(image);
        }
        mViewPager.setAdapter(new MyPagerAdapter());
        mViewPager.setOnPageChangeListener(new MyPagerChange());
    }

    /**
     * viewpager设置器
     */
    private class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return mImagelist.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mImagelist.get(position));
            return mImagelist.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mImagelist.get(position));
            // super.destroyItem(container, position, object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }

    /**
     * viewpager滑动监听器
     **/
    private class MyPagerChange implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < mImagelist.size(); i++) {
                if (i == position) {
                    mImagedos[i].setBackgroundResource(R.drawable.dot_selected);
                } else {
                    mImagedos[i].setBackgroundResource(R.drawable.dot_unselected);
                }
                if (position == mImagelist.size() - 1) {
                    mGotomain.setVisibility(View.VISIBLE);
                } else {
                    mGotomain.setVisibility(View.INVISIBLE);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
