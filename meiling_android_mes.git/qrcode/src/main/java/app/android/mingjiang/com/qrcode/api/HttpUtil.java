package app.android.mingjiang.com.qrcode.api;

import android.os.Looper;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;

/**
 * 网络请求
 *
 * @author wangdongjia
 * @version 1.0
 */
public class HttpUtil {

    protected static final String TAG = HttpUtil.class.getSimpleName();

    public static String getDataFromServer(String path, String submitData) {
        String resultData = "";
        byte[] data = submitData.getBytes();
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
            conn.setDoOutput(true);// 准备写出
            conn.getOutputStream().write(data);// 写出数据
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    resultData += inputLine + "\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.i(TAG, resultData);
        return resultData;
    }

    /**
     * @param url
     * @param order
     * @param code
     * @return
     */
    public static String sendJsonToServer(String url, String order, String code) {
        String returnStr = "";
        HttpClient httpClient = new DefaultHttpClient();
        try {
            HttpPost httpPost = new HttpPost(url);
            JSONObject params = new JSONObject();
            JSONObject params1 = new JSONObject();
            JSONObject params2 = new JSONObject();
//            {
//                "params": {
//                    "args": [],
//                    "kwargs": {
//                        "serial_number": "ML3",
//                        "order_id": "1"
//                    }
//                 }
//            }
            params.put("order_id", order);
            params.put("serial_number", code);
            params1.put("args", new ArrayList());
            params1.put("kwargs", params);
            params2.put("params", params1);
            StringEntity s = new StringEntity(params2.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            httpPost.setEntity(s);
            HttpResponse response = httpClient.execute(httpPost);
            Looper.prepare();
            Log.i("mes", params2.toString());
            httpClient.execute(httpPost);
            StatusLine statusLine = response.getStatusLine();
            if (statusLine != null && statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    returnStr = readInputStream(entity.getContent());
                    Log.i(TAG, returnStr);
                } else {
                    returnStr = "没有返回相关数据";
                    Log.i(TAG, returnStr);
                }

            } else {
                returnStr = "发送失败，可能服务器忙，请稍后再试";
                Log.i(TAG, returnStr);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return returnStr;
    }

    /**
     * 获取指定URL的响应字符串
     *
     * @param path
     * @return
     */
    public static String getURLResponse(String path) {
        HttpURLConnection conn = null; //连接对象
        InputStream is = null;
        String resultData = "";
        try {
            URL url = new URL(path); //URL对象
            conn = (HttpURLConnection) url.openConnection(); //使用URL打开一个链接
            conn.setDoInput(true); //允许输入流，即允许下载
            conn.setDoOutput(true); //允许输出流，即允许上传
            conn.setUseCaches(false); //不使用缓冲
            conn.setRequestMethod("GET"); //使用get请求  //指定请求方式
            is = conn.getInputStream();   //获取输入流，此时才真正建立链接
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader bufferReader = new BufferedReader(isr);
            String inputLine = "";
            while ((inputLine = bufferReader.readLine()) != null) {
                resultData += inputLine + "\n";
            }
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            if (conn != null) {
                conn.disconnect();
            }
        }
        return resultData;
    }

    /**
     * @param is
     * @return
     * @throws IOException
     */
    private static String readInputStream(InputStream is) throws IOException {
        if (is == null)
            return null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        int len = 0;
        byte[] buf = new byte[1024];
        while ((len = is.read(buf)) != -1) {
            bout.write(buf, 0, len);
        }
        is.close();
        return URLDecoder.decode(new String(bout.toByteArray()), "utf-8");
    }

}
