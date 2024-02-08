package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ViewForum extends AppCompatActivity {
    private String dataforum_id ="";
    private String dataforumtitle ="";
    private String dataforumuser ="";
    private String dataforumcontent ="";
    private String dataforumdate ="";

    private TextView forumtitle;
    private TextView forumdate;
    private TextView forumuser;
    private TextView forumtext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_forum);

        TextView forumtitle = findViewById(R.id.forum_title);
        TextView forumdate = findViewById(R.id.forum_date);
        TextView forumuser = findViewById(R.id.forum_user);
        TextView forumtext = findViewById(R.id.forum_text);

        Intent intent = getIntent();
        if (intent != null) {
            dataforum_id = intent.getStringExtra("forum_id");
            dataforumtitle = intent.getStringExtra("forumtitle");
            dataforumuser = intent.getStringExtra("forumuser");
            dataforumcontent = intent.getStringExtra("forumcontent");
            dataforumdate = intent.getStringExtra("forumdate");
            // Now, 'receivedData' contains the string sent from the previous activity
        }
        forumtitle.setText(dataforumtitle);
        forumdate.setText(dataforumdate);
        forumuser.setText(dataforumuser);
        forumtext.setText(dataforumcontent);
    }
}