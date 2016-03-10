package com.nshmura.feed;

import android.content.Context;
import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.squareup.okhttp.OkHttpClient;

public class StethoInitializer {

    public static void init(Context context) {
        Stetho.initializeWithDefaults(context);
    }

    public static void appendNetworkIntercepters(OkHttpClient client) {
        client.networkInterceptors().add(new StethoInterceptor());
    }
}
