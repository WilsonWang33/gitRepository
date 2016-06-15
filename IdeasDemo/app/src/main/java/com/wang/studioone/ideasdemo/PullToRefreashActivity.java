package com.wang.studioone.ideasdemo;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;
import com.lhh.ptrrv.library.PullToRefreshRecyclerView;
import com.lhh.ptrrv.library.footer.loadmore.DefaultLoadMoreView;


public class PullToRefreashActivity extends ActionBarActivity {

    private PullToRefreshRecyclerView mPtrrv;
    private PtrrvAdapter mAdapter;
    private static final int DEFAULT_ITEM_SIZE = 20;
    private static final int ITEM_SIZE_OFFSET = 20;

    private static final int MSG_CODE_REFRESH = 0;
    private static final int MSG_CODE_LOADMORE = 1;

    private static final int TIME = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pull_to_refreash);
        mPtrrv = (PullToRefreshRecyclerView) this.findViewById(R.id.ptrrv);
        DefaultLoadMoreView loadMoreView = new DefaultLoadMoreView(this, mPtrrv.getRecyclerView());
        loadMoreView.setLoadmoreString("loading...");
        loadMoreView.setLoadMorePadding(100);

        mPtrrv.setLoadMoreFooter(loadMoreView);

//remove header
        mPtrrv.removeHeader();

// set true to open swipe(pull to refresh, default is true)
        mPtrrv.setSwipeEnable(true);

// set the layoutManager which to use
        mPtrrv.setLayoutManager(new LinearLayoutManager(this));

// set PagingableListener
        mPtrrv.setPagingableListener(new PullToRefreshRecyclerView.PagingableListener() {
            @Override
            public void onLoadMoreItems() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_LOADMORE, TIME);
            }
        });

// set OnRefreshListener
        mPtrrv.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mHandler.sendEmptyMessageDelayed(MSG_CODE_REFRESH, TIME);
            }
        });

// add item divider to recyclerView
       /* mPtrrv.getRecyclerView().addItemDecoration(new DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL_LIST));*/

// add headerView
       // mPtrrv.addHeaderView(View.inflate(this, R.layout.header, null));

//set EmptyVIEW
       //mPtrrv.setEmptyView(View.inflat(this,R.layout.empty_view, null));

// set loadmore String
        mPtrrv.setLoadmoreString("loading");

// set loadmore enable, onFinishLoading(can load more? , select before item)
        mPtrrv.onFinishLoading(true, false);

        mAdapter = new MPtrrvAdapter(this);
        mPtrrv.setAdapter(mAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pull_to_refreash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == MSG_CODE_REFRESH) {
                mAdapter.setCount(DEFAULT_ITEM_SIZE);
                mAdapter.notifyDataSetChanged();
                mPtrrv.setOnRefreshComplete();
                mPtrrv.onFinishLoading(true, false);
            } else if (msg.what == MSG_CODE_LOADMORE) {
                if(mAdapter.getItemCount() == DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET){
                    //over
                    Toast.makeText(getApplicationContext(), "没有更多数据", Toast.LENGTH_SHORT).show();
                    mPtrrv.onFinishLoading(false, false);
                }else {
                    mAdapter.setCount(DEFAULT_ITEM_SIZE + ITEM_SIZE_OFFSET);
                    mAdapter.notifyDataSetChanged();
                    mPtrrv.onFinishLoading(true, false);
                }
            }
        }
    };

    class MPtrrvAdapter extends PtrrvAdapter<MPtrrvAdapter.ViewHolder>{

        public MPtrrvAdapter(Context context) {
            super(context);
        };

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mInflater.inflate(R.layout.ptrrv_item, null);
            return new ViewHolder(view);
        }

        @Override
            public void onBindViewHolder(ViewHolder holder, int position) {

        }

        class ViewHolder extends RecyclerView.ViewHolder{

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }

    }
}
