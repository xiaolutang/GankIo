package com.example.txl.redesign.fragment.wanandroid;

import com.example.txl.redesign.api.ApiRetrofit;
import com.example.txl.redesign.data.XmlyFmData;
import com.example.txl.redesign.data.wanandroid.ArticleList;
import com.example.txl.redesign.data.wanandroid.WanAndroidArticle;
import com.example.txl.redesign.data.wanandroid.WanAndroidBanner;
import com.example.txl.redesign.utils.RxJavaUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @author TXL
 * description :
 */
public class WanAndroidPresenter implements WanAndroidContract.Presenter {
    private ApiRetrofit.WanAndroidApi wanAndroidApi;

    WanAndroidContract.View<List<XmlyFmData>> view;
    private int page = 0;

    private ObservableTransformer<ArticleList, List<XmlyFmData>> observableTransformer = new ObservableTransformer<ArticleList, List<XmlyFmData>>() {
        @Override
        public ObservableSource<List<XmlyFmData>> apply(Observable<ArticleList> upstream) {
            return upstream.map(new Function<ArticleList, List<XmlyFmData>>() {
                @Override
                public List<XmlyFmData> apply(ArticleList s) throws Exception {
                    List<XmlyFmData> xmlyFmDataList = new ArrayList<>();
                    for (WanAndroidArticle article : s.getData().getDatas()){
                        XmlyFmData xmlyFmData = new XmlyFmData(XmlyFmData.WAN_ANDROID_TYPE_ARTICLE);
                        xmlyFmData.setAndroidArticle(article);
                        xmlyFmDataList.add(xmlyFmData);
                    }
                    return xmlyFmDataList;
                }
            });
        }
    };

    public WanAndroidPresenter(WanAndroidContract.View<List<XmlyFmData>> view) {
        this.view = view;
    }

    @Override
    public void refresh() {
        wanAndroidApi.getBanner()
                .compose(RxJavaUtils.gsonTransform(WanAndroidBanner.class))
                .subscribeOn( Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WanAndroidBanner>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(WanAndroidBanner wanAndroidBanner) {
                        XmlyFmData xmlyFmData = new XmlyFmData(XmlyFmData.WAN_ANDROID_TYPE_BANNER);
                        xmlyFmData.setWanAndroidBanner(wanAndroidBanner);
                        view.onBannerSuccess(xmlyFmData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onBannerFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
        page = 0;
        wanAndroidApi.getArticleList(page)
                .compose(RxJavaUtils.gsonTransform(ArticleList.class))
                .compose(observableTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<XmlyFmData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<XmlyFmData> xmlyFmData) {
                        view.onRefreshSuccess(xmlyFmData);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onRefreshFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void loadMore() {
        page ++;
        wanAndroidApi.getArticleList(page)
                .compose(RxJavaUtils.gsonTransform(ArticleList.class))
                .compose(observableTransformer)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<XmlyFmData>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<XmlyFmData> xmlyFmData) {
                        view.onLoadMoreSuccess(xmlyFmData,true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.onLoadMoreFailed();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void start() {
        wanAndroidApi = ApiRetrofit.getGWanAndroidApi();
    }
}
