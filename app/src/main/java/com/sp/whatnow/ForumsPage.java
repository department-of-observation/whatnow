package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.os.Bundle;

public class ForumsPage extends AppCompatActivity {
    private RecyclerView list;
    private Cursor model = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forums_page);
        list = findViewById(R.id.ForumList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getParent()); // dont know what this does
    }


}