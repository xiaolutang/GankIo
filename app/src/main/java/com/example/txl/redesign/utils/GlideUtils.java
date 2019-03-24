package com.example.txl.redesign.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

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

        Glide.with( context ).setDefaultRequestOptions( requestOptions ).load( url ).into(imageView);
    }
}
