package com.example.earthquakeapp;

public class format {
    private double mag;
    private  String place;
    private String date;
    private String time;
    private String offset;
    private  String url;
    public format(  double mad,String p,String o,String d,String t,String u)
    {
        this.mag=mad;
        this.place=p;
        this.date=d;
        this.time=t;
        this.offset=o;
        this.url=u;
    }


    double getMag()
    {
        return mag;
    }
    String getPlace()
    {
        return place;
    }
    String getDate()
    {
        return date;
    }

    String getTime()
    {
        return time;
    }
    String getOffset()
    {
        return offset;
    }
    String getUrl()
    {
        return url;
    }

}
