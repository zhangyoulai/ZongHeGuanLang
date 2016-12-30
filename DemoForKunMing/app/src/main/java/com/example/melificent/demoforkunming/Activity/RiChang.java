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
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.melificent.demoforkunming.Adapter.GridViewAdapter;
import com.example.melificent.demoforkunming.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

/**
 * Created by p on 2016/11/7.
 */
public class RiChang  extends AppCompatActivity {
    EditText et_shigongzhuangtai,et_guandaoquexian,et_guanjiaquexian,et_famenfalan,
            et_pengzhangwan,et_quyuzhouweizhuangtai;
    Button camera,submit;
    private  static  final  int TAKE_PHOTO = 1 ;
    private static  final  int CROP_PHOTO = 2;
    ImageView img_1,img_2,img_3,img_4,img_5,img_6;
    private Uri imageuri;
    List<Bitmap> bitmaps;
//    GridView gridView;
    GridViewAdapter adapter;
    boolean img1=false;
    boolean img2= false;
    boolean img3 = false;
    boolean img4 = false;
    boolean img5 = false;
    boolean img6 = false;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_richangxuncha);
        //初始化控件
        initViews();
        //设置按钮监听
        setListener();
        initEditText();


    }

    private void initEditText() {
        et_shigongzhuangtai.setText("光纤管道维修");
        et_guandaoquexian.setText("未发现管道缺陷");
        et_guanjiaquexian.setText("自来水管道支架松动");
        et_famenfalan.setText("未发现异常");
        et_pengzhangwan.setText("生锈腐蚀");
        et_quyuzhouweizhuangtai.setText("状态正常");
    }

//    private void GridViewsetSource() {
//        adapter =new GridViewAdapter(RiChang.this,bitmaps);
//        gridView.setAdapter(adapter);
//    }

    private void setListener() {
        //拍照
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建一个文件夹用于放置拍照存储
                File outputimage = new File(Environment.getExternalStorageDirectory(),"tempimage.jpg");
                try {
                    //判断放置拍照的文件夹是否存在，如果存在，就删除
                    if (!outputimage.exists()){
//                        outputimage.delete();
                          // 不存在那么就新建
                        outputimage.createNewFile();
                }

                }catch (IOException e){
                    e.printStackTrace();
                }

                //通过文件位置来获取uri
                imageuri = Uri.fromFile(outputimage);
                //给intent1来添加action，其中给intent1来传递参数用于onresultactivity方法的判断，并且使用startActivityForResult方法
                Intent intent1 = new Intent("android.media.action.IMAGE_CAPTURE");
                intent1.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                startActivityForResult(intent1,TAKE_PHOTO);
            }
        });
        //判断各项是否填写完整，如果没有toast提示，填写完整那么就跳到mainactivity，并且调用自身的finish（）方法
        submit .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_shigongzhuangtai.getText().toString().equals("")||et_quyuzhouweizhuangtai.getText().toString().equals("")
                        ||et_pengzhangwan.getText().toString().equals("")||et_guanjiaquexian.getText().toString().equals("")||
                        et_famenfalan.getText().toString().equals("")||et_guandaoquexian.getText().toString().equals("")){
                    Toast.makeText(RiChang.this,"未填写完整！！！",Toast.LENGTH_SHORT).show();
                }else {
                    startActivityForResult(new Intent(RiChang.this,MainActivity.class),1);
                    finish();
                }

            }
        });
    }
    //重写onActivityResult方法，用于相应startActivityForResult方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //通过requestcode的值来进行操作的响应判断
        switch (requestCode){
            //如果是拍照
            case TAKE_PHOTO:
                //判断resultcode是否是RESULT_OK
                if (resultCode==RESULT_OK){
                    //如果是，给intent2来添加action，并且携带参数，设置相关属性，进行裁剪图片的处理，再次调用startactivityforesult
                    Intent intent2 = new Intent("com.android.camera.action.CROP");
                    intent2.setDataAndType(imageuri,"image/*");
                    intent2.putExtra("scale",true);
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
                    startActivityForResult(intent2,CROP_PHOTO);
                }
                break;
            //如果是裁剪图片
            case CROP_PHOTO:
                //resultCode==RESULT_OK
                if (resultCode==RESULT_OK){
                    try {
                        //调用bitmapfactory.decodestream()方法，从文件中获取bitmap对象
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imageuri));
                        //给相应的imageview设置图片，应该可以使用image loader框架进行优化，防止出现OOM
//                        iamgeview.setImageBitmap(bitmap);
//                        bitmaps.add(bitmap);
//                        GridViewsetSource();
                        if (!img1){
                            img_1.setImageBitmap(bitmap);
                            img1=true;
                        }else  if (! img2){
                            img_2.setImageBitmap(bitmap);
                            img2=true;
                        }else  if (! img5){
                            img_5.setImageBitmap(bitmap);
                            img5=true;
                        }
                        else  if(! img3){
                            img_3.setImageBitmap(bitmap);
                            img3 = true;
                        }else  if (! img4){
                            img_4.setImageBitmap(bitmap);
                            img4 = true;
                        }else  if(! img6){
                            img_6.setImageBitmap(bitmap);
                            img6=true;
                        }
                        else{
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


    private void initViews() {
        et_famenfalan = (EditText) findViewById(R.id.et_famenfalan);
        et_guandaoquexian = (EditText) findViewById(R.id.et_guandaoquexian);
        et_guanjiaquexian = (EditText) findViewById(R.id.et_guanjiaquexian);
        et_pengzhangwan = (EditText) findViewById(R.id.et_pengzhangwan);
        et_quyuzhouweizhuangtai = (EditText) findViewById(R.id.et_quyuzhouweizhuangtai);
        et_shigongzhuangtai = (EditText) findViewById(R.id.et_shigongzhuangtai);
//        iamgeview = (ImageView) findViewById(R.id.image_richangxunjian);
        camera= (Button) findViewById(R.id.button_camera);
        submit = (Button) findViewById(R.id.button_richangxuncha_submit);
//        gridView = (GridView) findViewById(R.id.gridview);
        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        img_3 = (ImageView) findViewById(R.id.img_3);
        img_4 = (ImageView) findViewById(R.id.img_4);
        img_5 = (ImageView) findViewById(R.id.img_5);
        img_6 = (ImageView) findViewById(R.id.img_6);

    }
}
