package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DetailsForum extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsContent;
    private Button save_button;
    private FirebaseAuth firebaseAuth;
    private int volleyResponseStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_forum);
        detailsTitle = findViewById(R.id.details_title);
        detailsContent = findViewById(R.id.details_text);
        save_button = findViewById(R.id.details_save);
        save_button.setOnClickListener(onSave);
        firebaseAuth = FirebaseAuth.getInstance();



    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    View.OnClickListener onSave = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UUID uuid = UUID.randomUUID();
            String newuuid = uuid.toString();
            String titleStr =  detailsTitle.getText().toString();
            String contentStr = detailsContent.getText().toString();
            String currentDate = getCurrentDate();
            String nameStr = null;
            if (user != null) {
                nameStr = user.getDisplayName();
            } else {
                Toast.makeText(DetailsForum.this, "Please log in to Post.", Toast.LENGTH_SHORT).show();
                finish();
            }
            insertVolley(newuuid, titleStr, nameStr, contentStr, currentDate);

        }
        private String getCurrentDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date());
        }
    };

    // Insert a new record to Astra DB
    private void insertVolley(String id, String titleStr, String nameStr, String contentStr, String currentDate) {
        // Create a JSON object from the parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("forumtitle", titleStr);
        params.put("forumuser", nameStr);
        params.put("forumcontent", contentStr);
        params.put("forumdate", currentDate);
        JSONObject postdata = new JSONObject(params); // Data as JSON object to be insert into the database
        RequestQueue queue = Volley.newRequestQueue(this);
        // Rest api link
        String url = ForumVolleyHelper.url;
        // Use POST REST api call
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postdata,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

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
        };
        // add JsonObjectRequest to the RequestQueue
        queue.add(jsonObjectRequest);
    }
    // Update a current record
}