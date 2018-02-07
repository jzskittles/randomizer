package com.randomizerapp.randomizer;

import android.graphics.drawable.Drawable;

/**
 * Created by jenny on 12/23/2017.
 */

public class Item{//} implements Parcelable {
    CharSequence label; //package name
    CharSequence name; //app name
    Drawable icon; //app icon
    int position;

    public Item(){

    }

    public String getName(){
        return name.toString();
    }
    public void setName(String appName){
        name = appName;
    }
    public int getPosition(){
        return position;
    }

    public void setPosition(int i){
        position=i;
    }

    public String getLabel(){
        return label.toString();
    }

    public void setLabel(String newlabel){
        label = newlabel;
    }

    public Drawable getIcon(){return icon;}

    public void setIcon(Drawable appIcon){icon = appIcon;}



}
