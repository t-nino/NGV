package com.example.testviewer;

import android.app.Application;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class NGVApplication extends Application{

    @Override
    public void onCreate() {

        super.onCreate();
        // グローバル設定の生成と初期化を行う
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
            .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
            .memoryCacheSize(2 * 1024 * 1024)
            //... // ImageLoaderConfigurationの設定をメソッドチェインで繋いでいく
            .build();
        ImageLoader.getInstance().init(config);
    }
}
