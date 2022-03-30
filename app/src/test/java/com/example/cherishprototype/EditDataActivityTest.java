package com.example.cherishprototype;

import static org.junit.Assert.*;

import com.example.cherishprototype.contacts.EditDataActivity;

public class EditDataActivityTest {
    public void nameCorrect(){
        assertTrue(EditDataActivity.isValidName("Pat"));
    }
    public void numberCorrect(){
        assertTrue(EditDataActivity.isValidName("336"));
    }
}