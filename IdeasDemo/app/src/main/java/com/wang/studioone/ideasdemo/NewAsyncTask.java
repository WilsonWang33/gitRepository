/**
 * **********************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * 本软件为深圳康拓普开发研制。未经本公司正式书面同意，其他任何个人、团体
 * 不得使用、复制、修改或发布本软件.
 * *************************************************************
 */
package com.wang.studioone.ideasdemo;


import android.graphics.Bitmap;
import android.os.AsyncTask;import java.lang.Integer;import java.lang.Override;import java.lang.String;

/**
 * 一个新建的异步任务
 */
public class NewAsyncTask extends AsyncTask<String,Integer,Bitmap> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
    }
}
