package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.R;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/23.
 *
 */

public class InspectionActivity extends AppCompatActivity {
    @InjectView(R.id.inspection_line)
    LinearLayout inspection_line;
    @InjectView(R.id.inspection_task)
    LinearLayout inspection_task;
    @InjectView(R.id.inspection_fed_back)
    LinearLayout inspection_fed_back;
    @InjectView(R.id.inspection_orbit)
    LinearLayout inspection_orbit;
    @InjectView(R.id.inspection_record)
    LinearLayout inspection_record;


    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    private Uri imageuri;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xunjianguanli_activity);
        ButterKnife.inject(this);
        setLineaLayoutClickListener();
    }

    private void setLineaLayoutClickListener() {
        inspection_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InspectionActivity.this,MapActivity.class));
            }
        });
        inspection_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(InspectionActivity.this,InspectionTaskActivity.class));
            }
        });
        inspection_fed_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                View view = LayoutInflater.from(InspectionActivity.this).inflate(R.layout.inspection_issue_fed_back_pop,null);
//                PopupWindow popupWindow = new PopupWindow(view , LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
//                popupWindow.setAnimationStyle(R.style.MyPopupWindowFade);
//                popupWindow.showAtLocation(v, Gravity.RIGHT,0,0);
//                TextView issue_camera = (TextView) view.findViewById(R.id.issue_camera);
//                TextView issue_photograph = (TextView) view.findViewById(R.id.issue_photograph);
//                issue_camera.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
////                        File outputimage = new File(Environment.getExternalStorageDirectory(),"tempImage.jpg");
////                        try {
////
////                            if (outputimage.exists()){
////                                outputimage.delete();
////                            }
////                            outputimage.createNewFile();
////                        } catch (IOException e) {
////                            e.printStackTrace();
////                        }
////                        imageuri = Uri.fromFile(outputimage);
////                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////                        intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
////                        startActivityForResult(intent,TAKE_PHOTO);
//
//
//
//                    }
//                });
//
                startActivity(new Intent(InspectionActivity.this,InspectionIssueFedBackActivity
                .class));
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case TAKE_PHOTO :
                if (resultCode == RESULT_OK){
                    Intent intent = new Intent("com.android.camera.action.CROP");
                    intent.setDataAndType(imageuri,"image/*");
                    intent.putExtra("scale",true);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                    startActivityForResult(intent,CROP_PHOTO);
                }
                break;
            case CROP_PHOTO :
                if (resultCode == RESULT_OK){
                    Log.i("CropPhoto", "onActivityResult: CropPhoto");
                    Intent intent = new Intent(InspectionActivity.this,InspectionIssueFedBackActivity.class);
                    intent.putExtra("imageuri",imageuri+"");
                    startActivity(intent);
                }
        }
    }
}
