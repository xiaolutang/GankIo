package redesign.api;

import android.util.Log;

import com.example.txl.gankio.App;
import com.example.txl.gankio.utils.NetUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.X509TrustManager;

import io.reactivex.Observable;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Copyright (c) 2018, 唐小陆 All rights reserved.
 * author：txl
 * date：2018/7/6
 * description：
 */
public class ApiRetrofit {

    private static final int TIME_OUT = 1000 * 10;

    private static GankIoApi gankIoApi;

    private static final String GANK_IO_BASE_URL = "http://gank.io/api/";

    public GankIoApi getApiFactoryService(){
        return gankIoApi;
    }

    public ApiRetrofit() {
        //cache url
        File httpCacheDirectory = new File( App.getAppContext().getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache)
                .build();
    }

    static Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = chain -> {

        CacheControl.Builder cacheBuilder = new CacheControl.Builder();
        cacheBuilder.maxAge(0, TimeUnit.SECONDS);
        cacheBuilder.maxStale(365, TimeUnit.DAYS);
        CacheControl cacheControl = cacheBuilder.build();

        Request request = chain.request();
        if (!NetUtils.checkNetWorkIsAvailable(App.getAppContext())) {
            request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();

        }
        Response originalResponse = chain.proceed(request);
        if (NetUtils.checkNetWorkIsAvailable(App.getAppContext())) {
            int maxAge = 0; // read from cache
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=" + maxAge)
                    .build();
        } else {
            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };

    public static void initGankAPi(){
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.addInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR);
        builder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        builder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(GANK_IO_BASE_URL)
                .addConverterFactory(new JSONObjectConvertFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(builder.build())
                .build();
        gankIoApi = retrofit.create(GankIoApi.class);
    }

    public static synchronized GankIoApi getGankIoApi() {
        return gankIoApi;
    }

    public interface GankIoApi{
        @GET("xiandu/categories ")
        Call<JSONObject> getXianDuCategory ();

        /**
         * 今日最新干货
         * */
        @GET("today")
        Observable<JSONObject> getTodayGanHuo();
    }
}
