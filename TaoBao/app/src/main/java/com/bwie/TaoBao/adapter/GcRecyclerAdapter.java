package com.bwie.TaoBao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bwie.TaoBao.R;
import com.bwie.TaoBao.bean.GcTreeBean;
import com.bwie.TaoBao.bean.GcTwoBean;
import com.bwie.TaoBao.bean.Goods;
import com.bwie.TaoBao.view.XCFlowLayout;
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/12 13:55
 */

public class GcRecyclerAdapter extends RecyclerView.Adapter<GcRecyclerAdapter.GcViewHolder>{

    private Context context;
    private List<GcTwoBean.DatasBean.ClassListBean> class_list;
    private String str;

    public GcRecyclerAdapter(Context context, List<GcTwoBean.DatasBean.ClassListBean> class_list,String str) {
        this.context = context;
        this.class_list = class_list;
        this.str=str;
    }

    @Override
    public GcRecyclerAdapter.GcViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.gc_recycler_item,parent,false);
        GcViewHolder gcViewHolder=new GcViewHolder(view);
        return gcViewHolder;
    }

    @Override
    public void onBindViewHolder(final GcViewHolder holder, int position) {
        holder.tv_type.setText("---"+class_list.get(position).getGc_name()+"---");
        String str2=class_list.get(position).getGc_id();
        String url="http://169.254.64.79/mobile/index.php?act=goods_class&gc_id="+str+"&gc_id="+str2;

        RequestParams params=new RequestParams(url);
        Callback.Cancelable cancelable = x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Gson gson = new Gson();
                GcTreeBean gcTreeBean = gson.fromJson(result, GcTreeBean.class);
                List<GcTreeBean.DatasBean.ClassListBean> list = gcTreeBean.getDatas().getClass_list();
                ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                lp.leftMargin = 10;
                lp.rightMargin = 10;
                lp.topMargin = 10;
                lp.bottomMargin = 10;
                for (int i = 0; i < list.size(); i++) {
                    TextView view = new TextView(context);
                    view.setText(list.get(i).getGc_name());
                    view.setTextColor(Color.BLACK);
                    view.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.text_shape));
                    holder.xcf.addView(view, lp);
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


    @Override
    public int getItemCount() {
        return class_list.size();
    }
    class GcViewHolder extends RecyclerView.ViewHolder{
        TextView tv_type;
        XCFlowLayout xcf;
        public GcViewHolder(View itemView) {
            super(itemView);
            tv_type= (TextView) itemView.findViewById(R.id.tv_type);
            xcf= (XCFlowLayout) itemView.findViewById(R.id.costrom_xcf);
        }
    }

}
