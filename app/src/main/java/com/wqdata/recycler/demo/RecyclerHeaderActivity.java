/**
 * Copyright 2015 Bartosz Lipinski
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wqdata.recycler.demo;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;

import com.bartoszlipinski.recyclerviewheader.RecyclerViewHeader;

import java.util.ArrayList;

/**
 * Created by Bartosz Lipinski
 * 01.04.15
 */
public class RecyclerHeaderActivity extends FragmentActivity implements SwipeRefreshLayout.OnRefreshListener {

    private RecyclerViewHeader mRecyclerHeader;
    private RecyclerView mRecyclerView;
    private ViewPager mViewPager;
    private SwipeRefreshLayout mSwipefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private int lastVisibleItem;
    private RecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_header);
        setupViews();
    }

    private void setupViews() {
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new SimpleImageAdapter(getSupportFragmentManager()));
        mAdapter = new RecyclerAdapter();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.showLoading();
        // 这句话是为了，第一次进入页面的时候显示加载进度条

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView,
                                             int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE
                        && lastVisibleItem + 1 == mAdapter.getItemCount()) {
                    requestMoreData();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                mSwipefreshLayout.setEnabled(mLayoutManager
                        .findFirstCompletelyVisibleItemPosition() == 0);
            }
        });

        mRecyclerHeader = (RecyclerViewHeader) findViewById(R.id.header);
        mRecyclerHeader.attachTo(mRecyclerView, true);


        mSwipefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_layout);
        mSwipefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary);//设置刷新时进度条颜色，最多四种
        mSwipefreshLayout.setOnRefreshListener(this);
        requestRefreshData();
    }

    /**
     * 模拟刷新请求
     */
    private void requestRefreshData() {
        Log.e("requestRefreshData:", "requestRefreshData");
        mSwipefreshLayout.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DataBean> list = new ArrayList<>();
                for (int i = (int) (5 + Math.random() * 10); i >= 0; i--) {
                    DataBean bean = new DataBean();
                    bean.setId(i);
                    bean.setName("data_refresh_" + i);
                    list.add(bean);
                }
                mAdapter.clear();
                mAdapter.addFirstAll(list);
                mSwipefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    /**
     * 模拟加载更多请求
     */
    private void requestMoreData() {
        Log.e("requestMoreData:", "requestMoreData");
        mAdapter.showLoading();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ArrayList<DataBean> list = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    DataBean bean = new DataBean();
                    bean.setId(i);
                    bean.setName("data_load_more_" + i);
                    list.add(bean);
                }
                mAdapter.addAll(list);
                mAdapter.showLoadMore();
            }
        }, 3000);
    }

    @Override
    public void onRefresh() {
        requestRefreshData();
    }
}
