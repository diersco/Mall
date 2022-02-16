package com.cyty.mall.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @创建者 misJackLee
 * @创建时间 2022/2/16 11:07
 * @描述
 */
public class BitMapUtil {


    public static Bitmap bitmap;

    public static Bitmap returnBitMap(final String url){


        new Thread(new Runnable() {

            @Override
            public void run() {

                URL imageurl = null;

                try {

                    imageurl = new URL(url);
                } catch (MalformedURLException e) {

                    e.printStackTrace();
                }
                try {

                    HttpURLConnection conn = (HttpURLConnection)imageurl.openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                } catch (IOException e) {

                    e.printStackTrace();
                }
            }
        }).start();

        return bitmap;
    }
}