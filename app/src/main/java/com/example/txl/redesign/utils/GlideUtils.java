package com.example.txl.redesign.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.example.txl.gankio.R;

/**
 * Copyright (c) 2019, 唐小陆 All rights reserved.
 * author：txl
 * date：2019/3/24
 * description：
 */
public class GlideUtils {
    public static void loadImage(Context context, String url, ImageView imageView, Drawable placeholder){
        RequestOptions requestOptions = new RequestOptions();
        if(placeholder != null){
            requestOptions.placeholder( placeholder );
        }
        requestOptions=requestOptions.format(DecodeFormat.PREFER_RGB_565);

        Glide.with( context ).setDefaultRequestOptions( requestOptions ).load( url ).into(imageView);
    }

    public static void loadImage(Context context, String url, ImageView imageView, boolean useDefaultPlaceholder){
        Drawable placeholderDrawable = null;
        if(useDefaultPlaceholder){
            placeholderDrawable = ContextCompat.getDrawable( context, R.drawable.image_secondfloor );
        }
        loadImage( context,url,imageView, placeholderDrawable);
    }
}
