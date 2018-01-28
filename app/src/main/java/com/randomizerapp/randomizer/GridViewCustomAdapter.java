package com.randomizerapp.randomizer;

import android.app.Activity;
import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;

/**
 * Created by jenny on 1/1/2018.
 */

public class GridViewCustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Item> items;
    LayoutInflater inflter = null;

    public GridViewCustomAdapter(Context applicationContext, ArrayList<Item> tempTitle){
        this.context = applicationContext;
        items = tempTitle;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public final int getCount(){
        return items.size();
    }

    @Override
    public final Object getItem(int position){
        return items.get(position);
    }

    @Override
    public long getItemId(int i){
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        view = inflter.inflate(R.layout.item, null);
        return view;
    }
}
