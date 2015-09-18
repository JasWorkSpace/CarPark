package com.greenorange.gooutdoor.UI.adapter.entity;

/**
 * Created by JasWorkSpace on 15/9/14.
 */
public abstract class Category {
    public long     Key;
    public Object  Value;
    public long getKey(){
        return Key;
    }
    public abstract Object getHeader();
    public abstract Object getItem();
}
