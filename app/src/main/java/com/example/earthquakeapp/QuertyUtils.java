package com.example.earthquakeapp;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public final class QuertyUtils {



        /** Sample JSON response for a USGS query */
    /*    private static final String SAMPLE_JSON_RESPONSE = "{\"type\":\"FeatureCollection\",\"metadata\":{\"generated\":1574861938000,\"url\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-11-26&latitude=20.57&longitude=78.96&maxradiuskm=2000\",\"title\":\"USGS Earthquakes\",\"status\":200,\"api\":\"1.8.1\",\"count\":4},\"features\":[{\"type\":\"Feature\",\"properties\":{\"mag\":4.2999999999999998,\"place\":\"10km NE of `Alaqahdari-ye Kiran wa Munjan, Afghanistan\",\"time\":1574779498831,\"updated\":1574781699040,\"tz\":270,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006d6v\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006d6v&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":284,\"net\":\"us\",\"code\":\"70006d6v\",\"ids\":\",us70006d6v,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":1.5640000000000001,\"rms\":0.68000000000000005,\"gap\":84,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 4.3 - 10km NE of `Alaqahdari-ye Kiran wa Munjan, Afghanistan\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[70.845799999999997,36.106299999999997,109.01000000000001]},\"id\":\"us70006d6v\"},\n" +
                "{\"type\":\"Feature\",\"properties\":{\"mag\":5.4000000000000004,\"place\":\"26km WSW of Thayetmyo, Burma\",\"time\":1574766357534,\"updated\":1574852897903,\"tz\":390,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006d5e\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006d5e&format=geojson\",\"felt\":9,\"cdi\":3.7999999999999998,\"mmi\":4.1079999999999997,\"alert\":\"green\",\"status\":\"reviewed\",\"tsunami\":0,\"sig\":452,\"net\":\"us\",\"code\":\"70006d5e\",\"ids\":\",us70006d5e,\",\"sources\":\",us,\",\"types\":\",dyfi,geoserve,losspager,moment-tensor,origin,phase-data,shakemap,\",\"nst\":null,\"dmin\":2.4830000000000001,\"rms\":0.92000000000000004,\"gap\":24,\"magType\":\"mww\",\"type\":\"earthquake\",\"title\":\"M 5.4 - 26km WSW of Thayetmyo, Burma\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[94.945099999999996,19.241,59.759999999999998]},\"id\":\"us70006d5e\"},\n" +
                "{\"type\":\"Feature\",\"properties\":{\"mag\":4.7000000000000002,\"place\":\"20km SW of Thayetmyo, Burma\",\"time\":1574760972823,\"updated\":1574762440040,\"tz\":390,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006d4y\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006d4y&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":340,\"net\":\"us\",\"code\":\"70006d4y\",\"ids\":\",us70006d4y,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":2.444,\"rms\":0.69999999999999996,\"gap\":151,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 4.7 - 20km SW of Thayetmyo, Burma\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[95.027699999999996,19.206499999999998,78.150000000000006]},\"id\":\"us70006d4y\"},\n" +
                "{\"type\":\"Feature\",\"properties\":{\"mag\":4.5999999999999996,\"place\":\"49km SE of Kakching, India\",\"time\":1574740426892,\"updated\":1574741654040,\"tz\":390,\"url\":\"https://earthquake.usgs.gov/earthquakes/eventpage/us70006d1q\",\"detail\":\"https://earthquake.usgs.gov/fdsnws/event/1/query?eventid=us70006d1q&format=geojson\",\"felt\":null,\"cdi\":null,\"mmi\":null,\"alert\":null,\"status\":\"reviewed\",\"tsunami\":0,\"sig\":326,\"net\":\"us\",\"code\":\"70006d1q\",\"ids\":\",us70006d1q,\",\"sources\":\",us,\",\"types\":\",geoserve,origin,phase-data,\",\"nst\":null,\"dmin\":0.059999999999999998,\"rms\":0.45000000000000001,\"gap\":115,\"magType\":\"mb\",\"type\":\"earthquake\",\"title\":\"M 4.6 - 49km SE of Kakching, India\"},\"geometry\":{\"type\":\"Point\",\"coordinates\":[94.335800000000006,24.178599999999999,69.969999999999999]},\"id\":\"us70006d1q\"}],\"bbox\":[70.8458,19.2065,59.76,95.0277,36.1063,109.01]}";*/
        /**
         * Create a private constructor because no one should ever create a {@link QuertyUtils} object.
         * This class is only meant to hold static variables and methods, which can be accessed
         * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
         */

        private QuertyUtils()
        {

        }


        public  static ArrayList<format> extractEarthquakes( String s) {

            // Create an empty ArrayList that we can start adding earthquakes to
            ArrayList<format> earth = new ArrayList<format>();

            // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
            // is formatted, a JSONException exception object will be thrown.
            // Catch the exception so the app doesn't crash, and print the error message to the logs.
            try {
                JSONObject data=new JSONObject(s);
                 JSONArray dataarray =data.getJSONArray("features");
                for( int i=0;i<dataarray.length();i++)
                 {
                     JSONObject object=dataarray.getJSONObject(i);
                     JSONObject feature=object.getJSONObject("properties");
                     double  magni=feature.getDouble("mag");
                     String loc=feature.getString("place");
                     String url=feature.getString("url");
                     String[] arr=new String[2];
                     if(loc.contains("of"))
                     {
                         arr=loc.split("of");
                         arr[0]+="of";

                     }
                     else
                     {
                         arr[0]="Near the";
                         arr[1]=loc;
                     }
                     long tim = feature.getLong("time");
                     Date dateObject = new Date(tim);
                     SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM,dd, yyyy ");
                     String dateToDisplay = dateFormatter.format(dateObject);
                     SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
                     String timeToDisplay= timeFormat.format(dateObject);
                     earth.add(new format(magni,arr[0],arr[1],dateToDisplay,timeToDisplay,url));


                  }
                // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
                // build up a list of Earthquake objects with the corresponding data.

            }
            catch (JSONException e)
            {
                // If an error is thrown when executing any of the above statements in the "try" block,
                // catch the exception here, so the app doesn't crash. Print a log message
                // with the message from the exception.
                Log.e("QuertyUtils", "Problem parsing the earthquake JSON results", e);
            }

            // Return the list of earthquakes
            return earth;
        }

    }



