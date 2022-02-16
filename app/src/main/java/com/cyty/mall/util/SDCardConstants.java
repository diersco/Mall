package com.cyty.mall.util;

/**
 * @创建者 misJackLee
 * @创建时间 2020/8/4 15:42
 * @描述
 */

import android.content.Context;
import android.os.Build;
import android.os.Environment;

import java.io.File;

/**
 * 文件存储相关常量
 */
public  class SDCardConstants {

    private static final String TAG = "SDCardConstants";
    /**
     * 转码文件后缀
     */
    public final static String TRANSCODE_SUFFIX = ".mp4_transcode";

    /**
     * 裁剪文件后缀
     */
    public final static String CROP_SUFFIX = "-crop.mp4";

    /**
     * 合成文件后缀
     */
    public final static String COMPOSE_SUFFIX = "-compose.mp4";

    /**
     * 裁剪 & 录制 & 转码输出文件的目录
     * android Q 版本默认路径
     * /storage/emulated/0/Android/data/包名/files/Media/
     * android Q 以下版本默认"/sdcard/DCIM/Camera/"
     */
    public static String getDir(Context context) {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + File.separator + "Media" + File.separator;
        } else {
            dir = Environment.getExternalStorageDirectory() + File.separator + "DCIM"
                    + File.separator + "Camera" + File.separator;
        }
        File file = new File(dir);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return dir;
    }

    public static String getFileDir(Context context, String s) {
        String dir;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            dir = context.getExternalFilesDir("").getAbsolutePath() + File.separator;
        } else {
            dir = Environment.getExternalStorageDirectory() + File.separator + "DCIM"
                    + File.separator + "Camera" + File.separator;
        }
        File file = new File(dir);
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.mkdirs();
        }
        return dir;
    }

    /**
     * 获取外部缓存目录 版本默认"/storage/emulated/0/Android/data/包名/file/Cache"
     *
     * @param context Context
     * @return string path
     */
    public static String getCacheDir(Context context) {
        File cacheDir = context.getExternalCacheDir();
        return cacheDir == null ? "" : cacheDir.getPath();
    }


    /**
     * 递归删除文件/目录
     * @param file File
     */
    private static boolean deleteFile(File file) {
        if (file == null || !file.exists()) {
            return true;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null) {
                return true;
            }
            for (File f : files) {
                deleteFile(f);
            }
        }
        return file.delete();
    }

}