package com.example.melificent.myqianqi.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.melificent.myqianqi.Bean.WeatherBean.Result;
import com.example.melificent.myqianqi.Presenter.IShowWeatherInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IShowWeatherInfoPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.ShowWeatherInfo;

public class MainActivity extends AppCompatActivity implements ShowWeatherInfo {
    private Result result;
    IShowWeatherInfoPresenter presenter = new IShowWeatherInfoPresenterImpl(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.showWeatherInfoPresenter();
        setContentView(R.layout.blank_activity);

    }

    @Override
    public void showWeatherInfo(Result result) {
        this.result = result;
        Log.i("MainActivityweather", "showWeatherInfo: "+result.data.cw);
    }

    @Override
    public void showErrorInfo(String error_Msg) {

    }
}
