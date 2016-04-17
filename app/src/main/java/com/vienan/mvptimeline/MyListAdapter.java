package com.vienan.mvptimeline;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by vienan on 16/4/17.
 */
public class MyListAdapter extends ArrayAdapter<Item> {

    private LayoutInflater mLayoutInflater;
    private List<Item> mItems;

    public void setItems(List<Item> items) {
        mItems = items;
    }

    private int resource;
    public MyListAdapter(Context context, int resource) {
        super(context, resource);
        mLayoutInflater=LayoutInflater.from(context);
        this.resource=resource;
//        this.mItems=items;
    }

    @Override
    public int getCount() {
        return mItems==null?0:mItems.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Item getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            convertView=mLayoutInflater.inflate(resource,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.mTextView= (TextView) convertView.findViewById(R.id.text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        if (getItem(position)!=null) viewHolder.render(getItem(position));
        return convertView;
    }
    static class ViewHolder{
        TextView mTextView;

        public void render(Item item){
            mTextView.setText(item.itemName);
        }
    }
}
