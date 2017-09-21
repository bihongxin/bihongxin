package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bwie.TaoBao.R;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/12 23:50
 */

public class MeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private int TYPE_1=0;
    private int TYPE_2=1;
    private int TYPE_3=2;

    public MeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view= LayoutInflater.from(context).inflate(R.layout.merecycler_1,parent,false);
            MeViewHolder meViewHolder=new MeViewHolder(view);
            return meViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof MeViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_1;
        }
        return TYPE_1;
    }
    public class MeViewHolder extends RecyclerView.ViewHolder{

        public MeViewHolder(View itemView) {
            super(itemView);
        }
    }
}
