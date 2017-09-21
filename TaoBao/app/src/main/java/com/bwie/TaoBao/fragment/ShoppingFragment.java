package com.bwie.TaoBao.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.activity.LoginActivity;
import com.bwie.TaoBao.activity.OrderActivity;
import com.bwie.TaoBao.adapter.ExpandableAdapter;
import com.bwie.TaoBao.bean.ChildBean;
import com.bwie.TaoBao.bean.GroupBean;
import com.bwie.TaoBao.bean.ShoppingBean;
import com.bwie.TaoBao.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/8/31 17:04
 */

public class ShoppingFragment extends Fragment {

    private TextView shopping_tv_num;
    private TextView shopping_tv_edit;
    private ExpandableListView shopping_elv;
    private Handler handler = new Handler();
    private SharedPreferencesUtils utils;
    private SwipeRefreshLayout shopping_srl;
    private CheckBox select_all;
    private List<GroupBean> gList;
    private ExpandableAdapter adapter;
    private Button btn_sum;
    private TextView tv_sum_price;
    private List<ChildBean> clist;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.shoppingfragment, null);
        shopping_tv_num = (TextView) view.findViewById(R.id.shopping_tv_num);
        btn_sum = (Button) view.findViewById(R.id.btn_sum);
        shopping_tv_edit = (TextView) view.findViewById(R.id.shopping_tv_edit);
        tv_sum_price = (TextView) view.findViewById(R.id.tv_sum_price);
        shopping_elv = (ExpandableListView) view.findViewById(R.id.shopping_elv);
        shopping_srl = (SwipeRefreshLayout) view.findViewById(R.id.shopping_srl);
        select_all = (CheckBox) view.findViewById(R.id.select_all);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        utils = SharedPreferencesUtils.getUtil();
        boolean islogin = (boolean) utils.getKey(getActivity(), "login", true);
        if (islogin) {
            utils.insertKey(getActivity(), "login", false);
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
        } else {
            shopping_srl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            shopping_srl.setRefreshing(false);
                        }
                    }, 500);
                }
            });
            getShoppingData();
            selsetAll();
            settlementPrice();
        }
    }
    //结算价格
    private void settlementPrice() {
        clist = new ArrayList<>();
        btn_sum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(btn_sum.getText().toString().trim().contains("0")){
                    Toast.makeText(getActivity(), "您没有选择宝贝哦~", Toast.LENGTH_SHORT).show();
                }else{
                    for (int i = 0; i < gList.size(); i++) {
                        for (int j = 0; j < gList.get(i).getClist().size(); j++) {
                            boolean c_ischecked = gList.get(i).getClist().get(j).isC_ischecked();
                            if(c_ischecked){
                                clist.add(gList.get(i).getClist().get(j));
                            }
                        }
                    }
                    Intent intent=new Intent(getActivity(), OrderActivity.class);
                    intent.putExtra("clist", (Serializable) clist);
                    startActivity(intent);
                }
            }
        });
    }

    //获取购物车数据
    public void getShoppingData() {
        //给expandablelistview适配数据
        String key = (String) utils.getKey(getActivity(), "key", "");
        String url = "http://169.254.64.79/mobile/index.php?act=member_cart&op=cart_list";
        RequestParams params = new RequestParams(url);
        params.addBodyParameter("key", key);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                List<ShoppingBean.DatasBean.CartListBean> cart_list = gson.fromJson(result, ShoppingBean.class).getDatas().getCart_list();
                gList = new ArrayList<>();
                for (int i = 0; i < cart_list.size(); i++) {
                    GroupBean gb = new GroupBean();
                    gb.setG_ischecked(false);
                    gb.setGroup_name(cart_list.get(i).getStore_name());
                    List<ShoppingBean.DatasBean.CartListBean.GoodsBean> goods = cart_list.get(i).getGoods();
                    List<ChildBean> cList = new ArrayList<>();
                    for (int j = 0; j < goods.size(); j++) {
                        ChildBean childBean = new ChildBean();
                        childBean.setC_ischecked(false);
                        childBean.setC_image(goods.get(j).getGoods_image_url());
                        childBean.setC_name(goods.get(j).getGoods_name());
                        childBean.setC_price(goods.get(j).getGoods_price());
                        childBean.setC_number(goods.get(j).getGoods_num());
                        childBean.setC_cardid(goods.get(j).getCart_id());
                        cList.add(childBean);
                    }
                    gb.setClist(cList);
                    gList.add(gb);
                }
                adapter = new ExpandableAdapter(gList, getContext());
                shopping_elv.setAdapter(adapter);
                //设置Group默认展开
                shopping_elv.setGroupIndicator(null);
                int groupCount = adapter.getGroupCount();
                for (int i = 0; i < groupCount; i++) {
                    shopping_elv.expandGroup(i);
                }
                //一级控制全选
                adapter.setOnCheckedClick(new ExpandableAdapter.OnCheckedClick() {
                    @Override
                    public void setOnCheckedClick(boolean flag) {
                        select_all.setChecked(flag);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void setOnCheckedCount(int count) {
                        btn_sum.setText("结算(" + count + ")");
                        shopping_tv_num.setText("(" + count + ")");
                    }

                    @Override
                    public void setOnCheckedPrice(double price) {
                        tv_sum_price.setText("¥ " + price);
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

    //全选
    public void selsetAll() {
        //全选控制一级
        select_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = select_all.isChecked();
                select_all.setChecked(checked);
                if (checked) {
                    for (int i = 0; i < gList.size(); i++) {
                        gList.get(i).setG_ischecked(true);
                        for (int j = 0; j < gList.get(i).getClist().size(); j++) {
                            gList.get(i).getClist().get(j).setC_ischecked(true);
                        }
                    }
                } else {
                    for (int i = 0; i < gList.size(); i++) {
                        gList.get(i).setG_ischecked(false);
                        for (int j = 0; j < gList.get(i).getClist().size(); j++) {
                            gList.get(i).getClist().get(j).setC_ischecked(false);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
                sumPrice();
            }
        });

    }

    //算价格数量的方法
    public void sumPrice() {
        int count = 0;
        double price = 0.0;
        for (int i = 0; i < gList.size(); i++) {
            for (int j = 0; j < gList.get(i).getClist().size(); j++) {
                boolean c_ischecked = gList.get(i).getClist().get(j).isC_ischecked();
                if (c_ischecked) {
                    int i1 = Integer.parseInt(gList.get(i).getClist().get(j).getC_number());
                    double v = Double.parseDouble(gList.get(i).getClist().get(j).getC_price());
                    count += i1;
                    price += v * i1;
                }
            }
        }
        btn_sum.setText("结算(" + count + ")");
        shopping_tv_num.setText("(" + count + ")");
        tv_sum_price.setText("¥ " + price);
    }
}