package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.PrintLog;

import java.util.List;



/**
 * Created by wdongjia on 2016/9/8.
 */
public class PrintLogAdapter extends BaseAdapter {
    private Context mContext;

    private LayoutInflater mInflater;

    public List<PrintLog> mDatas;

    public PrintLogAdapter(Context context, List<PrintLog> datas) {
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        mDatas = datas;
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
            convertView = mInflater.inflate(R.layout.log_item_layout, null);

            holder = new ViewHolder();

            holder.printTypeTextview = (TextView) convertView.findViewById(R.id.tv_print_type);
            holder.printcodeTextView = (TextView) convertView.findViewById(R.id.tv_print_code);
            holder.printStatusTextView = (TextView) convertView.findViewById(R.id.tv_print_status);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PrintLog printLog = mDatas.get(position);

        if (printLog != null) {
            holder.printTypeTextview.setText(printLog.getPrintType());
            holder.printcodeTextView.setText(printLog.getPrintCode());
            if (printLog.isPrintStatus()) {//true已打印
                holder.printStatusTextView.setText("已打印");
                holder.printStatusTextView.setTextColor(Color.RED);
                holder.printTypeTextview.setTextColor(Color.RED);
                holder.printcodeTextView.setTextColor(Color.RED);
            } else {
                holder.printStatusTextView.setText("未打印");
                holder.printStatusTextView.setTextColor(Color.BLUE);
                holder.printTypeTextview.setTextColor(Color.BLUE);
                holder.printcodeTextView.setTextColor(Color.BLUE);
            }
        }
        return convertView;
    }

    static class ViewHolder {
        TextView printTypeTextview;
        TextView printcodeTextView;
        TextView printStatusTextView;
    }
}
