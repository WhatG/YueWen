package com.examplel.awesome_men.yuewen.DataClass;

import android.os.Parcel;
import android.os.Parcelable;

import com.examplel.awesome_men.yuewen.Utils.AppUtils;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by longer on 2017/4/15.
 */

public class Artical implements Parcelable{
    private String id;
    private String title;
    private String desc;
    private String uid;
    private String uname;
    private String uicon;
    private String time;
    private String content;

    public void setContent(JSONObject jsonContent){
        try{
            title = jsonContent.getString("atitle");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            desc = jsonContent.getString("adscp");
            desc = desc.equals("null")||desc.equals("")?null:desc;
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
        }try{
            uicon = jsonContent.getString("uicon");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            time = AppUtils.stampToDate(jsonContent.getString("atime"));

        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            id = jsonContent.getString("aid");
        }catch (JSONException je){
            je.printStackTrace();
        }
        try{
            content = jsonContent.getString("acontent");
        }catch (JSONException je){
            je.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
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

    public String getContent(){
        return content;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(uid);
        dest.writeString(uname);
        dest.writeString(uicon);
        dest.writeString(time);
        dest.writeString(content);
    }

    public  static final Parcelable.Creator<Artical> CREATOR = new Creator<Artical>() {
        @Override
        public Artical createFromParcel(Parcel source) {
            return new Artical(source);
        }

        @Override
        public Artical[] newArray(int size) {
            return new Artical[size];
        }
    };

    public Artical(){

    }

    private Artical(Parcel source){
        this.id = source.readString();
        this.title = source.readString();
        this.desc = source.readString();
        this.uid = source.readString();
        this.uname = source.readString();
        this.uicon = source.readString();
        this.time = source.readString();
        this.content = source.readString();
    }
}
