package com.example.txl.redesign.utils;


import com.google.gson.Gson;

import org.json.JSONObject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Function;

/**
 * @author TXL
 * description :
 */
public class RxJavaUtils {
    public static <T> ObservableTransformer<JSONObject, T> gsonTransform(final Class<T> classRef) {
        return new ObservableTransformer<JSONObject, T>() {
            @Override
            public ObservableSource<T> apply(Observable<JSONObject> upstream) {
                return upstream.map(new Function<JSONObject, T>() {
                    @Override
                    public T apply(JSONObject s) throws Exception {
                        return new Gson().fromJson(s.toString(),classRef);
                    }
                });
            }
        };
    }
}
