package com.example.cherishprototype.login;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cherishprototype.R;
import com.example.cherishprototype.contacts.DatabaseHelper;

import java.util.ArrayList;

public class RegisterScreen extends AppCompatActivity {
    EditText name , number , email,pass;
    TextView login;
    DatabaseHelper dbHelper;
    ArrayList arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        name=findViewById(R.id.textName);
        number=findViewById(R.id.textNumber);
        email=findViewById(R.id.textEmail);
        pass=findViewById(R.id.textPass);
        Button signUpAcc = findViewById(R.id.btnSignUpAcc);
        dbHelper = new DatabaseHelper(this);
        arrayList = (ArrayList<String>) getIntent().getSerializableExtra("key");

        signUpAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name1 = name.getText().toString();
                String number1 = number.getText().toString();
                String email1 = email.getText().toString();
                String pass1 = pass.getText().toString();
                boolean b =dbHelper.insertUserData(name1,number1,email1,pass1);
                if (b){
                    Toast.makeText(RegisterScreen.this,"Data inserted",Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(RegisterScreen.this,Login.class);
                    i.putExtra("key2",arrayList);
                    startActivity(i);
                }else {
                    Toast.makeText(RegisterScreen.this,"Failed To insert Data",Toast.LENGTH_SHORT).show();
                }
            }
        });
        login=findViewById(R.id.loginAcc);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RegisterScreen.this,Login.class);
                i.putExtra("key2",arrayList);
                startActivity(i);
            }
        });
    }
}