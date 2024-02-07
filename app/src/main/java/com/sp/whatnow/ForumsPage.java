package com.sp.whatnow;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ForumsPage extends AppCompatActivity {
    private RecyclerView list;
    private List<Retrieval> model = new ArrayList<>();
    private ForumAdapter adapter = null;
    private RequestQueue queue;
    private ForumHelper helper;
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

    static class ForumAdapter extends RecyclerView.Adapter<ForumAdapter.ForumHolder>{
        private Context context;
        private ForumHelper helper;
        private Cursor cursor;
        private LayoutInflater inflater;
        private OnItemClickListener listener;

        interface OnItemClickListener {
            void onItemClick(int i, double longitude, double latitiude);
        }
        public void setOnItemClickListener(OnItemClickListener listener) {
            this.listener = listener;
        }

        ForumAdapter(Context context, Cursor cursor, ForumHelper helper){
            this.cursor = cursor;
            this.helper = helper;
            inflater=LayoutInflater.from(context);
        }
        public void swapCursor(Cursor newCursor) {
            if (cursor != null) {
                cursor.close();
            }
            cursor = newCursor;

            if (newCursor != null) {
                notifyDataSetChanged();
            }
        }
        public ForumHolder onCreateViewHolder(ViewGroup parent,int viewType){
            return new ForumHolder(this.inflater.inflate(R.layout.row, parent, false));
        }

        public void onBindViewHolder(ForumAdapter.ForumHolder holder,final int position){
            cursor.moveToPosition(position);
            holder.populateFrom(cursor,helper);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    int clickedPosition = holder.getAdapterPosition();
                    if (ForumAdapter.this.listener != null && clickedPosition != -1) {
                        ForumAdapter.this.cursor.moveToPosition(clickedPosition);
                        int i = clickedPosition;

                    }
                }
            });


        }
        public int getItemCount(){return cursor.getCount();}

        class ForumHolder extends RecyclerView.ViewHolder{
            //initialises the views
            private TextView rowTitle;
            private TextView rowUser;
            private TextView rowContent;
            private TextView rowDate;

            //retrieves the views to be changed
            ForumHolder(View row){
                super(row);
                rowTitle = row.findViewById(R.id.row_title);
                rowUser = row.findViewById(R.id.row_user);
                rowContent = row.findViewById(R.id.row_content);
                rowDate = row.findViewById(R.id.row_date);

            }
            //changes the views according to the database
            public void populateFrom(Cursor c, ForumHelper helper){
                rowTitle.setText(helper.getforumtitle(c));
                rowDate.setText(helper.getforumdate(c));
                rowUser.setText(helper.getforumuser(c));
                rowContent.setText(helper.getforumcontent(c));

            }

        }

    }
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



}