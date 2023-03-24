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
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;

import edu.pdx.cs410J.ParserException;

public class AftflightCreate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_aftflight);

        EditText airlineNameCreateText = findViewById(R.id.creation_aftflight_airlineNameEdit);
        EditText flightNumberCreateText = findViewById(R.id.creation_aftflight_flightNumberEdit);
        EditText srcCreateText = findViewById(R.id.creation_aftflight_srcEdit);
        EditText departTimeCreateText = findViewById(R.id.creation_aftflight_departEdit);
        EditText destCreateText = findViewById(R.id.creation_aftflight_destEdit);
        EditText arriveTimeCreateText = findViewById(R.id.creation_aftflight_arriveEdit);

        Button createButton = findViewById(R.id.creation_aftflight_createButton);

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String airlineName = airlineNameCreateText.getText().toString();
                String flightNumber = flightNumberCreateText.getText().toString();
                String src = srcCreateText.getText().toString();
                String departTime = departTimeCreateText.getText().toString();
                String dest = destCreateText.getText().toString();
                String arriveTime = arriveTimeCreateText.getText().toString();

                if (airlineName.isEmpty() || flightNumber.isEmpty() || src.isEmpty() || departTime.isEmpty() || dest.isEmpty() || arriveTime.isEmpty()) {
                    Snackbar.make(view, "Please fill out all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    Airline lufthansa = null;
                    Flight runway = null;
                    File androidPath = view.getContext().getFilesDir();
                    File file = new File(androidPath, airlineName);
                    TextParser parsley = new TextParser(file);

                    try {
                        lufthansa = TextParser.parsley(file);
                    } catch (ParserException e1) {
                        //e1.printStackTrace();
                        Snackbar.make(view, e1.getMessage(), Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }

                    if (lufthansa == null) {
                        try
                        {
                            lufthansa = new Airline(airlineName, new Flight(flightNumber, src, departTime, dest, arriveTime));
                        }
                        catch (ParseException e2)
                        {
                            Snackbar.make(view, e2.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        Snackbar.make(view, "Flight created!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else
                    {
                        try
                        {
                            runway = new Flight(flightNumber, src, departTime, dest, arriveTime);
                        }
                        catch (ParseException e3)
                        {
                            Snackbar.make(view, e3.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        lufthansa.addFlight(runway);
                        PrettyPrinter xerox = new PrettyPrinter(file);
                        try
                        {
                            xerox.dump(lufthansa);
                        }
                        catch (IOException e4)
                        {
                            Snackbar.make(view, e4.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }

                        Snackbar.make(view, "Flight created for pre-existing airline: \"" + airlineName, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                }

                Intent aftflightCreation = new Intent(AftflightCreate.this, AftflightDisplay.class);
                aftflightCreation.putExtra("airlineName", airlineName);
                aftflightCreation.putExtra("flightNumber", flightNumber);
                aftflightCreation.putExtra("src", src);
                aftflightCreation.putExtra("departTime", departTime);
                aftflightCreation.putExtra("dest", dest);
                aftflightCreation.putExtra("arriveTime", arriveTime);
                startActivity(aftflightCreation);
            }
        });
    }

}
