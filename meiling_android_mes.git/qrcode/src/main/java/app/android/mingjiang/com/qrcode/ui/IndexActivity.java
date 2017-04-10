package app.android.mingjiang.com.qrcode.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import app.android.mingjiang.com.qrcode.R;

public class IndexActivity extends Activity implements View.OnClickListener {

    protected final static String TAG = IndexActivity.class.getSimpleName();
    protected String classes, classessToNum;
    Spinner spWorkstationNo, spProductClasses;
    Button btnSave, btnReturn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initView();
    }

    private void initView() {
        spWorkstationNo = (Spinner) findViewById(R.id.sp_workstation_no);
        spProductClasses = (Spinner) findViewById(R.id.sp_product_classes);
        btnSave = (Button) findViewById(R.id.btn_save);
        btnSave.setOnClickListener(this);
        btnReturn = (Button) findViewById(R.id.btn_return);
        btnReturn.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (v.getId() == R.id.btn_save) {
            saveSetting();
        } else if (v.getId() == R.id.btn_return) {
            finish();
        } else {

        }

    }

    private void saveSetting() {
        classes = spProductClasses.getSelectedItem().toString().trim();
        if (classes.contains("甲")) {
            classessToNum = "1";
        } else if (classes.contains("乙")) {
            classessToNum = "2";
        } else if (classes.contains("丙")) {
            classessToNum = "3";
        } else {
        }
        if (true) {

        } else {

        }
    }

}
