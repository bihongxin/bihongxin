package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.fragment.ShoppingFragment;
import com.bwie.TaoBao.util.SharedPreferencesUtils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginActivity extends AppCompatActivity {

    private EditText login_name;
    private EditText login_password;
    private Button btn_login;
    private TextView register_intent;
    private String name;
    private String pwd;
    private SharedPreferencesUtils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        utils = SharedPreferencesUtils.getUtil();
        initView();
        loginUser();
    }

    private void initView() {
        login_name = (EditText) findViewById(R.id.login_name);
        login_password = (EditText) findViewById(R.id.login_password);
        btn_login = (Button) findViewById(R.id.btn_login);
        register_intent = (TextView) findViewById(R.id.register_intent);
        register_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    //登录操作
    private void loginUser() {
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = login_name.getText().toString().trim();
                pwd = login_password.getText().toString().trim();
                String url="http://169.254.64.79/mobile/index.php?act=login";
                RequestParams params=new RequestParams(url);
                params.addBodyParameter("username",name);
                params.addBodyParameter("password",pwd);
                params.addBodyParameter("client","android");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object=new JSONObject(result);
                            int code = object.optInt("code");
                            String key = object.optJSONObject("datas").optString("key");
                            utils.insertKey(LoginActivity.this,"key",key);
                            Log.i("登录",key);
                            if(code==200){
                                //SharedPreferencesUtils utils = SharedPreferencesUtils.getUtil();
                                //utils.insertKey(LoginActivity.this,"login",false);
                                finish();
                            }else{
                                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
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
}
