package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddComment extends AppCompatActivity {

    private EditText comment;
    private Button sendComment;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_comment);

        comment = findViewById(R.id.commentText);
        sendComment = findViewById(R.id.sendComment);
        sendComment.setOnClickListener(onSend);
        firebaseAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    View.OnClickListener onSend = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            UUID uuid = UUID.randomUUID();
            String newuuid = uuid.toString();
            String comment_content = comment.getText().toString();
            String nameStr = null;
            if (user != null) {
                nameStr = user.getDisplayName();
            } else {
                Toast.makeText(AddComment.this, "Please log in to Post.", Toast.LENGTH_SHORT).show();
                finish();
            }
            insertVolley(forum_id, newuuid, nameStr, comment_content);
            finish();
        }
        private String getCurrentDate() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(new Date());
        }
    };

    private void insertVolley(String forum_id, String comment_id, String forum_user, String comment_content) {
        // Create a JSON object from the parameters
        Map<String, String> params = new HashMap<String, String>();
        params.put("forum_id", forum_id);
        params.put("comment_id", comment_id);
        params.put("forum_user", forum_user);
        params.put("comment_content", comment_content);
        JSONObject postdata = new JSONObject(params); // Data as JSON object to be insert into the database
        RequestQueue queue = Volley.newRequestQueue(this);
        // Rest api link
        String url = CommentVolleyHelper.url;
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
                return CommentVolleyHelper.getHeader();
            }
        };
        // add JsonObjectRequest to the RequestQueue
        queue.add(jsonObjectRequest);
    }
}