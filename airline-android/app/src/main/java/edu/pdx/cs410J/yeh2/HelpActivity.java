package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import com.google.android.material.floatingactionbutton.*;
import android.view.View;
import com.google.android.material.snackbar.*;

import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class HelpActivity extends AppCompatActivity {
    private TextView helpView = null;

    /**
     * This function is called when the user presses the back button on the phone.
     * @param buttonz Item selected!
     * @return true, to exit to the home screen (if back button is pressed!)
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem buttonz)
    {
        if (buttonz.getItemId() == android.R.id.home)
        {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(buttonz);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        helpView = findViewById(R.id.help_textView);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferedInputStream helpbuffer = new BufferedInputStream(getResources().openRawResource(R.raw.readme6));
                InputStreamReader helpinputreader = new InputStreamReader(helpbuffer);
                BufferedReader helpbuffreader = new BufferedReader(helpinputreader);

                String readme = "\n";

                try
                {
                    String bufferline = helpbuffreader.readLine();
                    while (bufferline != null)
                    {
                        readme += "\n" + bufferline;
                        bufferline = helpbuffreader.readLine();
                    }
                }
                catch (IOException e6)
                {
                    Snackbar.make(v, "Uh oh, the help function is borked!" + e6.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                    //e6.printStackTrace();
                }
//                Snackbar.make(v, "Help", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                helpView.setText(readme);
            }
        });
    }
}
