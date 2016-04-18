package com.vienan.mvptimeline;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import com.hannesdorfmann.mosby.mvp.lce.MvpLceActivity;

import net.danlew.android.joda.JodaTimeAndroid;

import java.util.List;

public class MainActivity extends MvpLceActivity<SwipeRefreshLayout, List<Item>, ItemsView, ItemsPresenter>
        implements ItemsView, SwipeRefreshLayout.OnRefreshListener {

    private android.widget.ListView listView;

    private MyListAdapter mAdapter;
    private LayoutInflater viewInflater;
    private View headerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JodaTimeAndroid.init(this);
        this.listView = (ListView) findViewById(R.id.listView);
        viewInflater=LayoutInflater.from(this);

        // Setup contentView == SwipeRefreshView
        contentView.setOnRefreshListener(this);

        // Setup list view
        mAdapter=new MyListAdapter(this,R.layout.timeline_item);
        listView.setAdapter(mAdapter);
        loadData(true);
    }

    @NonNull
    @Override
    public ItemsPresenter createPresenter() {
        return new SimpleItemsPresenter();
    }

    @Override
    protected String getErrorMessage(Throwable e, boolean pullToRefresh) {
        return "error";
    }

    @Override
    public void setData(List<Item> data) {
        mAdapter.setItems(data);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadData(boolean pullToRefresh) {
        presenter.loadItems(pullToRefresh);
    }

    @Override
    public void onRefresh() {
        loadData(true);
    }

    @Override
    public void showContent() {
        super.showContent();
        if (headerView==null){
            headerView=viewInflater.inflate(R.layout.list_header,null);
            listView.addHeaderView(headerView);
        }
        contentView.setRefreshing(false);
    }

    @Override
    public void showError(Throwable e, boolean pullToRefresh) {
        super.showError(e, pullToRefresh);
        contentView.setRefreshing(false);
    }
}
