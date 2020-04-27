package com.example.earthquakeapp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.loader.content.AsyncTaskLoader;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class EarthquakeLoader extends AsyncTaskLoader<ArrayList<format>> {
    String str;
    public EarthquakeLoader(Context context,String s)
    {
        super(context);
        str=s;
    }
    @Override
   protected void onStartLoading()
   {
        forceLoad();
   }
    @Override
    public ArrayList<format> loadInBackground() {
        URL url=MainActivity.createUrl(str);
        if(url==null)
        {
            return null;
        }
        String JSON_response= "";
        try{

            JSON_response= MainActivity.makeHttpRequest(url);
        }
        catch (IOException e)
        {
            Log.e("JSON_ERROR","ERROR  IS THERE",e);
        }
        ArrayList<format> info=new ArrayList<format>();
        info= QuertyUtils.extractEarthquakes(JSON_response);
        return info;

    }
}
