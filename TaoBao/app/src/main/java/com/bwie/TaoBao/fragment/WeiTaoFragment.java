package com.bwie.TaoBao.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.adapter.ListViewAdapter;
import com.bwie.TaoBao.bean.GcBean;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/8/31 17:04
 */

public class WeiTaoFragment extends Fragment{

    public static int mPosition;
    private ListView listView;
    private ListViewAdapter adapter;
    private ListViewFragment myFragment;
    private String[] strs;
    private List<GcBean.DatasBean.ClassListBean> class_list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=View.inflate(getActivity(),R.layout.weitaofragment,null);
        listView= (ListView) view.findViewById(R.id.listview);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();

    }
    //获取后台数据
    private void getData() {
        String url="http://169.254.64.79/mobile/index.php?act=goods_class";
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                GcBean gcBean = gson.fromJson(result, GcBean.class);
                class_list = gcBean.getDatas().getClass_list();
                adapter = new ListViewAdapter(getContext(),class_list);
                listView.setAdapter(adapter);
                addFragment();
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

    //动态添加Fragment
    private void addFragment() {
        //创建MyFragment对象
        myFragment = new ListViewFragment();
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                .beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, myFragment);
        //通过bundle传值给MyFragment
        Bundle bundle = new Bundle();
        bundle.putString(ListViewFragment.TAG, class_list.get(0).getGc_id());
        myFragment.setArguments(bundle);
        fragmentTransaction.commit();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //拿到当前位置
                mPosition = position;
                //即使刷新adapter
                adapter.notifyDataSetChanged();
                for (int i = 0; i < class_list.size(); i++) {
                    myFragment = new ListViewFragment();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager()
                            .beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container, myFragment);
                    Bundle bundle = new Bundle();
                    bundle.putString(ListViewFragment.TAG, class_list.get(position).getGc_id());
                    myFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            }
        });
    }


}
