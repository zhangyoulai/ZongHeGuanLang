package com.example.melificent.myqianqi.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.melificent.myqianqi.Bean.RealNameRegex.RealNameRegexResult;
import com.example.melificent.myqianqi.Presenter.IGetRealNameRegexResultPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetRealNameRegexResultPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetRealNameRegexResult;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/16.
 */

public class RealNameRegexActivity extends AppCompatActivity implements GetRealNameRegexResult {
   @InjectView(R.id.realname)
    EditText realname;
    @InjectView(R.id.idcard)
    EditText idcard;
    @InjectView(R.id.realname_query)
    Button query;
    RealNameRegexResult result ;
    IGetRealNameRegexResultPresenter presenter = new IGetRealNameRegexResultPresenterImpl(this);
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realnameactivity);
        ButterKnife.inject(this);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.getRealNameRegexResultPresenter(realname.getText().toString().trim(),idcard.getText().toString().trim());
            }
        });
    }

    @Override
    public void getRealNameRegexSuccess(RealNameRegexResult result) {
        this.result  = result;
        Log.i("realnameregexresult", "realnameregexresult: "+result.res);
    }
}
