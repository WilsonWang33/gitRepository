/**
 * **********************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * �����Ϊ���ڿ����տ������ơ�δ������˾��ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 * *************************************************************
 */
package com.wang.studioone.ideasdemo;


import android.app.Application;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        //����Ĭ�ϵ�ImageLoader���ò���
        ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
    }
}
