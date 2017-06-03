package com.mnayef.library.presenter;

import com.mnayef.library.model.Link;
import com.mnayef.library.view.LinkPreviewView;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public interface LinkPresenterContract {

    /**
     * This interface to handle view actions
     */
    interface Actions {
        /**
         * This method is called from @{@link LinkPreviewView#load} method in @{@link LinkPreviewView} view for get link data by OkHttp
         *
         * @param url This is the link you want get information.
         */
        void onLoad(String url);
    }

    /**
     * This interface to handle view data
     */
    interface View {
        /**
         * This method for show message if the link is malformed
         */
        void showInvalidLinkView();

        /**
         * This method for show message if
         * <p>Http response return error.</p>
         * <p>No internet connection.</p>
         */
        void showFailedLoadView();

        /**
         * This method tells @{@link LinkPreviewView} hit response successfully.
         *
         * @param link This returns the link information.
         */
        void onFinish(Link link);
    }
}
