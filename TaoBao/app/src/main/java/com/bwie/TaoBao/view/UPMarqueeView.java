package com.bwie.TaoBao.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import com.bwie.TaoBao.R;

import java.util.List;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/8 13:51
 */

public class UPMarqueeView extends ViewFlipper {
    private Context mContext;
    private boolean isSetAnimDuration = false;
    private int interval = 3000;//滑动的间隙。
    private int animDuration = 500;//动画时间

    public UPMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int i) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_in);
        if (isSetAnimDuration)
            animIn.setDuration(animDuration);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, R.anim.anim_marquee_out);
        if (isSetAnimDuration)
            animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }
    /**
     * 设置循环滚动的监听
     */
    public void setViews(final List<View> views){
        if (views==null||views.size()==0){
            return;
        }
        removeAllViews();
        for (int i = 0; i < views.size(); i++) {
            final int position=i;

            //设置监听
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (onItemClickListener!=null){
                        onItemClickListener.onItemClick(position,views.get(position));
                    }

                }
            });
            addView(views.get(i));
        }
        startFlipping();
    }
    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener{
        void onItemClick(int position ,View view);
    }
}
