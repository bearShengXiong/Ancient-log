package com.example.bearbear.diary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    static ArrayList<String> diary = new ArrayList<>();
    static ArrayAdapter arrayAdapter;
    static Set<String> set;
    TextView mTextViewDate, mTextViewCompose,mTextViewSave,mTextViewLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get Current Date
        Calendar c = Calendar.getInstance();
        System.out.println("Current time => "+c.getTime());
        SimpleDateFormat format = new SimpleDateFormat("d");
        String date = format.format(new Date());

        if(date.endsWith("1") && !date.endsWith("11"))
            format = new SimpleDateFormat("yyyy.MMM.d'st'");
        else if(date.endsWith("2") && !date.endsWith("12"))
            format = new SimpleDateFormat("yyyy.MMM.d'nd'");
        else if(date.endsWith("3") && !date.endsWith("13"))
            format = new SimpleDateFormat("yyyy.MMM.d'rd'");
        else
            format = new SimpleDateFormat("yyyy.MMM.d'th'");

        String yourDate = format.format(new Date());



        //Put Current Date into TextView
        mTextViewDate = (TextView)findViewById(R.id.textViewDate);
        mTextViewDate.setText(yourDate);

        //Change font for text
        Typeface typeFace=Typeface.createFromAsset(getAssets(),"fonts/StraightToHellBB.ttf");

        mTextViewCompose = (TextView)findViewById(R.id.textViewCompose);
        mTextViewSave = (TextView)findViewById(R.id.textViewSave);
        mTextViewLoad = (TextView)findViewById(R.id.textViewLoad);

        mTextViewDate.setTypeface(typeFace);
        mTextViewCompose.setTypeface(typeFace);
        mTextViewSave.setTypeface(typeFace);
        mTextViewLoad.setTypeface(typeFace);




        //intial diary
        ListView diaryListView = (ListView)findViewById(R.id.DiaryContainerListView);

        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.bearbear.diary", Context.MODE_PRIVATE);

        set = sharedPreferences.getStringSet("notes", null);

        diary.clear();

        if (set!= null){

            diary.addAll(set);
        } else {
            diary.add("Example");
            set = new HashSet<String>();
            set.addAll(diary);
            sharedPreferences.edit().putStringSet("notes",set).apply();
        }

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1,diary);
        diaryListView.setAdapter(arrayAdapter);
        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent I = new Intent(getApplicationContext(), EditActivity.class);
                I.putExtra("noteId", i);
                startActivity(I);

            }
        });

    }

    public void compose(View view){
        diary.add("");
        SharedPreferences sharedPreferences = this.getSharedPreferences("com.example.bearbear.diary", Context.MODE_PRIVATE);
        if(set == null){
            set = new HashSet<String>();
        }else{
            set.clear();
        }
        set.clear();
        set.addAll(diary);
        arrayAdapter.notifyDataSetChanged();
        sharedPreferences.edit().remove("notes").apply();
        sharedPreferences.edit().putStringSet("notes",set).apply();
        Intent I = new Intent(getApplicationContext(), EditActivity.class);
        I.putExtra("noteId", diary.size() -1 );
        startActivity(I);
    }
}
