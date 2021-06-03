package com.e.learnhindi;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView number,family,phrases,colors;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        number=findViewById(R.id.Numbers);
        family=findViewById(R.id.FamilyMember);
        phrases=findViewById(R.id.phrases);
        colors=findViewById(R.id.Colors);

        number.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                numberList();
            }
        });


        family.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                familyList();
            }
        });

        phrases.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phrasesList();
            }
        });

        colors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                colorsList();
            }
        });


    }

    void numberList(){
        Intent intent=new Intent(MainActivity.this,NumbersActivity.class);
        intent.putExtra("title1","Numbers");
        startActivity(intent);
    }

    void familyList(){
        Intent intent=new Intent(MainActivity.this,FamilyMembersActivity.class);
        intent.putExtra("title2","Family Members");
        startActivity(intent);
    }
    void phrasesList(){
        Intent intent=new Intent(MainActivity.this,PhrasesActivity.class);
        intent.putExtra("title3","Phrases");
        startActivity(intent);
    }

    void colorsList(){
        Intent intent=new Intent(MainActivity.this,ColorsActivity.class);
        intent.putExtra("title4","Colors");
        startActivity(intent);
    }
}
