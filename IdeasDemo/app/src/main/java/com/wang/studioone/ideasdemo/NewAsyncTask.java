/**
 * **********************************************************
 * Copyright (C) 2014 ShenZhen ComTop Information Technology Co.,Ltd
 * All Rights Reserved.
 * �����Ϊ���ڿ����տ������ơ�δ������˾��ʽ����ͬ�⣬�����κθ��ˡ�����
 * ����ʹ�á����ơ��޸Ļ򷢲������.
 * *************************************************************
 */
package com.wang.studioone.ideasdemo;


import android.graphics.Bitmap;
import android.os.AsyncTask;import java.lang.Integer;import java.lang.Override;import java.lang.String;

/**
 * һ���½����첽����
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
