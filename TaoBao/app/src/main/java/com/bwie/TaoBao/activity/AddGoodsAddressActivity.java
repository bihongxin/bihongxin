package com.bwie.TaoBao.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.util.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class AddGoodsAddressActivity extends AppCompatActivity {

    private EditText address_name;
    private EditText address_phone;
    private EditText address_city;
    private EditText address_area;
    private EditText address;
    private EditText address_info;
    private SharedPreferencesUtils utils;
    private Button btn_com;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods_address);
        initView();
    }

    //初始化控件
    private void initView() {
        address_name = (EditText) findViewById(R.id.address_name);
        address_phone = (EditText) findViewById(R.id.address_phone);
        address_city = (EditText) findViewById(R.id.address_city);
        address_area = (EditText) findViewById(R.id.address_area);
        address = (EditText) findViewById(R.id.address);
        address_info = (EditText) findViewById(R.id.address_info);
        btn_com = (Button) findViewById(R.id.btn_com);
        btn_com.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                upInfo();
            }
        });
    }
    //上传订单信息
    private void upInfo() {
        utils = SharedPreferencesUtils.getUtil();
        String key = (String) utils.getKey(AddGoodsAddressActivity.this, "key", "");
        String url="http://169.254.64.79/mobile/index.php?act=member_address&op=address_add";
        RequestParams params=new RequestParams(url);
        params.addBodyParameter("key",key);
        params.addBodyParameter("true_name",address_name.getText().toString().trim());
        params.addBodyParameter("mob_phone",address_phone.getText().toString().trim());
        params.addBodyParameter("city_id",address_city.getText().toString().trim());
        params.addBodyParameter("area_id",address_area.getText().toString().trim());
        params.addBodyParameter("address",address.getText().toString().trim());
        params.addBodyParameter("area_info",address_info.getText().toString().trim());
        x.http().post(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                JSONObject object= null;
                try {
                    object = new JSONObject(result);
                    int code = object.optInt("code");
                    if(code==200){
                        Toast.makeText(AddGoodsAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddGoodsAddressActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
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
}
