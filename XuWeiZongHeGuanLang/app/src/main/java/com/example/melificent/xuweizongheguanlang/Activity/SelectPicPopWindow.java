package com.example.melificent.xuweizongheguanlang.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.Utils.MyConstants;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/1/13.
 * not use ..............
 */

public class SelectPicPopWindow extends Activity {
    @InjectView(R.id.map2D)
    Button map2D;
    @InjectView(R.id.map3D)
    Button map3D;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_form_select);
        ButterKnife.inject(this);
        SetButtonListener();
        MyConstants.IS_CHANGE_TO_3D = false;
        MyConstants.IS_CHANGE_TO_2D = false;
    }

    private void SetButtonListener() {
        map2D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.example.melificent.changeMapto2D");
//                sendBroadcast(intent);
                MyConstants.IS_CHANGE_TO_2D = true;
                finish();
            }
        });
        map3D.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent("com.example.melificent.changeMapto3D");
//                sendBroadcast(intent);
                MyConstants.IS_CHANGE_TO_3D = true;
                finish();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        finish();
        return true;
    }
}
