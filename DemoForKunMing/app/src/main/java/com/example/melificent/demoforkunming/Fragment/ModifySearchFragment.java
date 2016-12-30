package com.example.melificent.demoforkunming.Fragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.melificent.demoforkunming.Adapter.Record;
import com.example.melificent.demoforkunming.Adapter.RecordAdapter;
import com.example.melificent.demoforkunming.DataBse.RecordSQLiteHelper;
import com.example.melificent.demoforkunming.R;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2016/12/19.
 */

public class ModifySearchFragment extends Fragment {
    ImageView gas,equipment,node,line,tempreture,more;
    ImageButton back;
    TextView clear;
    ListView listview;
    Button search;
    EditText search_content;
    RecordSQLiteHelper helper;
    SQLiteDatabase database;
    String searchContent;
    boolean isHasRecord = false;
    boolean isEmpity = true;

    List<String> tempList;
    List<Record> records;

    RecordAdapter recordAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.second_search_fragment,container,false);
        //初始化
        gas = (ImageView) view.findViewById(R.id.gas_search);
        equipment = (ImageView) view.findViewById(R.id.equipment_search);
        node= (ImageView) view.findViewById(R.id.node_search);
        line = (ImageView) view.findViewById(R.id.line_search);
        tempreture = (ImageView) view.findViewById(R.id.tempreture_search);
        more = (ImageView) view.findViewById(R.id.more_search);
        back = (ImageButton) view.findViewById(R.id.search_back_to_main);
        search_content = (EditText) view.findViewById(R.id.et_search_content);
        search = (Button) view.findViewById(R.id.secondsearch_button);
        listview = (ListView) view.findViewById(R.id.search_history_listview);
        clear = (TextView) view.findViewById(R.id.textview_clear_history);
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                judgeInputText();
//            }
//        });
        search_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                judgeInputText();
            }
        });
        //获取数据库
        helper = new RecordSQLiteHelper(getActivity());
        return view;
    }

    private void judgeInputText() {
        //获取输入框内容
        searchContent = search_content.getText().toString().trim();
        Log.i("search_Content",""+searchContent);
        search.setVisibility(View.VISIBLE);
        if(searchContent.length()>0){
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //待写查询模块
                }
            });
            saveSearchHistory();
        }else{
            //待写
            listview.setVisibility(View.INVISIBLE);
            Toast.makeText(getActivity(),"输入的内容不能为空!!!",Toast.LENGTH_SHORT).show();
        }
    }

    private void saveSearchHistory() {
        if (isEmpity){
            //获取输入框内容
            searchContent = search_content.getText().toString().trim();
            //第一次没有消息记录
            ContentValues values = new ContentValues();
            database.execSQL("insert into record (name) values('"+searchContent+"')");
            database.close();
            Log.i("insert values","insert values");

            isEmpity =false;
        }else {
            if (hasRecord(searchContent)){
                //获取输入框内容
                searchContent = search_content.getText().toString().trim();
                database = helper.getWritableDatabase();
                //保存记录
                database.execSQL("insert into record (name) values('"+searchContent+"')");
                database.close();
                //获取不包含当前记录的全部记录
                getAllRecordNotContainNow();
            }else{
                //不保存,获取全部记录
                getAllRecord();
            }

            //倒序显示
            displaySearchHistorybyListview();
            //显示清除框
            clear.setVisibility(View.VISIBLE);
            clear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //清除所有的记录
                    clearAllRecord();
                }
            });
        }

    }

    private void clearAllRecord() {
        database = helper.getWritableDatabase();
        database.execSQL("delete from record");
        database.close();
    }

    private void getAllRecordNotContainNow() {
        database = helper.getWritableDatabase();
         Cursor c = database.query("record",null,null,null,null,null,null);
        for (int i=0;i<c.getCount()-2;i++){
            records.add(new Record(c.getString(c.getColumnIndexOrThrow("name"))));
        }
        Log.i("records",""+records);
        database.close();
    }

    private void getAllRecord() {
        database = helper.getWritableDatabase();
        Cursor c = database.query("record",null,null,null,null,null,null);
        while (c.moveToNext()){
            String record = c.getString(c.getColumnIndexOrThrow("name"));
            records.add(new Record(record));
        }
        database.close();
    }

    private void displaySearchHistorybyListview() {

        for (int i = records.size()-1;i>=0;i--){
            tempList.add(records.get(i).record);
        }
        recordAdapter = new RecordAdapter(getActivity(),records);
        listview.setAdapter(recordAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                search_content.setText((CharSequence) parent.getSelectedItem());
            }
        });

    }

    private boolean hasRecord(String searchContent) {
        database = helper.getWritableDatabase();
        Cursor c = database.query("record",null,null,null,null,null,null);
        while (c.moveToNext()){
            String name = c.getString(c.getColumnIndexOrThrow("name"));
            if (name.equals(searchContent)){
                isHasRecord =true;
            }
        }
        database.close();
        return  isHasRecord;
    }


}
