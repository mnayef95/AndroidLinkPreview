package com.mnayef.library.callback;

import com.mnayef.library.model.Link;
import com.mnayef.library.presenter.LinkPresenterContract;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public interface Callback {

    /**
     * This method called if hit response successfully.
     *
     * @see LinkPresenterContract.View#onFinish(Link)
     */
    void onSuccess(Link link);

    /**
     * This method called if
     * <p>Http response return error.</p>
     * <p>No internet connection.</p>
     *
     * @see LinkPresenterContract.View#showFailedLoadView()
     */
    void onFailed();

    /**
     * This method called if the link is malformed
     *
     * @see LinkPresenterContract.View#showInvalidLinkView()
     */
    void onMalformedUrl();

}
