package com.randomizerapp.randomizer;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

/**
 * Created by jenny on 12/23/2017.
 */

public class Item{
    CharSequence label; //package name
    CharSequence name; //app name
    Drawable icon; //app icon
    int position;

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


}
