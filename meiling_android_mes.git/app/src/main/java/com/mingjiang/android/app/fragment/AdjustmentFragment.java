package com.mingjiang.android.app.fragment;

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

import com.mingjiang.android.app.AppContext;
import com.mingjiang.android.app.R;
import com.mingjiang.android.app.adapter.AdjustmentAdapter;
import com.mingjiang.android.app.adapter.AdjustmentDataAdapter;
import com.mingjiang.android.app.bean.AdjustResult;
import com.mingjiang.android.app.bean.AdjustmentItem;
import com.mingjiang.android.app.bean.AroundMaterialQuery;
import com.mingjiang.android.app.bean.AroundMaterialResult;
import com.mingjiang.android.app.bean.AroundMaterialValue;
import com.mingjiang.android.app.bean.BeanKwargs;
import com.mingjiang.android.app.bean.BeanParams;
import com.mingjiang.android.app.bean.SubmitDdata;
import com.mingjiang.android.app.util.SetLineUtils;
import com.mingjiang.android.base.api.bootstrap.BootstrapButtonApi;
import com.mingjiang.android.base.api.bootstrap.BootstrapEditTextApi;
import com.mingjiang.android.base.event.ComEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import app.android.mingjiang.com.androidbootstrap.BootstrapEditText;
import de.greenrobot.event.EventBus;

import rx.functions.Action1;

public class AdjustmentFragment extends Fragment {
    private Context context = null;
    private View view = null;
    private AdjustmentAdapter adapter = null;
    private ListView materialList,mAdjustListview;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText areaIdEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn,mBtnSubmit,mBtnCreatList;
    private List<AroundMaterialValue> mList;
    private BeanKwargs mBeanKwargs;
    AroundMaterialQuery mAroundMaterialQuery;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mBeanKwargs=new BeanKwargs();
        mAroundMaterialQuery = new AroundMaterialQuery();
        view = inflater.inflate(R.layout.adjustment_fragment, null);
        context=getActivity();
        initView();
        return view;
    }
    private void initView(){
        materialList = (ListView)view.findViewById(R.id.around_material_list);
        mAdjustListview=(ListView)view.findViewById(R.id.adjustment_fragment_listview2);
        materialIdEdit = (BootstrapEditText)view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText)view.findViewById(R.id.material_name);
        areaIdEdit = (BootstrapEditText)view.findViewById(R.id.area_id);
        //设置为上次提交时的下料点
        SetLineUtils.setLineNum(areaIdEdit, context);
        pageSizeSpinner = (Spinner)view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText)view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton)view.findViewById(R.id.material_query1);
        mBtnSubmit=(BootstrapButton)view.findViewById(R.id.submit_adjustment);
        mBtnCreatList=(BootstrapButton)view.findViewById(R.id.create_list);
        BootstrapButtonApi.initConfig(queryBtn, 2);
        BootstrapButtonApi.initConfig(mBtnSubmit, 2);
        BootstrapButtonApi.initConfig(mBtnCreatList, 2);
        BootstrapEditTextApi.initConfig(materialIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit, 1.5f);
        BootstrapEditTextApi.initConfig(areaIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(pageNumberEdit, 1.5f);
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
        QueryClick queryClick=new QueryClick();
        queryBtn.setOnClickListener(queryClick);
        mBtnSubmit.setOnClickListener(queryClick);
        mBtnCreatList.setOnClickListener(queryClick);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }



    //执行物料数据查询操作。
    class QueryClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            int i = v.getId();
            if (i == R.id.submit_adjustment) {
                submitAdjustment(mBeanKwargs);
            }else if (i==R.id.material_query1){
                dealQueryData();
            }else if (i==R.id.create_list){
                creatAdjustmentList();
            }

        }
    }
    private void creatAdjustmentList(){
        List<AdjustmentItem> list = new ArrayList<>();
        if (mList!=null&&mList.size()>0){
            int len=mList.size();
            for (int i=0;i<len;i++){
                if (mList.get(i).adjust_num!=0){
                    list.add(new AdjustmentItem(mList.get(i).material_id,mList.get(i).material_name,mList.get(i).adjust_num));
                }
            }
            mBeanKwargs.area_id=areaIdEdit.getText().toString();
            mBeanKwargs.material=list;
            AdjustmentDataAdapter adapter = new AdjustmentDataAdapter(context,list);
            mAdjustListview.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }else {
            Toast.makeText(context, "请选择需要调整的物料", Toast.LENGTH_SHORT).show();
        }

    }
    private void submitAdjustment(BeanKwargs beanKwargs){
        //更新下料点编号
        SetLineUtils.updateLineNum(areaIdEdit,context);
        Toast.makeText(context, "正在提交数据", Toast.LENGTH_SHORT).show();
        SubmitDdata submitDdata=new SubmitDdata(new BeanParams(new ArrayList(),beanKwargs));
        AppContext.getApp().getNetService(context).sendFridgePostBean(submitDdata, AppContext.sessionId).subscribe(new Action1<AdjustResult>() {
            @Override
            public void call(AdjustResult adjustResult) {
                EventBus.getDefault().post(new ComEvent(adjustResult.result, ComEvent.ADJUSTMNET_MARRTIAL));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                EventBus.getDefault().post(new ComEvent("调整物料数量失败", ComEvent.ADJUSTMENT_MARRTIAL_MISS));
            }
        });
    }
    //组织查询数据。
    private void dealQueryData(){

        mAroundMaterialQuery.material_id = materialIdEdit.getText().toString();
        mAroundMaterialQuery.material_name = materialNameEdit.getText().toString();
        mAroundMaterialQuery.area_id = areaIdEdit.getText().toString();
        mAroundMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        mAroundMaterialQuery.page_number = pageNumberEdit.getText().toString();

        dealQueary(mAroundMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(AroundMaterialQuery aroundMaterialQuery){
        Map<String,String> retMap = aroundMaterialQuery.setData();
        AppContext.getApp().getNetService(context).queryAroundMaterial(retMap).subscribe(new Action1<AroundMaterialResult>() {
            @Override
            public void call(AroundMaterialResult aroundMaterialResult) {
                EventBus.getDefault().post(new ComEvent(aroundMaterialResult, ComEvent.REQUEST_LINSE_STORAGE_MARRTIAL));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(AroundMaterialResult aroundMaterialResult){
        mList =  aroundMaterialResult.result;
        adapter = new AdjustmentAdapter(context,mList);
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ComEvent event) {
        int actionType=event.getActionType();
        if(ComEvent.REQUEST_LINSE_STORAGE_MARRTIAL == actionType){
            AroundMaterialResult aroundMaterialResult = (AroundMaterialResult)event.getObjectMsg();
            dealQueryDataShow(aroundMaterialResult);
            int number = Integer.valueOf(pageNumberEdit.getText().toString());
            pageNumberEdit.setText(number + 1);
        }if(ComEvent.ADJUSTMNET_MARRTIAL == actionType){
            dealQueryData();
            Toast.makeText(context,"物料调整成功", Toast.LENGTH_SHORT).show();
        }if(ComEvent.ADJUSTMENT_MARRTIAL_MISS == actionType){
            Toast.makeText(context, event.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}
