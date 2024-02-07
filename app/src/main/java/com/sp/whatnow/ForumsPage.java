package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuItem;

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
    private List<Forum> model = new ArrayList<>();
    private ForumAdapter adapter = null;
    private RequestQueue queue;
    private ForumHelper helper;
    private Toolbar toolbar;
    private int volleyResponseStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums_page);
        list = findViewById(R.id.ForumList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getParent()); // dont know what this does

        toolbar = findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
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

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.forum_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        if (item.getItemId() == R.id.forum_refresh) {
            getAllVolley(); //Update the RecyclerView
        } else if (item.getItemId() == R.id.forum_add) {
            intent = new Intent(ForumsPage.this,DetailsForum.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


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
                                        r.setTitle(data.getJSONObject(i).getString("forumtitle")); //extract the restaurantname
                                        r.setUser(data.getJSONObject(i).getString("forumuser")); //extract the restaurantaddress
                                        r.setContent(data.getJSONObject(i).getString("forumcontent")); //extract the restauranttel
                                        r.setDate(data.getJSONObject(i).getString("forumdate")); //extract the restauranttype
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
    static class ForumHolder extends RecyclerView.ViewHolder {
        private TextView rowTitle;
        private TextView rowUser;
        private TextView rowContent;
        private TextView rowDate;

        ForumHolder(View itemView) {
            super(itemView);
            rowTitle = itemView.findViewById(R.id.row_title);
            rowUser = itemView.findViewById(R.id.row_user);
            rowContent = itemView.findViewById(R.id.row_content);
            rowDate = itemView.findViewById(R.id.row_date);
        }

        void populateFrom(Forum r) {
            rowTitle.setText(r.getTitle());
            rowUser.setText(r.getUser());
            rowContent.setText(r.getContent());
            rowDate.setText(r.getDate());
        }
    }
    class ForumAdapter extends RecyclerView.Adapter<ForumHolder> {

        public void add(Forum forum) {
            model.add(forum);
            notifyDataSetChanged();
        }

        public void clear() {
            model.clear();
            notifyDataSetChanged();
        }
        @Override
        public ForumHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row, parent, false);
            return new ForumHolder(view);
        }

        @Override
        public void onBindViewHolder(ForumHolder holder, int position) {
            holder.populateFrom(model.get(position));
        }

        @Override
        public int getItemCount() {
            return model.size();
        }
    }

}
    /*
    static class ForumHolder{
        private TextView rowTitle;
        private TextView rowDate;
        private TextView rowUser;
        private TextView rowContent;

        ForumHolder(View row){
            rowTitle = row.findViewById(R.id.row_title);
            rowDate = row.findViewById(R.id.row_date);
            rowUser = row.findViewById(R.id.row_user);
            rowContent = row.findViewById(R.id.row_content);
        }
        void populateFrom(Cursor c,ForumHelper helper){
            rowTitle.setText(helper.getforumtitle(c));
            rowDate.setText(helper.getforumdate(c));
            rowUser.setText(helper.getforumuser(c));
            rowContent.setText(helper.getforumcontent(c));
        }
    }

     */
    /*
    class RestaurantAdapter extends CursorAdapter {
        RestaurantAdapter(Context context, Cursor cursor, int flags) {
            super(context,cursor,flags);
        }

        @Override
        public void bindView (View view, Context context, Cursor cursor){
            ForumHolder holder = (ForumHolder) view.getTag();
            holder.populateFrom(cursor,helper);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent){
            LayoutInflater inflater = getLayoutInflater();
            View row = inflater.inflate(R.layout.row, parent, false);
            ForumHolder holder = new ForumHolder(row);
            row.setTag(holder);
            return (row);
        }
    }

     */