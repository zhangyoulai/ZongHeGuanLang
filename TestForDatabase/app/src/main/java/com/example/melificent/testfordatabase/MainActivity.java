package com.example.melificent.testfordatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static android.R.id.primary;

public class MainActivity extends AppCompatActivity {
    Button button;
    MyDatabaseHelper helper;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        textView  = (TextView) findViewById(R.id.textview);
        helper = new MyDatabaseHelper(this,"BookStore.db",null,1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SQLiteDatabase db  =  helper.getReadableDatabase();
                ContentValues values = new ContentValues();
                values.put("name","melificent");
                values.put("author","aaa");
                values.put("pages",454);
                values.put("price",16.96);
                db.insert("Book",null,values);
                    values.clear();
                values.put("name","melificent1");
                values.put("author","aaa1");
                values.put("pages",4541);
                values.put("price",161.96);
                db.insert("Book",null,values);
                values.clear();
                values.put("name","melificent2");
                values.put("author","aaa2");
                values.put("pages",4542);
                values.put("price",162.96);
                db.insert("Book",null,values);


                Cursor cursor = db.query("Book",null,null,null,null,null,null);
                if (cursor.moveToFirst()){
                    do {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        textView.setText(name);
                    }while (cursor.moveToNext());
                }
                cursor.close();
                String table = "Book";
                String[] columns = {
                    "author","name","pages"
                };

                String[]whereArgs= null;
                Cursor c = db.query(table,columns,null,whereArgs,null,null,null);
                if (c.moveToPosition(2)){
                    Log.i("author",c.getString(0));
                    Log.i("name",c.getString(1));
                    Log.i("pages",c.getInt(2)+"");
                }
                c.close();






            }
        });

    }

    class  MyDatabaseHelper extends SQLiteOpenHelper{
        private Context mcontext;

        public MyDatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
          mcontext = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_Book);
            Toast.makeText(mcontext,"creat succeded",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
        public  static  final String CREATE_Book = "create table book(" +
                "id integer primary key autoincrement,"+ "author text,"
                +"price real,"
                +"pages interger,"
                +"name text)";

    }
}
