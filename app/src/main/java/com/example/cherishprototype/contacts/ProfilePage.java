package com.example.cherishprototype.contacts;

import androidx.appcompat.app.AppCompatActivity;
import com.example.cherishprototype.R;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {
    TextView textView;
    Button button;
    String name;
    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        textView = (TextView) findViewById(R.id.tv_name);
        button = (Button) findViewById(R.id.saveButton);
        name =(String) getIntent().getSerializableExtra("name");
        myDB = new DatabaseHelper(this);
        Cursor nameCursor = myDB.getUserName();

        while(nameCursor.moveToNext()){
            name = nameCursor.getString(1);
            textView.setText(name);
        }



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this,SavedContacts.class);
                startActivity(intent);
            }
        });
        //textView.setText(name);
    }
}