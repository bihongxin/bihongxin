package com.bwie.TaoBao.activity;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.TaoBao.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class RegisterActivity extends AppCompatActivity {

    private EditText register_name;
    private EditText register_password;
    private EditText register_password_two;
    private EditText register_email;
    private ImageView register_return;
    private Button btn_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        registerUser();
    }
    private void initView() {
        register_name = (EditText) findViewById(R.id.register_name);
        register_password = (EditText) findViewById(R.id.register_password);
        register_return= (ImageView) findViewById(R.id.register_return);
        register_password_two = (EditText) findViewById(R.id.register_pasword_two);
        register_email = (EditText) findViewById(R.id.register_email);
        btn_register = (Button) findViewById(R.id.btn_register);
    }
    //注册用户信息
    private void registerUser() {

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String url="http://169.254.64.79/mobile/index.php?act=login&op=register";
                RequestParams params=new RequestParams(url);
                params.addBodyParameter("username",register_name.getText().toString().trim());
                params.addBodyParameter("password",register_password.getText().toString().trim());
                params.addBodyParameter("password_confirm",register_password_two.getText().toString().trim());
                params.addBodyParameter("email ",register_email.getText().toString().trim());
                params.addBodyParameter("client","android");
                x.http().post(params, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            JSONObject object = new JSONObject(result);
                            int code = object.optInt("code");
                            if(code==200){
                                Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
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
        register_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
