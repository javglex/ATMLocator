package com.atmlocator.jeg.atmlocator.Controllers;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atmlocator.jeg.atmlocator.Models.ATMInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Javier Gonzalez on 4/18/2017.
 * Handles requests through Volley
 */

public class PostServices {

    public static PostServices postServices;
    RequestQueue mQueue;
    String url ="https://m.chase.com/PSRWeb/location/list.action";
    private Context context;


    PostServices(Context context) {
        mQueue = Volley.newRequestQueue(context);
        this.context = context;

    }

    public static PostServices getInstance(Context context) {
        if (postServices == null) {
            postServices = new PostServices(context);
        }
        return postServices;
    }

    /**
     * Sends a request for a list of nearby ATMs depending on location
     * @param lat user position in latitude
     * @param lng user position in longitude
     * @param callback returns a callback with a list of ATM objects
     */
    public void requestLocations(final double lat, final double lng, final VolleyCallback callback) {

        if (!networkConnected(context)){
            Toast.makeText(context,"Please connect to a network.",
                    Toast.LENGTH_SHORT).show();
            return;

        }

        StringRequest stringRequest = new StringRequest (Request.Method.POST,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("response", response.toString());

                    //Creating JsonObject from response String
                    JSONObject jsonObject= new JSONObject(response.toString());
                    // parse "locations"
                    List<ATMInfo> locations = new ArrayList<ATMInfo>();
                    List<JSONObject> listObjs = parseJsonData(jsonObject,"locations");
                    for (JSONObject o: listObjs) {
                        ATMInfo atm = new ATMInfo();
                        atm.setState(o.optString("state"));
                        atm.setLocType(o.optString("locType"));
                        atm.setAccess(o.optString("access"));
                        atm.setAddress(o.optString("address"));
                        atm.setCity(o.optString("city"));
                        atm.setZip(o.optString("zip"));
                        atm.setName(o.optString("name"));
                        atm.setLabel(o.optString("label"));
                        atm.setLat(o.optString("lat"));
                        atm.setLng(o.optString("lng"));
                        atm.setBank(o.optString("bank"));
                        atm.setDistance(o.optString("distance"));
                        atm.setLanguages(o.optJSONArray("languages"));
                        atm.setServices(o.optJSONArray("services"));
                        atm.setHours(o.optJSONArray("lobbyHrs"));
                        locations.add(atm);
                        Log.d("atm", atm.getAccess() + " " +atm.getBank() +" "+ atm.getAddress() + " "+atm.getServices());
                    }

                    Log.d("result", listObjs.toString());
                    callback.onSuccess(locations);   //if succesfull will return list of ATMs

                } catch (JSONException e) {

                }

            }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO Auto-generated method stub
                    try {
                        Log.e("Post Services", error.getMessage().toString());
                    } catch (NullPointerException e) {

                        e.printStackTrace();
                    }
                }
            }){
            /*
                Set the post request paramters
             */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> parameters = new HashMap<String,String>();
                parameters.put("lat", String.valueOf(lat));
                parameters.put("lng",String.valueOf(lng));
                return parameters;
            }

        };
            // Adding request to request queue
            mQueue.add(stringRequest);


        }

    //returns true of either wifi or 4g is connected to the network
    public static boolean networkConnected (Context context) {

        ConnectivityManager connectivityMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = connectivityMgr.getActiveNetworkInfo();
        NetworkInfo cellular = connectivityMgr.getActiveNetworkInfo();
        if (wifi != null && wifi.getType() == ConnectivityManager.TYPE_WIFI)
            return true;
        else
            if (cellular != null && cellular.getType() == ConnectivityManager.TYPE_MOBILE)
            return true;

        return false;

    }

    public static List<JSONObject> parseJsonData(JSONObject obj, String pattern) throws JSONException {

        List<JSONObject> objects = new ArrayList<>();
        JSONArray data = obj.getJSONArray (pattern);
        for (int i = 0; i < data.length(); ++i) {
            final JSONObject site = data.getJSONObject(i);
            objects.add(site);
        }
        return objects;
    }

    public interface VolleyCallback{
        void onSuccess(List<ATMInfo> result);
    }
}
