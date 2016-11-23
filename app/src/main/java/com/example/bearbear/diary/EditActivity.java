package com.example.bearbear.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashSet;

public class EditActivity extends AppCompatActivity implements TextWatcher {

    int noteId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        //Get Current Date


        EditText editText = (EditText)findViewById(R.id.editText);
        Intent i = getIntent();
        noteId = i.getIntExtra("noteId", -1);

        if (noteId != -1){
            editText.setText(MainActivity.diary.get(noteId));
        }
        editText.addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        MainActivity.diary.set(noteId, String.valueOf(charSequence) );
        MainActivity.arrayAdapter.notifyDataSetChanged();

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.bearbear.diary", Context.MODE_PRIVATE);

        if(MainActivity.set == null){
            MainActivity.set = new HashSet<String>();
        }else{
            MainActivity.set.clear();
        }

        MainActivity.set.clear();
        MainActivity.set.addAll(MainActivity.diary);
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",MainActivity.set).apply();
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}
