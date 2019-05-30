package com.bzh.floodview.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.afollestad.materialdialogs.MaterialDialog;
import com.bzh.floodview.App;
import com.bzh.floodview.MainAttrs;
import com.bzh.floodview.model.BaseApi;
import com.bzh.floodview.module.login.LoginActivity;
import com.bzh.floodview.utils.AppManager;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * 网络处理类
 */
public class RetrofitHelper {

    private MainAttrs mainAttrs;

    private Retrofit mRetrofit;

    private RetrofitService retrofitService;

    public RetrofitHelper(MainAttrs mainAttrs) {
        this.mainAttrs = mainAttrs;
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://" + App.ip) //192.168.1.196:8090  215   192.168.31.75
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //添加动态head
    private OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new RequestCookiesInterceptor())
            .addInterceptor(new ReceivedCookiesInterceptor())
            .build();

    private GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());

    public RetrofitService getServer() {
        if (retrofitService == null) {
            retrofitService = mRetrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }

    //动态head
    public class RequestCookiesInterceptor implements Interceptor {

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Request original = chain.request();
            Request request = original.newBuilder()
                    .header("cookie", App.cookid)
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        }
    }

    public class ReceivedCookiesInterceptor implements Interceptor {
        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {
            Response originalResponse = chain.proceed(chain.request());

            if (App.cookid.equals("")) {
                if (!originalResponse.headers("Set-Cookie").isEmpty()) {
                    for (String header : originalResponse.headers("Set-Cookie")) {
                        int signal = header.indexOf("JSESSIONID");
                        if(signal != -1){
                            App.cookid = header.substring(0,header.indexOf(';'));
                        }

                    }
                }
            }

            return originalResponse;
        }
    }

    @SuppressWarnings("CheckResult")
    public <T> void successHandler(Observable<T> observable, callBack callBack) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((T t) -> {
                    callBack.run(t);
                }, throwable -> {
                    callBack.handError();
                    System.out.println("网络请求发生错误");
                    System.out.println(throwable.getMessage());
                });
    }

    @SuppressWarnings("CheckResult")
    public <T> void requestHandler(Observable<T> observable, Context context, callHandler<T> callBack) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((T t) -> {
                    BaseApi<Object> baseApi = (BaseApi<Object>) t;
                    if (baseApi.getCode() == 455) { //账号异常
                        MaterialDialog dialog = new MaterialDialog.Builder(context).title("警告")
                                .content((String) baseApi.getMessage())
                                .positiveText("确认").onPositive((dialog1, which) -> {
                                    AppManager.getAppManager().finishAllActivity();
                                    LoginActivity.open(context);
                                }).build();
                        dialog.show();
                    } else {
                        callBack.run(t);
                    }
                }, throwable -> {
                    Timber.e(throwable.getMessage());
                    callBack.handlerError();
                });
    }

    public interface callBack {
        <T> void run(T t);

        default void handError() {
        }
    }

    public interface callHandler<T> {
        void run(T t);

        default void handlerError() {
        }
    }
}
