package com.example.cherishprototype;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class SavedContacts extends AppCompatActivity {
    TextView savedView;
    /*
        Creates a textview that shows the desired saved contacts from the previous activity.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_contacts);
        savedView = findViewById(R.id.savedView);
        Intent intent =getIntent();
        ArrayList<String> myList = (ArrayList<String>) getIntent().getSerializableExtra("key");
        for (int i = 0; i < myList.size(); i++) {
            savedView.setText(myList.toString());
        }
        savedView.setTypeface(null, Typeface.BOLD);



    }
}