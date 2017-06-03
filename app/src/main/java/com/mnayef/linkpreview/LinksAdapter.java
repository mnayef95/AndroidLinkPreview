package com.mnayef.linkpreview;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnayef.library.view.LinkPreviewView;

import java.util.List;

/**
 * Created by Mohamed Hamdan on 2017-Jun-02.
 * mohamed.nayef95@gmail.com
 */
public class LinksAdapter extends RecyclerView.Adapter<LinksAdapter.ViewHolder> {

    private List<String> mLinks;

    public LinksAdapter(List<String> links) {
        this.mLinks = links;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.linkPreviewView.load(mLinks.get(position));
    }

    @Override
    public int getItemCount() {
        return mLinks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinkPreviewView linkPreviewView;

        public ViewHolder(View itemView) {
            super(itemView);
            linkPreviewView = (LinkPreviewView) itemView.findViewById(R.id.link_preview);
        }
    }
}
