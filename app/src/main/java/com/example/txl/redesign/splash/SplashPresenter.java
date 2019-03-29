package com.example.txl.redesign.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.txl.redesign.api.ApiRetrofit;
import com.example.txl.redesign.api.XmlyApi;
import com.example.txl.redesign.utils.AppExecutors;
import com.example.txl.redesign.utils.GlobalCacheUtils;

import org.json.JSONObject;

import java.util.concurrent.CountDownLatch;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author TXL
 * description :
 */
public class SplashPresenter implements SplashContract.Presenter {
    private final String TAG = getClass().getSimpleName();
    /**
     * 需要跑多少接口准备数据
     * */
    private final int NET_COUNT = 3;

    SplashContract.View view;
    CountDownLatch countDownLatch;

    public SplashPresenter(@NonNull SplashContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override
    public void prepareMainData() {

    }

    @Override
    public void prepareSplashData() {

    }

    @Override
    public void start() {
        countDownLatch = new CountDownLatch( NET_COUNT );
        AppExecutors.getInstance().networkIO().execute( new Runnable() {
            @Override
            public void run() {
                try {
                    countDownLatch.await();
                    AppExecutors.getInstance().mainThread().execute( new Runnable() {
                        @Override
                        public void run() {
                            view.prepareDataFinish();
                        }
                    } );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } );
        ApiRetrofit.init();
        ApiRetrofit.GankIoApi gankIoApi = ApiRetrofit.getGankIoApi();
        gankIoApi.getXianDuCategory().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                countDownLatch.countDown();
                GlobalCacheUtils.cache(GlobalCacheUtils.KEY_XIAN_DU_CATEGORY,response.body());
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Log.d(TAG,"onFailure sssssss"+t.toString());
                countDownLatch.countDown();
            }
        });
        Observer<JSONObject> observer = new Observer<JSONObject>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe : " + d);
            }

            @Override
            public void onNext(JSONObject s) {
                Log.d(TAG, Thread.currentThread().getName() +  "  Item: onNext " + s);
                GlobalCacheUtils.cache(GlobalCacheUtils.KEY_TODAY,s);
                countDownLatch.countDown();
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
                countDownLatch.countDown();
            }

            @Override
            public void onComplete() {

            }
        };
        gankIoApi.getTodayGanHuo()
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(observer);

        gankIoApi.getFuLi( GlobalCacheUtils.KEY_FU_LI, 6,1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( new Observer<JSONObject>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        GlobalCacheUtils.cache(GlobalCacheUtils.KEY_XIAN_DU_CATEGORY,jsonObject);
                        view.prepareSplashFinish(jsonObject);
                        countDownLatch.countDown();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                } );
    }
}
