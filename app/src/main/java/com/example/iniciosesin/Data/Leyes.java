package com.example.iniciosesin.Data;

import android.os.Parcel;
import android.os.Parcelable;

public class Leyes implements Parcelable {
    private String name;
    private String description;
    private String linkInfo;

    protected Leyes(Parcel in) {
        name = in.readString();
        description = in.readString();
        linkInfo = in.readString();
    }

    public static final Creator<Leyes> CREATOR = new Creator<Leyes>() {
        @Override
        public Leyes createFromParcel(Parcel in) {
            return new Leyes(in);
        }

        @Override
        public Leyes[] newArray(int size) {
            return new Leyes[size];
        }
    };

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name= name;
    }

    public String getDescription(){
        return description;
    }
    public void setDescription(String description){
        this.description= description;
    }

    public String getLinkInfo(){
        return linkInfo;
    }
    public void setLinkInfo(String linkInfo){
        this.linkInfo= linkInfo;
    }

    public Leyes(String name, String description, String linkInfo) {
        this.name = name;
        this.description = description;
        this.linkInfo= linkInfo;
    }

    @Override
    public String toString(){
        return name+ "\n\n\n" +description+ "\n\nEncuentra más en: "+linkInfo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(linkInfo);
    }
}
