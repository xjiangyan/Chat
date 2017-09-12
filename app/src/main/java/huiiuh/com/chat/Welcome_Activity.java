package huiiuh.com.chat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (SpUtil.getBooelan(getApplicationContext(), "isused", false)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.welcomeview);
            init();
        }
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.viewpager_welcome);
        final int[] imageresouse = new int[]{
                R.drawable.logo1, R.drawable.logo2, R.drawable.logo3, R.drawable.logo4, R.drawable.logo5
        };
        mImagelist = new ArrayList<>();
        for (int i = 0; i < imageresouse.length; i++) {
            ImageView image = new ImageView(getApplicationContext());

            image.setImageResource(imageresouse[i]);
            mImagelist.add(image);
        }
        mViewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imageresouse.length;
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
        });
    }
}
