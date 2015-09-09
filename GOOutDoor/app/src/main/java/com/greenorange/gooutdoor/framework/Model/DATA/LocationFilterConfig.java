package com.greenorange.gooutdoor.framework.Model.DATA;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public class LocationFilterConfig {
    public int maptype;
    public int sportstype;
    public int mindistance;
    public int maxdistance;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LocationFilterConfig{")
                .append("maptype="+maptype)
                .append(", sportstype="+sportstype)
                .append(", mindistance="+mindistance)
                .append(", maxdistance="+maxdistance)
                .append("}");
        return sb.toString();
    }
}
