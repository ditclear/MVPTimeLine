package com.vienan.mvptimeline;

import android.os.AsyncTask;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vienan on 16/4/17.
 */
public class ItemsAsyncLoader extends AsyncTask<Void,Void,List<Item>> {

    public interface ItemsLoaderListener {
        public void onSuccess(List<Item> countries);

        public void onError(Exception e);
    }

    private boolean shouldFail;
    private ItemsLoaderListener listener;

    public ItemsAsyncLoader(boolean shouldFail, ItemsLoaderListener listener) {
        this.listener = listener;
        this.shouldFail = shouldFail;
    }

    @Override protected List<Item> doInBackground(Void... params) {

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            return null;
        }

        List<Item> countries = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            String time=DateTime.now().minusWeeks(i).toString(" MM - dd");
            countries.add(new Item(time,"item #"+time));
        }
        return countries;
    }

    @Override protected void onPostExecute(List<Item> countries) {

        if (isCancelled() || countries == null) {
            return;
        }

        if (shouldFail) {
            listener.onError(new Exception("Oops something went wrong"));
        } else {
            listener.onSuccess(countries);
        }
    }
}
