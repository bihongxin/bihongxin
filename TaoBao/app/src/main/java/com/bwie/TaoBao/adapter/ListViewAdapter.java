package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.GcBean;
import com.bwie.TaoBao.fragment.WeiTaoFragment;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/8 20:11
 */

public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private List<GcBean.DatasBean.ClassListBean> list;
    public static int mPosition;

    public ListViewAdapter(Context context, List<GcBean.DatasBean.ClassListBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override

    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        convertView = LayoutInflater.from(context).inflate(R.layout.listview_item, null);
        TextView tv = (TextView) convertView.findViewById(R.id.tv);
        mPosition = position;
        tv.setText(list.get(position).getGc_name());

        if (position == WeiTaoFragment.mPosition) {
            convertView.setBackgroundResource(R.drawable.tongcheng_all_bg01);
            tv.setTextColor(Color.WHITE);
        } else {
            tv.setTextColor(Color.BLACK);
        }
        return convertView;
    }
}
