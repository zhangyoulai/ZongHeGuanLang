package meiling.mingjiang.line_storage.return_fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import meiling.mingjiang.line_storage.R;

/**
 * 备注：线边库数据显示。
 * 作者：wangzs on 2016/2/19 17:32
 * 邮箱：wangzhaosen@shmingjiang.org.cn
 */
public class ReturnDataAdapter extends BaseAdapter {

    private Context context = null;
    private List<MaterialList> mMaterialList = new ArrayList<>();

    public ReturnDataAdapter(Context context, List<MaterialList> materialLists){
        this.context = context;
        this.mMaterialList = materialLists;
    }

    @Override
    public int getCount() {
        return mMaterialList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMaterialList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MaterialList materialItem = mMaterialList.get(position);

        ViewHolder viewHolder=null;
        if (convertView==null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.return_fragment_item2, null);
            viewHolder.material_id = (TextView)convertView.findViewById(R.id.material_id);
            viewHolder.adjust_num = (TextView)convertView.findViewById(R.id.material_name);
            convertView.setTag(viewHolder);
        }else{
            viewHolder=(ViewHolder)convertView.getTag();
        }
        if (position % 2 == 0) {
            convertView.setBackgroundResource(R.color.gray);
        } else {
            convertView.setBackgroundResource(R.color.white);
        }
        viewHolder.material_id.setText(materialItem.material_name);
        viewHolder.adjust_num.setText("" + materialItem.alter_number);
        return convertView;
    }
    class ViewHolder{
        TextView material_id,adjust_num;
    }
}
