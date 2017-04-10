package com.example.hasee.compare.utils;

import android.util.Log;

import com.example.hasee.compare.bean.CodeAndSpec;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 16-4-8.
 */
public class MockDataUtils {

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
    private static ArrayList<HashMap<String, Object>> Analysis(String jsonStr) throws JSONException {
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
