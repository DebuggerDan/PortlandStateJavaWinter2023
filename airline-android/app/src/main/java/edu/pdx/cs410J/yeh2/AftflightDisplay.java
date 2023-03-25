package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.*;
import com.google.android.material.snackbar.Snackbar;

import java.io.IOException;

public class AftflightDisplay extends AppCompatActivity {

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
        setContentView(R.layout.display_aftflight);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        TextView airlineDisplayNameView = findViewById(R.id.display_aftflight_airlineNameText);
        LinearLayout airlineLinearView = findViewById(R.id.display_aftflight_flightLinearListView);

        //String airlineName = "Airline Name";
        //String airlineName = getIntent().getStringExtra("airlineSearchToDisplayName");
        Bundle searchStuff = getIntent().getExtras();
        String airlineName = searchStuff.getString("airlineSearchToDisplayName");
        boolean specificSearch = searchStuff.getBoolean("specificSearch", false);
        //if (getIntent().getBooleanExtra("airlineSearchToDisplay", false))
        if (specificSearch = true)
        {
            String srcSearch = searchStuff.getString("srcSearch");
            String destSearch = searchStuff.getString("destSearch");
        }

        Airline lufthansa = (Airline) searchStuff.getSerializable("foundAirline");

        airlineDisplayNameView.setText(airlineName);

        if (lufthansa == null)
        {
            Snackbar.make(findViewById(android.R.id.content), "Airline not found", Snackbar.LENGTH_LONG).show();
            return;
        }

        Intent displayResultIntent = new Intent();
        displayResultIntent.putExtra("airlineSearchToDisplayName", airlineName);

        PrettyPrinter xerox = new PrettyPrinter(null, false);
        String displayResult = null;
        try
        {
            xerox.dump(lufthansa);
            displayResult = xerox.getPlottedPrint();
        }
        catch (IOException e7)
        {
            Snackbar.make(findViewById(android.R.id.content), "Display Error: " + e7.getMessage(), Snackbar.LENGTH_LONG).show();
            return;
        }

        displayResultIntent.putExtra("displayResult", displayResult);
        setResult(RESULT_OK, displayResultIntent);
        finish();

//        for (Flight flight : lufthansa.getFlights())
//        {
//            TextView flightNumberView = new TextView(this);
//            flightNumberView.setText("Flight Number: " + flight.getNumber());
//            airlineLinearView.addView(flightNumberView);
//
//            TextView flightSrcView = new TextView(this);
//            flightSrcView.setText("Source: " + flight.getSource());
//            airlineLinearView.addView(flightSrcView);
//
//            TextView flightDepartureView = new TextView(this);
//            flightDepartureView.setText("Departure: " + flight.getDepartureString());
//            airlineLinearView.addView(flightDepartureView);
//
//            TextView flightDestView = new TextView(this);
//            flightDestView.setText("Destination: " + flight.getDestination());
//            airlineLinearView.addView(flightDestView);
//
//            TextView flightArrivalView = new TextView(this);
//            flightArrivalView.setText("Arrival: " + flight.getArrivalString());
//            airlineLinearView.addView(flightArrivalView);
//        }


    }

}
