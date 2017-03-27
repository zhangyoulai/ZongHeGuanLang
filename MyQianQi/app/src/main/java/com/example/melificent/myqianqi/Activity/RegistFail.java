package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.melificent.myqianqi.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/22.
 */

public class RegistFail extends AppCompatActivity {
    @InjectView(R.id.notnow)
    ImageView notNow;
    @InjectView(R.id.try_agian)
    Button try_again;
    @InjectView(R.id.realnameregexfailgoback)
    ImageView back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.realnameregexfail);
        ButterKnife.inject(this);
        notNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistFail.this,FrameActivity.class));
            }
        });
        try_again.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistFail.this,RegexActivity.class));
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
