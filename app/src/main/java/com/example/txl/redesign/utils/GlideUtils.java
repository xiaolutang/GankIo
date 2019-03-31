package com.example.txl.redesign.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.txl.gankio.R;
import com.example.txl.redesign.utils.imageutils.BlurTransformation;
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

    public static class GlideUtilsBuilder {
        private Context context;
        private String url;
        private ImageView imageView;
        private Drawable placeholder;
        private Drawable errorDrawable;
        private RequestOptions requestOptions;
        private boolean useDefaultPlaceholder;
        /**
         * 高斯模糊
         * */
        private boolean blur;
        private RequestListener<Drawable> requestListener;
        /**
         * 是否是圆形
         * */
        private boolean isCicrle;

        public GlideUtilsBuilder setContext(Context context) {
            this.context = context;
            return this;
        }

        public GlideUtilsBuilder setUrl(String url) {
            this.url = url;
            return this;
        }

        public GlideUtilsBuilder setImageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public GlideUtilsBuilder setPlaceholder(Drawable placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public GlideUtilsBuilder setErrorDrawable(Drawable errorDrawable) {
            this.errorDrawable = errorDrawable;
            return this;
        }

        public GlideUtilsBuilder isCicrle(boolean isCicrle){
            this.isCicrle = isCicrle;
            return this;
        }

        public GlideUtilsBuilder useDefaultPlaceholder(boolean useDefaultPlaceholder){
            this.useDefaultPlaceholder = useDefaultPlaceholder;
            return this;
        }

        public GlideUtilsBuilder setBlur(boolean blur) {
            this.blur = blur;
            return this;
        }

        public GlideUtilsBuilder setRequestListener(RequestListener<Drawable> requestListener) {
            this.requestListener = requestListener;
            return this;
        }

        public void load(){
            initParams();

            Glide.with( context )
                    .setDefaultRequestOptions( requestOptions )
                    .load( url )
                    .listener( new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    if(requestListener != null){
                        return requestListener.onLoadFailed( e,model,target,isFirstResource );
                    }
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            } ).into(imageView);
        }

        private void initParams() {
            if(requestOptions == null){
                requestOptions = new RequestOptions();
            }
            if(isCicrle){
                requestOptions = requestOptions.apply( RequestOptions.bitmapTransform(new CircleCrop()) );
            }
            if(blur){
                requestOptions =new RequestOptions();
                requestOptions = requestOptions.transform( new BlurTransformation(context, 25, 3));
            }
            if(placeholder != null){
                requestOptions =requestOptions.placeholder( placeholder );
            }else if(useDefaultPlaceholder){
                placeholder = ContextCompat.getDrawable( context, R.drawable.image_secondfloor );
                requestOptions =requestOptions.placeholder( placeholder );
            }
            if(errorDrawable != null){
                requestOptions = requestOptions.error( errorDrawable );
            }
        }

        public void loadAsDrawable(){
            initParams();
            Glide.with(context)
                    .setDefaultRequestOptions( requestOptions )
                    .load(url)
                    .listener( new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            if(requestListener != null){
                                return requestListener.onLoadFailed( e,model,target,isFirstResource );
                            }
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            if(requestListener != null){
                                return requestListener.onResourceReady( resource,model,target,dataSource, isFirstResource);
                            }
                            return false;
                        }
                    } )
                    .into(new SimpleTarget<Drawable>() {
                        @Override
                        public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {

                        }
                    });
        }
    }
}

