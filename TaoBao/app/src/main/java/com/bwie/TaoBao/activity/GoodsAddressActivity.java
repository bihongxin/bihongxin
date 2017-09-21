package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.Address;
import com.bwie.TaoBao.bean.Goods;
import com.bwie.TaoBao.util.SharedPreferencesUtils;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

public class GoodsAddressActivity extends AppCompatActivity {

    private Button btn_addAddress;
    private ListView lv_address;
    private SharedPreferencesUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_address);
        btn_addAddress = (Button) findViewById(R.id.btn_addAddress);
        lv_address = (ListView) findViewById(R.id.lv_address);
        btn_addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GoodsAddressActivity.this,AddGoodsAddressActivity.class);
                startActivity(intent);
            }
        });

        showAddress();
    }
    //展示收货地址的方法
    private void showAddress() {
        utils = SharedPreferencesUtils.getUtil();
        String key = (String) utils.getKey(GoodsAddressActivity.this, "key", "");
        String url="http://169.254.64.79/mobile/index.php?act=member_address&op=address_list";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("key",key);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                List<Address.DatasBean.AddressListBean> address_list = gson.fromJson(result, Address.class).getDatas().getAddress_list();
                MyAdapter myAdapter=new MyAdapter(address_list);
                lv_address.setAdapter(myAdapter);
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
    //地址数据适配器
    class MyAdapter extends BaseAdapter{

        private List<Address.DatasBean.AddressListBean> list;

        public MyAdapter(List<Address.DatasBean.AddressListBean> address_list) {
            this.list = address_list;
        }

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
            ViewHolder vh=new ViewHolder();
            if(convertView==null){
                convertView=View.inflate(GoodsAddressActivity.this,R.layout.address_item,null);
                vh.address_name= (TextView) convertView.findViewById(R.id.address_name);
                vh.address_phone= (TextView) convertView.findViewById(R.id.address_phone);
                vh.address= (TextView) convertView.findViewById(R.id.address);
                convertView.setTag(vh);
            }else{
                vh= (ViewHolder) convertView.getTag();
            }
            vh.address_name.setText(list.get(position).getTrue_name());
            vh.address_phone.setText(list.get(position).getMob_phone());
            vh.address.setText(list.get(position).getAddress()+" "+list.get(position).getArea_info());
            return convertView;
        }
        class ViewHolder{
            TextView address_name,address_phone,address;
        }
    }
}
