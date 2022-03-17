package com.example.cherishprototype;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class LoadingScreen extends AppCompatActivity {
    ArrayList<String> arrayList;
    Button button;

    private static int SPLASH_TIME_OUT = 4000;


    /*
        Creates a loading screen.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loading_screen);



        arrayList=new ArrayList<>();

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        else {
            getContact();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(LoadingScreen.this, SelectContacts.class);
                homeIntent.putExtra("key", arrayList);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
    }
}