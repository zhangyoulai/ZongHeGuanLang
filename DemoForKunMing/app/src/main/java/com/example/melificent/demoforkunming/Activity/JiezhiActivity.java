package com.example.melificent.demoforkunming.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.PersistableBundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.melificent.demoforkunming.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by p on 2016/11/7.
 * 具体参照日常运维方案的方法
 */

public class JiezhiActivity extends AppCompatActivity {
    Button camera,submit;
    ImageView img_a,img_b,img_c,img_d,img_e,img_f;
    EditText et1,et2,et3,et4,et5,et6;
    private  static  final  int TAKE_PHOTO = 1 ;
    private static  final  int CROP_PHOTO = 2;
    private Uri imageuri;
    boolean img1 = false,img2 = false,img3 = false,img4 = false,img5 = false,img6 = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jiezhi_activity);
        camera = (Button) findViewById(R.id.button_camera_jiezhi);
        submit = (Button) findViewById(R.id.button_jiezhi_submit);
//        imageView = (ImageView) findViewById(R.id.image_jiezhi);
        img_a = (ImageView) findViewById(R.id.img_a);
        img_b = (ImageView) findViewById(R.id.img_b);
        img_c = (ImageView) findViewById(R.id.img_c);
        img_d = (ImageView) findViewById(R.id.img_d);
        img_e = (ImageView) findViewById(R.id.img_e);
        img_f = (ImageView) findViewById(R.id.img_f);
        et1 = (EditText) findViewById(R.id.et1);
        et2 = (EditText) findViewById(R.id.et2);
        et3 = (EditText) findViewById(R.id.et3);
        et4 = (EditText) findViewById(R.id.et4);
        et5 = (EditText) findViewById(R.id.et5);
        et6 = (EditText) findViewById(R.id.et6);
        setListener();
        initEditText();


    }

    private void initEditText() {
        et1.setText("光导正常");
        et2.setText("无泄露");
        et3.setText("绝缘皮破损");
        et4.setText("未发现异味，无泄漏");
        et5.setText("无管道破损");
        et6.setText("信号传导正常，管线无破损");
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
                if (et1.getText().toString().equals("")||
                        et2.getText().toString().equals("")||
                        et3.getText().toString().equals("")||
                        et4.getText().toString().equals("")||
                        et5.getText().toString().equals("")||
                        et6.getText().toString().equals("")
                        ){
                    Toast.makeText(JiezhiActivity.this,"未填写完整！！！",Toast.LENGTH_SHORT).show();

                }
                else{
                    startActivityForResult(new Intent(JiezhiActivity.this,MainActivity.class),1);
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
