package com.bwie.TaoBao.banner;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.youth.banner.loader.ImageLoader;

/**
 * 1.类的用途
 * 2.@authorDell
 * 3.@date2017/9/6 15:15
 */

public class BannerImage extends ImageLoader{

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        Glide.with(context).load(path).into(imageView);
    }
}
