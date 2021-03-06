package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.Goods;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/6 19:41
 */

public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.ItemViewHolder>{

    private Context context;
    private LayoutInflater inflater;
    private List<Goods.DataBean.DefaultGoodsListBean> list;
    private List<Integer> hlist;

    public ItemRecyclerAdapter(Context context, List<Goods.DataBean.DefaultGoodsListBean> list) {
        this.context = context;
        inflater=LayoutInflater.from(context);
        this.list = list;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.recycler_item,null);
        ItemViewHolder itemViewHolder=new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        getRandom(list);
        ViewGroup.LayoutParams layoutParams = holder.iv_goods.getLayoutParams();
        layoutParams.height=hlist.get(position);
        holder.iv_goods.setLayoutParams(layoutParams);
        Picasso.with(context).load(list.get(position).getGoods_img()).into(holder.iv_goods);
        holder.tv_goods.setText(list.get(position).getGoods_name());
        holder.tv_price.setText("¥"+list.get(position).getShop_price());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        ImageView iv_goods;
        TextView tv_goods,tv_price;
        public ItemViewHolder(View itemView) {
            super(itemView);
            iv_goods= (ImageView) itemView.findViewById(R.id.iv_goods);
            tv_goods= (TextView) itemView.findViewById(R.id.tv_goods);
            tv_price= (TextView) itemView.findViewById(R.id.tv_price);
        }
    }
    public void getRandom(List<Goods.DataBean.DefaultGoodsListBean> list){
        hlist=new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            hlist.add((int) (200+Math.random()*300));
        }
    }
}
