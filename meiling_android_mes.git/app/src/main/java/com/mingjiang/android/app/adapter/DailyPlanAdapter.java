package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.Plan;

import java.util.List;


/**
 * Created by wdongjia on 2016/8/15.
 */
public class DailyPlanAdapter extends BaseAdapter {

    private Context mContext;

    private LayoutInflater mInflater;

    private List<Plan> mDatas;

    private OnClickListener onClickListener;

    public DailyPlanAdapter(Context context, List<Plan> datas,
                            OnClickListener onClickListener) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
        this.onClickListener = onClickListener;
    }

    @Override
    public int getCount() {
        return (mDatas != null ? mDatas.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return (mDatas != null ? mDatas.get(position) : null);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            // 下拉项布局
            convertView = mInflater.inflate(R.layout.item_layout, null);

            holder = new ViewHolder();

//            holder.avatar = (ImageView) convertView.findViewById(R.id.avatar);
            holder.dateTextView = (TextView) convertView.findViewById(R.id.tv_date);
            holder.codeTextView = (TextView) convertView.findViewById(R.id.tv_code);
            holder.r3codeTextView = (TextView) convertView.findViewById(R.id.tv_r3code);
            holder.orderTextView = (TextView) convertView.findViewById(R.id.tv_order);
            holder.numberTextView = (TextView) convertView.findViewById(R.id.tv_number);
            holder.completedTextView = (TextView) convertView.findViewById(R.id.tv_completed);
            holder.button = (Button) convertView.findViewById(R.id.btn_print);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Plan plan = mDatas.get(position);

        if (plan != null) {
            holder.dateTextView.setText(plan.getDate());
            holder.codeTextView.setText(plan.getSpec());
            holder.r3codeTextView.setText(plan.getR3code());
            holder.orderTextView.setText(plan.getOrder());
            holder.numberTextView.setText(String.valueOf(plan.getNumber()));
            holder.completedTextView.setText(String.valueOf(plan.getCompleted()));
            // 通常将position设置为tag，方便之后判断点击的button是哪一个
            holder.button.setTag(position);
            holder.button.setOnClickListener(this.onClickListener);
        }

        return convertView;
    }

    static class ViewHolder {
        //        ImageView avatar;
        TextView dateTextView;
        TextView codeTextView;
        TextView r3codeTextView;
        TextView orderTextView;
        TextView numberTextView;
        TextView completedTextView;
        Button button;
    }

}