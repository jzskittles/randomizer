package com.randomizerapp.randomizer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by jenny on 1/30/2018.
 */

public class SpinnerAdapter extends ArrayAdapter<Item> {
    int groupid;
    ArrayList<Item> list;
    LayoutInflater inflater;

    public SpinnerAdapter(Context context, int groupid, int id, ArrayList<Item> list){
        super(context, id, list);
        this.list = list;
        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid = groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        View itemView = inflater.inflate(groupid, parent, false);
        ImageView imageView = (ImageView)itemView.findViewById(R.id.spinnerImage);
        imageView.setImageDrawable(list.get(position).getIcon());
        TextView textView = (TextView)itemView.findViewById(R.id.spinnerText);
        textView.setText(list.get(position).getName());
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup parent){
        return getView(position, convertView, parent);
    }

}
