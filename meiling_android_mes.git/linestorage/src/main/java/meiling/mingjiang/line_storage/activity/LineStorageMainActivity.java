package meiling.mingjiang.line_storage.activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;
import de.greenrobot.event.EventBus;
import meiling.mingjiang.line_storage.R;
import meiling.mingjiang.line_storage.adjustment_fragment.AdjustmentFragment;
import meiling.mingjiang.line_storage.marrtial_fragment.MarrtialFragment;
import meiling.mingjiang.line_storage.request_fragment.RequestFragment;
import meiling.mingjiang.line_storage.return_fragment.ReturnFragment;

/**
 * 备注：
 * 作者：wangzs on 2016/2/19 17:03
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class LineStorageMainActivity extends FragmentActivity {

    private HorizontalScrollView scrollView;
    private ViewPager pager;
    private TextView indicator;
    private RadioGroup rg;
    private List<Fragment> listFragment = new ArrayList<Fragment>();

    private AdjustmentFragment adjustmentFragment = null;
    private MarrtialFragment lineStorageFragment = null;
    private RequestFragment requestFragment = null;
    private ReturnFragment returnragment=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.line_storage_main);
        initView();
        initViewPager();
    }

    private void initView() {
        scrollView = (HorizontalScrollView) findViewById(R.id.scroll);
        pager = (ViewPager) findViewById(R.id.pager);
        indicator = (TextView) findViewById(R.id.indicator);
        rg = (RadioGroup) findViewById(R.id.rg);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(lineStorageFragment);
        EventBus.getDefault().unregister(adjustmentFragment);
        EventBus.getDefault().unregister(requestFragment);
        EventBus.getDefault().unregister(returnragment);

    }

    private void initViewPager() {
        lineStorageFragment = new MarrtialFragment();
        adjustmentFragment = new AdjustmentFragment();
        requestFragment = new RequestFragment();
        returnragment=new ReturnFragment();

        EventBus.getDefault().register(lineStorageFragment);
        EventBus.getDefault().register(adjustmentFragment);
        EventBus.getDefault().register(requestFragment);
        EventBus.getDefault().register(returnragment);

        listFragment.add(lineStorageFragment);
        listFragment.add(adjustmentFragment);
        listFragment.add(requestFragment);
        listFragment.add(returnragment);

        MyFragmentAdapter adapter = new MyFragmentAdapter(getSupportFragmentManager());
        pager.setAdapter(adapter);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // 选中的RadioButton播放动画
                ScaleAnimation sAnim = new ScaleAnimation(1, 1.2f, 1, 1.2f,
                        Animation.RELATIVE_TO_SELF, 0.5f,
                        Animation.RELATIVE_TO_SELF, 0.5f);
                sAnim.setDuration(2000);
                sAnim.setFillAfter(true);

                // 遍历所有的RadioButton
                for (int i = 0; i < group.getChildCount(); i++) {
                    RadioButton radioBtn = (RadioButton) group.getChildAt(i);
                    if (radioBtn.isChecked()) {
                        radioBtn.startAnimation(sAnim);
                    } else {
                        radioBtn.clearAnimation();
                    }
                }
                if (checkedId == R.id.rb1) {
                    pager.setCurrentItem(0);
                } else if (checkedId == R.id.rb2) {
                    pager.setCurrentItem(1);
                } else if(checkedId == R.id.rb3){
                    pager.setCurrentItem(2);
                }
            }
        });
        // 设置pager缓存页数限制
        pager.setOffscreenPageLimit(3);
        pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                // 获取对应位置的RadioButton
                RadioButton radioBtn = (RadioButton) rg.getChildAt(arg0);
                // 设置对应位置的RadioButton为选中的状态
                radioBtn.setChecked(true);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
				/*
				 * arg0 : 可见项的第一项的编号 arg1 : 相对于屏幕左侧的偏移比（0无偏移 1偏移一个屏幕） arg2 :
				 * 平移的绝对的像素值
				 *
				 * px = 70 * (320 / 160)
				 *
				 * px = dp * (dpi / 160)
				 */
                // 获得控件的属性。。获取TextView在其父容器LinearLayout中的布局参数
                // int height = params.height;
                // int width = params.width;
                // int margin = params.leftMargin;
                LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) indicator
                        .getLayoutParams();
                // 设置下方标志的滚动
                int leftmargin = (int) ((arg0 + arg1) * params.width);
                params.leftMargin = leftmargin;
                indicator.setLayoutParams(params);

                // 设置整个卷轴的滚动
                // 获取手机的屏幕宽
                // DisplayMetrics outMetrics = new DisplayMetrics();
                // getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                DisplayMetrics outMetrics = new DisplayMetrics();
                getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
                // 计算滚动距离，使选定的radiobutton总处于屏幕特定位置
                int distance = (int) (leftmargin + params.width - outMetrics.widthPixels / 2);
                // 滚动，数值为负时不滚动
                scrollView.scrollTo(distance, 0);
            }
            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    class MyFragmentAdapter extends FragmentPagerAdapter {
        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public Fragment getItem(int position) {
            Fragment view = listFragment.get(position);
            return view;
        }
        @Override
        public int getCount() {
            return listFragment.size();
        }
    }
}
