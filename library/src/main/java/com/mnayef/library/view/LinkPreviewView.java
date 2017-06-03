package com.mnayef.library.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.makeramen.roundedimageview.RoundedImageView;
import com.mnayef.library.R;
import com.mnayef.library.model.Link;
import com.mnayef.library.presenter.LinkPresenter;
import com.mnayef.library.presenter.LinkPresenterContract;
import com.mnayef.library.utils.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

/**
 * Created by Mohamed Hamdan on 2017-May-31.
 * mohamed.nayef95@gmail.com
 */
@SuppressWarnings("unused")
public class LinkPreviewView extends RelativeLayout implements LinkPresenterContract.View, Callback, View.OnClickListener {

    private String mInvalidLinkMsg;
    private String mFailedLoadMsg;

    private String mUrl;
    private LinkPresenterContract.Actions mPresenter;

    private ProgressBar mPbLink;
    private ProgressBar mPbLinkImage;
    private LinearLayout mLlLinkContent;
    private LinearLayout mLlFailedLoad;
    private LinearLayout mLlInvalidUrl;
    private AppCompatTextView mTvTitle;
    private AppCompatTextView mTvDescription;
    private AppCompatTextView mTvLink;
    private AppCompatTextView mTvFailedLoad;
    private AppCompatTextView mTvInvalidLink;
    private RoundedImageView mIvLink;

    public LinkPreviewView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public LinkPreviewView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public LinkPreviewView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    /**
     * This method to initialize views by @{@link View#findViewById(int)}
     */
    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LinkPreviewView, defStyleAttr, 0);
        mFailedLoadMsg = typedArray.getString(R.styleable.LinkPreviewView_failedLoadMsg);
        mInvalidLinkMsg = typedArray.getString(R.styleable.LinkPreviewView_invalidLinkMsg);
        typedArray.recycle();

        View view = LayoutInflater.from(context).inflate(R.layout.link_preview, this, false);

        mLlLinkContent = (LinearLayout) view.findViewById(R.id.ll_link_content);
        mLlFailedLoad = (LinearLayout) view.findViewById(R.id.ll_failed_load);
        mLlInvalidUrl = (LinearLayout) view.findViewById(R.id.ll_invalid_url);
        mPbLink = (ProgressBar) view.findViewById(R.id.pb_link);
        mPbLinkImage = (ProgressBar) view.findViewById(R.id.pb_link_image);
        mTvDescription = (AppCompatTextView) view.findViewById(R.id.tv_link_description);
        mTvTitle = (AppCompatTextView) view.findViewById(R.id.tv_link_title);
        mTvLink = (AppCompatTextView) view.findViewById(R.id.tv_link);
        mTvFailedLoad = (AppCompatTextView) view.findViewById(R.id.tv_failed_load);
        mTvInvalidLink = (AppCompatTextView) view.findViewById(R.id.tv_invalid_link);
        mIvLink = (RoundedImageView) view.findViewById(R.id.link_image);

        mTvFailedLoad.setText(mFailedLoadMsg != null ? mFailedLoadMsg : getContext().getString(R.string.failed_load_url));
        mTvInvalidLink.setText(mInvalidLinkMsg != null ? mInvalidLinkMsg : getContext().getString(R.string.invalid_url));

        mLlFailedLoad.setOnClickListener(this);

        mPresenter = new LinkPresenter(getContext(), this);

        addView(view);
    }

    /**
     * This method is used to start load url data
     *
     * @param url This is the link you want get information.
     * @see LinkPresenterContract.Actions#onLoad(String)
     */
    public void load(String url) {
        this.mUrl = url;
        post(new Runnable() {
            @Override
            public void run() {
                mPbLink.setVisibility(VISIBLE);
                mLlLinkContent.setVisibility(INVISIBLE);
                mLlFailedLoad.setVisibility(GONE);
                mLlInvalidUrl.setVisibility(GONE);
                mTvDescription.setText("");
                mTvLink.setText("");
                mTvTitle.setText("");

                mPresenter.onLoad(mUrl);
            }
        });
    }

    /**
     * @see LinkPresenterContract.View#showInvalidLinkView()
     */
    @Override
    public void showInvalidLinkView() {
        post(new Runnable() {
            @Override
            public void run() {
                mPbLink.setVisibility(GONE);
                mLlLinkContent.setVisibility(INVISIBLE);
                mLlFailedLoad.setVisibility(GONE);
                mLlInvalidUrl.setVisibility(VISIBLE);
            }
        });
    }

    /**
     * @see LinkPresenterContract.View#showFailedLoadView()
     */
    @Override
    public void showFailedLoadView() {
        post(new Runnable() {
            @Override
            public void run() {
                mPbLink.setVisibility(GONE);
                mLlLinkContent.setVisibility(INVISIBLE);
                mLlInvalidUrl.setVisibility(GONE);
                mLlFailedLoad.setVisibility(VISIBLE);
            }
        });
    }

    /**
     * @see LinkPresenterContract.View#onFinish(Link)
     */
    @Override
    public void onFinish(final Link link) {
        post(new Runnable() {
            @Override
            public void run() {
                String description = link.getDescription();
                String title = link.getTitle();
                String url = link.getUrl();

                int titleCharsFit = Utils.getTvFitChars(title, mTvTitle);
                int descCharsFit = Utils.getTvFitChars(description, mTvDescription);

                titleCharsFit = titleCharsFit == 0 ? 40 : titleCharsFit + (titleCharsFit / 2);
                descCharsFit = descCharsFit == 0 ? 50 : descCharsFit + (descCharsFit / 2);

                mTvDescription.setText(description.length() > descCharsFit ? description.substring(0, descCharsFit) + "..." : description);
                mTvTitle.setText(title.length() > titleCharsFit ? title.substring(0, titleCharsFit) + "..." : title);
                mTvLink.setText(url);

                if (link.getImage() != null && !link.getImage().isEmpty()) {
                    Picasso.with(getContext()).load(link.getImage()).fit().into(mIvLink, LinkPreviewView.this);
                } else {
                    mIvLink.setImageResource(R.mipmap.no_image);
                    mPbLinkImage.setVisibility(GONE);
                }

                mPbLink.setVisibility(GONE);
                mLlLinkContent.setVisibility(VISIBLE);
            }
        });
    }

    /**
     * @see com.squareup.picasso.Callback#onSuccess()
     */
    @Override
    public void onSuccess() {
        mPbLinkImage.setVisibility(GONE);
    }

    /**
     * @see com.squareup.picasso.Callback#onError()
     */
    @Override
    public void onError() {
        mPbLinkImage.setVisibility(GONE);
        mIvLink.setImageResource(R.mipmap.no_image);
    }

    /**
     * @see android.view.View.OnClickListener#onClick(View)
     */
    @Override
    public void onClick(View view) {
        load(mUrl);
    }

    public void setFailedLoadMsg(String failedLoadMsg) {
        this.mFailedLoadMsg = failedLoadMsg;
        mTvFailedLoad.setText(failedLoadMsg);
    }

    public void setFailedLoadMsg(int failedLoadMsg) {
        this.mFailedLoadMsg = getContext().getResources().getString(failedLoadMsg);
        mTvFailedLoad.setText(failedLoadMsg);
    }

    public void setInvalidLinkMsg(String invalidLinkMsg) {
        this.mInvalidLinkMsg = invalidLinkMsg;
        mTvInvalidLink.setText(invalidLinkMsg);
    }

    public void setInvalidLinkMsg(int invalidLinkMsg) {
        this.mInvalidLinkMsg = getContext().getResources().getString(invalidLinkMsg);
        mTvInvalidLink.setText(invalidLinkMsg);
    }

    public String getInvalidLinkMsg() {
        return mInvalidLinkMsg;
    }

    public String getFailedLoadMsg() {
        return mFailedLoadMsg;
    }
}
