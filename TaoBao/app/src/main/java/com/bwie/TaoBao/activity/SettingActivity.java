package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.bwie.TaoBao.R;

public class SettingActivity extends AppCompatActivity {

    private LinearLayout take_goods_address;
    private Button exit_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        take_goods_address = (LinearLayout) findViewById(R.id.take_goods_address);
        exit_login = (Button) findViewById(R.id.exit_login);
        goodsAddress();
        exitLogin();
    }
    //退出当前账户的方法
    private void exitLogin() {

    }

    //跳转添加收货地址的方法
    private void goodsAddress() {
        take_goods_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SettingActivity.this,GoodsAddressActivity.class);
                startActivity(intent);
            }
        });

    }
}
