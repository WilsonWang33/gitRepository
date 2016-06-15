package com.wang.studioone.ideasdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import com.jakewharton.disklrucache.DiskLruCache;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.DefaultLoadMoreView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author wangyaping
 * @version 1.0.0
 * @des
 * @class
 * @date
 */
public class MainActivity extends ActionBarActivity {
    ProgressDialog progressDialog;
    ImageView imageView2;
    ImageView imageView3;

    /**
     * asynctask img path
     */
    private String img_path = "http://www.todayonhistory.com/upic/200905/17/2316340889.jpg";

    /**
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        String imageUrl = "https://www.baidu.com/img/bd_logo1.png";
        String imageUrl1 = "https://gss0.baidu.com/-4o3dSag_xI4khGko9WTAnF6hhy/bainuo/crop%3D0%2C8%2C614%2C372%3Bw%3D470%3Bq%3D80/sign=a34ee00c2af5e0fefa57d34161501890/503d269759ee3d6d06aa68de44166d224f4adeb3.jpg";
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ImageView imageView = (ImageView) findViewById(R.id.img);
        final ImageView imageView1 = (ImageView) findViewById(R.id.img1);
        imageView2 = (ImageView) findViewById(R.id.img2);
        imageView3 = (ImageView) findViewById(R.id.img3);
        ImageLoader.getInstance().loadImage(imageUrl, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                imageView.setImageBitmap(bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {

            }
        });

        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .resetViewBeforeLoading(false)  // default
                .delayBeforeLoading(1000)
                .cacheInMemory(true) // default
                .cacheOnDisk(false) // default
                .build();
        ImageLoader.getInstance().displayImage(imageUrl1, imageView1, options);
        WebView web = (WebView) findViewById(R.id.wb);
        web.loadUrl("http://www.baidu.com");
        WebSettings settings = web.getSettings();
        settings.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });

        //初始化进度条
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("加载图片");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //asynctask加载图片
        new NewImgAsyncTask().execute(img_path);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void to(View v){
        startActivity(new Intent(this, PullToRefreashActivity.class));
    }
    public void togo(View v){
        startActivity(new Intent(this,SwipeMenuActivity.class));
    }

    /**
     * asynctask
     */
    public class NewImgAsyncTask extends AsyncTask<String, Integer, Bitmap> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressDialog.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            imageView3.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            Bitmap bitmap = null;
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            InputStream inputStream = null;
            try {
                HttpClient httpClient = new DefaultHttpClient();
                HttpGet httpGet = new HttpGet(params[0]);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode()==200){
                    inputStream =httpResponse.getEntity().getContent();
                    long file_length = httpResponse.getEntity().getContentLength();
                    int len = 0;
                    byte[] data = new byte[1024];
                    int total_length = 0;
                    while((len=inputStream.read(data))!=-1){
                        total_length+=len;
                        int values = (int) ((total_length/file_length)*100);
                        publishProgress(values);
                        byteArrayOutputStream.write(data,0,len);
                    }
                    byte[] result = byteArrayOutputStream.toByteArray();
                    bitmap = BitmapFactory.decodeByteArray(result,0,result.length);
                }
            } catch (Exception e) {
                Log.i("网络异常",e.getMessage());
            }finally {
                if (inputStream!=null){
                    try{
                        inputStream.close();
                    }catch (Exception e){
                        Log.i("网络异常",e.getMessage());
                    }
                }
            }
            return bitmap;
        }
    }
}
