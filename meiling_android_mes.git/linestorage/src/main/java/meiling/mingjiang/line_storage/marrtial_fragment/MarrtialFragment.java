package meiling.mingjiang.line_storage.marrtial_fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.api.bootstrap.BootstrapButtonApi;
import com.mingjiang.android.base.api.bootstrap.BootstrapEditTextApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import de.greenrobot.event.EventBus;
import meiling.mingjiang.line_storage.R;
import meiling.mingjiang.line_storage.bean.AroundMaterialQuery;
import meiling.mingjiang.line_storage.bean.AroundMaterialResult;
import meiling.mingjiang.line_storage.bean.AroundMaterialValue;
import meiling.mingjiang.line_storage.utils.MaterialApp;
import meiling.mingjiang.line_storage.utils.SetLineUtils;
import rx.functions.Action1;

public class MarrtialFragment extends Fragment {
    private Context context = null;
    private View view = null;
    private MarrtialAdapter adapter = null;
    private ListView materialList,mReceiveListView = null;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText areaIdEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn,mBtnAsk,mBtnCommit = null;
    List<MarrtialItem> mMarrtialItems;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.marrtial_fragment, null);
        context=getActivity();
        initView();
        initViewForEvent();
        return view;
    }
    private void initViewForEvent() {
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealQueryData();
            }
        });
        mBtnAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String,String> map=new HashMap<>();
                map.put("point", areaIdEdit.getText().toString());
                MaterialApp.getApp().getNetService(context).sendReceivenAsk(map, MaterialApp.sessionId).subscribe(new Action1<ArrayList< MarrtialItem >> ()
                {
                    @Override
                    public void call(ArrayList<MarrtialItem> marrtialItems) {
                        EventBus.getDefault().post(new ComEvent(marrtialItems, ComEvent.RECEIVE_MARRTIAL));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        EventBus.getDefault().post(new ComEvent("请求卸料失败", ComEvent.RECEIVE_MARRTIAL_MISS));
                    }
                });
            }
        });
        mBtnCommit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新下料点编号
                SetLineUtils.updateLineNum(areaIdEdit,context);
                Map<String,String> map=new HashMap<>();
                map.put("materials",  mMarrtialItems.toString());
                MaterialApp.getApp().getNetService(context).sendReceivenData(map, MaterialApp.sessionId).subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        EventBus.getDefault().post(new ComEvent(result, ComEvent.RECEIVE_MARRTIAL_COMMIT));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        EventBus.getDefault().post(new ComEvent("提交卸料信息失败", ComEvent.RECEIVE_MARRTIAL_COMMIT_MISS));
                    }
                });
            }
        });
        pageSizeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                TextView tv = (TextView) view;
                tv.setTextColor(getResources().getColor(R.color.colorPrimary));    //设置颜色
                tv.setTextSize(getResources().getDimension(R.dimen.item_text));    //设置大小
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    private void initView(){
        materialList = (ListView)view.findViewById(R.id.around_material_list);
        materialIdEdit = (BootstrapEditText)view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText)view.findViewById(R.id.material_name);
        areaIdEdit = (BootstrapEditText)view.findViewById(R.id.area_id);
        //设置为上次提交时的下料点
        SetLineUtils.setLineNum(areaIdEdit, context);
        pageSizeSpinner = (Spinner)view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText)view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton)view.findViewById(R.id.material_query);
        mBtnAsk= (BootstrapButton) view.findViewById(R.id.receiven_ask);
        mBtnCommit= (BootstrapButton) view.findViewById(R.id.submit_marrtial_data);
        mReceiveListView= (ListView) view.findViewById(R.id.marrtial_fragment_listview2);
        BootstrapButtonApi.initConfig(queryBtn, 2);
        BootstrapButtonApi.initConfig(mBtnAsk, 2);
        BootstrapButtonApi.initConfig(mBtnCommit, 2);
        BootstrapEditTextApi.initConfig(materialIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit,1.5f);
        BootstrapEditTextApi.initConfig(areaIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(pageNumberEdit, 1.5f);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //组织查询数据。
    private void dealQueryData(){
        AroundMaterialQuery aroundMaterialQuery = new AroundMaterialQuery();
        aroundMaterialQuery.material_id = materialIdEdit.getText().toString();
        aroundMaterialQuery.material_name = materialNameEdit.getText().toString();
        aroundMaterialQuery.area_id = areaIdEdit.getText().toString();
        aroundMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        aroundMaterialQuery.page_number = pageNumberEdit.getText().toString();
        dealQueary(aroundMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(AroundMaterialQuery aroundMaterialQuery){
        Map<String,String> retMap = aroundMaterialQuery.setData();
        MaterialApp.getApp().getNetService(context).queryAroundMaterial(retMap).subscribe(new Action1<AroundMaterialResult>() {
            @Override
            public void call(AroundMaterialResult aroundMaterialResult) {
                EventBus.getDefault().post(new ComEvent(aroundMaterialResult, ComEvent.RECEIVE_MARRTIAL_DATA));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(AroundMaterialResult aroundMaterialResult){
        List<AroundMaterialValue> list =  aroundMaterialResult.result;
        adapter = new MarrtialAdapter(context,list);
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ComEvent event) {
        if(ComEvent.RECEIVE_MARRTIAL_DATA == event.getActionType()){
            AroundMaterialResult aroundMaterialResult = (AroundMaterialResult)event.getObjectMsg();
            dealQueryDataShow(aroundMaterialResult);
            int number = Integer.valueOf(pageNumberEdit.getText().toString());
            pageNumberEdit.setText(number + 1);
        }else if(ComEvent.RECEIVE_MARRTIAL == event.getActionType()){
            mMarrtialItems= (List<MarrtialItem>) event.getObjectMsg();
            MarrtialDataAdapter adapter = new MarrtialDataAdapter(context,mMarrtialItems);
            mReceiveListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else if(ComEvent.RECEIVE_MARRTIAL_MISS == event.getActionType()){
            Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(ComEvent.RECEIVE_MARRTIAL_COMMIT == event.getActionType()){
            Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
        }else if(ComEvent.RECEIVE_MARRTIAL_COMMIT_MISS == event.getActionType()){
            Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
