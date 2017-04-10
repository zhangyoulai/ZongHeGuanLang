package meiling.mingjiang.line_storage.request_fragment;

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

import com.mingjiang.android.base.event.ComEvent;
import com.mingjiang.android.base.api.bootstrap.BootstrapButtonApi;
import com.mingjiang.android.base.api.bootstrap.BootstrapEditTextApi;

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

public class RequestFragment extends Fragment {
    private Context context = null;
    private View view = null;
    private RequestAdapter adapter = null;
    private ListView materialList;
    private BootstrapEditText materialIdEdit = null;
    private BootstrapEditText materialNameEdit = null;
    private BootstrapEditText areaIdEdit = null;
    private Spinner pageSizeSpinner = null;
    private BootstrapEditText pageNumberEdit = null;
    private BootstrapButton queryBtn;
    private List<AroundMaterialValue> mList;
    private TextView mStatusTv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.request_fragment, null);
        context = getActivity();
        initView();
        return view;
    }

    private void initView() {
        materialList = (ListView) view.findViewById(R.id.around_material_list);
        materialIdEdit = (BootstrapEditText) view.findViewById(R.id.material_id);
        materialNameEdit = (BootstrapEditText) view.findViewById(R.id.material_name);
        areaIdEdit = (BootstrapEditText) view.findViewById(R.id.area_id);
        //设置为上次提交时的下料点
        SetLineUtils.setLineNum(areaIdEdit, context);
        pageSizeSpinner = (Spinner) view.findViewById(R.id.page_size);
        pageNumberEdit = (BootstrapEditText) view.findViewById(R.id.page_number);
        queryBtn = (BootstrapButton) view.findViewById(R.id.material_query1);
        BootstrapButtonApi.initConfig(queryBtn, 2);
        BootstrapEditTextApi.initConfig(materialIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(materialNameEdit, 1.5f);
        BootstrapEditTextApi.initConfig(areaIdEdit, 1.5f);
        BootstrapEditTextApi.initConfig(pageNumberEdit, 1.5f);
        queryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //更新下料点编号
                SetLineUtils.updateLineNum(areaIdEdit,context);
                dealQueryData();
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

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    //组织查询数据。
    private void dealQueryData() {
        AroundMaterialQuery aroundMaterialQuery = new AroundMaterialQuery();
        aroundMaterialQuery.material_id = materialIdEdit.getText().toString();
        aroundMaterialQuery.material_name = materialNameEdit.getText().toString();
        aroundMaterialQuery.area_id = areaIdEdit.getText().toString();
        aroundMaterialQuery.page_size = pageSizeSpinner.getSelectedItem().toString();
        aroundMaterialQuery.page_number = pageNumberEdit.getText().toString();
        dealQueary(aroundMaterialQuery);
    }

    //执行查询操作
    private void dealQueary(AroundMaterialQuery aroundMaterialQuery) {
        Map<String, String> retMap = aroundMaterialQuery.setData();
        MaterialApp.getApp().getNetService(context).queryAroundMaterial(retMap).subscribe(new Action1<AroundMaterialResult>() {
            @Override
            public void call(AroundMaterialResult aroundMaterialResult) {
                EventBus.getDefault().post(new ComEvent(aroundMaterialResult, ComEvent.ACTION_AROUND_LIBARY_QUERY));
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
            }
        });
    }

    //处理数据显示
    private void dealQueryDataShow(AroundMaterialResult aroundMaterialResult) {
        mList = aroundMaterialResult.result;
        adapter = new RequestAdapter(context, mList, new RequestAdapter.OnRequestBtnListener() {
            @Override
            public void onRequestBtnListenner(TextView textView, String marrtialId) {
                mStatusTv=textView;
                Map<String, String> map = new HashMap<>();
                map.put("code",marrtialId);
                map.put("point_id",areaIdEdit.getText().toString());
                MaterialApp.getApp().getNetService(context).sendRequestData(map, MaterialApp.sessionId).subscribe(new Action1<String>() {
                    @Override
                    public void call(String result) {
                        EventBus.getDefault().post(new ComEvent(result, ComEvent.REQUEST_MARRTIAL_SUCCEED));
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        EventBus.getDefault().post(new ComEvent("手动叫料失败", ComEvent.REQUEST_MARRTIAL_MISS));
                    }
                });
            }
        });
        materialList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onEventMainThread(ComEvent event) {
        if (ComEvent.ACTION_AROUND_LIBARY_QUERY == event.getActionType()) {
            AroundMaterialResult aroundMaterialResult = (AroundMaterialResult) event.getObjectMsg();
            dealQueryDataShow(aroundMaterialResult);
            int number = Integer.valueOf(pageNumberEdit.getText().toString());
            pageNumberEdit.setText(number + 1);
        }else  if (ComEvent.REQUEST_MARRTIAL_SUCCEED == event.getActionType()) {
            mStatusTv.setText(event.getMessage());
        }else  if (ComEvent.REQUEST_MARRTIAL_MISS == event.getActionType()) {
            mStatusTv.setText(event.getMessage());
        }
    }

}
