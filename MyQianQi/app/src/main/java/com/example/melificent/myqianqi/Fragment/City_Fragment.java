package com.example.melificent.myqianqi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Activity.AirPlaneQueryActivity;
import com.example.melificent.myqianqi.Activity.EconomicWebpager;
import com.example.melificent.myqianqi.Activity.FunWebpager;
import com.example.melificent.myqianqi.Activity.HeadlineNewsWebPager;
import com.example.melificent.myqianqi.Activity.TrainQueryActivity;
import com.example.melificent.myqianqi.Activity.Webpage;
import com.example.melificent.myqianqi.Bean.EveryHourWeather.EveryHourResult;
import com.example.melificent.myqianqi.Bean.EveryHourWeather.Series;
import com.example.melificent.myqianqi.Bean.Loop.LoopBean;
import com.example.melificent.myqianqi.Bean.StrockIndex.StrockIndexResult;
import com.example.melificent.myqianqi.Bean.WeatherBean.Result;
import com.example.melificent.myqianqi.Presenter.IGetEveryHourWeatherPresenter;
import com.example.melificent.myqianqi.Presenter.IGetStrockInfoPresenter;
import com.example.melificent.myqianqi.Presenter.IShowWeatherInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetEveryHourWeatherPresenterImpl;
import com.example.melificent.myqianqi.Presenter.Impl.IGetStrockIndexResultPresenterImpl;
import com.example.melificent.myqianqi.Presenter.Impl.IShowWeatherInfoPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.Utils.TimeFormatUtils;
import com.example.melificent.myqianqi.View.GetEveryHourWeatherInfo;
import com.example.melificent.myqianqi.View.GetStrockIndex;
import com.example.melificent.myqianqi.View.ShowWeatherInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/21.
 */

public class City_Fragment extends Fragment implements ShowWeatherInfo,GetStrockIndex,GetEveryHourWeatherInfo {
    // field about viewpager
    @InjectView(R.id.news_viewpager)
    ViewPager news_viewpager;
    @InjectView(R.id.dot_linearlayout)
    LinearLayout dot_linearlayout;
    private MyHandler myHandler;
    List<LoopBean> list=  new ArrayList<>();

    //field about headline news
    @InjectView(R.id.headline_news)
    LinearLayout headline_news;

    //field about movie
    @InjectView(R.id.funs)
    LinearLayout funs;

    //field about weather
    @InjectView(R.id.weather_change_area_and_description)
    TextView weather;
    @InjectView(R.id.now_tempreture)
    TextView now_Tempreture;
    @InjectView(R.id.air_quality)
            TextView airquality;
    IShowWeatherInfoPresenter presenter;
    IGetEveryHourWeatherPresenter presenter_byHour;
    Result result;
    String weather_w;

    // field about economic
    @InjectView(R.id.shenzhenzhishu)
            TextView shenzhenzhishu;
    @InjectView(R.id.shenzhenzhishuPri)
            TextView shenzhenzhishuPri;
    @InjectView(R.id.shangzhengzhishu)
            TextView shangzhengzhishu;
    @InjectView(R.id.shangzhengzhishuPri)
            TextView shangzhengzhishuPri;
    @InjectView(R.id.shangzhengdealNum)
            TextView shangzhengdealNum;
    @InjectView(R.id.shangzhengdealPri)
            TextView shangzhengdealPri;
    @InjectView(R.id.shenzhendealNum)
            TextView shenzhendealNum;
    @InjectView(R.id.shenzhendealPri)
            TextView shenzhendealPri;
    @InjectView(R.id.shanghai)
            LinearLayout shanghai;
    @InjectView(R.id.shenzhen)
            LinearLayout shenzhen;
    @InjectView(R.id.shanghai1)
            LinearLayout shanghai1;
    @InjectView(R.id.shenzhen1)
            LinearLayout shenzhen1;
    IGetStrockInfoPresenter presenter_Strock ;
    int typeSHANGHAI = 0;
    int typeSHENZHEN =1;
    List<StrockIndexResult> Strocklist = new ArrayList<>();

    //book airplane ticket
    @InjectView(R.id.airplane)
    LinearLayout airplane;

    //book train ticket
    @InjectView(R.id.train)
    LinearLayout train;
    public City_Fragment() {
        presenter = new IShowWeatherInfoPresenterImpl(this);
        presenter_Strock = new IGetStrockIndexResultPresenterImpl(this);
        presenter_byHour = new IGetEveryHourWeatherPresenterImpl(this);
    }

    Handler MyHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 2:
                    Bundle b =msg.getData();
                    shenzhenzhishuPri.setText(b.getString("shenzhenPri"));
                    break;
                case 3:
                    Bundle b1 = msg.getData();
                    shenzhenzhishu.setText(b1.getString("shenzhen"));
                    break;
                case 4:
                    Bundle b2 = msg.getData();
                    shangzhengzhishu.setText(b2.getString("shanghai"));
                    break;
                case 5:
                    Bundle b3 = msg.getData();
                    shangzhengzhishuPri.setText(b3.getString("shanghaiPri"));
                    break;
                case 6:
                    Bundle b4 =msg.getData();
                    shangzhengdealNum.setText(b4.getString("shanghaidealNum"));
                    break;
                case 7:
                    Bundle b5 = msg.getData();
                    shangzhengdealPri.setText(b5.getString("shanghaidealPri"));
                    break;
                case 8:
                    Bundle b6 = msg.getData();
                    shenzhendealNum.setText(b6.getString("shenzhendealNum"));
                    break;
                case 9:
                    Bundle b7 = msg.getData();
                    shenzhendealPri.setText(b7.getString("shenzhendealPri"));
                    break;


            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.city_fragment,null);
        ButterKnife.inject(this,view);
        //this is setting for viewpager to get loop and link to news pager of QianQi
        initViews();
        initListener();
        initData();
        //headline news link
        LinkToHeadlineNews();
        //movie link
        LinkToMovie();
        //weather about
        ShowWeatherInformations();
        //economic about
        GetEconomicInfo();
        //airplane ticket book and query
        BookAirPlaneTicketAndQuery();
        //train ticket book and query
        BookTrainTicketAndQuery();
        return view;
    }

    private void BookTrainTicketAndQuery() {
        train.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TrainQueryActivity.class));
            }
        });
    }

    private void BookAirPlaneTicketAndQuery() {
        airplane.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AirPlaneQueryActivity.class));
            }
        });
    }

    private void GetEconomicInfo() {
        presenter_Strock.getStrockInfo(typeSHENZHEN);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.currentThread().sleep(200);
                    presenter_Strock.getStrockInfo(typeSHANGHAI);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        shanghai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EconomicWebpager.class));
            }
        });
        shenzhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EconomicWebpager.class));
            }
        });
        shanghai1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), EconomicWebpager.class));
            }
        });
        shenzhen1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(),EconomicWebpager.class));
            }
        });

    }

    private void ShowWeatherInformations() {
        presenter.showWeatherInfoPresenter();
        Date date = new Date();
        String CurrentTime = TimeFormatUtils.formatDate(date);
        Log.i("CurrentTime", "CurrentTime: "+CurrentTime);
        String currentTime = CurrentTime.substring(0,4)+CurrentTime.substring(5,7)+CurrentTime.substring(8);
        Log.i("currenttime", "currenttime: "+currentTime);
        presenter_byHour.getEveryHourWeatherPresenter(currentTime+"00",currentTime+"24");
    }

    private void LinkToMovie() {
        funs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FunWebpager.class));
            }
        });
    }

    private void LinkToHeadlineNews() {
        headline_news.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HeadlineNewsWebPager.class));
            }
        });

    }

    private void initViews() {
        myHandler = new MyHandler();
        myHandler.sendEmptyMessageDelayed(0,2000);
        news_viewpager.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        myHandler.removeCallbacksAndMessages(null);
                        startActivity(new Intent(getActivity(), Webpage.class));
                        break;
                    case MotionEvent.ACTION_UP:
                        myHandler.sendEmptyMessageDelayed(0,2000);
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
    }
    private void initData(){
        list.add(new LoopBean(R.drawable.a));
        list.add(new LoopBean(R.drawable.wodeguiji));
        list.add(new LoopBean(R.drawable.xunjianluxian));
        initDots();
        news_viewpager.setAdapter(new MyPagerAdapter());
        updateIntroAndDot();
    }
    private void initDots() {
        for (int i=0;i<list.size();i++){
            View view = new View(getActivity());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(20,20);
            if (i != 0){
                params.leftMargin = 5;

            }
            view.setLayoutParams(params);
            view.setBackgroundResource(R.drawable.yuan01);
            dot_linearlayout.addView(view);
        }
    }
    private void initListener(){
        news_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateIntroAndDot();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void updateIntroAndDot() {
        int currentPage = news_viewpager.getCurrentItem()%list.size();

        for (int i =0;i<dot_linearlayout.getChildCount();i++){
            dot_linearlayout.getChildAt(i).setEnabled(i== currentPage);
        }

    }

    @Override
    public void showWeatherInfo(Result result) {
            this.result = result;
         weather_w = result.data.w;
        Log.i("weather_w", "weather_w: "+weather_w);
        Log.i("showeatherinfo", "showWeatherInfo: "+result.data.tmp);
        now_Tempreture.setText(result.data.tmp+"℃");
        int aqi  = result.data.aqi;
        if (0<aqi && aqi<50){
            airquality.setText("优 "+aqi);
        }else if (aqi>= 50 && aqi<100){
            airquality.setText("良 "+aqi);
        }else if (aqi>= 100 && aqi< 150){
            airquality.setText("轻度"+aqi);
        }else if (aqi>=150 && aqi< 200){
            airquality.setText("中度"+aqi);
        }else if (aqi>= 200 && aqi< 300 ){
            airquality.setText("重度"+aqi);
        }else {
            airquality.setText("严重"+aqi);
        }

    }

    @Override
    public void showErrorInfo(String error_Msg) {
        Toast.makeText(getActivity(), "获取天气信息失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getStrockIndexSuccess(StrockIndexResult result) {
            Strocklist.add(result);
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.currentThread().sleep(500);

                    if (Strocklist.size() == 0){
                        Toast.makeText(getActivity(), "获取财富信息失败，请重试", Toast.LENGTH_SHORT).show();
                    }else {
                        Message msg1 = new Message();
                        msg1.what = 2;
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("shenzhenPri",Strocklist.get(0).nowpri.substring(0,Strocklist.get(0).nowpri.length()-1));
                        msg1.setData(bundle1);
                        MyHandler.sendMessage(msg1);

                        Message msg2 = new Message();
                        msg2.what = 3;
                        Bundle bundle2= new Bundle();
                        bundle2.putString("shenzhen",Strocklist.get(0).increPer+"%");
                        msg2.setData(bundle2);
                        MyHandler.sendMessage(msg2);

                        Message msg3 = new Message();
                        msg3.what = 4;
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("shanghai",Strocklist.get(1).increPer+"%");
                        msg3.setData(bundle3);
                        MyHandler.sendMessage(msg3);
                        Log.i("shanghai", "run: "+Strocklist.get(1).increPer+"%");
//
                        Message msg4 = new Message();
                        msg4.what = 5;
                        Bundle bundle4= new Bundle();
                        bundle4.putString("shanghaiPri",Strocklist.get(1).nowpri.substring(0,Strocklist.get(1).nowpri.length()-2));
                        msg4.setData(bundle4);
                        MyHandler.sendMessage(msg4);
                        Log.i("shanghaiPri", "run: "+Strocklist.get(1).nowpri.substring(0,Strocklist.get(1).nowpri.length()-2));

                        Message msg5 = new Message();
                        msg5.what = 6;
                        Bundle bundle5= new Bundle();
                        bundle5.putString("shanghaidealNum",Strocklist.get(1).dealNum.substring(0,5)+"."+Strocklist.get(1).dealNum.substring(5,7));
                        msg5.setData(bundle5);
                        MyHandler.sendMessage(msg5);
                        Log.i("shanghaidealNum", "run: "+Strocklist.get(1).dealNum.substring(Strocklist.get(1).dealNum.length()-5,Strocklist.get(1).dealNum.length()));
                        Log.i("shanghaidealNum", "run: "+Strocklist.get(1).dealNum.substring(0,4)+"."+Strocklist.get(1).dealNum.substring(4,6));

                        Message msg6 = new Message();
                        msg6.what = 7;
                        Bundle bundle6= new Bundle();
                        bundle6.putString("shanghaidealPri",Strocklist.get(1).dealPri.substring(0,4)+"."+Strocklist.get(1).dealNum.substring(4,6));
                        msg6.setData(bundle6);
                        MyHandler.sendMessage(msg6);
                        Log.i("shanghaidealPri", "run: "+Strocklist.get(1).dealPri.substring(0,4)+"."+Strocklist.get(1).dealNum.substring(4,6));

                        Message msg7 = new Message();
                        msg7.what = 8;
                        Bundle bundle7= new Bundle();
                        bundle7.putString("shenzhendealNum",Strocklist.get(0).dealNum.substring(0,7)+"."+Strocklist.get(0).dealNum.substring(7,9));
                        msg7.setData(bundle7);
                        MyHandler.sendMessage(msg7);

                        Message msg8 = new Message();
                        msg8.what = 9;
                        Bundle bundle8= new Bundle();
                        bundle8.putString("shenzhendealPri",Strocklist.get(0).dealPri.substring(0,4)+"."+Strocklist.get(0).dealNum.substring(4,6));
                        msg8.setData(bundle8);
                        MyHandler.sendMessage(msg8);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();


    }

    @Override
    public void getStrockIndexFial(String Msg) {

    }

    @Override
    public void getWeatherInfoSuccess(EveryHourResult result) {

            List<Series> list_series = new ArrayList<>();
            list_series = result.series;
        int [] tem = new int[25] ;
       for (int i=0;i<list_series.size();i++){
           tem[i] = list_series.get(i).tmp;
       }
        Arrays.sort(tem);
        Log.i("weatherOrder", "getMax: "+tem[24]+" ,getMin:" +tem[0]);
        String weather_predict =  weather_w+tem[0]+"℃"+"/"+tem[24]+"℃";
        Log.i("weather_predict", "weather_predict: "+weather_predict);
        weather.setText(weather_predict);

    }

    @Override
    public void getWeatherInfoFail(String Msg) {

    }

    class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int currentItem =news_viewpager.getCurrentItem();
            news_viewpager.setCurrentItem(++currentItem);
            myHandler.sendEmptyMessageDelayed(0,2000);
        }
    }

    class MyPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View view  = View.inflate(getActivity(),R.layout.adapter_loop,null);
            ImageView imageview = (ImageView) view.findViewById(R.id.image);
            LoopBean ad = list.get(position%list.size());
            imageview.setImageResource(ad.imageRes);
            container.addView(view);

            return view ;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }
}
