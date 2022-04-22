package com.example.cherishprototype.contacts;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.example.cherishprototype.R;
import com.example.cherishprototype.login.Login;
import com.example.cherishprototype.login.RegisterScreen;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Profile page activity that allows the user to change email, password, or their phone number.
 */

public class ProfilePage extends AppCompatActivity {
    TextView textView;
    Button button;
    String name, newName, email, oldPassword, newPassword, phoneNumber, newPhoneNumber,correctPass;
    DatabaseHelper myDB;
    EditText emailText, oldPassEdit, newPassEdit, phoneNumberEdit, newNameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);
        textView = (TextView) findViewById(R.id.tv_name);
        button = (Button) findViewById(R.id.saveButton);
        emailText = (EditText) findViewById(R.id.editTextEmail);
        phoneNumberEdit =(EditText) findViewById(R.id.editTextPhone);
        oldPassEdit = (EditText) findViewById(R.id.editTextOldPass);
        newPassEdit = (EditText) findViewById(R.id.editTextNewPass);
        newNameEdit = (EditText) findViewById(R.id.editTextNewName);

        name =(String) getIntent().getSerializableExtra("name");
        myDB = new DatabaseHelper(this);
        Cursor userDetailsCursor = myDB.getData2();


        //get the names, number, and email from database and set it to the editText.
        while(userDetailsCursor.moveToNext()){
            name = userDetailsCursor.getString(1);
            textView.setText(name);
            email = userDetailsCursor.getString(0);
            emailText.setText(email);
            phoneNumber = userDetailsCursor.getString(3);
            phoneNumberEdit.setText(phoneNumber);
            oldPassword = userDetailsCursor.getString(2);
            correctPass = userDetailsCursor.getString(2);
        }

        //getting all the editable data and saving it to the database.



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProfilePage.this,SavedContacts.class);
                newPhoneNumber = phoneNumberEdit.getText().toString();
                oldPassword = oldPassEdit.getText().toString();
                newPassword = newPassEdit.getText().toString();
                newName = newNameEdit.getText().toString();

                //updates phone if it is changed
                if(!newPhoneNumber.equals(phoneNumber)){
                    myDB.updatePhone(newPhoneNumber,phoneNumber);
                }
                //updates name if it is changed
                if(!newName.isEmpty()){
                    myDB.updateUsername(newName,name);
                }

                //updates password if it is changed
                if (correctPass.equals(oldPassword)) {
                    myDB.updatePassword(newPassword,oldPassword);
                    Toast.makeText(ProfilePage.this,"Changes Saved",Toast.LENGTH_SHORT).show();
                    startActivity(intent);

                }
                else if(oldPassword.isEmpty()){
                    Toast.makeText(ProfilePage.this,"Changes Saved",Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ProfilePage.this);
                    builder.setCancelable(true);
                    builder.setTitle("Wrong password");
                    builder.setMessage("Wrong password");
                    builder.show();
                }
            }
        });

    }
}