package com.example.cherishprototype.contacts;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cherishprototype.R;

/**
 * Activity that lets the user edit contact information of the contacts they have saved in the app.
 */

public class EditDataActivity extends AppCompatActivity{

    private static final String TAG = "EditDataActivity";
    private Button btnSave,btnDelete,btnNotif ;
    private EditText editName, editPhone;
    DatabaseHelper mDatabaseHelper;
    private String selectedName;
    private int selectedID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        btnSave = (Button) findViewById(R.id.btnSave);
        btnDelete = (Button) findViewById(R.id.btnDelete);
        btnNotif = (Button) findViewById(R.id.btnNotif);
        editName = (EditText) findViewById(R.id.editName);
        editPhone = (EditText) findViewById(R.id.editPhone);
        mDatabaseHelper = new DatabaseHelper(this);

        //get the intent extra from the SavedContactsActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1); //NOTE: -1 is just the default value

        //now get the name we passed as an extra
        selectedName = receivedIntent.getStringExtra("name");
        String[] split = selectedName.split(": ");

        //set the text to show the current selected name
        editName.setText(split[0]);
        editPhone.setText(split[1]);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editName.getText().toString();
                String number = editPhone.getText().toString();
                String item = name + ": " + number;
                if(isValidName(name)==false){
                    toastMessage("You must enter a valid name");
                }
                else if(isValidNumber(number)){
                    toastMessage("You must enter a valid number");
                }
                else{
                    mDatabaseHelper.updateName(item,selectedID,selectedName);
                    Intent backIntent = new Intent(EditDataActivity.this, SavedContacts.class);
                    startActivity(backIntent);
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseHelper.deleteName(selectedID,selectedName);
                editName.setText("");
                editPhone.setText("");
                toastMessage("Removed from database");
                Intent backIntent = new Intent(EditDataActivity.this, SavedContacts.class);
                startActivity(backIntent);
            }
        });

        btnNotif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent notifIntent = new Intent(EditDataActivity.this, NotificationSet.class);
                notifIntent.putExtra("contact", selectedName);
                startActivity(notifIntent);

            }
        });

    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }

    public static boolean isValidName(String name){
        if(name.equals("")){
            return false;
        }
        else if(name.matches(".*\\d.*")){
            return false;
        }
        else{
            return true;
        }
    }

    public static boolean isValidNumber(String number){
        int flag=0;
        for (int i = 0; i < number.length(); i++) {
            // checks whether the character is not a letter
            // if it is not a letter ,it will return false
            if ((Character.isLetter(number.charAt(i)) == true)) {
                flag = 1;
            }
        }

        if(number.equals("")){
            return false;
        }
        else if(flag == 0){
            return false;
        }
        else{
            return true;
        }
    }

}