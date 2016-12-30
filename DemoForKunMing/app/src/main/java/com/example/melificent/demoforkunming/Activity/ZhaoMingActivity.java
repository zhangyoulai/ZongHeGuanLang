package com.example.melificent.demoforkunming.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.VideoView;

import com.example.melificent.demoforkunming.Fragment.MapFragment;
import com.example.melificent.demoforkunming.R;

/**
 * Created by p on 2016/10/27.
 */
public class ZhaoMingActivity extends AppCompatActivity {
    ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhaomingtujie);
        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(ZhaoMingActivity.this, MainActivity.class);
                intent.putExtra("page",0);
                startActivity(intent);
            }
        });


    }
}
