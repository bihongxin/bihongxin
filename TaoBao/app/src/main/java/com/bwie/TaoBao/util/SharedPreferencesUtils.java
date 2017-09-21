package com.bwie.TaoBao.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/8/31 14:36
 */

public class SharedPreferencesUtils {
    private static SharedPreferencesUtils spUtils;
    private SharedPreferencesUtils() {
    }
    public static SharedPreferencesUtils getUtil(){
        if(spUtils==null){
            spUtils=new SharedPreferencesUtils();
        }
        return spUtils;
    }
    //存值的方法
    public void insertKey(Context context,String key,Object object){
        SharedPreferences sp=context.getSharedPreferences("SP",Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if(object instanceof Boolean){
            edit.putBoolean(key, (Boolean) object);
            edit.commit();
        }else if(object instanceof String){
            edit.putString(key, (String) object);
            edit.commit();
        }
    }
    //取值的方法
    public Object getKey(Context context,String key,Object object){
        SharedPreferences sp=context.getSharedPreferences("SP",Context.MODE_PRIVATE);
        if(object instanceof Boolean){
            boolean b = sp.getBoolean(key, (Boolean) object);
            return b;
        }else if(object instanceof String){
            String s = sp.getString(key, (String) object);
            return s;
        }
        return null;
    }
}
