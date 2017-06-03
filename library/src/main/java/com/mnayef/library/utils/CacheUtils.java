package com.mnayef.library.utils;

import android.content.Context;

import com.jakewharton.disklrucache.DiskLruCache;
import com.mnayef.library.model.Link;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by Mohamed Hamdan on 2017-Jun-02.
 * mohamed.nayef95@gmail.com
 */
public class CacheUtils {

    private DiskLruCache mDiskLruCache;

    public CacheUtils(Context context) {
        try {
            mDiskLruCache = DiskLruCache.open(FileUtils.getDiskCacheDir(context, "LinkPreviewCache"), 1, 1, 1024 * 1024 * 10);
        } catch (IOException ignored) {
        }
    }

    /**
     * This method for cache object by @{@link DiskLruCache}
     *
     * @param key   For get object by it.
     * @param value Object you want cache.
     */
    public void put(String key, Link value) {
        try {
            DiskLruCache.Editor editor;
            editor = mDiskLruCache.edit(key);

            ObjectOutputStream out = new ObjectOutputStream(editor.newOutputStream(0));
            out.writeObject(value);
            out.close();
            editor.commit();
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * This method for fetch object from cache by key.
     *
     * @param key For get object by it.
     * @return Object if exist or null if !exist
     */
    public Link get(String key) {
        DiskLruCache.Snapshot snapshot;
        try {
            snapshot = mDiskLruCache.get(key);
            ObjectInputStream in = new ObjectInputStream(snapshot.getInputStream(0));
            return (Link) in.readObject();
        } catch (ClassNotFoundException | IOException | NullPointerException ex) {
            return null;
        }
    }
}