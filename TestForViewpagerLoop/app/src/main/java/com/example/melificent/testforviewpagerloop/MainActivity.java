package com.example.melificent.testforviewpagerloop;

import android.media.Image;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.util.ArrayList;

import static com.example.melificent.testforviewpagerloop.R.id.viewpager;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{
private  ViewPager viewPager;
    private LinearLayout pointLinearlayout;
    ArrayList<View> views = new ArrayList<>();
    ArrayList<ImageView> imageviews = new ArrayList<>();
    int currImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager= (ViewPager) findViewById(viewpager);
        initViews();
        initPoints();
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(new MyAdapter());
    }

    private void initPoints() {
        pointLinearlayout = (LinearLayout) findViewById(R.id.point_layout);
        int count = pointLinearlayout.getChildCount();
        for (int i=0;i<count;i++){
            imageviews.add((ImageView) pointLinearlayout.getChildAt(i));
        }
        imageviews.get(0).setImageResource(R.drawable.yuan01);
    }

    private void initViews() {
        View view1 = getLayoutInflater().inflate(R.layout.linearlayout_01,null);
        View view2  = getLayoutInflater().inflate(R.layout.linearlayout_02,null);
        View view3  = getLayoutInflater().inflate(R.layout.linearlayout,null);
        views.add(view1);
        views.add(view2);
        views.add(view3);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        ImageView preImg= imageviews.get(currImage);
        preImg.setImageResource(R.drawable.yuan01);
        ImageView currImageview = imageviews.get(position);
        currImageview.setImageResource(R.drawable.yuan02);
        currImage = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private  class MyAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return false;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }
    }
}
