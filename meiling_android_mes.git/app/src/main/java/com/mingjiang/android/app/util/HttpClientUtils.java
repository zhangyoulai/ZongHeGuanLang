package com.mingjiang.android.app.util;


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
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by kouzeping on 2016/1/26.
 * email：kouzeping@shmingjiang.org.cn
 */
public class HttpClientUtils {
    private static final String TAG = HttpClientUtils.class.getSimpleName();
    public static final String SESSIONID = "X-Openerp-Session-Id";

    //通过地址获得byte数据
    public static String getData(String path, String sessionid) {
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty(SESSIONID, sessionid);// 请求头, 必须设置
            InputStream inStream = conn.getInputStream();
            byte[] bf = new byte[1024];
            int len = 0;
            while ((len = inStream.read(bf)) != -1) {
                result = result + new String(bf, 0, len);
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;

    }

    public static boolean postData(String path, String submitData, String sessionid) {
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
            if (null != sessionid && !"".equals(sessionid)) {
                conn.setRequestProperty(SESSIONID, sessionid);
            }

            conn.setDoOutput(true);// 准备写出
            conn.getOutputStream().write(data);// 写出数据
            int code = conn.getResponseCode();
            if (code == 200) {
                result = true;
            }
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //通过地址获得byte数据
    public static String getData(String path) {
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3 * 1000);
            conn.setRequestMethod("GET");
            InputStream inStream = conn.getInputStream();
            byte[] bf = new byte[1024];
            int len = 0;
            while ((len = inStream.read(bf)) != -1) {
                result = result + new String(bf, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String postData(String path, String submitData) {
        byte[] updata = null;
        byte[] data = null;
        int len = 0;
        if (submitData != null) {
//            submitData = URLEncoder.encode(submitData);// 中文数据需要经过URL编码
            updata = submitData.getBytes();
            len = updata.length;
        }
        String result = "";
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(3000);
            //这是请求方式为POST
            conn.setRequestMethod("POST");
            //设置post请求必要的请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
            conn.setRequestProperty("Accept-Language", "zh-CN");// 请求头, 必须设置
            conn.setRequestProperty("Content-Length", len + "");// 注意是字节长度, 不是字符长度
            if (updata != null && updata.length > 0 && len > 0) {
                conn.setDoOutput(true);// 准备写出
                conn.getOutputStream().write(updata);// 写出数据
            } else {
                conn.setDoOutput(false);// 不写出
            }
            int code = conn.getResponseCode();
            if (code == 200) {
                InputStream is = conn.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while ((line = rd.readLine()) != null) {
                    response.append(line);
                }
                rd.close();
                result = response.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

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


    /**
     * 读取报废订单的流水号
     *
     * @param path
     * @param submitData
     * @return
     */
    public static String getCodeFromServer(String path, String submitData) {
        String result1 = "";
//        String s = "code=" + URLEncoder.encode(submitData.toString());
        String s = URLEncoder.encode(submitData.toString());
        byte[] data = s.getBytes();
        boolean result = false;
        URL url = null;
        try {
            url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            //超时
            conn.setConnectTimeout(3000);
            //这是请求方式为POST
            conn.setRequestMethod("POST");
            //设置post请求必要的请求头
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");// 请求头, 必须设置
//            conn.setRequestProperty("Content-Length", data.length + "");// 注意是字节长度, 不是字符长度
//            if (null!=header&&!"".equals(header)){
//                conn.setRequestProperty("X-Openerp-Session-Id",header);
//                conn.connect();
//            }
            conn.setDoOutput(true);// 准备写出
            conn.getOutputStream().write(data);// 写出数据

            int code = conn.getResponseCode();
            if (code == 200) {
                result = true;
                InputStreamReader in = new InputStreamReader(conn.getInputStream());
                BufferedReader buffer = new BufferedReader(in);
                String inputLine = null;
                while ((inputLine = buffer.readLine()) != null) {
                    result1 += inputLine + "\n";
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result1;
    }

}