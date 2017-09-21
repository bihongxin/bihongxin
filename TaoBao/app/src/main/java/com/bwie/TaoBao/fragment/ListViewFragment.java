package com.bwie.TaoBao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.adapter.GcRecyclerAdapter;
import com.bwie.TaoBao.bean.GcTwoBean;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/8 20:25
 */

public class ListViewFragment extends Fragment{
    public static final String TAG = "MyFragment";
    private String str;
    private RecyclerView gc_recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.myfragment, null);
        gc_recycler = (RecyclerView) view.findViewById(R.id.gc_recycler);
        //得到数据
        str = getArguments().getString(TAG);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
    }

    private void getData() {
        String url="http://169.254.64.79/mobile/index.php?act=goods_class&gc_id="+str;

        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                GcTwoBean gcTwoBean = gson.fromJson(result, GcTwoBean.class);
                List<GcTwoBean.DatasBean.ClassListBean> class_list = gcTwoBean.getDatas().getClass_list();
                LinearLayoutManager manager=new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                gc_recycler.setLayoutManager(manager);
                GcRecyclerAdapter gc=new GcRecyclerAdapter(getContext(),class_list,str);
                gc_recycler.setAdapter(gc);
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
