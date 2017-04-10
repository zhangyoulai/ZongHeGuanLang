package com.example.melificent.testforsearchhistory;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by p on 2017/2/7.
 */

public class Search_View extends LinearLayout {
    private Context context;
    /*
    UI组件
     */
    private EditText et_search;
    private TextView clear;
    private TextView text_tip;
    private Button search;

    /*
    列表及其适配器
     */
    private Search_Listview search_listview;
    private BaseAdapter adapter;

    /*
    数据库变量
     */
    private RecordDbOpenHelper helper;
    private SQLiteDatabase database;
    private String temp;


    public Search_View(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }

    public Search_View(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init();
    }

    public Search_View(Context context) {
        super(context);
        this.context = context;
        init();
    }
    private void init() {
        //初始化ui
        initViews();
        //数据库
        helper = new RecordDbOpenHelper(context);
        //第一次进入时查询所有的数据库
        queryData("");
        //按钮清空所有的历史搜索
        clear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
                queryData("");
            }
        });

        //监听文本输入框
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            if (s.toString().trim().length()==0){
                text_tip.setText("搜索历史");
            }else {
                text_tip.setText("搜索结果");
            }
                //每次输入后都会根据输入值来查询结果
                //输入结束后根据值来模糊查询
                 temp = et_search.getText().toString();
                queryData(temp);

            }
        });

        //输入框的键盘搜索回调
et_search.setOnKeyListener(new OnKeyListener() {
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN){
            boolean hasData = hasData(et_search.getText().toString().trim());
            if (!hasData){
                insertData(et_search.getText().toString().trim());
                queryData("");
            }
        }

        return false;
    }
});

        search_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view.findViewById(android.R.id.text1);
                String name = textView.getText().toString();
                et_search.setText(name);
                Toast.makeText(context,name,Toast.LENGTH_LONG).show();

            }
        });

        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean hasData = hasData(et_search.getText().toString().trim());
                if (!hasData){
                    insertData(et_search.getText().toString());
                    queryData("");
                }
            }
        });


    }

    private void insertData(String trim) {
        database = helper.getWritableDatabase();
        database.execSQL("insert into records (name) values ('"+ trim +"')");
        database.close();
    }

    private boolean hasData(String trim) {
        Cursor c  = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name = ?",new String[]{trim}
        );


        return c.moveToNext();
    }

    private void deleteData() {
        database = helper.getWritableDatabase();
        database.execSQL("delete from records");
        database.close();
    }

    private void queryData(String s) {
        Cursor c = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name like '%"+ s +"%'order by id desc",null
        );
        adapter = new SimpleCursorAdapter(context, android.R.layout.simple_list_item_1,c,new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        search_listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void initViews() {
        LayoutInflater.from(context).inflate(R.layout.search_layout,this);
        et_search = (EditText) findViewById(R.id.et_search);
        search = (Button) findViewById(R.id.search);
        text_tip = (TextView) findViewById(R.id.text_tip);
        clear = (TextView) findViewById(R.id.clear);
        search_listview = (Search_Listview) findViewById(R.id.listview);

    }
}
