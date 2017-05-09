package com.example.hand.mockingbot.entity;

import android.net.Uri;

/**
 * Created by zhy on 2017/5/9.
 */

public class AttauchBean {

    private String FieldName;
    private Uri uri;
    private String Extension;
    private String Size;

    public void setFieldName(String fieldName){
        this.FieldName = fieldName;
    }

    public String getFieldName(){
        return FieldName;
    }

    public void setUri(Uri uri1){
        this.uri = uri1;
    }

    public Uri geturi(){
        return uri;
    }

    public void setExtension(String extension){
        this.Extension = extension;
    }

    public String getExtension(){
        return Extension;
    }

    public void setSize(String size){
        this.Size = size;
    }

    public String getSize(){
        return Size;
    }
}
