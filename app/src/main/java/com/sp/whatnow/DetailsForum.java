package com.sp.whatnow;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class DetailsForum extends AppCompatActivity {

    private EditText detailsTitle;
    private EditText detailsContent;
    private Button save_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_forum);
        detailsTitle = findViewById(R.id.details_title);
        detailsContent = findViewById(R.id.details_text);
        save_button = findViewById(R.id.details_save);
    }
}