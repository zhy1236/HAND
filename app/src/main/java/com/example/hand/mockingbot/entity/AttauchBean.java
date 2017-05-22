package com.example.hand.mockingbot.entity;

/**
 * Created by zhy on 2017/5/9.
 */

public class AttauchBean {

    private String FieldName;
    private String Extension;
    private String Size;
    private String Path;

    public void setPath(String path) {
        Path = path;
    }

    public String getPath() {
        return Path;
    }

    public void setFieldName(String fieldName){
        this.FieldName = fieldName;
    }

    public String getFieldName(){
        return FieldName;
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
