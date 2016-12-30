package com.example.melificent.demoforkunming.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.melificent.demoforkunming.Activity.TestActivity;
import com.example.melificent.demoforkunming.R;
import com.example.melificent.demoforkunming.Spinner.Infoentity;
import com.example.melificent.demoforkunming.Spinner.JiedianEntity;
import com.example.melificent.demoforkunming.Spinner.SpinnerAdapterForJiedian;
import com.example.melificent.demoforkunming.Spinner.SpinnerDataAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by p on 2016/10/30.
 * 具体实现思路：
 * 1.通过构建实体数据库，来模拟存放数据
 * 2.通过每一个spinner的点击监听，来进行相应操作的判断
 * 3.通过书库据的查询匹配，来达到查询的效果
 * 4.查询到的数据构建实体类，在相应的listview上面显示
 */

public class SearchFragmnet extends Fragment {
    Spinner spinner_choose;
    Spinner spinner_jiedian;
    Spinner spinner_guanxian;
    Spinner spinner_shebei;
    Button button_search;
    Button back;
    EditText et_input_term;
    ListView lv_jiedian;
    ListView lv_guanxian;
    ListView lv_shebei;
    SpinnerDataAdapter chooseAdapter;
    SpinnerAdapterForJiedian jiedianAdapter;
    SpinnerAdapterForJiedian guanxianAdapter;
    SpinnerAdapterForJiedian shebieAdapter;
    MyDataBaseHelper helper;
    AdapterShebei shebeiAdapter;
    AdapterShebei1 shebeiAdapter1;
    AdapterForJiedian jiedianAdapter1;
    AdapterForGuanxian guanxianAdapter1;
    LinearLayout ll_1;
    LinearLayout ll_2;
    LinearLayout ll_3;
    FrameLayout frameLayout;
    SQLiteDatabase db;
    Boolean isFirstClick = true;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view  = inflater.inflate(R.layout.search_fragment,null);
        //初始化控件的过程
        spinner_choose = (Spinner) view.findViewById(R.id.spinner_search);
        spinner_guanxian = (Spinner) view.findViewById(R.id.spinner_guanxian);
        spinner_jiedian = (Spinner) view.findViewById(R.id.spinner_jiedian);
        spinner_shebei = (Spinner) view.findViewById(R.id.spinner_shebei);
        button_search = (Button) view.findViewById(R.id.button_search);
        back = (Button) view.findViewById(R.id.back_to_position_in_map);
        et_input_term  = (EditText) view.findViewById(R.id.et_term);
        lv_guanxian = (ListView) view.findViewById(R.id.listview_guanxian);
        lv_jiedian = (ListView) view.findViewById(R.id.listview_jiedian);
        lv_shebei = (ListView) view.findViewById(R.id.listview_shebei);
        ll_1 = (LinearLayout) view.findViewById(R.id.ll_1);
        ll_2 = (LinearLayout) view.findViewById(R.id.ll_2);
        ll_3 = (LinearLayout) view.findViewById(R.id.ll_3);
        frameLayout = (FrameLayout) view.findViewById(R.id.framelayout);
        //为spinner设置adapter
        setAdapterForSpinner();
        //为spinner设置监听器
        setSpinnerListener();
        //新建名称为search的数据库
        helper = new MyDataBaseHelper(getActivity(),"search",null,1);
        //获得可写数据库，（如果没有那么就会新建）
        db = helper.getWritableDatabase();
        //初始化话数据库数据
        initDataBaseData();

        setButtonClickListener();

        return view;
    }

    private void setButtonClickListener() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent = new Intent(getActivity(),TestActivity.class);
                intent.putExtra("lat",25.202055469989023);
                intent.putExtra("lng",102.94531734452889);
                startActivity(intent);
            }
        });
    }

    //自定义MyDataBaseHelper，新建数据表，重写oncreate，和相关的spl语句
    class   MyDataBaseHelper extends  SQLiteOpenHelper{
        private  Context mcontext;
        public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
            mcontext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_SEARCH);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        //新建树表名称为search
       public static final String CREATE_SEARCH = "create table search("
                +"id integer primary key autoincrement,"
               +"weizhi text,"
               +"weixiujiluyi text,"
               +"weixiujiluer text,"
               +"weixiujilusan text,"
               +"shiyongnianxian text,"
                +"jiedianmingcheng text,"
                +"jiedianweizhi text,"
                +"jiediansheshi text,"
                +"leixing text,"
                +"caizhi text,"
                +"guanjing text,"
                +"quanshudanwei text,"
                +"shebeimingcheng text,"
                +"shebeixinghao text"+")";
    }

//为数据库初始化数据
    private void initDataBaseData() {

        ContentValues values = new ContentValues();
        values.put("jiedianmingcheng","s001");
        values.put("jiedianweizhi","(23.01,122.1)");
        values.put("jiediansheshi","灭火器");
        values.put("leixing","rs231");
        values.put("caizhi","Cu");
        values.put("guanjing","0.8");
        values.put("quanshudanwei","消防");
        values.put("shebeimingcheng","探测仪");
        values.put("shebeixinghao","ss-wr-001");
        values.put("weizhi","1号门东");
        values.put("weixiujiluyi","维护雷达，21/8/2016");
        values.put("weixiujiluer","悬挂架更换，22/9/2016");
        values.put("weixiujilusan","探头更换，23/10/2016");
        values.put("shiyongnianxian","3年");
         db.insert("search",null,values);
        values.clear();
        values.put("jiedianmingcheng","s002");
        values.put("jiedianweizhi","(23.02,122.1)");
        values.put("jiediansheshi","控制器");
        values.put("leixing","rs232");
        values.put("caizhi","Fe");
        values.put("guanjing","0.9");
        values.put("quanshudanwei","应急");
        values.put("shebeimingcheng","监控器");
        values.put("shebeixinghao","ss-wr-002");
        values.put("weizhi","2号门南");
        values.put("weixiujiluyi","摄像头清洗，21/6/2016");
        values.put("weixiujiluer","软件升级，22/7/2016");
        values.put("weixiujilusan","探头更换，23/8/2016");
        values.put("shiyongnianxian","4年");
       db.insert("search",null,values);
        values.clear();
        values.put("jiedianmingcheng","s003");
        values.put("jiedianweizhi","(23.03,122.3)");
        values.put("jiediansheshi","感应器");
        values.put("leixing","rs233");
        values.put("caizhi","Al");
        values.put("guanjing","1.0");
        values.put("quanshudanwei","后勤");
        values.put("shebeimingcheng","感应探头");
        values.put("shebeixinghao","ss-wr-003");
        values.put("weizhi","3号门西");
        values.put("weixiujiluyi","传感器更换，21/3/2016");
        values.put("weixiujiluer","报警升级，22/4/2016");
        values.put("weixiujilusan","探头维护，23/5/2016");
        values.put("shiyongnianxian","2年");
        db.insert("search",null,values);
        values.clear();
        values.put("jiedianmingcheng","s004");
        values.put("jiedianweizhi","(23.04,122.4)");
        values.put("jiediansheshi","通风口");
        values.put("leixing","rs234");
        values.put("caizhi","Au");
        values.put("guanjing","1.2");
        values.put("quanshudanwei","控制");
        values.put("shebeimingcheng","换气扇");
        values.put("shebeixinghao","ss-wr-004");
        values.put("weizhi","4号门北");
        values.put("weixiujiluyi","电机修理，21/10/2015");
        values.put("weixiujiluer","通风管更换，22/11/2015");
        values.put("weixiujilusan","电源线修理，23/12/2015");
        values.put("shiyongnianxian","2年");
        db.insert("search",null,values);



    }

    //为对应的spinner设置监听器，spinner设置setOnItemSelectedListener，并且重写其中的onItemSelected方法
    private void setSpinnerListener() {
        spinner_choose.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //选择相应的查询大项
                OnItemSelect(position);
        }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_shebei.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //设备相关属性选择
                spinner_shebeiOnItemClick(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_jiedian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //节点相关条件选择
                spinner_jiedianOnItemClickListener(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_guanxian.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               //管线相关条件选择
                spinner_guanxianOnItemClick(position);
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void spinner_guanxianOnItemClick(int position) {
        switch(position){
            case 0:
                et_input_term.setText("");
                spinner_shebei.setSelection(0);
                spinner_jiedian.setSelection(0);
                break;
            case 1:
                et_input_term.setText("rs231");
//                et_input_term.setText("");
                spinner_shebei.setSelection(0);
                spinner_jiedian.setSelection(0);
                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shebeiSearchByType();
                    }
                });
                break;
            case 2:
                spinner_shebei.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("Al");
//                et_input_term.setText("");

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shebeiSearchByMeterial();
                    }
                });
                break;
            case 3:
                spinner_shebei.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("1.2");
//                et_input_term.setText("");

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shebeiSearchByGuanjing();
                    }
                });
                break;
            case 4:
                spinner_shebei.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("消防");
//                et_input_term.setText("");

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        shebeiSearchByQuanshudanwei();
                    }
                });
                break;

        }
    }
    //通过管线的权属单位查询
    private void shebeiSearchByQuanshudanwei() {
        String table = "search";
        String[] columns = {
                "quanshudanwei"  ,"leixing","caizhi","guanjing"
        };

        String[]whereArgs= null;
        //通关查询search数据表中的相关属性，调用数据库的query方法
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        //获得输入框的内容
        String name = et_input_term.getText().toString().trim();
        //通过输入框的内容来进行switch case 的判断，当条件匹配时，通过构建实体类的方式，来给
        //相应的list view来进行内容的显示工作，并且同时来屏蔽无关的list（通过set visibility（））
        switch (name){
            case "消防":
                if (c.moveToPosition(0)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),c.getString(3),name));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "应急":
                if (c.moveToPosition(2)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),c.getString(3),name));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "后勤":
                if (c.moveToPosition(3)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),c.getString(3),name));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "控制":
                if (c.moveToPosition(4)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),c.getString(3),name));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;

        }
        lv_guanxian.setAdapter(guanxianAdapter1);
        lv_shebei.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.VISIBLE);
        c.close();

    }
    //通过管线的管径查询
    private void shebeiSearchByGuanjing() {
        String table = "search";
        String[] columns = {
                "guanjing", "leixing","caizhi","quanshudanwei"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        String name = et_input_term.getText().toString().trim();
        switch (name){
            case "0.8":
                if (c.moveToPosition(0)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),name,c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "0.9":
                if (c.moveToPosition(2)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),name,c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "1.0":
                if (c.moveToPosition(3)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),name,c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "1.2":
                if (c.moveToPosition(4)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),c.getString(2),name,c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;

        }
        lv_guanxian.setAdapter(guanxianAdapter1);
        frameLayout.setVisibility(View.GONE);
        lv_jiedian.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.VISIBLE);
        c.close();

    }
    //通过管线的材质查询
    private void shebeiSearchByMeterial() {
        String table = "search";
        String[] columns = {
                "caizhi",  "leixing","guanjing","quanshudanwei"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        String name = et_input_term.getText().toString().trim();
        switch (name){
            case "Cu":
                if (c.moveToPosition(0)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),name,c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "Fe":
                if (c.moveToPosition(2)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),name,c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "Al":
                if (c.moveToPosition(3)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),name,c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "Au":
                if (c.moveToPosition(4)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(c.getString(1),name,c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;

        }
        lv_guanxian.setAdapter(guanxianAdapter1);
       lv_guanxian.setVisibility(View.GONE);
       frameLayout.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.VISIBLE);
        c.close();

    }
    //通过管线的类型查询
    private void shebeiSearchByType() {
        String table = "search";
        String[] columns = {
               "leixing","caizhi","guanjing","quanshudanwei"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        String name = et_input_term.getText().toString().trim();
        switch (name){
            case "rs231":
                if (c.moveToPosition(0)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(name,c.getString(1),c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "rs232":
                if (c.moveToPosition(2)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(name,c.getString(1),c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "rs233":
                if (c.moveToPosition(3)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(name,c.getString(1),c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;
            case "rs234":
                if (c.moveToPosition(4)){
                    List<guanxianentity> guanxianentities  = new ArrayList<>();
                    guanxianentities.add(new guanxianentity(name,c.getString(1),c.getString(2),c.getString(3)));
                    guanxianAdapter1 = new AdapterForGuanxian(getActivity(),guanxianentities);
                }
                break;

        }
        lv_guanxian.setAdapter(guanxianAdapter1);
        lv_shebei.setVisibility(View.GONE);
        frameLayout.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.VISIBLE);
        c.close();

    }

    private void spinner_shebeiOnItemClick(int position) {
        switch (position){
            case 0:
                spinner_guanxian.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("");
                break;
            case 1:
                spinner_guanxian.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("探测仪");
//                et_input_term.setText("");

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchByName();
                    }
                });
                break;
            case 2:
                spinner_guanxian.setSelection(0);
                spinner_jiedian.setSelection(0);
                et_input_term.setText("ss-wr-002");
//                et_input_term.setText("");

                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SearchByType();
                    }
                });
                break;
        }



    }

    private void spinner_jiedianOnItemClickListener(int position) {
        switch (position){
            case 0 :
                spinner_guanxian.setSelection(0);
                spinner_shebei.setSelection(0);
                et_input_term.setText("");
                break;
            case 1:
                spinner_guanxian.setSelection(0);
                spinner_shebei.setSelection(0);
                et_input_term.setText("");
                button_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                            SearchByJiedianName();



                    }
                });


        }
    }
    //通过节点的名称来查询
    private void SearchByJiedianName() {
        String table = "search";
        String[] columns = {
                "jiedianmingcheng","jiedianweizhi","jiediansheshi"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        String name = et_input_term.getText().toString().trim();
        switch (name){
            case "s001":
                if (c.moveToPosition(0)){
                    List<jiedianentity>jiedianentities=new ArrayList<>();

                     jiedianentities.add(new jiedianentity(name,c.getString(1),c.getString(2),BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.zhaomingbaojing_1)));
                    jiedianAdapter1 = new AdapterForJiedian(jiedianentities,getActivity());
                }
                break;
            case "s002":
                if (c.moveToPosition(2)){
                    List<jiedianentity>jiedianentities=new ArrayList<>();

                    jiedianentities.add(new jiedianentity(name,c.getString(1),c.getString(2),BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.b)));
                    jiedianAdapter1 = new AdapterForJiedian(jiedianentities,getActivity());
                }
                break;
            case "s003":
                if (c.moveToPosition(3)){
                    List<jiedianentity>jiedianentities=new ArrayList<>();

                    jiedianentities.add(new jiedianentity(name,c.getString(1),c.getString(2),BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.c)));
                    jiedianAdapter1 = new AdapterForJiedian(jiedianentities,getActivity());
                }
                break;
            case "s004":
                if (c.moveToPosition(4)){
                    List<jiedianentity>jiedianentities=new ArrayList<>();

                    jiedianentities.add(new jiedianentity(name,c.getString(1),c.getString(2),BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.d)));
                    jiedianAdapter1 = new AdapterForJiedian(jiedianentities,getActivity());
                }
                break;



        }
        lv_jiedian.setAdapter(jiedianAdapter1);
        lv_shebei.setVisibility(View.GONE);
        frameLayout.setVisibility(View.VISIBLE);
        lv_guanxian.setVisibility(View.GONE);
        c.close();
    }

    //通过设备的类型来查询
    private void SearchByType() {
        String table = "search";
        String[] columns = {
                "shebeixinghao","shebeimingcheng",
                "weizhi",
                "weixiujiluyi","weixiujiluer",
                "weixiujilusan","shiyongnianxian"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
        String name = et_input_term.getText().toString().trim();
        switch (name){
            case "ss-wr-001":
                if (c.moveToPosition(0)){
                    List<shebeientity> shebeientities1 = new ArrayList<>();
                    shebeientities1.add(new shebeientity(c.getString(1),name,
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter1 = new AdapterShebei1(getActivity(),shebeientities1);
                }
                break;
            case  "ss-wr-002":
                if (c.moveToPosition(1)){
                    List<shebeientity> shebeientities = new ArrayList<>();
                    shebeientities.add(new shebeientity(c.getString(1),name,c.getString(2)
                    ,c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter1 = new AdapterShebei1(getActivity(),shebeientities);
                }
                break;
            case  "ss-wr-003":
                if (c.moveToPosition(2)){
                    List<shebeientity> shebeientities = new ArrayList<>();
                    shebeientities.add(new shebeientity(c.getString(1),name,
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter1 = new AdapterShebei1(getActivity(),shebeientities);

                }
                break;
            case  "ss-wr-004":
                if (c.moveToPosition(3)){
                    List<shebeientity> shebeientities = new ArrayList<>();
                    shebeientities.add(new shebeientity(c.getString(1),name,
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter1 = new AdapterShebei1(getActivity(),shebeientities);

                }
                break;
        }
        lv_shebei.setAdapter(shebeiAdapter1);
        lv_shebei.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.GONE);
        c.close();
    }

    //通过设备的名称来查询
    private void SearchByName() {
//        SQLiteDatabase db = helper.getWritableDatabase();
        String table = "search";
        String[] columns = {
                "shebeixinghao","shebeimingcheng",
                "weizhi",
                "weixiujiluyi","weixiujiluer",
                "weixiujilusan","shiyongnianxian"
        };

        String[]whereArgs= null;
        Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
       String name = et_input_term.getText().toString().trim();
        switch (name){
            case "探测仪":
                if (c.moveToPosition(0)){
                    List<shebeientity> shebeientities1 = new ArrayList<>();
                    shebeientities1.add(new shebeientity(name,c.getString(0),
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter = new AdapterShebei(getActivity(),shebeientities1);
                }
                break;
            case "监控器":
                if (c.moveToPosition(1)){

                    List<shebeientity> shebeientities1 = new ArrayList<>();
                    shebeientities1.add(new shebeientity(name,c.getString(0),
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter = new AdapterShebei(getActivity(),shebeientities1);
                }
                break;
            case "感应探头":
                if (c.moveToPosition(2)){

                    List<shebeientity> shebeientities1 = new ArrayList<>();
                    shebeientities1.add(new shebeientity(name,c.getString(0),
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter = new AdapterShebei(getActivity(),shebeientities1);
                }
                break;
            case "换气扇":
                if (c.moveToPosition(3)){

                    List<shebeientity> shebeientities1 = new ArrayList<>();
                    shebeientities1.add(new shebeientity(name,c.getString(0),
                            c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6)));
                    shebeiAdapter = new AdapterShebei(getActivity(),shebeientities1);
                }
                break;



        }


        lv_shebei.setAdapter(shebeiAdapter);
        lv_shebei.setVisibility(View.VISIBLE);
        frameLayout.setVisibility(View.GONE);
        lv_guanxian.setVisibility(View.GONE);
        c.close();




    }

    private void OnItemSelect(int position) {
        switch (position){
            case 0:
                et_input_term.setText("");
                ll_1.setVisibility(View.GONE);
                ll_2.setVisibility(View.GONE);
                ll_2.setVisibility(View.GONE);
                break;
            case 1:
                et_input_term.setText("");

                ll_1.setVisibility(View.VISIBLE);
                ll_2.setVisibility(View.GONE);
                ll_3.setVisibility(View.GONE);
                break;
            case 2:
                et_input_term.setText("");
                ll_1.setVisibility(View.GONE);
                ll_3.setVisibility(View.GONE);
                ll_2.setVisibility(View.VISIBLE);
                break;
            case  3:
                et_input_term.setText("");
                ll_1.setVisibility(View.GONE);
                ll_3.setVisibility(View.VISIBLE);
                ll_2.setVisibility(View.GONE);
                break;

        }
    }

    private void setAdapterForSpinner() {
        //选择查询类别spinnerAdapter
        List<Infoentity> infos  = new ArrayList<>();
        infos.add(new Infoentity("类型",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.leixing)));
        infos.add(new Infoentity("节点",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.jiedian)));
        infos.add(new Infoentity("管线",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.guanxian)));
        infos.add(new Infoentity("设备",BitmapFactory.decodeResource(getActivity().getResources(),R.drawable.equipment)));
        chooseAdapter = new SpinnerDataAdapter(getActivity(),infos);
        spinner_choose.setAdapter(chooseAdapter);
        //节点spinnerAdapter
        List<JiedianEntity> jiedians = new ArrayList<>();
        jiedians.add(new JiedianEntity("查询条件"));
        jiedians.add(new JiedianEntity("节点名称"));
        jiedianAdapter  = new SpinnerAdapterForJiedian(jiedians,getActivity());
        spinner_jiedian.setAdapter(jiedianAdapter);
        //管线Adapter
        List<JiedianEntity> guanxians = new ArrayList<>();
        guanxians.add(new JiedianEntity("查询条件"));
        guanxians.add(new JiedianEntity("类别"));
        guanxians.add(new JiedianEntity("材质"));
        guanxians.add(new JiedianEntity("管径"));
        guanxians.add(new JiedianEntity("权属单位"));
        guanxianAdapter  = new SpinnerAdapterForJiedian(guanxians,getActivity());
       spinner_guanxian.setAdapter(guanxianAdapter);
        //设备Adapter
        List<JiedianEntity> shebeis = new ArrayList<>();
        shebeis.add(new JiedianEntity("查询条件"));
        shebeis.add(new JiedianEntity("设备名称"));
        shebeis.add(new JiedianEntity("设备型号"));
        shebieAdapter = new SpinnerAdapterForJiedian(shebeis,getActivity());
        spinner_shebei .setAdapter(shebieAdapter);





    }

}
