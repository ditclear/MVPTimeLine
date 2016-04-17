package com.vienan.mvptimeline;

import android.util.Log;

import com.hannesdorfmann.mosby.mvp.MvpBasePresenter;

import java.util.List;

/**
 * Created by vienan on 16/4/17.
 */
public class SimpleItemsPresenter extends MvpBasePresenter<ItemsView>
            implements ItemsPresenter{
    private static final String TAG = "SimpleItemsPresenter";

    private int failingCounter = 0;
    private ItemsAsyncLoader itemsLoader;

    public SimpleItemsPresenter() {
        Log.d(TAG, "constructor " + toString());
    }

    @Override
    public void loadItems(final boolean pullToRefresh) {
        Log.d(TAG, "loadCountries(" + pullToRefresh + ")");

        Log.d(TAG, "showLoading(" + pullToRefresh + ")");

        getView().showLoading(pullToRefresh);

        if (itemsLoader != null && !itemsLoader.isCancelled()) {
            itemsLoader.cancel(true);
        }

        itemsLoader = new ItemsAsyncLoader(++failingCounter % 2 != 0,
                new ItemsAsyncLoader.ItemsLoaderListener() {

                    @Override public void onSuccess(List<Item> items) {

                        if (isViewAttached()) {
                            Log.d(TAG, "setData()");
                            getView().setData(items);

                            Log.d(TAG, "showContent()");
                            getView().showContent();
                        }
                    }

                    @Override public void onError(Exception e) {

                        if (isViewAttached()) {

                            Log.d(TAG, "showError(" + e.getClass().getSimpleName() + " , " + pullToRefresh + ")");
                            getView().showError(e, pullToRefresh);
                        }
                    }
                });
        itemsLoader.execute();
    }

}
