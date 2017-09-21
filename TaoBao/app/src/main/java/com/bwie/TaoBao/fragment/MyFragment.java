package com.bwie.TaoBao.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.activity.LoginActivity;
import com.bwie.TaoBao.activity.SettingActivity;
import com.bwie.TaoBao.adapter.MeAdapter;
import com.bwie.TaoBao.util.SharedPreferencesUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/8/31 17:04
 */

public class MyFragment extends Fragment{

    private TextView tv_setting;
    private XRecyclerView xlv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(),R.layout.mefragment,null);
        tv_setting = (TextView) view.findViewById(R.id.tv_setting);
        xlv = (XRecyclerView) view.findViewById(R.id.xlv);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferencesUtils utils = SharedPreferencesUtils.getUtil();
        boolean islogin = (boolean) utils.getKey(getActivity(), "login", true);
        if(islogin){
            utils.insertKey(getActivity(),"login",false);
            Intent intent=new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        }else{
            initView();
            setting();
        }

    }

    //设置界面
    private void setting() {
        tv_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(), SettingActivity.class);
                startActivity(intent);
            }
        });
    }

    //加载布局
    private void initView() {
        LinearLayoutManager manager=new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        xlv.setLayoutManager(manager);
        MeAdapter meAdapter=new MeAdapter(getContext());
        xlv.setAdapter(meAdapter);
    }

}
