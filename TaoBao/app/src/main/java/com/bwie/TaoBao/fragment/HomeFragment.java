package com.bwie.TaoBao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.activity.GoodsWebView;
import com.bwie.TaoBao.activity.SearchActivity;
import com.bwie.TaoBao.adapter.HomeAdapter;
import com.bwie.TaoBao.bean.Goods;
import com.google.gson.Gson;
import com.google.zxing.WeChatCaptureActivity;
import com.google.zxing.oned.rss.FinderPattern;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.TextHttpResponseHandler;
import com.yalantis.phoenix.PullToRefreshView;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

import bihongxin20170908.bhx.com.swipetorefashlibrary.WaveSwipeRefreshLayout;
import cz.msebera.android.httpclient.Header;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/8/31 17:04
 */

public class HomeFragment extends Fragment{

    private WaveSwipeRefreshLayout pv;
    private LinearLayout include_home;
    private View view;
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
    private Handler handler=new Handler();
    private ImageView iv_search,iv_sao;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view = View.inflate(getActivity(), R.layout.homefragment,null);
        }
        ViewGroup group = (ViewGroup) container.getParent();
        if(group!=null){
            group.removeView(view);
        }
        pv = (WaveSwipeRefreshLayout) view.findViewById(R.id.pv);
        include_home = (LinearLayout) view.findViewById(R.id.include_home);
        iv_search = (ImageView) view.findViewById(R.id.iv_search);
        recyclerView= (RecyclerView) view.findViewById(R.id.recyclerview);
        iv_sao= (ImageView) view.findViewById(R.id.iv_sao);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onRefash();
        getServiceData();
        search();
        code();

    }
    //二维码扫描
    private void code() {
        iv_sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), WeChatCaptureActivity.class);
                startActivityForResult(intent,100);
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){

        }
    }

    //搜索界面
    private void search(){
        iv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }
    //下拉刷新动画
    private void onRefash() {
        pv.setWaveColor(Color.argb(200,255,0,0));
        pv.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                include_home.setVisibility(View.GONE);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pv.setRefreshing(false);
                        include_home.setVisibility(View.VISIBLE);
                    }
                },1500);
            }
        });


    }
    //获取数据
    private void getServiceData() {
        final String url="http://m.yunifang.com/yunifang/mobile/home";
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                Goods goods = gson.fromJson(result, Goods.class);
                final Goods.DataBean data = goods.getData();
                LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(manager);
                recyclerView.setAdapter(homeAdapter=new HomeAdapter(getContext(),data));
                homeAdapter.setOnRecyclerItemClick(new HomeAdapter.onRecyclerItemClick() {
                    @Override
                    public void setOnRecyclerItemClick(int position) {
                        Intent intent=new Intent(getActivity(), GoodsWebView.class);
                        intent.putExtra("url",data.getAd1().get(position).getAd_type_dynamic_data());
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


}
