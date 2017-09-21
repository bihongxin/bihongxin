package com.bwie.TaoBao.activity;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioGroup;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.fragment.HomeFragment;
import com.bwie.TaoBao.fragment.MessageFragment;
import com.bwie.TaoBao.fragment.MyFragment;
import com.bwie.TaoBao.fragment.ShoppingFragment;
import com.bwie.TaoBao.fragment.WeiTaoFragment;

public class MainActivity extends AppCompatActivity {
    FrameLayout frame;
    RadioGroup group;
    private Fragment f1,f2,f3,f4,f5;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        frame= (FrameLayout) findViewById(R.id.frame);
        group= (RadioGroup) findViewById(R.id.rg_group);
        addFragment();
    }
    //动态添加Fragment
    private void addFragment() {
        manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        //默认选中首页
        group.check(R.id.rb_shouye);
        f1 =new HomeFragment();
        transaction.replace(R.id.frame, f1);
        transaction.commit();

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId){
                    case R.id.rb_shouye:
                        FragmentTransaction transaction2 = manager.beginTransaction();
                        hideFragment(transaction2);
                        f1 =new HomeFragment();
                        transaction2.replace(R.id.frame, f1);
                        transaction2.commit();
                        break;
                    case R.id.rb_weitao:
                        FragmentTransaction transaction3 = manager.beginTransaction();
                        hideFragment(transaction3);
                        f2 =new WeiTaoFragment();
                        transaction3.replace(R.id.frame, f2);
                        transaction3.commit();
                        break;
                    case R.id.rb_xiaoxi:
                        FragmentTransaction transaction4 = manager.beginTransaction();
                        hideFragment(transaction4);
                        f3 =new MessageFragment();
                        transaction4.replace(R.id.frame, f3);
                        transaction4.commit();
                        break;
                    case R.id.rb_gouwuche:
                        FragmentTransaction transaction5 = manager.beginTransaction();
                        hideFragment(transaction5);
                        f4 =new ShoppingFragment();
                        transaction5.replace(R.id.frame, f4);
                        transaction5.commit();
                        break;
                    case R.id.rb_my:
                        FragmentTransaction transaction6 = manager.beginTransaction();
                        hideFragment(transaction6);
                        f5 =new MyFragment();
                        transaction6.replace(R.id.frame, f5);
                        transaction6.commit();
                        break;
                }
            }
        });
    }
    //隐藏Fragment的方法
    private void hideFragment(FragmentTransaction transaction) {
        if (f1 != null) {
            transaction.hide(f1);//隐藏方法也可以实现同样的效果，不过我一般使用去除
            //transaction.remove(f1);
        }
        if (f2 != null) {
            transaction.hide(f2);
            //transaction.remove(f2);
        }
        if (f3 != null) {
            transaction.hide(f3);
            //transaction.remove(f3);
        }
        if (f4 != null) {
            transaction.hide(f4);
            //transaction.remove(f4);
        }
        if (f5 != null) {
            transaction.hide(f5);
            //transaction.remove(f5);
        }
    }

}
