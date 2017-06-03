package com.mnayef.library.http;

import com.mnayef.library.presenter.LinkPresenter;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Mohamed Hamdan on 2017-May-31.
 * mohamed.nayef95@gmail.com
 */
public class HttpClient {

    private static HttpClient mInstance;
    private OkHttpClient mClient;

    /**
     * This method used singleton pattern to create shared instance from @{@link HttpClient}
     */
    public static HttpClient getInstance() {
        if (mInstance == null) {
            mInstance = new HttpClient();
        }
        return mInstance;
    }

    /**
     * This constructor for initialize @{@link OkHttpClient} object
     */
    private HttpClient() {
        mClient = new OkHttpClient();
    }

    /**
     * This method for start get request by @{@link OkHttpClient}
     *
     * @param url This is the link you want get information.
     * @see LinkPresenter#onLoad(String)
     * @see okhttp3.Callback
     */
    public void get(String url, Callback callback) {
        Request request = new Request.Builder().url(url).build();
        mClient.newCall(request).enqueue(callback);
    }
}
