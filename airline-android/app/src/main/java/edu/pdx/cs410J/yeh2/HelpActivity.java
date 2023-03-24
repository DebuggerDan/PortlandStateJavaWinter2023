package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.*;
import android.view.View;
import com.google.android.material.snackbar.*;

import android.widget.TextView;


public class HelpActivity extends AppCompatActivity {
    private View tempHelp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        tempHelp = findViewById(R.id.help);
        tempHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Help", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
}
