package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.GroupBean;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/14 13:41
 */

public class ExpandableAdapter extends BaseExpandableListAdapter{

    private List<GroupBean> glist;
    private Context context;


    public ExpandableAdapter(List<GroupBean> glist, Context context) {
        this.glist = glist;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return glist.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return glist.get(groupPosition).getClist().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return glist.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return glist.get(groupPosition).getClist().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        final GroupViewHolder gh;
        if(convertView==null){
            gh=new GroupViewHolder();
            convertView=View.inflate(context, R.layout.expandable_group,null);
            gh.cb_group= (CheckBox) convertView.findViewById(R.id.cb_group);
            gh.tv_group_name= (TextView) convertView.findViewById(R.id.tv_group_name);
            convertView.setTag(gh);
        }else{
            gh= (GroupViewHolder) convertView.getTag();
        }
            gh.cb_group.setChecked(glist.get(groupPosition).isG_ischecked());
            gh.tv_group_name.setText(glist.get(groupPosition).getGroup_name());
        gh.cb_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = gh.cb_group.isChecked();
                if(checked){
                    glist.get(groupPosition).setG_ischecked(true);
                    for (int i = 0; i < glist.get(groupPosition).getClist().size(); i++) {
                        glist.get(groupPosition).getClist().get(i).setC_ischecked(true);
                    }
                }else{
                    glist.get(groupPosition).setG_ischecked(false);
                    for (int i = 0; i < glist.get(groupPosition).getClist().size(); i++) {
                        glist.get(groupPosition).getClist().get(i).setC_ischecked(false);

                    }
                }
                onCheckedClick.setOnCheckedClick(checked);
                sumPrice();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final ChildViewHolder ch;
        if(convertView==null){
            ch=new ChildViewHolder();
            convertView=View.inflate(context,R.layout.expandable_child,null);
            ch.cb_child= (CheckBox) convertView.findViewById(R.id.cb_child);
            ch.iv_child_image= (ImageView) convertView.findViewById(R.id.iv_child_image);
            ch.tv_child_name= (TextView) convertView.findViewById(R.id.tv_child_name);
            ch.tv_child_price= (TextView) convertView.findViewById(R.id.tv_child_price);
            ch.child_number= (TextView) convertView.findViewById(R.id.child_number);
            convertView.setTag(ch);
        }else{
            ch= (ChildViewHolder) convertView.getTag();
        }
        ch.cb_child.setChecked(glist.get(groupPosition).getClist().get(childPosition).isC_ischecked());
        Picasso.with(context).load(glist.get(groupPosition).getClist().get(childPosition).getC_image()).into(ch.iv_child_image);
        ch.tv_child_name.setText(glist.get(groupPosition).getClist().get(childPosition).getC_name());
        ch.tv_child_price.setText(glist.get(groupPosition).getClist().get(childPosition).getC_price());
        ch.child_number.setText(glist.get(groupPosition).getClist().get(childPosition).getC_number());

        ch.cb_child.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ch.cb_child.isChecked();
                if(checked){
                    glist.get(groupPosition).getClist().get(childPosition).setC_ischecked(true);
                }else{
                    glist.get(groupPosition).getClist().get(childPosition).setC_ischecked(false);
                }

                int n=0;
                for (int i = 0; i < glist.get(groupPosition).getClist().size(); i++) {
                    boolean c_ischecked = glist.get(groupPosition).getClist().get(i).isC_ischecked();
                    if(c_ischecked){
                        n++;
                    }
                }
                if(n==glist.get(groupPosition).getClist().size()){
                    glist.get(groupPosition).setG_ischecked(true);
                    onCheckedClick.setOnCheckedClick(true);
                }else{
                    glist.get(groupPosition).setG_ischecked(false);
                    onCheckedClick.setOnCheckedClick(false);
                }
                sumPrice();
                notifyDataSetChanged();
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
    class GroupViewHolder{
        CheckBox cb_group;
        TextView tv_group_name;
    }
    class ChildViewHolder{
        CheckBox cb_child;
        ImageView iv_child_image;
        TextView tv_child_name;
        TextView tv_child_price;
        ImageView child_jian,child_jia;
        TextView child_number;
    }
    public OnCheckedClick onCheckedClick;

    public void setOnCheckedClick(OnCheckedClick onCheckedClick) {
        this.onCheckedClick = onCheckedClick;
    }

    public interface OnCheckedClick{
        void setOnCheckedClick(boolean flag);
        void setOnCheckedCount(int count);
        void setOnCheckedPrice(double price);
    }
    //算价格数量的方法
    public void sumPrice(){
        int count=0;
        double price=0.0;
        for (int i = 0; i < glist.size(); i++) {
            for (int j = 0; j < glist.get(i).getClist().size(); j++) {
                boolean c_ischecked = glist.get(i).getClist().get(j).isC_ischecked();
                if(c_ischecked){
                    int i1 = Integer.parseInt(glist.get(i).getClist().get(j).getC_number());
                    double v = Double.parseDouble(glist.get(i).getClist().get(j).getC_price());
                    count+=i1;
                    price+=v*i1;
                }
            }
        }
        onCheckedClick.setOnCheckedCount(count);
        onCheckedClick.setOnCheckedPrice(price);
    }
}
