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
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class SelectContacts extends AppCompatActivity {
    Button button;
    ArrayList<String> arrayList;
    ArrayList<String> savedContacts;
    CheckBox ch1, ch2, ch3, ch4;

    /*  Creates 4 checkboxes and 1 button to move on to the SavedContacts activity.
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        button = findViewById(R.id.button);
        ch1=findViewById(R.id.checkBox1);
        ch2=findViewById(R.id.checkBox2);
        ch3=findViewById(R.id.checkBox3);
        ch4=findViewById(R.id.checkBox4);


        button.setOnClickListener((new View.OnClickListener() {
            @Override public void onClick(View view) {
                openSavedContacts();
            }
        }));

        /*
            Arraylist has the contacts, SavedContacts is the arraylist that is passed to the next activity with the contacts
            that want to be saved.
        */
        arrayList = new ArrayList<>();
        savedContacts = new ArrayList<>();

        //  Checks android build version and calls get contact function.
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, 1);
        }
        else {
            getContact();
        }
        ch1.setText(arrayList.get(0));
        ch2.setText(arrayList.get(1));
        ch3.setText(arrayList.get(2));
        ch4.setText(arrayList.get(3));
    }

    /*
        A check function to check whether a checkbox has been checked or not
    */
    public void Check(View v)
    {
        String msg="";

        // Concatenation of the checked options in if

        // isChecked() is used to check whether
        // the CheckBox is in true state or not.

        if(ch1.isChecked()){
            msg = msg + " Contact 1 ";
            String s1 = arrayList.get(0);
            savedContacts.add(s1);
        }
        if(ch2.isChecked()){
            msg = msg + " Contact 2 ";
            String s2 = arrayList.get(1);
            savedContacts.add(s2);
        }
        if(ch3.isChecked()){
            msg = msg + " Contact 3 ";
            String s3 = arrayList.get(2);
            savedContacts.add(s3);
        }
        if(ch4.isChecked()){
            msg = msg + " Contact 4 ";
            String s4 = arrayList.get(3);
            savedContacts.add(s4);
        }


        // Toast is created to display the
        // message using show() method.
        Toast.makeText(this, msg + "are selected",
                Toast.LENGTH_LONG).show();
    }

    /*
        Gets the contacts from contact app and place them into an arraylist
     */
    private void getContact(){
        Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null,null,null,null);
        while(cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String mobile = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

            arrayList.add(name + "\n" + mobile + "\n");

        }
    }

    /*
        Puts the savedContacts array into an intent to be passed to the next activity which is the SavedContacts.
     */
    public void openSavedContacts(){
        Intent intent = new Intent(this, SavedContacts.class);
        intent.putExtra("key", savedContacts);
        startActivity(intent);
    }

    /*
        Permission to get the contacts.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                getContact();
            }
        }
    }
}