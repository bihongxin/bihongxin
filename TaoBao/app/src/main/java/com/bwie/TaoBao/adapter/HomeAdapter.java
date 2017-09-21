package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.banner.BannerImage;
import com.bwie.TaoBao.bean.Goods;
import com.bwie.TaoBao.view.UPMarqueeView;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/5 14:58
 */

public class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private int TYPE_BANNER=0;
    private int TYPE_MENU=1;
    private int TYPE_TOUTIAO=2;
    private int TYPE_ANDROID_UI=4;
    private int TYPE_TYPE=3;

    private Context context;
    private Goods.DataBean data;
    private LayoutInflater inflater;
    private List<String> imagelist;

    public HomeAdapter(Context context, Goods.DataBean data) {
        this.context = context;
        this.data = data;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getItemViewType(int position) {
        if(position==0){
            return TYPE_BANNER;
        }else if(position==1){
            return TYPE_MENU;
        }else if(position==4){
            return TYPE_ANDROID_UI;
        }else if(position==2){
            return TYPE_TOUTIAO;
        }else if(position==3){
            return TYPE_TYPE;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view = inflater.inflate(R.layout.banner_adapter, null);
            final MyViewHolder myViewHolder = new MyViewHolder(view);

            return myViewHolder;
        }else if(viewType==1){
            View view = inflater.from(context)
                    .inflate(R.layout.menu, parent, false);
            return new MyViewHolder2(view);
        }else if(viewType==2){
            View view=inflater.inflate(R.layout.recycler_toutiao,null);
            MyViewHolder4 myViewHolder4=new MyViewHolder4(view);
            return myViewHolder4;
        }else if(viewType==4){
            View view = inflater.inflate(R.layout.item_recycler_view, null);
            MyViewHolder3 myViewHolder3 = new MyViewHolder3(view);
            return myViewHolder3;
        }else if(viewType==3){
            View view=inflater.inflate(R.layout.recycler_type,parent,false);
            MyViewHolder5 myViewHolder5=new MyViewHolder5(view);
            return myViewHolder5;
        }
        return null;

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof MyViewHolder){
            ((MyViewHolder) holder).ban.setImageLoader(new BannerImage());
            imagelist=new ArrayList<>();
            for (int i = 0; i < 4; i++) {
                imagelist.add(data.getAd1().get(i).getImage());
            }
            ((MyViewHolder) holder).ban.setImages(imagelist);
            ((MyViewHolder) holder).ban.start();
            ((MyViewHolder) holder).ban.setOnBannerListener(new OnBannerListener() {
                @Override
                public void OnBannerClick(int position) {
                    onRecyclerItemClick.setOnRecyclerItemClick(position);
                }
            });
        }else if(holder instanceof MyViewHolder2){

        }else if(holder instanceof MyViewHolder4){
            List<View> views = new ArrayList<View>();
            for (int i = 0; i < data.getSubjects().size(); i++) {
                // 设置滚动的单个布局
                LinearLayout moreView = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.item_view, null);
                // 初始化布局的控件
                TextView tv1 = (TextView) moreView.findViewById(R.id.tv1);
                // 进行对控件赋值
                tv1.setText(data.getSubjects().get(i).getTitle());
                // 添加到循环滚动数组里面去
                views.add(moreView);
            }
            ((MyViewHolder4) holder).sl_activity.setViews(views);
        }else if(holder instanceof MyViewHolder3){
            ItemRecyclerAdapter itemRecyclerAdapter=new ItemRecyclerAdapter(context,data.getDefaultGoodsList());
            StaggeredGridLayoutManager manager=new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
            ((MyViewHolder3) holder).item_recycler.setLayoutManager(manager);
            ((MyViewHolder3) holder).item_recycler.setAdapter(itemRecyclerAdapter);
            ((MyViewHolder3) holder).item_recycler.setTag(position);

        }else if(holder instanceof MyViewHolder5){
            Picasso.with(context).load(data.getSubjects().get(0).getDescImage()).into(((MyViewHolder5) holder).iv_recycler);
            RecyclerTypeAdapter rt=new RecyclerTypeAdapter(context,data.getSubjects().get(0).getGoodsList());
            LinearLayoutManager manager=new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
            ((MyViewHolder5) holder).rv_type.setLayoutManager(manager);
            ((MyViewHolder5) holder).rv_type.setAdapter(rt);

        }
    }

    @Override
    public int getItemCount() {
        return 5;
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        Banner ban;
        public MyViewHolder(View itemView) {
            super(itemView);
            ban= (Banner) itemView.findViewById(R.id.ban);
        }
    }
    public class MyViewHolder2 extends RecyclerView.ViewHolder{

        public MyViewHolder2(View itemView) {
            super(itemView);

        }
    }
    public class MyViewHolder3 extends RecyclerView.ViewHolder{
        RecyclerView item_recycler;
        public MyViewHolder3(View itemView) {
            super(itemView);
            item_recycler= (RecyclerView) itemView.findViewById(R.id.item_recycler);
        }
    }
    public class MyViewHolder4 extends RecyclerView.ViewHolder{
        UPMarqueeView sl_activity;
        public MyViewHolder4(View itemView) {
            super(itemView);
            sl_activity= (UPMarqueeView) itemView.findViewById(R.id.sl_activity);
        }
    }
    public class MyViewHolder5 extends RecyclerView.ViewHolder{
        ImageView iv_recycler;
        RecyclerView rv_type;
        public MyViewHolder5(View itemView) {
            super(itemView);
            iv_recycler= (ImageView) itemView.findViewById(R.id.iv_recycler);
            rv_type= (RecyclerView) itemView.findViewById(R.id.rv_type);
        }
    }
    //接口回调
    public onRecyclerItemClick onRecyclerItemClick;
    public interface onRecyclerItemClick{
        void setOnRecyclerItemClick(int position);
    }
    public void setOnRecyclerItemClick(HomeAdapter.onRecyclerItemClick onRecyclerItemClick) {
        this.onRecyclerItemClick = onRecyclerItemClick;
    }
}
