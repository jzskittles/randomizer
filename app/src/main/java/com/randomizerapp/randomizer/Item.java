package com.randomizerapp.randomizer;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

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

    /*public Item (Parcel in){
        this.label = in.readString();
        this.name = in.readString();
        Bitmap bitmap = (Bitmap) in.readParcelable(getClass().getClassLoader());
        if(bitmap!=null){
            this.icon = new BitmapDrawable(bitmap);
        }else
            this.icon = null;

    }*/

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

    /*@Override
    public void writeToParcel(Parcel dest, int flags){
        dest.writeString(label.toString());
        dest.writeString(name.toString());
        if(icon !=null){
            Bitmap bitmap = ((BitmapDrawable)icon).getBitmap();
            dest.writeParcelable(bitmap, flags);
        }else
            dest.writeParcelable(null, flags);
    }

    public int describeContents(){
        return 0;
    }

    static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>(){
        public Item createFromParcel(Parcel in){
            return new Item(in);
        }

        public Item[] newArray(int size){
            return new Item[size];
        }
    };*/


}
