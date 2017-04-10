package com.example.melificent.testforspinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by p on 2016/10/28.
 */

public class ItemAdapter extends BaseAdapter{
    private List<Item> items;
    private Context context;

    public ItemAdapter(Context c,List<Item> items) {
        this.items = items;
        this.context = c;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
      ViewHolder holder ;
        if (convertView==null){
            convertView = View.inflate(context,R.layout.spinner,null);
            holder = new ViewHolder();

           holder.t  = (TextView) convertView.findViewById(R.id.textview);
            convertView.setTag(holder);
        }
        holder = (ViewHolder) convertView.getTag();
        Item item = items.get(position);
        holder.t.setText(item.name);
        return convertView;
    }
    class  ViewHolder{
        TextView t ;
    }
}
