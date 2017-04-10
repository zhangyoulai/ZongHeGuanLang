package org.mj.com.app.client;

import android.net.Uri;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class HttpClientUtils {
    public static final String SESSIONID = "X-Openerp-Session-Id";
    private static final String TAG = "";
    //通过地址获得byte数据
    public static String  getData(String path,String sessionid) {
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setConnectTimeout(3*1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty(SESSIONID, sessionid);// 请求头, 必须设置
            InputStream inStream = conn.getInputStream();
            byte[] bf=new byte[1024];
            int len=0;
            while ((len=inStream.read(bf))!=-1){
                result=result+new String(bf,0,len);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }
    public static boolean postData(String path,String submitData,String sessionid){
        submitData = URLEncoder.encode(submitData);// 中文数据需要经过URL编码
        String params = "test_detail=" + submitData;
        byte[] data = params.getBytes();
        boolean result = false;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            //这是请求方式为POST
            conn.setRequestMethod("POST");
            //设置post请求必要的请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
            conn.setRequestProperty("Content-Length", data.length + "");// 注意是字节长度, 不是字符长度
            if (null!=sessionid&&!"".equals(sessionid)){
                conn.setRequestProperty(SESSIONID,sessionid);
            }

            conn.setDoOutput(true);// 准备写出
            conn.getOutputStream().write(data);// 写出数据
            int code=conn.getResponseCode();
            if ( code== 200){
                result=true;
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
