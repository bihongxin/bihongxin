package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.DetailsGoodsBean;
import com.bwie.TaoBao.bean.Goods;
import com.bwie.TaoBao.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import uk.co.senab.photoview.PhotoView;

public class GoodsActivity extends AppCompatActivity {

    private String goods_id;
    private XRecyclerView goods_xlv;
    private DetailsGoodsBean.DatasBean datas;
    private Button btn_addGoodsCar;
    private SharedPreferencesUtils utils;
    private int goods_num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods);
        utils=SharedPreferencesUtils.getUtil();
        goods_xlv = (XRecyclerView) findViewById(R.id.goods_xlv);
        btn_addGoodsCar = (Button) findViewById(R.id.btn_addGoodsCar);
        Intent intent=getIntent();
        goods_id = intent.getStringExtra("goods_id");
        getDetails();
        //加入购物车
        btn_addGoodsCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addShoppingCar();
            }
        });
    }
    //添加购物车
    private void addShoppingCar() {
        View view=View.inflate(GoodsActivity.this,R.layout.popupwindow_shopping,null);
        PopupWindow pw=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        ImageView iv= (ImageView) view.findViewById(R.id.p_iv_image);
        TextView tv_price= (TextView) view.findViewById(R.id.p_tv_price);
        TextView tv_count= (TextView) view.findViewById(R.id.p_tv_count);
        ImageView p_jian= (ImageView) view.findViewById(R.id.p_jian);
        ImageView p_jia= (ImageView) view.findViewById(R.id.p_jia);
        final TextView p_number = (TextView) view.findViewById(R.id.p_number);
        Button btn_sure= (Button) view.findViewById(R.id.p_sure);
        Picasso.with(GoodsActivity.this).load(datas.getGoods_image()).into(iv);
        tv_price.setText("¥"+datas.getGoods_info().getGoods_price());
        tv_count.setText("库存"+datas.getStore_info().getGoods_count()+"件");
        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置焦点
        pw.setFocusable(true);
        //设置触摸
        pw.setTouchable(true);
        //显示popupwindow
        View rootview = View.inflate(GoodsActivity.this,R.layout.activity_goods, null);
        pw.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
        //数量加减
        p_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goods_num>0){
                    goods_num--;
                    p_number.setText(goods_num+"");
                }
            }
        });
        p_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goods_num++;
                p_number.setText(goods_num+"");
            }
        });
        btn_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="http://169.254.64.79/mobile/index.php?act=member_cart&op=cart_add";
                RequestParams params=new RequestParams(url);
                String key = (String) utils.getKey(GoodsActivity.this, "key","");
                params.addBodyParameter("key",key);
                params.addBodyParameter("goods_id",goods_id);
                String trim = p_number.getText().toString().trim();
                params.addBodyParameter("quantity", trim);
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object=new JSONObject(result);
                            int code = object.optInt("code");
                            if(code==200){
                                Toast.makeText(GoodsActivity.this, "添加购物车成功~", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(GoodsActivity.this, "添加购物车失败~", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
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
        });
    }

    //商品详情
    private void getDetails() {
        String url = "http://169.254.64.79/mobile/index.php?act=goods&op=goods_detail&goods_id=" + goods_id;
        RequestParams params = new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                DetailsGoodsBean detailsGoodsBean = gson.fromJson(result, DetailsGoodsBean.class);
                datas = detailsGoodsBean.getDatas();
                MyXRecyclerViewAdapter m=new MyXRecyclerViewAdapter();
                LinearLayoutManager manager=new LinearLayoutManager(GoodsActivity.this,LinearLayoutManager.VERTICAL,false);
                goods_xlv.setLayoutManager(manager);
                goods_xlv.setAdapter(m);
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

    //XRecyclerView适配器
    public class MyXRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view= LayoutInflater.from(GoodsActivity.this).inflate(R.layout.details_item,parent,false);
            XViewHolder xViewHolder=new XViewHolder(view);
            return xViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            if(holder instanceof XViewHolder){
                ((XViewHolder) holder).goods_price.setTextColor(Color.RED);
                ((XViewHolder) holder).goods_image.setScaleType(ImageView.ScaleType.FIT_XY);
                Picasso.with(GoodsActivity.this).load(datas.getGoods_image()).into(((XViewHolder) holder).goods_image);
                ((XViewHolder) holder).goods_name.setText(datas.getGoods_info().getGoods_name());
                ((XViewHolder) holder).goods_price.setText("¥"+datas.getGoods_info().getGoods_price());

            }

        }

        @Override
        public int getItemCount() {
            return 1;
        }
        class XViewHolder extends RecyclerView.ViewHolder{
            PhotoView goods_image;
            TextView goods_name;
            TextView goods_price;
            public XViewHolder(View itemView) {
                super(itemView);
                goods_image= (PhotoView) itemView.findViewById(R.id.goods_image);
                goods_name= (TextView) itemView.findViewById(R.id.goods_name);
                goods_price= (TextView) itemView.findViewById(R.id.goods_price);
            }
        }
    }

}
