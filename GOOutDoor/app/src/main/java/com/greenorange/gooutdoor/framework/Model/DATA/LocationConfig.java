package com.greenorange.gooutdoor.framework.Model.DATA;

/**
 * Created by JasWorkSpace on 15/4/19.
 */
public class LocationConfig {
    public int maptype;
    public boolean enable;
    public int frq;
    public int mindistance;

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LocationConfig{")
                .append("maptype="+maptype)
                .append(", enable="+enable)
                .append(", frq="+frq)
                .append(", mindistance="+mindistance)
                .append("}");
        return sb.toString();
    }
}
