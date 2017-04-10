package meiling.mingjiang.line_storage.adjustment_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.base.api.bootstrap.BootstrapButtonApi;

import java.util.ArrayList;
import java.util.List;

import app.android.mingjiang.com.androidbootstrap.BootstrapButton;
import meiling.mingjiang.line_storage.R;
import meiling.mingjiang.line_storage.bean.AroundMaterialValue;

/**
 * 备注：线边库数据显示。
 * 作者：wangzs on 2016/2/19 17:32
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class AdjustmentAdapter extends BaseAdapter {

    private Context context = null;
    private List<AroundMaterialValue> aroundMaterialValueArrayList = new ArrayList<AroundMaterialValue>();

    public AdjustmentAdapter(Context context, List<AroundMaterialValue> aroundMaterialValueArrayList){
        this.context = context;
        this.aroundMaterialValueArrayList = aroundMaterialValueArrayList;
    }

    @Override
    public int getCount() {
        return aroundMaterialValueArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return aroundMaterialValueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final AroundMaterialValue aroundMaterialValue = aroundMaterialValueArrayList.get(position);

        ViewHolder viewHolder=null;
        if (convertView==null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.adjustment_fragment_item, null);
            viewHolder = new ViewHolder();
            viewHolder.material_id = (TextView)convertView.findViewById(R.id.material_id);
            viewHolder.material_name = (TextView)convertView.findViewById(R.id.material_name);
            viewHolder.safety = (TextView)convertView.findViewById(R.id.safety);
            viewHolder.safety_stock = (TextView)convertView.findViewById(R.id.safety_stock);
            viewHolder.number = (TextView)convertView.findViewById(R.id.number);
            viewHolder.shortage = (TextView)convertView.findViewById(R.id.shortage);
            viewHolder.adjust_num = (TextView)convertView.findViewById(R.id.adjsutment_num);
            viewHolder.bt_add= (BootstrapButton) convertView.findViewById(R.id.adjustment_add);
            viewHolder.bt_reduce= (BootstrapButton) convertView.findViewById(R.id.adjustment_reduce);
            BootstrapButtonApi.initConfig(viewHolder.bt_add, 2);
            BootstrapButtonApi.initConfig(viewHolder.bt_reduce, 2);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.gray);
        } else {
            convertView.setBackgroundResource(R.color.white);
        }
        viewHolder.material_id.setText(aroundMaterialValue.material_id);
        viewHolder.material_name.setText(aroundMaterialValue.material_name);
        if (aroundMaterialValue.safety){
            viewHolder.safety.setText("是");
        }else {
            viewHolder.safety.setText("否");
            convertView.setBackgroundResource(R.color.red);
        }
        viewHolder.safety_stock.setText(aroundMaterialValue.safety_stock);
        viewHolder.number.setText(aroundMaterialValue.number);
        viewHolder.shortage.setText(aroundMaterialValue.shortage);
        viewHolder.adjust_num.setText(aroundMaterialValue.expends);
        viewHolder.adjust_num.setText(""+aroundMaterialValue.adjust_num);
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.bt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aroundMaterialValue.adjust_num=aroundMaterialValue.adjust_num+1;
                finalViewHolder.adjust_num.setText(""+aroundMaterialValue.adjust_num);
            }
        });
        viewHolder.bt_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aroundMaterialValue.adjust_num=aroundMaterialValue.adjust_num-1;
                finalViewHolder.adjust_num.setText(""+aroundMaterialValue.adjust_num);
            }
        });
        return convertView;
    }
    class ViewHolder{
        TextView material_id,material_name,safety,safety_stock,number,shortage,adjust_num;
        private BootstrapButton bt_add,bt_reduce;
    }

}
