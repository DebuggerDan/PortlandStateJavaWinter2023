package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aftflightCreateButton = findViewById(R.id.main_createButton);
        Button aftflightSearchButton = findViewById(R.id.main_searchButton);
        //Button aftflightDisplayButton = findViewById(R.id.main_displayButton);

        aftflightCreateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aftflightCreation = new Intent(MainActivity.this, AftflightCreate.class);
                startActivity(aftflightCreation);
            }
        });

//        aftflightDisplayButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent aftflightDisplay = new Intent(MainActivity.this, AftflightDisplay.class);
//                startActivity(aftflightDisplay);
//            }
//        });

        aftflightSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent aftflightSearch = new Intent(MainActivity.this, AftflightSearch.class);
                startActivity(aftflightSearch);
            }
        });


    }

}