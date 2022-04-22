package com.example.cherishprototype.login;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.cherishprototype.R;
import com.example.cherishprototype.contacts.DatabaseHelper;
import com.example.cherishprototype.contacts.SelectContacts;

import java.util.ArrayList;


public class Login extends AppCompatActivity {
    EditText email , password;
    Button btnSubmit;
    TextView createAcc;
    DatabaseHelper dbHelper;
    ArrayList arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Boolean e=false,p=false;
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.text_email);
        password=findViewById(R.id.text_pass);
        btnSubmit = findViewById(R.id.btnSubmit_login);
        dbHelper = new DatabaseHelper(this);
        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("key2");
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailCheck = email.getText().toString();
                String passCheck = password.getText().toString();
                Cursor cursor = dbHelper.getData2();

                if(cursor.getCount() == 0){
                    Toast.makeText(Login.this,"No entries Exists",Toast.LENGTH_LONG).show();
                }
                if (loginCheck(cursor,emailCheck,passCheck)) {
                    Intent intent = new Intent(Login.this,SelectContacts.class);
                    intent.putExtra("key2",arrayList);
                    email.setText("");
                    password.setText("");
                    startActivity(intent);
                }else {
                    Toast.makeText(Login.this,"Wrong email or password",Toast.LENGTH_LONG).show();
                }
                dbHelper.close();
            }
        });
        createAcc=findViewById(R.id.createAcc);
        createAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Login.this,RegisterScreen.class);
                intent.putExtra("key",arrayList);
                startActivity(intent);
            }
        });

    }
    public static boolean loginCheck(Cursor cursor,String emailCheck,String passCheck) {
        while (cursor.moveToNext()){
            if (cursor.getString(0).equals(emailCheck)) {
                if (cursor.getString(2).equals(passCheck)) {
                    return true;
                }
                return false;
            }
        }
        return false;
    }
}