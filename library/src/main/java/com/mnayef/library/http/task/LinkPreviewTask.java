package com.mnayef.library.http.task;

import android.content.Context;

import com.mnayef.library.callback.Callback;
import com.mnayef.library.http.HttpClient;
import com.mnayef.library.model.Link;
import com.mnayef.library.utils.CacheUtils;
import com.mnayef.library.utils.ParseUtils;
import com.mnayef.library.utils.Utils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public class LinkPreviewTask implements okhttp3.Callback {

    private String mCacheKey;
    private URL mUrlClass;
    private String mUrl;
    private Callback mCallback;
    private CacheUtils mCache;

    public LinkPreviewTask(Context context, String url, Callback callback) {
        this.mUrl = url;
        this.mCallback = callback;
        mCache = new CacheUtils(context);
    }

    /**
     * This method for start request if cache == null or return link if cache != null
     */
    public void start() {
        try {
            mCacheKey = Utils.getCacheKey(mUrl);
            mUrlClass = new URL(mUrl);
            Link link = mCache.get(mCacheKey);

            if (link == null) {
                HttpClient.getInstance().get(mUrl, this);
            } else {
                mCallback.onSuccess(link);
            }
        } catch (MalformedURLException e) {
            mCallback.onMalformedUrl();
        }
    }

    /**
     * @see okhttp3.Callback#onFailure(Call, IOException)
     */
    @Override
    public void onFailure(Call call, IOException e) {
        mCallback.onFailed();
    }

    /**
     * @see okhttp3.Callback#onResponse(Call, Response)
     */
    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (response.isSuccessful()) {
            ResponseBody body = response.body();
            if (body != null) {
                try {
                    Link link = ParseUtils.getLinkData(mUrlClass, body.string());
                    mCache.put(mCacheKey, link);
                    mCallback.onSuccess(link);
                } catch (IOException ignored) {
                }
            }
        } else {
            mCallback.onFailed();
        }
    }
}
