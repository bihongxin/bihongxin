package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.Biao;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class SearchItemActivity extends AppCompatActivity {

    private List<Biao.DatasBean.GoodsListBean> list;
    private ListView lv_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_item);
        lv_search= (ListView) findViewById(R.id.lv_search);

        Intent intent=getIntent();
        String json = intent.getStringExtra("json");
        Gson gson=new Gson();
        Biao biao = gson.fromJson(json, Biao.class);
        list = biao.getDatas().getGoods_list();
        lv_search.setAdapter(new MyAdapter());
        lv_search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String goods_id = list.get(position).getGoods_id();
                Intent intent=new Intent(SearchItemActivity.this,GoodsActivity.class);
                intent.putExtra("goods_id",goods_id);
                startActivity(intent);

            }
        });
    }

    //适配器
   public class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if(convertView==null){
                vh=new ViewHolder();
                convertView=View.inflate(SearchItemActivity.this,R.layout.search_item,null);
                vh.iv= (ImageView) convertView.findViewById(R.id.iv_biao);
                vh.tv_name= (TextView) convertView.findViewById(R.id.tv_name_biao);
                vh.tv_price= (TextView) convertView.findViewById(R.id.tv_price_biao);
                convertView.setTag(vh);
            }else{
                vh= (ViewHolder) convertView.getTag();
            }
            Picasso.with(SearchItemActivity.this).load(list.get(position).getGoods_image_url()).into(vh.iv);
            vh.tv_name.setText(list.get(position).getGoods_name());
            vh.tv_price.setText("¥"+list.get(position).getGoods_price());
            vh.tv_price.setTextColor(Color.RED);
            return convertView;
        }
        class ViewHolder{
            ImageView iv;
            TextView tv_name,tv_price;
        }
    }
}
