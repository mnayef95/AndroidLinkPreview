package com.mnayef.library.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * Created by Mohamed Hamdan on 2017-Jun-03.
 * mohamed.nayef95@gmail.com
 */
public class FileUtils {

    /**
     * This method for get cache folder path.
     *
     * @param uniqueName Unique folder name
     * @return File
     */
    public static File getDiskCacheDir(Context context, String uniqueName) {
        final String cachePath = Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable() ? context.getExternalCacheDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }
}
