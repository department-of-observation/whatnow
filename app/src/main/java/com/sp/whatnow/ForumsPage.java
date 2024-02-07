package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForumsPage extends AppCompatActivity {
    private RecyclerView list;
    private Cursor model = null;
    private RequestQueue queue;
    private int volleyResponseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums_page);
        list = findViewById(R.id.ForumList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getParent()); // dont know what this does
    }

    @Override
    protected void onStart(){
        invalidateOptionsMenu();
        super.onStart();
    }

    @Override
    protected void onResume() {
        //getAllVolley();
        super.onResume();
    }

    static class forumholder{
        private TextView rowTitle;
        private TextView rowDate;
        private TextView rowUser;
        private TextView rowContent;

        forumholder(View row){
            rowTitle = row.findViewById(R.id.row_title);
            rowDate = row.findViewById(R.id.row_date);
            rowUser = row.findViewById(R.id.row_user);
            rowContent = row.findViewById(R.id.row_content);
        }
    }
    /*
    private void getAllVolley() {
        queue = Volley.newRequestQueue(this);
        String url = ForumVolleyHelper.url + "rows"; //Query all records
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if (volleyResponseStatus == 200) { // Read successfully from database
                            try {
                                int count = response.getInt("count"); //Number of records from database
                                adapter.clear(); //reset adapter
                                if (count > 0) { //Has more than 1 record
                                    JSONArray data = response.getJSONArray("data");//Get all the records as JSON array
                                    for (int i = 0; i <= count; i++) { // Loop through all records
                                        Forum r = new Forum();
                                        // Store the lastest id in lastID
                                        if (ForumVolleyHelper.lastID < data.getJSONObject(i).getInt("id")) {
                                            ForumVolleyHelper.lastID = data.getJSONObject(i).getInt("id");
                                        }
                                        // For each json record
                                        r.setId(data.getJSONObject(i).getString("id")); //read the id
                                        r.setName(data.getJSONObject(i).getString("restaurantname")); //extract the restaurantname
                                        r.setAddress(data.getJSONObject(i).getString("restaurantaddress")); //extract the restaurantaddress
                                        r.setTelephone(data.getJSONObject(i).getString("restauranttel")); //extract the restauranttel
                                        r.setRestaurantType(data.getJSONObject(i).getString("restauranttype")); //extract the restauranttype
                                        r.setLat(data.getJSONObject(i).getString("lat")); //extract the lat
                                        r.setLon(data.getJSONObject(i).getString("lon")); //extract the lon
                                        adapter.add(r); // add the record to the adapter
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error
                        Log.e("OnErrorResponse", error.toString());
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() {
                return ForumVolleyHelper.getHeader();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                volleyResponseStatus = response.statusCode;
                return super.parseNetworkResponse(response);
            }
        };
        // add JsonObjectRequest to the RequestQueue
        queue.add(jsonObjectRequest);
    }

     */

}