package com.example.txl.redesign.splash;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.txl.redesign.api.ApiRetrofit;

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
        countDownLatch = new CountDownLatch( 2 );
        ApiRetrofit.initGankAPi();
        ApiRetrofit.GankIoApi gankIoApi = ApiRetrofit.getGankIoApi();
        gankIoApi.getXianDuCategory().enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                Log.d(TAG,"onResponse sssssss"+response.body().toString());
                countDownLatch.countDown();
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
                countDownLatch.countDown();
            }
            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "Error!");
            }

            @Override
            public void onComplete() {
                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        gankIoApi.getTodayGanHuo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
