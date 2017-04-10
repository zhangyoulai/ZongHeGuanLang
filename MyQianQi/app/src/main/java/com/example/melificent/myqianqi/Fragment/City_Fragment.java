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
import com.example.melificent.myqianqi.Activity.ExpressQueryActivity;
import com.example.melificent.myqianqi.Activity.FunWebpager;
import com.example.melificent.myqianqi.Activity.HeadlineNewsWebPager;
import com.example.melificent.myqianqi.Activity.TourismWebview;
import com.example.melificent.myqianqi.Activity.TrainQueryActivity;
import com.example.melificent.myqianqi.Activity.Webpage;
import com.example.melificent.myqianqi.Activity.ZhengwufuwuActivity;
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

public class City_Fragment extends Fragment  {
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
    @InjectView(R.id.weather_description)
    TextView description;
//    Result result;
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
    List<StrockIndexResult> Strocklist = new ArrayList<>();

    //book airplane ticket
    @InjectView(R.id.airplane)
    LinearLayout airplane;

    //book train ticket
    @InjectView(R.id.train)
    LinearLayout train;

    // express field
    @InjectView(R.id.express)
    ImageView express;

    //tourism field
    @InjectView(R.id.tourism)
    LinearLayout tourism;

    //zhengfudating
    @InjectView(R.id.zhengwudating)
    ImageView zhengwudating;

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
        GetWeatherInformationFromWelcome();
        //economic about
        GetEconomicInfo();
        GetEconomicInfoFromWelcome();
        //airplane ticket book and query
        BookAirPlaneTicketAndQuery();
        //train ticket book and query
        BookTrainTicketAndQuery();
        //express about
        ExpressInfo();
        //tourism about
        LinkToTourism();
        //Link to zhengwudating
        LinkToZhengwu();
        return view;
    }

    private void LinkToZhengwu() {
        zhengwudating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ZhengwufuwuActivity.class));
            }
        });
    }

    private void GetWeatherInformationFromWelcome() {
        Intent intent = getActivity().getIntent();
        Result result = (Result) intent.getSerializableExtra("weather");
        EveryHourResult everyHourResult = (EveryHourResult) intent.getSerializableExtra("everyHourweather");
        if (result != null){
            weather_w = result.data.w;
            description.setText(weather_w);
            Log.i("weather_w", "weather_w: "+weather_w);
            Log.i("showeatherinfo", "showWeatherInfo: "+result.data.tmp);
            now_Tempreture.setText(result.data.tmp+"℃");
            int aqi  = result.data.aqi;
            Log.i("aqicount", "showWeatherInfo: "+aqi);
            if (0<aqi && aqi<50){
                airquality.setText("优 "+aqi);
            }else if (aqi>= 50 && aqi<100){
                airquality.setText("良 "+aqi);
            }else if (aqi>= 100 && aqi< 150){
                airquality.setText("轻度 "+aqi);
            }else if (aqi>=150 && aqi< 200){
                airquality.setText("中度 "+aqi);
            }else if (aqi>= 200 && aqi< 300 ){
                airquality.setText("重度 "+aqi);
            }else {
                airquality.setText("严重 "+aqi);
            }
        }

        if (everyHourResult != null){
            List<Series> list_series = new ArrayList<>();
            list_series = everyHourResult.series;
            int [] tem = new int[25] ;
            for (int i=0;i<list_series.size();i++){
                tem[i] = list_series.get(i).tmp;
            }
            Arrays.sort(tem);
            Log.i("weatherOrder", "getMax: "+tem[24]+" ,getMin:" +tem[0]);
            String weather_predict;
            if (result.data.tmp>tem[24]){
                weather_predict =tem[0]+"℃"+"/"+result.data.tmp+"℃";
            }else {
                weather_predict =tem[0]+"℃"+"/"+tem[24]+"℃";

            }

            Log.i("weather_predict", "weather_predict: "+weather_predict);
            weather.setText(weather_predict);
        }

    }

    private void GetEconomicInfoFromWelcome() {
        Intent intent = getActivity().getIntent();
        Strocklist = (List<StrockIndexResult>) intent.getSerializableExtra("strockIndex");
        if (Strocklist != null){
            shenzhenzhishu.setText(Strocklist.get(0).increPer+"%");
            shenzhenzhishuPri.setText(Strocklist.get(0).nowpri.substring(0,Strocklist.get(0).nowpri.length()-1));
            shangzhengzhishu.setText(Strocklist.get(1).increPer+"%");
            shangzhengzhishuPri.setText(Strocklist.get(1).nowpri.substring(0,Strocklist.get(1).nowpri.length()-2));
            if (Strocklist.get(1).dealNum.length()<=4){
                shangzhengdealNum.setText("0."+Strocklist.get(1).dealNum);

            }else {
                shangzhengdealNum.setText(Strocklist.get(1).dealNum.substring(0,Strocklist.get(1).dealNum.length()-4)+"."+Strocklist.get(1).dealNum.substring(Strocklist.get(1).dealNum.length()-4,Strocklist.get(1).dealNum.length()-2));
            }

            String dealprice= Strocklist.get(1).dealPri;
            if (dealprice.length()== 8 ){
                shangzhengdealPri.setText("0."+dealprice.substring(0,4));

            }
            else if (dealprice.length() == 7){
                shangzhengdealPri.setText("0.0"+dealprice.substring(0,3));

            }else  if (dealprice.length() == 6){
                shangzhengdealPri.setText("0.00"+dealprice.substring(0,2));

            }else if (dealprice.length() == 5){
                shangzhengdealPri.setText("0.000"+dealprice.substring(0,1));

            }
            else if (dealprice.length()<5){
                shangzhengdealPri.setText("0.0000");

            }else {
                shangzhengdealPri.setText(dealprice.substring(0,dealprice.length()-8)+"."+dealprice.substring(dealprice.length()-8,dealprice.length()-6));

            }

            if (Strocklist.get(0).dealNum.length()<=4){
                shenzhendealNum.setText("0."+Strocklist.get(0).dealNum);

            }else {
                shenzhendealNum.setText(Strocklist.get(0).dealNum.substring(0,Strocklist.get(0).dealNum.length()-4)+"."+Strocklist.get(0).dealNum.substring(Strocklist.get(0).dealNum.length()-4,Strocklist.get(0).dealNum.length()-2));

            }

            String dealplriceShenzhen = Strocklist.get(0).dealPri.split("\\.")[0];
            Log.i("ShenzhenDealprice", "run: "+Strocklist.get(0).dealPri);


            if (dealplriceShenzhen.length() == 8){
                shenzhendealPri.setText("0."+dealplriceShenzhen.substring(0,4));

            }else if (dealplriceShenzhen.length() ==7){
                shenzhendealPri.setText("0.0"+dealplriceShenzhen.substring(0,3));

            }else if (dealplriceShenzhen.length() ==6){
                shenzhendealPri.setText("0.00"+dealplriceShenzhen.substring(0,2));

            }else if (dealplriceShenzhen.length() ==5){
                shenzhendealPri.setText("0.000"+dealplriceShenzhen.substring(0,1));

            }else if (dealplriceShenzhen.length() <5){
                shenzhendealPri.setText("0.0000");

            }else {
                shenzhendealPri.setText(dealplriceShenzhen.substring(0,dealplriceShenzhen.length()-8)+"."+dealplriceShenzhen.substring(dealplriceShenzhen.length()-8,dealplriceShenzhen.length()-6));

            }
        }else {

        }




    }

    private void LinkToTourism() {
        tourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TourismWebview.class));
            }
        });
    }

    private void ExpressInfo() {
        express.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ExpressQueryActivity.class));
            }
        });
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
        list.add(new LoopBean(R.drawable.loop1));
        list.add(new LoopBean(R.drawable.loop2));
        list.add(new LoopBean(R.drawable.loop3));
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
