package com.example.cherishprototype.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.cherishprototype.R;

import java.util.ArrayList;

public class SelectContacts extends AppCompatActivity {

    DatabaseHelper myDB;
    Button btnAdd,btnView;
    ArrayList arrayList;
    ArrayAdapter<String> arrayAdapter;
    private static final String TAG = "SelectContacts";
    private ListView lv;
    ArrayList savedList;
    String name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contacts);
        lv = findViewById(R.id.lv);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);

        myDB = new DatabaseHelper(this);
        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("key2");
        name = (String) getIntent().getSerializableExtra("name");
        savedList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(this, R.layout.listviewcustom, arrayList);

        lv.setAdapter(arrayAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if(position == i){
                        savedList.add(arrayList.get(i));
                        lv.getChildAt(i).setBackgroundColor(Color.GRAY);
                        lv.getChildAt(i).setFocusable(true);
                    }
                }
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                for(int i = 0; i< savedList.size(); i++){
                    AddData(savedList.get(i).toString());
                }
                savedList.clear();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectContacts.this, SavedContacts.class);
                //intent.putExtra("key",arrayList);
                intent.putExtra("name",name);
                startActivity(intent);
            }
        });
    }

    public void AddData(String newEntry) {

        boolean insertData = myDB.addData(newEntry);

        if(insertData==true){
            Toast.makeText(this, "Data Successfully Inserted!", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Something went wrong :(.", Toast.LENGTH_LONG).show();
        }
    }
}