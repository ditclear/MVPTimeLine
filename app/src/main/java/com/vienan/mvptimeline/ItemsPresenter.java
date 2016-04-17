package com.vienan.mvptimeline;

import com.hannesdorfmann.mosby.mvp.MvpPresenter;

/**
 * Created by vienan on 16/4/17.
 */
public interface ItemsPresenter extends MvpPresenter<ItemsView> {

    public void loadItems(final boolean pullToRefresh);
}
