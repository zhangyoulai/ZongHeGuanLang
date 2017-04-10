package meiling.mingjiang.instructionpdf.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import java.util.List;


import meiling.mingjiang.instructionpdf.R;
import meiling.mingjiang.instructionpdf.entity.YieldData;

/**
 * Created by kouzeping on 2016/5/3.
 * email：kouzeping@shmingjiang.org.cn
 */
public class MyAdapter extends BaseAdapter {
    public MyAdapter(List<YieldData> mList, Context mContext) {
        this.mList = mList;
        this.mContext = mContext;
    }

    List<YieldData> mList;
    Context mContext;
    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView= LayoutInflater.from(mContext).inflate(R.layout.yield_lv_item,null);
            viewHolder=new ViewHolder();
            viewHolder.textViewSpec=(TextView)convertView.findViewById(R.id.yield_spec);
            viewHolder.textViewCount=(TextView)convertView.findViewById(R.id.yield_count);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        YieldData yield_data=mList.get(position);
        viewHolder.textViewSpec.setText(yield_data.spec);
        viewHolder.textViewCount.setText(""+yield_data.count);
        return convertView;
    }
    class ViewHolder{
        TextView textViewSpec,textViewCount;
    }
}
