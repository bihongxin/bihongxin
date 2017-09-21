package com.bwie.TaoBao.activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.AuthTask;
import com.alipay.sdk.app.PayTask;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.Address;
import com.bwie.TaoBao.bean.ChildBean;
import com.bwie.TaoBao.pay.PayResult;
import com.bwie.TaoBao.pay.SignUtils;
import com.bwie.TaoBao.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity implements View.OnClickListener, AMapLocationListener {

    private SharedPreferencesUtils utils;
    private TextView address_name;
    private TextView address_phone;
    private TextView address;
    private TextView tv_group;
    private ListView order_lv;
    private Button submit_order;
    private List<ChildBean> clist;
    private String key;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Button btn_true;
    private static final int SDK_PAY_FLAG = 1;
    // 商户私钥，pkcs8格式
    public static final String RSA_PRIVATE = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAM" +
            "/KCxg/OIj6er2GEig0DOkHqBSzEPHGigMbTXP1k2nrxEHeE59xOOuy" +
            "ovQH/A1LgbuVKyOac3uAN4GXIBEoozRVzDhu5dobeNm48BPcpYSAfvN3K" +
            "/5GLacvJeENqsiBx8KufM/9inlHaDRQV7WhX1Oe2ckat1EkdHwxxQgc" +
            "36NhAgMBAAECgYEAwn3sWpq6cUR65LD8h9MIjopTImTlpFjgz72bhsHD" +
            "ZK6A+eJDXcddrwh7DI34t/0IBqu+QEoOc/f0fIEXS9hMwTvFY59XG7M8" +
            "M6SdeaAbemrGmZ1IdD6YDmpbQFHn2ishaYF0YDZIkBS3WLDFrtk/efaar" +
            "BCpGAVXeEiVQE4LewECQQD5W1rpkq+dHDRzzdtdi1bJ479wun5CfmVDV" +
            "b2CJH7Iz0t8zyp/iEVV2QELnxZMphmoSzKaLXutTTj2OImpzCtRAkEA1" +
            "VMxG6nQq9NkU51H1+I3mlUXRZ0XeFA1GFJ7xWpNRAVhEWlDz2zy9v/g" +
            "X+RFpNC3f5uznycas70Xp78ew43TEQJAZFFqi9mlqTF1sLk67bFnIyX" +
            "rGPEOZrXvC13tNfR0xVkQZ4/46wHp0xXQo9pG4GNaoyhNnVV7EkelCPn" +
            "J+HPZYQJAQh6T9QgQZoGR8hyovPAf3dUL7oa/VIo/urcuJ8VIB5JHQNdI" +
            "rk0NjaNHj1E4iNosVgATj3vWWel9IIArb99QkQJAKvfm78lwnImtg5IM6" +
            "04hdn/Wu1XF8tpxsKLWcnfchMr0bM9rCmKmhAY+wdmqSyPZRiNb1QaaaD" +
            "TqJxLy6AnQ+Q==";
    private TextView loacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();
        showAddress();
        showGoods();
        getLocation();
    }
    //展示商品信息
    private void showGoods() {
        Intent intent=getIntent();
        clist = (List<ChildBean>) intent.getSerializableExtra("clist");
        MyAdapter m=new MyAdapter(clist);
        order_lv.setAdapter(m);
    }
    //位置定位
    private void getLocation(){
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位监听
        mlocationClient.setLocationListener(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }
    //初始化控件
    private void initView() {
        address_name = (TextView) findViewById(R.id.address_name);
        address_phone = (TextView) findViewById(R.id.address_phone);
        address = (TextView) findViewById(R.id.address);
        loacation = (TextView) findViewById(R.id.location);
        tv_group = (TextView) findViewById(R.id.tv_group);
        order_lv = (ListView) findViewById(R.id.order_lv);
        submit_order = (Button) findViewById(R.id.submit_order);
        submit_order.setOnClickListener(this);
    }
    //展示收货地址的方法
    private void showAddress() {
        utils = SharedPreferencesUtils.getUtil();
        key = (String) utils.getKey(OrderActivity.this, "key", "");
        String url="http://169.254.64.79/mobile/index.php?act=member_address&op=address_list";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("key", key);
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson=new Gson();
                List<Address.DatasBean.AddressListBean> address_list = gson.fromJson(result, Address.class).getDatas().getAddress_list();
                address_name.setText(address_list.get(0).getTrue_name());
                address_phone.setText(address_list.get(0).getMob_phone());
                address.setText(address_list.get(0).getAddress()+" "+address_list.get(0).getArea_info());
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
    //提交按钮
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.submit_order:
                submitInfo();
                shoppingFirst();
                break;
        }

    }

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                amapLocation.getLatitude();//获取纬度
                amapLocation.getLongitude();//获取经度
                amapLocation.getAccuracy();//获取精度信息
                String address = amapLocation.getAddress();//获取地址信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(amapLocation.getTime());
                df.format(date);//定位时间
                loacation.setText("定位："+address);
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError","location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
            }
        }
    }


    //地址数据适配器
    class MyAdapter extends BaseAdapter {

        private List<ChildBean> list;

        public MyAdapter(List<ChildBean> list) {
            this.list = list;
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
                convertView=View.inflate(OrderActivity.this,R.layout.order_item,null);
                vh.tv_goods_name= (TextView) convertView.findViewById(R.id.tv_goods_name);
                vh.iv_goods= (ImageView) convertView.findViewById(R.id.iv_goods);
                vh.tv_goods_price= (TextView) convertView.findViewById(R.id.tv_goods_price);
                vh.goods_number= (TextView) convertView.findViewById(R.id.goods_number);
                convertView.setTag(vh);
            }else{
                vh= (ViewHolder) convertView.getTag();
            }
            Picasso.with(OrderActivity.this).load(list.get(position).getC_image()).into(vh.iv_goods);
            vh.tv_goods_name.setText(list.get(position).getC_name());
            vh.tv_goods_price.setText("¥"+list.get(position).getC_price());
            vh.tv_goods_price.setTextColor(Color.RED);
            vh.goods_number.setText("×"+list.get(position).getC_number());
            return convertView;
        }
        class ViewHolder{
            TextView tv_goods_name,tv_goods_price;
            TextView goods_number;
            ImageView iv_goods;
        }
    }
    //提交方式
    public void submitInfo(){
        View view=View.inflate(OrderActivity.this,R.layout.order_popupwindow,null);
        btn_true = (Button) view.findViewById(R.id.btn_true);
        btn_true.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pay();
            }
        });
        PopupWindow pw=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        pw.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        //设置焦点
        pw.setFocusable(true);
        //设置触摸
        pw.setTouchable(true);
        //显示popupwindow
        View rootview = View.inflate(OrderActivity.this,R.layout.activity_order, null);
        pw.showAtLocation(rootview, Gravity.BOTTOM, 0, 0);
    }
    //购买商品第一步
    public void shoppingFirst(){
        String sb="";
        for (int i = 0; i < clist.size(); i++) {
            if(i==0){
                sb=clist.get(i).getC_cardid()+"|"+clist.get(i).getC_number();
            }else{
                sb+=","+clist.get(i).getC_cardid()+"|"+clist.get(i).getC_number();
            }
        }
        String url="http://169.254.64.79/mobile/index.php?act=member_buy&op=buy_step1";
        RequestParams params=new RequestParams(url);
        params.addParameter("key",key);
        params.addParameter("cart_id",sb);
        params.addParameter("ifcart","1");
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object=new JSONObject(result);
                    int code = object.optInt("code");
                    if(code==200){
                        Toast.makeText(OrderActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(OrderActivity.this, "提交失败", Toast.LENGTH_SHORT).show();
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

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        Toast.makeText(OrderActivity.this, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            Toast.makeText(OrderActivity.this, "支付结果确认中", Toast.LENGTH_SHORT).show();

                        } else {
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                            Toast.makeText(OrderActivity.this, "支付失败", Toast.LENGTH_SHORT).show();

                        }
                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    /**
     * call alipay sdk pay. 调用SDK支付
     */
    public void pay() {
        paySignFromServer();
        //客户端进行订单的签名
        //paySignFromClient();
    }

    private void paySignFromServer() {
        //添加参数，暂时写死，项目中，从页面获取
        String url = "http://169.254.26.25:8080/PayServer/AlipayDemo";
        StringBuffer sb = new StringBuffer("?");
        sb.append("subject=");
        sb.append("来自Server测试的商品");
        sb.append("&");
        sb.append("body=");
        sb.append("该测试商品的详细描述");
        sb.append("&");
        sb.append("total_fee=");
        sb.append("0.01");
        url = url + sb.toString();
        //到服务器进行订单加密
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                final String signResult = result;
                Log.i("TAG", signResult);

                Runnable payRunnable = new Runnable() {
                    @Override
                    public void run() {
                        // 构造PayTask 对象
                        PayTask alipay = new PayTask(OrderActivity.this);
                        // 调用支付接口，获取支付结果
                        String result = alipay.pay(signResult, true);
                        Log.i("TAG", "走了pay支付方法.............");

                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                };

                Thread payThread = new Thread(payRunnable);
                payThread.start();
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
    /**
     * sign the order info. 对订单信息进行签名
     *
     * @param content 待签名订单信息
     */
    private String sign(String content) {
        return SignUtils.sign(content, RSA_PRIVATE);
    }

    /**
     * get the sign type we use. 获取签名方式
     */
    private String getSignType() {
        return "sign_type=\"RSA\"";
    }
}
