package com.mingjiang.android.equipmonitor.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;


import com.mingjiang.android.equipmonitor.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 红外测试数据查询
 */
public class HWActivity extends AppCompatActivity {
    protected final static String TAG = HWActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hw);
        initSQL();
    }

    private void initSQL(){
        try {
            db = "MLIR";
            username = "sa";
            password = "sql@2008";
            ipaddress = "10.18.113.244:1433";
            connect = ConnectionHelper(username, password, db, ipaddress);
            if (connect.isClosed()==false)
            {
                _isOpened=true;
                System.out.println("connect ok");
                testConnection(connect);
            }
            else
            {
                _isOpened=false;
                System.out.println("connect fail");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean _isOpened=false;
    String ipaddress, db, username, password;
    public static Connection connect;
    Statement st;
    public boolean isOpened()
    {
        return _isOpened;
    }

    @SuppressLint("NewApi")
    private Connection ConnectionHelper(String user, String password,
                                        String database, String server) {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                .permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + server + ";"
                    + "databaseName=" + database + ";user=" + user
                    + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        } catch (SQLException se) {
            Log.e("ERROR", se.getMessage());
        } catch (ClassNotFoundException e) {
            Log.e("ERROR", e.getMessage());
        } catch (Exception e) {
            Log.e("ERROR", e.getMessage());
        }
        return connection;
    }

    /**
     * 查找数据库中最后一条记录
     * @param con
     * @throws SQLException
     */
    public void testConnection(Connection con) throws SQLException {
        try {
            String sql = "select top 1 * from dbo.T_BxData order by id desc";//根据Id查询最新一条记录
//            String sql = "SELECT * FROM dbo.T_BxData";//查询表名为“table_test”的所有内容
            Statement stmt = con.createStatement();//创建Statement
            ResultSet rs = stmt.executeQuery(sql);//ResultSet类似Cursor
            while (rs.next()) {//<code>ResultSet</code>最初指向第一行
                Log.i(TAG,rs.getString("BarCode"));
            }
            rs.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage().toString());
        } finally {
            if (con != null)
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
        }
    }
}
