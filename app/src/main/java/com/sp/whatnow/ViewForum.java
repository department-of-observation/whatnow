package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;


import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ViewForum extends AppCompatActivity {
    private String dataforum_id ="";
    private String dataforumtitle ="";
    private String dataforumuser ="";
    private String dataforumcontent ="";
    private String dataforumdate ="";
    private String datagoogleid ="";

    private TextView forumtitle;
    private TextView forumdate;
    private TextView forumuser;
    private TextView forumtext;
    private Button deletebutton;
    private Toolbar toolbar;
    private ListView list;
    private List<Comment> model = new ArrayList<>();
    private CommentAdapter adapter = null;
    private RequestQueue queue;
    private int volleyResponseStatus;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forum);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String currentgoogleid = "";
        TextView forumtitle = findViewById(R.id.forum_title);
        TextView forumdate = findViewById(R.id.forum_date);
        TextView forumuser = findViewById(R.id.forum_user);
        TextView forumtext = findViewById(R.id.forum_text);
        list = findViewById(R.id.comments_list);
        adapter = new CommentAdapter();
        list.setAdapter(adapter);
        toolbar = findViewById(R.id.toolbar3);
        deletebutton = findViewById(R.id.delete_forum);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        if (intent != null) {
            dataforum_id = intent.getStringExtra("forum_id");
            dataforumtitle = intent.getStringExtra("forumtitle");
            dataforumuser = intent.getStringExtra("forumuser");
            dataforumcontent = intent.getStringExtra("forumcontent");
            dataforumdate = intent.getStringExtra("forumdate");
            datagoogleid = intent.getStringExtra("googleid");
            // Now, 'receivedData' contains the string sent from the previous activity
        }
        forumtitle.setText(dataforumtitle);
        forumdate.setText(dataforumdate);
        forumuser.setText(dataforumuser);
        forumtext.setText(dataforumcontent);
        if (user != null) {
            currentgoogleid = user.getUid();
            Log.d("geh", "getting google id lol");
        }
        if (currentgoogleid.equals(datagoogleid)) {
            deletebutton.setVisibility(View.VISIBLE);
            Log.d("ceh", "WOOSH VISIBLEEE");
        } else {
            deletebutton.setVisibility(View.GONE);
            Log.d("beh", "AWWWW ITS GONEEEEE");
            Log.d("beg",currentgoogleid);
            Log.d("beg",datagoogleid);
        }
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dataforum_id != null) {
                    // Delele current record
                    deleteVolley(dataforum_id);
                }
                finish();
            }
        });

    }
    @Override
    protected void onResume() {
        getAllVolley();
        super.onResume();
    }
    static class CommentHolder {
        private TextView commUser = null;
        private TextView commCont = null;

        CommentHolder(View row) {
            commUser = row.findViewById(R.id.comment_user);
            commCont = row.findViewById(R.id.comment_text);
        }

        void populateFrom(Comment r) {
            commUser.setText(r.getUser());
            commCont.setText(r.getContent());

        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.forum_refresh) {
            Log.d("soh", dataforum_id);
            getAllVolley();
        } else if (item.getItemId() == R.id.forum_add) {
            intent = new Intent(ViewForum.this,AddComment.class);
            intent.putExtra("forum_id", dataforum_id);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
    class CommentAdapter extends ArrayAdapter<Comment> {
        CommentAdapter() {
            super(ViewForum.this, R.layout.comments, model);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row = convertView;
            CommentHolder holder;

            if (row == null) {
                LayoutInflater inflater = LayoutInflater.from(getContext());
                row = inflater.inflate(R.layout.comments, parent, false);
                holder = new CommentHolder(row);
                row.setTag(holder);
            } else {
                holder = (CommentHolder) row.getTag();
            }

            holder.populateFrom(model.get(position));

            return row;
        }
    }

    private void getAllVolley() {
        queue = Volley.newRequestQueue(this);
        String url = CommentVolleyHelper.url + dataforum_id + "?";
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
                                    for (int i = 0; i < count; i++) { // Loop through all records
                                        Comment r = new Comment();
                                        // For each json record
                                        r.setId(data.getJSONObject(i).getString("forum_id")); //read the id
                                        r.setCId(data.getJSONObject(i).getString("comment_id")); //extract the restaurantname
                                        r.setUser(data.getJSONObject(i).getString("forum_user")); //extract the restaurantaddress
                                        r.setContent(data.getJSONObject(i).getString("comment_content")); //extract the restauranttel
                                        Log.d("geh", r.getUser());
                                        Log.d("geh", r.getId());
                                        Log.d("soh", dataforum_id);
                                        Log.d("geh", r.getCId());
                                        Log.d("geh", r.getContent());

                                        adapter.add(r); // add the record to the adapter
                                    }
                                    adapter.notifyDataSetChanged(); // notify adapter of data change
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
                return CommentVolleyHelper.getHeader();
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

    private void deleteVolley(String id) {
        // Rest api link
        String url = ForumVolleyHelper.url + id; // Delete by id
        RequestQueue queue = Volley.newRequestQueue(this);
        // Use DELETE REST api call
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getApplicationContext(), "Response status : " + volleyResponseStatus, Toast.LENGTH_LONG).show();
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

}