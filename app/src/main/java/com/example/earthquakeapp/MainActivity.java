package com.example.earthquakeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements LoaderManager.LoaderCallbacks<ArrayList<format>> {
    private  static final String USGS_URL="https://earthquake.usgs.gov/fdsnws/" +
            "event/1/query";

    private FormatAdapter itemAdapter;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        URL url = createUrl(USGS_URL);
        itemAdapter = new FormatAdapter(this, new ArrayList<format>());
        ListView listView = (ListView) findViewById(R.id.list_v);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                format current = (format) parent.getAdapter().getItem(position);
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(current.getUrl()));
                startActivity(i);

            }
        });
        listView.setAdapter(itemAdapter);
        emptyView = findViewById(R.id.emptyView);
        listView.setEmptyView(emptyView);
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager.getInstance(this).initLoader(1, null, this);
        }
        else
        {
            emptyView.setText("No internet connection");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Log.v("tag13","working13");
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
       // Log.v("tag14","working14");
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
           // Log.v("tag15","working15");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public Loader<ArrayList<format>> onCreateLoader(int i, Bundle bundle)
    {
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default));
        Uri baseUri = Uri.parse(USGS_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", "10");
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

       return new EarthquakeLoader(MainActivity.this, uriBuilder.toString());
    }

    public void onLoadFinished(Loader<ArrayList<format>> loader,ArrayList<format> info)
    {
        ProgressBar progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        itemAdapter.clear();
        if(info!=null&&!info.isEmpty()) {
            itemAdapter.addAll(info);
        }
           else
            {
                emptyView.setText("No data to show");
            }
        }


    public void onLoaderReset(Loader<ArrayList<format>> loader)
    {
        itemAdapter=new FormatAdapter(this,new ArrayList<format>());
    }


    public  static String makeHttpRequest(URL url) throws IOException
    {
        String jsonResponse="";
        HttpURLConnection urlConnection= null;
        InputStream inputStream=null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
           //Log.v("tag1","working1");
            urlConnection.connect();
           // Log.v("tag2","working2");
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            Log.e("HTTP_ERROR","MAKING REQUEST",e);
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
        }


    public static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e("URL_ERROR", "Error with creating URL", exception);
            return null;
        }
        return url;
    }

    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
}
