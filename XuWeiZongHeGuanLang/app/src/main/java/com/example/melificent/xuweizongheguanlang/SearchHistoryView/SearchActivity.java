package com.example.melificent.xuweizongheguanlang.SearchHistoryView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.melificent.xuweizongheguanlang.Activity.NewOprationActivity;
import com.example.melificent.xuweizongheguanlang.Activity.OprationActivity;
import com.example.melificent.xuweizongheguanlang.R;

import butterknife.ButterKnife;
import butterknife.InjectView;



public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_history);

    }


}
