package com.example.melificent.demoforkunming.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.melificent.demoforkunming.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.melificent.demoforkunming.R.layout.zhuanxiang_activity;

/**
 * Created by p on 2016/11/7.
 * 具体办法参照日常运行选项方法
 */

public class ZhuanxiangActivity extends AppCompatActivity {
    Button camera,submit;
//    ImageView imageView;
    EditText et7,et8,et9,et10,et11,et12;
    private  static  final  int TAKE_PHOTO = 1 ;
    private static  final  int CROP_PHOTO = 2;
    private Uri imageuri;
    ImageView img_a,img_b,img_c,img_d,img_e,img_f;
    boolean img1 = false,img2 = false,img3 = false,img4 = false,img5 = false,img6 = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuanxiang_activity);
        camera = (Button) findViewById(R.id.button_camera_zhuaxiang);
        submit = (Button) findViewById(R.id.button_zhuanxiang_submit);
//        imageView = (ImageView) findViewById(R.id.image_zhuanxiang);
        img_a = (ImageView) findViewById(R.id.img_7);
        img_b = (ImageView) findViewById(R.id.img_8);
        img_c = (ImageView) findViewById(R.id.img_9);
        img_d = (ImageView) findViewById(R.id.img_10);
        img_e = (ImageView) findViewById(R.id.img_11);
        img_f = (ImageView) findViewById(R.id.img_12);
        et7 = (EditText) findViewById(R.id.et7);
        et8 = (EditText) findViewById(R.id.et8);
        et9 = (EditText) findViewById(R.id.et9);
        et10 = (EditText) findViewById(R.id.et10);
        et11= (EditText) findViewById(R.id.et11);
        et12 = (EditText) findViewById(R.id.et12);

        setListener();
        initEditText();


    }

    private void initEditText() {
        et7.setText("5cm(正常:4cm-8cm)");
        et8.setText("正常");
        et9.setText("ch4值超标,怀疑泄露");
        et10.setText("无静电残留");
        et11.setText("25C");
        et12.setText("0.1(正常:0.1-0.3)");
    }

    private void setListener() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File outputimage = new File(Environment.getExternalStorageDirectory(),"tempimage.jpg");
                try {
                    if (outputimage.exists()){
                        outputimage.delete();
                    }
                    outputimage.createNewFile();
                }catch (IOException e){
                    e.printStackTrace();
                }


                imageuri = Uri.fromFile(outputimage);
                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                startActivityForResult(intent1,TAKE_PHOTO);
            }
        });
        submit .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et7.getText().toString().equals("")||
                        et8.getText().toString().equals("")||
                        et9.getText().toString().equals("")||
                        et10.getText().toString().equals("")||
                        et11.getText().toString().equals("")||
                        et12.getText().toString().equals("")
                        ){
                    Toast.makeText(ZhuanxiangActivity.this,"未填写完整！！！",Toast.LENGTH_SHORT).show();

                }else {
                    startActivityForResult(new Intent(ZhuanxiangActivity.this,MainActivity.class),1);
                    finish();
                }

            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    Intent intent2 = new Intent("com.android.camera.action.CROP");
                    intent2.setDataAndType(imageuri, "image/*");
                    intent2.putExtra("scale", true);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, imageuri);
                    startActivityForResult(intent2, CROP_PHOTO);
                }
                break;
            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
                    try {
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                                .openInputStream(imageuri));
//                        imageView.setImageBitmap(bitmap);
                        if (!img1){
                            img_a.setImageBitmap(bitmap);
                            img1 = true;
                        }else  if (!img2){
                            img_b.setImageBitmap(bitmap);
                            img2= true;
                        }else  if (! img3){
                            img_c.setImageBitmap(bitmap);
                            img3 = true;
                        }else  if (! img4){
                            img_d.setImageBitmap(bitmap);
                            img4=true;
                        }else  if (!img5){
                            img_e.setImageBitmap(bitmap);
                            img5 = true;
                        }else  if (!img6){
                            img_f.setImageBitmap(bitmap);
                            img6 = true;
                        }else {
                            Toast.makeText(this, "已经达到上限！！！", Toast.LENGTH_SHORT).show();
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }
}
