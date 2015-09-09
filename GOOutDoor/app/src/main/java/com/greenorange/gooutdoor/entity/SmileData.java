package com.greenorange.gooutdoor.entity;


/**
 * Created by JasWorkSpace on 15/8/11.
 */
public class SmileData {

    private String url;

    private String thumbnail;

    private String name;

    private int    id;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("SmileData{")
                .append("uri=" + url)
                .append(", thumbnail=" + thumbnail)
                .append(", name=" + name)
                .append(", id="+id)
                .append("}");
        return sb.toString();
    }
}
