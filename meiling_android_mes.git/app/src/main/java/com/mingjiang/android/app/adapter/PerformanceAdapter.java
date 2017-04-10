package com.mingjiang.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mingjiang.android.app.R;
import com.mingjiang.android.app.bean.Performance;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by CaoBin on 2016/9/21.
 */
public class PerformanceAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<Performance> performances;

    public PerformanceAdapter(Context context, List<Performance> performances) {
        this.inflater =LayoutInflater.from(context);
        this.performances=performances;
    }

    public List<Performance> getPerformances() {
        return performances;
    }

    public void setPerformances(List<Performance> performances) {
        this.performances = performances;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return performances.size();
    }

    @Override
    public Object getItem(int position) {
        return performances.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView , ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            convertView=inflater.inflate(R.layout.fragment_performance_item,null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        Performance performance=performances.get(position);
        viewHolder.tvModel.setText(performance.getProduct_type());
        viewHolder.tvNumber1.setText(performance.getMdxn3().getBad_count()+"件");
        viewHolder.tvNumber2.setText(performance.getMdxn5().getBad_count()+"件");
        viewHolder.tvNumber3.setText(performance.getMld().getBad_count()+"件");
        viewHolder.tvNumber4.setText(performance.getMlj().getBad_count()+"件");
        viewHolder.tvNumber5.setText(performance.getWifi().getBad_count()+"件");
        viewHolder.tvNumber6.setText(performance.getMLIR().getBad_count()+"件");

        viewHolder.tvTest1.setText(performance.getMdxn3().getCount()+"件");
        viewHolder.tvTest2.setText(performance.getMdxn5().getCount()+"件");
        viewHolder.tvTest3.setText(performance.getMld().getCount()+"件");
        viewHolder.tvTest4.setText(performance.getMlj().getCount()+"件");
        viewHolder.tvTest5.setText(performance.getWifi().getCount()+"件");
        viewHolder.tvTest6.setText(performance.getMLIR().getCount()+"件");

        viewHolder.tvProportion1.setText(getProprotion(performance.getMdxn3().getBad_count(),performance.getMdxn3().getCount()));
        viewHolder.tvProportion2.setText(getProprotion(performance.getMdxn5().getBad_count(),performance.getMdxn5().getCount()));
        viewHolder.tvProportion3.setText(getProprotion(performance.getMld().getBad_count(),performance.getMld().getCount()));
        viewHolder.tvProportion4.setText(getProprotion(performance.getMlj().getBad_count(),performance.getMlj().getCount()));
        viewHolder.tvProportion5.setText(getProprotion(performance.getWifi().getBad_count(),performance.getWifi().getCount()));
        viewHolder.tvProportion6.setText(getProprotion(performance.getMLIR().getBad_count(),performance.getMLIR().getCount()));
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_model)
        TextView tvModel;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_number_1)
        TextView tvNumber1;
        @InjectView(R.id.tv_number_2)
        TextView tvNumber2;
        @InjectView(R.id.tv_number_3)
        TextView tvNumber3;
        @InjectView(R.id.tv_number_4)
        TextView tvNumber4;
        @InjectView(R.id.tv_number_5)
        TextView tvNumber5;
        @InjectView(R.id.tv_number_6)
        TextView tvNumber6;
        @InjectView(R.id.tv_test_1)
        TextView tvTest1;
        @InjectView(R.id.tv_test_2)
        TextView tvTest2;
        @InjectView(R.id.tv_test_3)
        TextView tvTest3;
        @InjectView(R.id.tv_test_4)
        TextView tvTest4;
        @InjectView(R.id.tv_test_5)
        TextView tvTest5;
        @InjectView(R.id.tv_test_6)
        TextView tvTest6;
        @InjectView(R.id.tv_proportion_1)
        TextView tvProportion1;
        @InjectView(R.id.tv_proportion_2)
        TextView tvProportion2;
        @InjectView(R.id.tv_proportion_3)
        TextView tvProportion3;
        @InjectView(R.id.tv_proportion_4)
        TextView tvProportion4;
        @InjectView(R.id.tv_proportion_5)
        TextView tvProportion5;
        @InjectView(R.id.tv_proportion_6)
        TextView tvProportion6;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    public String getProprotion(int i,int j ){
        if (j==0||i==0){
            return "100%";
        }
        float k= (1-(float) i/j)*100;
        DecimalFormat fnum  =   new  DecimalFormat("##0.00");
        String   dd=fnum.format(k);
        return dd+"%";
    }
}
