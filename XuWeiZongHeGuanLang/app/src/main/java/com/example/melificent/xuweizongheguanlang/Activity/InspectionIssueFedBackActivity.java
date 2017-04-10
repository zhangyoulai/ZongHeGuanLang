package com.example.melificent.xuweizongheguanlang.Activity;

import android.Manifest;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.example.melificent.xuweizongheguanlang.Adapter.IssueGridViewAdapter;
import com.example.melificent.xuweizongheguanlang.Bean.IssueGridViewBean;
import com.example.melificent.xuweizongheguanlang.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * this activity is used to submit the issue to server
 * on the top is back button and the submit button ,Respectively use to go back and submit.
 * then there is a filed to give description for the issue .you can give the most issue here
 * then you can clicked add button to select whether you take a photo by camera or select a photo from album.
 * if you choose take a photo ,you will open camera ,and take a photo ,then will enter  crop photo,
 * you will get a photo as you like after you crop,then set the now imageview and the next will be a new add button until the last one set image.
 * as the the same you can choose a photo from album as you like ,but in here you can not crop the photo , and will set the image to now and the next will be add util the last one set.
 * in here we must to take ways to avoid Out Of Memory ,so we must dispose the bitmap before we set.
 * In this process we use OnActivityResult() method to handle different "Request Code"request and give the different method to handle;
 * On the choose photo part we use onRequestPermissionResult() method to confirm the permission,if you not have ,will be a Toast ;
 * at last get bitmap will have different ways ,in here use 2 ways are BitmapFactory.decodeFile() and Bitmap.decodeResource().
 * that is all .
 */
public class InspectionIssueFedBackActivity extends AppCompatActivity {
    @InjectView(R.id.img1)
    ImageView img1;
    @InjectView(R.id.img2)
    ImageView img2;
    @InjectView(R.id.img3)
    ImageView img3;
    @InjectView(R.id.img4)
    ImageView img4;
    @InjectView(R.id.img5)
    ImageView img5;
    @InjectView(R.id.img6)
    ImageView img6;
    @InjectView(R.id.img7)
    ImageView img7;
    @InjectView(R.id.img8)
    ImageView img8;
    @InjectView(R.id.img9)
    ImageView img9;
    @InjectView(R.id.img10)
    ImageView img10;
    @InjectView(R.id.img11)
    ImageView img11;
    @InjectView(R.id.img12)
    ImageView img12;

    List<Bitmap> bitmapList = new ArrayList<>();
    List<ImageView> imageViewList = new ArrayList<>();
    Uri imageuri;
    Bitmap bitmap;
    Uri chooseUri;

    public static final int TAKE_PHOTO = 1;
    public static final int CROP_PHOTO = 2;
    public static final int CHOOSE_PHOTO = 3;

    Boolean isFirstSet = true;
    int count = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inspection_issue_fed_back);
        ButterKnife.inject(this);
        setImageForImageView();
        initImageviewList();

    }

    private void initImageviewList() {
        imageViewList.add(img1);
        imageViewList.add(img2);
        imageViewList.add(img3);
        imageViewList.add(img4);
        imageViewList.add(img5);
        imageViewList.add(img6);
        imageViewList.add(img7);
        imageViewList.add(img8);
        imageViewList.add(img9);
        imageViewList.add(img10);
        imageViewList.add(img11);
        imageViewList.add(img12);

    }

    private void setImageForImageView() {

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             initPopwindows(v);
            }
        });

        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               initPopwindows(v);
            }
        });

        img3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });

        img7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });
        img12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initPopwindows(v);
            }
        });

            }




        private void initPopwindows(View v){
            View view = LayoutInflater.from(this).inflate(R.layout.inspection_issue_fed_back_pop,null);
            final PopupWindow pop = new PopupWindow(view ,LinearLayoutCompat.LayoutParams.MATCH_PARENT,LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
            pop.setAnimationStyle(R.style.MyPopupWindowFade);
            pop.showAtLocation(v,Gravity.RIGHT,0,0);
            TextView issue_camera = (TextView) view.findViewById(R.id.issue_camera);
            TextView issue_photo_gallery = (TextView) view.findViewById(R.id.issue_photograph);
            issue_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    File outputImage  = new File(Environment.getExternalStorageDirectory(),"tempimage.jpg");

                    try {
                        if (outputImage.exists()){
                        outputImage.delete();
                    }
                        outputImage.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageuri = Uri.fromFile(outputImage);
                    Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                    intent.putExtra(MediaStore.EXTRA_OUTPUT ,imageuri);
                    startActivityForResult(intent, TAKE_PHOTO);
                    pop.dismiss();
                }
            });
            issue_photo_gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   if(ContextCompat.checkSelfPermission(InspectionIssueFedBackActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                       ActivityCompat.requestPermissions(InspectionIssueFedBackActivity.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

                   }else {
                       openAlbum();
                   }
        }
});
        }

    private void openAlbum() {
        Intent intent  =new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:
                if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openAlbum();
                }else {
                    Toast.makeText(this, "you need to denied the permission", Toast.LENGTH_SHORT).show();
                }
        }
    }

    ;
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
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK){
                    if (Build.VERSION.SDK_INT >= 19 ){
                        handleImageOnKitKat(data);
                    }else {
                        handleImageBeforeKitKat(data);
                    }
                }
            case CROP_PHOTO :
                if (resultCode == RESULT_OK){
                    try {
                        bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageuri));
                        Bitmap bitmap_choose = BitmapFactory.decodeStream(getContentResolver().openInputStream(chooseUri));
                        img1.setImageBitmap(bitmap_choose);
                        if (isFirstSet){
                            bitmapList.add(bitmap);
                            imageViewList.get(0).setImageBitmap(bitmap);
                            count ++;
                            bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                            imageViewList.get(bitmapList.size()-count).setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                            count++;
                            isFirstSet = false;
                        }else if (count == 12){
                            bitmapList.add(bitmap);
                            imageViewList.get(bitmapList.size()-count).setImageBitmap(bitmap);
                        }
                        else {
                            bitmapList.add(bitmap);
                            imageViewList.get(bitmapList.size()-count).setImageBitmap(bitmap);
                            bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                            imageViewList.get(bitmapList.size()-count).setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                            count ++;
                        }


                        if (bitmapList.size()>= 23){
                            Toast.makeText(this, "over the index", Toast.LENGTH_SHORT).show();
                            break;
                        }
                        Log.i("countValue", "countValue: "+count);

                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }

    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath (MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else  if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }else if("content".equalsIgnoreCase(uri.getScheme())){
                imagePath = getImagePath(uri,null);
            }else if ("file".equalsIgnoreCase(uri.getScheme())){
                imagePath = uri.getPath();
            }
            displayImage(imagePath);
        }

    }

    private void displayImage(String imagePath) {
        if (imagePath != null){
            Bitmap bitmap  = BitmapFactory.decodeFile(imagePath);
            if (isFirstSet){
                bitmapList.add(bitmap);
                imageViewList.get(0).setImageBitmap(bitmap);
                count ++;
                bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                imageViewList.get(bitmapList.size()-count).setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                count++;
                isFirstSet = false;
            }else if (count == 12){
                bitmapList.add(bitmap);
                imageViewList.get(bitmapList.size()-count).setImageBitmap(bitmap);
            }
            else {
                bitmapList.add(bitmap);
                imageViewList.get(bitmapList.size()-count).setImageBitmap(bitmap);
                bitmapList.add(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                imageViewList.get(bitmapList.size()-count).setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.addpic_1));
                count ++;
            }
        }
    }

    private String getImagePath(Uri externalContentUri, String selection) {
        String path = null;
        Cursor c = getContentResolver().query(externalContentUri,null,selection,null,null);
        if (c.moveToFirst()){
            path= c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA));
        }
        c.close();
        return  path;
    }


}
