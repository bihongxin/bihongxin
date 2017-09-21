package com.bwie.TaoBao.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.application.MyApplication;
import com.bwie.TaoBao.bean.WatchBean;
import com.bwie.TaoBao.dao.WatchBeanDao;
import com.bwie.TaoBao.util.SharedPreferencesUtils;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;
import java.util.List;


public class SearchActivity extends AppCompatActivity{

    private ImageView iv_search_ruturn;
    private Button btn_search;
    private EditText et_search;
    private ListView search_lv;
    private WatchBeanDao watchBeanDao;
    private List<WatchBean> wblist;
    private MyAdapter m;
    private SharedPreferencesUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        initView();
        getServiceData();
    }

    private void initView() {
        btn_search = (Button) findViewById(R.id.btn_search);
        et_search = (EditText) findViewById(R.id.et_search);
        iv_search_ruturn = (ImageView) findViewById(R.id.iv_search_reutrn);
        iv_search_ruturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        search_lv = (ListView) findViewById(R.id.search_lv);

    }
    //请求后台数据
    private void getServiceData() {
        utils=SharedPreferencesUtils.getUtil();
        watchBeanDao = MyApplication.getInstances().getDaoSession().getWatchBeanDao();

        final String url="http://169.254.64.79/mobile/index.php?act=goods&op=goods_list&page=100";
        RequestParams params=new RequestParams(url);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(final String result) {
                btn_search.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        utils.insertKey(SearchActivity.this,"search",true);
                        String trim = et_search.getText().toString().trim();
                        /*WatchBean wb=new WatchBean(trim,result);
                        watchBeanDao.insert(wb);*/
                        if("劳力士".equals(trim)){
                            Intent intent=new Intent(SearchActivity.this,SearchItemActivity.class);
                            intent.putExtra("json",result);
                            startActivity(intent);
                        }else{
                            Toast.makeText(SearchActivity.this, "没有此商品~", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                boolean search = (boolean) utils.getKey(SearchActivity.this, "search", false);
                //历史信息
                /*if(search==true){
                    wblist = watchBeanDao.loadAll();
                    m = new MyAdapter(wblist);
                    search_lv.setAdapter(m);
                }else{
                    m.notifyDataSetChanged();
                }*/

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
    //适配器
    private class MyAdapter extends BaseAdapter{
        List<WatchBean> wblist;

        public MyAdapter(List<WatchBean> wblist) {
            this.wblist = wblist;
        }

        @Override
        public int getCount() {
            return wblist.size();
        }

        @Override
        public Object getItem(int position) {
            return wblist.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            viewHolder vh=new viewHolder();
            if(convertView==null){
                convertView=View.inflate(SearchActivity.this,R.layout.activity_search_item,null);
                vh.search_tv_goods_name= (TextView) convertView.findViewById(R.id.search_tv_goods_name);
                convertView.setTag(vh);
            }else{
                vh= (viewHolder) convertView.getTag();
            }
            vh.search_tv_goods_name.setText("劳力士");
            return convertView;
        }
        class viewHolder{
            TextView search_tv_goods_name;
        }
    }
}
