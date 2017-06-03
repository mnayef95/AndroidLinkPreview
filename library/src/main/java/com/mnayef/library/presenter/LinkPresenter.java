package com.mnayef.library.presenter;

import android.content.Context;

import com.mnayef.library.callback.Callback;
import com.mnayef.library.http.task.LinkPreviewTask;
import com.mnayef.library.model.Link;
import com.mnayef.library.presenter.LinkPresenterContract.Actions;
import com.mnayef.library.presenter.LinkPresenterContract.View;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public class LinkPresenter implements Actions, Callback {

    private View mView;
    private Context mContext;

    public LinkPresenter(Context context, View view) {
        this.mView = view;
        this.mContext = context;
    }

    /**
     * @see LinkPresenterContract.Actions#onLoad(String)
     * @see LinkPreviewTask#start()
     */
    @Override
    public void onLoad(String url) {
        LinkPreviewTask task = new LinkPreviewTask(mContext, url, this);
        task.start();
    }

    /**
     * @see Callback#onSuccess(Link)
     */
    @Override
    public void onSuccess(Link link) {
        mView.onFinish(link);
    }

    /**
     * @see Callback#onFailed()
     */
    @Override
    public void onFailed() {
        mView.showFailedLoadView();
    }

    /**
     * @see Callback#onMalformedUrl()
     */
    @Override
    public void onMalformedUrl() {
        mView.showInvalidLinkView();
    }
}
