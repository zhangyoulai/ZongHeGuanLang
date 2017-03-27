package com.example.melificent.myqianqi.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.melificent.myqianqi.Bean.VehicleViolationBean.VehicleViolationResult;
import com.example.melificent.myqianqi.LocalDataBase.DrivingDataBase;
import com.example.melificent.myqianqi.Presenter.IGetVehicleViolationInfoPresenter;
import com.example.melificent.myqianqi.Presenter.Impl.IGetVehicleViolationInfoPresenterImpl;
import com.example.melificent.myqianqi.R;
import com.example.melificent.myqianqi.View.GetVehicleViolationInfo;

import java.io.Serializable;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/3/23.
 */

public class VehicleViolationQueryActivity extends AppCompatActivity implements GetVehicleViolationInfo {
    @InjectView(R.id.vehicleviolation_carNo)
    EditText carNo;
    @InjectView(R.id.vehicleviolation_frameNo)
    EditText frameNo;
    @InjectView(R.id.vehicleviolation_engineNo)
    EditText engineNo;
    @InjectView(R.id.vehicleviolation_query)
    Button query;
    @InjectView(R.id.vehicleviolationgoback)
    ImageView back;
    IGetVehicleViolationInfoPresenter presenter = new IGetVehicleViolationInfoPresenterImpl(this);
    String RealNeedEnginNo;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vehicleviolationquery);
        ButterKnife.inject(this);
        helper= new DrivingDataBase(this);
        setButtonListener();
    }

    private void setButtonListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (carNo.getText().toString().trim() != null && frameNo.getText().toString() !=null && engineNo.getText().toString().trim() != null){
                    String CarNo =carNo.getText().toString().trim();
                    String FrameNo = frameNo.getText().toString().trim();
                    if (FrameNo.length()!= 6){
                        Toast.makeText(VehicleViolationQueryActivity.this, "输入车架号位数错误", Toast.LENGTH_SHORT).show();
                    }
                    if (CarNo.startsWith("宁A")){
                        String EnginNo = engineNo.getText().toString().trim();
                        RealNeedEnginNo = EnginNo.substring(2);
                    }else if (CarNo.startsWith("蒙K")){
                        RealNeedEnginNo = engineNo.getText().toString().trim();
                    }
                    Log.i("RealNeedEnginNo", "RealNeedEnginNo: "+RealNeedEnginNo);
                    presenter.IGetVehicleViolationInfoPresenter(FrameNo,RealNeedEnginNo,CarNo);
                }else {
                    Toast.makeText(VehicleViolationQueryActivity.this, "信息不能为空!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void getVehicleViolationInfoSucess(List<VehicleViolationResult> results) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into drive(carNo,frameNo,engineNo) values(?,?,?)",new String[]{
                carNo.getText().toString().trim(),frameNo.getText().toString().trim(),engineNo.getText().toString().trim()
        });
        db.close();

        Log.i("listSize", "size: "+results.size());
        Intent intent = new Intent(VehicleViolationQueryActivity.this,VehicleViolationResultActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("vehicleviolation", (Serializable) results);
        intent.putExtras(b);
        startActivity(intent);

        finish();
    }

    @Override
    public void getVehicleViolationInfoError(String Msg) {
        Toast.makeText(this, "查询失败，请确认后重试！", Toast.LENGTH_SHORT).show();
    }

    @Override

    public void NoVehicleViolationBehavior(String reason) {
    startActivity(new Intent(this,NoVehicleViolationActivity.class));
    }
}
