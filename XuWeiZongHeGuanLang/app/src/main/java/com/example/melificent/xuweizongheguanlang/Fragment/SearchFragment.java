package com.example.melificent.xuweizongheguanlang.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.melificent.xuweizongheguanlang.Activity.GasActivity;
import com.example.melificent.xuweizongheguanlang.Activity.TempretureActivity;
import com.example.melificent.xuweizongheguanlang.R;
import com.example.melificent.xuweizongheguanlang.SearchHistoryView.RecordSQLiteOpenHelper;
import com.example.melificent.xuweizongheguanlang.SearchHistoryView.SearchActivity;
import com.example.melificent.xuweizongheguanlang.SearchHistoryView.Search_Listview;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/1/12.
 */

public class SearchFragment extends Fragment {
    @InjectView(R.id.search_gas)
    Button gas;
    @InjectView(R.id.search_humidity)
    Button humidity;
    @InjectView(R.id.search_tempreture)
    Button tempreture;
    @InjectView(R.id.search_line)
    Button line;
    @InjectView(R.id.search_node)
    Button node;
    @InjectView(R.id.search_area)
    Button area;
    @InjectView(R.id.search_equipment)
    Button equipment;
    @InjectView(R.id.search_more)
    Button more;
    @InjectView(R.id.searchfragment_input)
    EditText search_input;
    @InjectView(R.id.tv_clear)
    TextView tv_clear;
    @InjectView(R.id.tv_tip)
    TextView tv_tip;
    @InjectView(R.id.listView)
    Search_Listview  listview;

    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    BaseAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.search_fragment,null);
        ButterKnife.inject(this,view);
        //实现搜索历史，搜索记忆功能
        init();

        setButtonListener();

        return view;
    }
    private void init(){
        //初始化数据库
        helper = new RecordSQLiteOpenHelper(getActivity());

        queryData("");
        tv_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete();
                queryData("");
            }
        });

        //监听文本框
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length()==0){
                    tv_tip.setText("搜索历史");
                }else {
                    tv_tip.setText("搜索结果");
                }
                //输入后查询数据库，进行模糊查询
                String tempname = search_input.getText().toString();
                queryData(tempname);
            }


        });

        //监听键盘点击回调
        search_input.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN){
                    boolean hasData = hasData(search_input.getText().toString().trim());
                    if (!hasData){
                        insertData(search_input.getText().toString());
                        queryData("");
                    }
                    //待实现的具体业务。。。。。。。

                }
                return false;
            }
        });

        //点击每一项传值到edittext
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textview = (TextView) view.findViewById(android.R.id.text1);
                String name = textview.getText().toString();
                search_input.setText(name);
            }
        });

    }

    private void insertData(String s) {
        db  = helper.getWritableDatabase();
        db.execSQL("insert into records (name) values ('"+s+"')");
        db.close();
    }

    private boolean hasData(String trim) {
        Cursor c = helper.getReadableDatabase().rawQuery(
          "select id as _id,name from records where name =?",new String[]{trim}
        );
        return c.moveToNext();
    }

    private void delete() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }

    private void queryData(String s) {
        Cursor c = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records  where name like '%"+s+"%'order by id desc",null
        );
        adapter = new SimpleCursorAdapter(
          getActivity(),android.R.layout.simple_list_item_1,c,new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void setEdittextListener() {
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length()>=0){
//                    startActivity(new Intent(getActivity(),SearchActivity.class));
                }
            }
        });
//        search_input.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getActivity(),SearchActivity.class));
//            }
//        });
    }

    private void setButtonListener() {
        gas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), GasActivity.class));
            }
        });
        tempreture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), TempretureActivity.class));
            }
        });
    }
}
