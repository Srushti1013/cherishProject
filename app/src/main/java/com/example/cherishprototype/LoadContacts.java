package com.example.cherishprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

/*
    TODO
    This class is used to load in contacts but has functions that are also included in the SelectContacts class, therefore it is redundant.
    However the app will crash if this code is not here. So right now it serves as a title screen for the app.
 */

public class LoadContacts extends AppCompatActivity {
    ArrayList <String> arrayList;
    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_contacts);
        arrayList=new ArrayList<>();
        button = findViewById(R.id.button);
        button.setOnClickListener((new View.OnClickListener() {
            @Override public void onClick(View view) {
                openHome();
            }
        }));

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        else {
            getContact();
        }
    }

    private void getContact(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(name + "\n" + mobile + "\n");

        }
    }
    public void openHome(){
        Intent intent = new Intent(this, SelectContacts.class);
        intent.putExtra("key", arrayList);
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
    }
}