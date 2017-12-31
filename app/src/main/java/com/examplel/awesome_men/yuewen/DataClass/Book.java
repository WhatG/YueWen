package com.examplel.awesome_men.yuewen.DataClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.examplel.awesome_men.yuewen.Utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by longer on 2017/4/15.
 */

public class Book implements Parcelable{
    private String id;
    private String uid;
    private String uicon;
    private String uname;
    private String bname;
    private String bdesc;
    private String bauthor;
    private String brepublic;
    private String images;
    private String time;

    public void setContent(JSONObject jsonContent){
        try{
            id = jsonContent.getString("id");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            uid = jsonContent.getString("uid");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            uname = jsonContent.getString("uname");
        }catch(JSONException je){
            je.printStackTrace();
        }
        try{
            uicon = jsonContent.getString("uicon");
        }catch (JSONException je){
            je.printStackTrace();
        }

        try{
            time = AppUtils.stampToDate(jsonContent.getString("btime"));

        }catch (JSONException je){
            je.printStackTrace();
        }

        try{
            bdesc = jsonContent.getString("bdesc");
            bdesc = bdesc.equals("null")||bdesc.equals("")?null:bdesc;
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            bname = jsonContent.getString("bname");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            bauthor = jsonContent.getString("bauthor");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            brepublic = jsonContent.getString("brepublic");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            images = jsonContent.getString("images");
            images = (images.equals("null")||images.equals(""))?null:images;
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public String getBname() {
        return bname;
    }

    public String getBdesc() {
        return bdesc;
    }

    public String getTime() {
        return time;
    }

    public String getUicon() {
        return uicon;
    }

    public String getUname() {
        return uname;
    }

    public String getId() {
        return id;
    }

    public String getUid() {
        return uid;
    }

    public String getBauthor(){
        return bauthor;
    }

    public String getBrepublic() {
        return brepublic;
    }

    public String getImages() {
        return images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(uid);
        dest.writeString(uname);
        dest.writeString(uicon);
        dest.writeString(bname);
        dest.writeString(bauthor);
        dest.writeString(brepublic);
        dest.writeString(bdesc);
        dest.writeString(time);
        dest.writeString(images);
    }

    public  static final Parcelable.Creator<Book> CREATOR = new Parcelable.Creator<Book>() {
        @Override
        public Book createFromParcel(Parcel source) {
            return new Book(source);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    public Book(){

    }

    private Book(Parcel source){
        this.id = source.readString();
        this.uid = source.readString();
        this.uname= source.readString();
        this.uicon = source.readString();
        this.bname = source.readString();
        this.bauthor = source.readString();
        this.brepublic = source.readString();
        this.bdesc = source.readString();
        this.time = source.readString();
        this.images = source.readString();
    }
}
