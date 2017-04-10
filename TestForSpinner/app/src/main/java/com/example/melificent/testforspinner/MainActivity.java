package com.example.melificent.testforspinner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Spinner spinner;
    ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<Item> items = new ArrayList<>();
        items.add(new Item("电灯","111"));
        items.add(new Item("摄像头","222"));
        adapter = new ItemAdapter(this,items);
        spinner.setAdapter(adapter);
        SetListener();

    }

    private void SetListener() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"aaaa",Toast.LENGTH_SHORT).show();
                test(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void test(int position){
        switch (position){
            case 0:
                Toast.makeText(MainActivity.this,"222222222",Toast.LENGTH_SHORT).show();

                break;
            case  1:
                Intent intent  = new Intent(MainActivity.this,SencondActivity.class);
                startActivity(intent);
                break;
        }
    }
}
