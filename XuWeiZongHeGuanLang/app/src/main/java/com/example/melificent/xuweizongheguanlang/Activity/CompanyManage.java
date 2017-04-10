package com.example.melificent.xuweizongheguanlang.Activity;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.melificent.xuweizongheguanlang.DataBase.CompanyRecord;
import com.example.melificent.xuweizongheguanlang.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by p on 2017/2/13.
 * Manage Company for Add ,Query ,Modify and something else need
 */

public class CompanyManage extends AppCompatActivity {
    @InjectView(R.id.company_management_input)
    EditText add_input;
    @InjectView(R.id.company_search_input)
    EditText search_input;
    @InjectView(R.id.add)
    Button add;
    @InjectView(R.id.company_search)
    Button search;
    @InjectView(R.id.company_listview)
    ListView listview;
    SQLiteOpenHelper helper;
    SQLiteDatabase db;
    BaseAdapter adapter;
    PopupWindow popwindows;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_company_management);
        ButterKnife.inject(this);
        initDataBase();
        AddCompany();
        QueryCompany();
//        setListviewlongclickListener();
    }

    private void setListviewlongclickListener() {
        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, final long id) {
                Log.i("itemid",id+"");
                final AlertDialog.Builder builder = new AlertDialog.Builder(CompanyManage.this);
                builder.setTitle("企业信息编辑");
                builder.setCancelable(false);
                builder.setPositiveButton("编辑", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        View view1 = CompanyManage.this.getLayoutInflater().inflate(R.layout.company_modify_view,null);
                        popwindows = new PopupWindow(view1, LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.MATCH_PARENT,true);
                        popwindows.setAnimationStyle(R.style.AnimationFade);
                        popwindows.showAtLocation(view,Gravity.RIGHT,0,0);
                        Button disappear = (Button) view1.findViewById(R.id.company_modify_disappear);
                        Button modify = (Button) view1.findViewById(R.id.company_modify_btn);
                        EditText input = (EditText) view1.findViewById(R.id.company_modify_input);
                        final  String s1 = input.getText().toString();
                        disappear.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (popwindows!= null&& popwindows.isShowing()){
                                    popwindows.dismiss();
                                    popwindows = null;
                                }
                            }
                        });
                        modify.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ModifyCompany(s1,id+"");
                                if (popwindows!= null&& popwindows.isShowing()){
                                    popwindows.dismiss();
                                    popwindows = null;
                                }
                            }
                        });

                    }
                });
                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();

             return true;
            }
        });
    }



    private void QueryCompany() {
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasCompany(search_input.getText().toString())){
                    queryCompany(search_input.getText().toString());
                }else {
                    Toast.makeText(CompanyManage.this,"no company query",Toast.LENGTH_LONG).show();
                }

            }
        });
        search_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length()>0){
                    queryCompany(s.toString());
                }
            }
        });
    }

    private void clearDatabase() {
        db= helper.getWritableDatabase();
        db.execSQL("delete from company");
        db.close();
    }

    private void AddCompany() {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s = add_input.getText().toString().trim();
                if (!hasCompany(s)){
                    addCompany(s);
//                    Toast.makeText(CompanyManage.this,"add company sucess!",Toast.LENGTH_LONG).show();
                    add_input.setText("");
                }else {
                    Toast.makeText(CompanyManage.this,"has company already",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void queryCompany(String s) {
        Cursor c = helper.getReadableDatabase().rawQuery(
           "select id as _id,name from company where name like '%"+s+"%'order by id desc",null
        ) ;
        adapter = new SimpleCursorAdapter(
                this,android.R.layout.simple_list_item_1,c,new String[]{"name"},
                new int[]{android.R.id.text1}, CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );
        listview.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void addCompany(String s) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into company (name) values('"+s+"')");
        db.close();
    }
    private boolean hasCompany(String s){
        Cursor c = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from company where name =?",new String[]{s}
        );
        return c.moveToNext();
    }

    private void initDataBase() {
        helper = new CompanyRecord(this);
    }

    private void ModifyCompany(String s1,String s2) {
        db = helper.getWritableDatabase();
//        db.execSQL("update company set name = ? where id = ?",new String[]{
//         s1,s2
//        });
        ContentValues values = new ContentValues();
        values.put("name",s1);
        db.update("company",values,"id = ?",new String[]{s2});
        db.close();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }
}
