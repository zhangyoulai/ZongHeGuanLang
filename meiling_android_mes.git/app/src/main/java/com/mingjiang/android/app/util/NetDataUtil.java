package com.mingjiang.android.app.util;

import android.util.Log;

import com.mingjiang.android.app.bean.CodeAndSpec;
import com.mingjiang.android.app.bean.Plan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;




public class NetDataUtil {

    protected final static String TAG = NetDataUtil.class.getSimpleName();
    protected static String export_id1 = null;//出口评审号

    /**
     * 读取报废流水号
     *
     * @param url
     * @param code 报废单号
     * @return 报废流水号数组
     * @throws JSONException
     */
    public static List<String> getScrappedCodes(String url, String code) throws JSONException {
        List<String> stringList = new ArrayList<>();
        String s = "number=" + URLEncoder.encode(code.toString());
        String arrayData = HttpClientUtils.getDataFromServer(url, s);//["1","2","3"]
        arrayData = arrayData.substring(1, arrayData.length() - 2);
        String[] a = arrayData.split(",");
        for (int i = 0; i < a.length; i++) {
            stringList.add(a[i]);
        }
        return stringList;
    }

    /**
     * 从日计划读数据
     *
     * @param url
     * @param classes 班次
     * @return
     */
    public static List<Plan> getDailyPlanItems(String url, String date, String classes) {
        ArrayList<HashMap<String, Object>> allData;
        List<Plan> items = new ArrayList<>();
        try {
            //调用解析方法，得到解析后的列表，列表里的元素是HashMap
            String str = "date=" + URLEncoder.encode(date.toString());
            allData = Analysis(HttpClientUtils.getDataFromServer(url, str));
            Iterator<HashMap<String, Object>> it = allData.iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                String spec = (String) ma.get("product_spec");
                //R3码
                String s1 = (String) ma.get("product_code");
//                String s = s1.substring(2, 9);
                //数量
                int quantity = (int) ma.get("plan_quantity");
                //已完成数量
                int count = (int) ma.get("count");
                //内销出口标志位
                String export = (String) ma.get("export");

                String date1 = (String) ma.get("plan_date");

                if (export.contains("0")) {//0内销
                    Plan item = new Plan("", "0", date1, spec, s1, (String) ma.get("order_id"), quantity, count);
                    items.add(item);
                } else {//1出口
                    //判断出口评审号
                    String export_id = (String) ma.get("export_id");
                    //出口评审号的长度为7
                    if (export_id.length() >= 5) {// 判断是否长度大于等于5
                        export_id1 = export_id.substring(export_id.length() - 5, export_id.length());//截取后五位
                    } else {
                        export_id1 = "00000";
                        Log.i(TAG, "出口评审号的长度有误!");
                    }
                    Plan item = new Plan(export_id1, "1", date1, spec, s1, (String) ma.get("order_id"), quantity, count);
                    items.add(item);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * 解析日计划数据
     *
     * @param jsonStr
     * @throws JSONException
     */
    private static ArrayList<HashMap<String, Object>> Analysis(String jsonStr) throws JSONException {
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> dailyPlanList = new ArrayList<HashMap<String, Object>>();
        jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("plan_quantity", jsonObject.getInt("plan_quantity"));//计划数
            map.put("export", jsonObject.getString("export"));//是否出口
            map.put("export_id", jsonObject.getString("export_id"));//出口评审号，没有时为null
            map.put("product_code", jsonObject.getString("product_code"));//成品代码
            map.put("order_id", jsonObject.getString("order_id"));//订单号
            map.put("product_spec", jsonObject.getString("product_spec"));//产品型号
            map.put("count", jsonObject.getInt("count"));//已打印数量
            map.put("plan_date", jsonObject.getString("plan_date"));//计划日期
            dailyPlanList.add(map);
        }
        return dailyPlanList;
    }

    //从日计划读数据
    public static List<CodeAndSpec> getCodeAndSpec(String url) {
        ArrayList<HashMap<String, Object>> allData;
        List<CodeAndSpec> items = new ArrayList<>();
        try {
            //调用解析方法，得到解析后的列表，列表里的元素是HashMap
            allData = Analysis(HttpClientUtils.getURLResponse(url));
            Iterator<HashMap<String, Object>> it = allData.iterator();
            while (it.hasNext()) {
                Map<String, Object> ma = it.next();
                //
                String spec = (String)ma.get("product_spec");
                //R3码
                String s = (String) ma.get("product_code");
                s = s.substring(2, 9);
                CodeAndSpec item = new CodeAndSpec(s,spec);
                items.add(item);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return items;
    }

    /**
     * 解析
     *
     * @throws JSONException
     */
    private static ArrayList<HashMap<String, Object>> Analysis1(String jsonStr) throws JSONException {
        JSONArray jsonArray = null;
        // 初始化list数组对象
        ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
        jsonArray = new JSONArray(jsonStr);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            // 初始化map数组对象
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("plan_quantity", jsonObject.getInt("plan_quantity"));//计划数
            map.put("export", jsonObject.getString("export"));//是否出口
            map.put("export_id", jsonObject.getString("export_id"));//出口评审号，没有时为null
            map.put("product_code", jsonObject.getString("product_code"));//成品代码
            map.put("order_id", jsonObject.getString("order_id"));//订单号
            map.put("product_spec",jsonObject.getString("product_spec"));//产品型号
            list.add(map);
        }
        return list;
    }
}
