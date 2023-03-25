package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.pdx.cs410J.ParserException;

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

    /**
     * This function creates files properly for the Android system!
     * @param view The Android view!
     * @param airlineName The name of the airline!
     * @param extension The extension of the file!
     * @return The file that was freshly created!
     */
    private File aftFlightFile(View view, String airlineName, String extension)
    {
        if (extension == null || extension.isEmpty())
        {
            File androidFile = view.getContext().getFilesDir();
            return new File(androidFile, airlineName);
        }
        else
        {
            File androidFile = view.getContext().getFilesDir();
            return new File(androidFile, airlineName + extension);
        }
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
        View view = findViewById(R.id.display_aftflight_mainScrollView);
        File file = aftFlightFile(view, airlineName, ".txt");
        boolean searchMode = getIntent().getBooleanExtra("specificSearch", false);
        //if (getIntent().getBooleanExtra("airlineSearchToDisplay", false))
        String srcSearch = null;
        String destSearch = null;
        if (searchMode)
        {
            srcSearch = getIntent().getStringExtra("srcSearchToDisplay");
            destSearch = getIntent().getStringExtra("destSearchToDisplay");
        }

        //Airline lufthansa = (Airline) searchStuff.getSerializable("foundAirline");
        //Airline lufthansa = getIntent().getParcelableExtra("foundAirline", Airline.class);
        //ArrayList<Flight> baggageClaim = (ArrayList<Flight>) getIntent().getSerializableExtra("foundFlights");
        //File file = getIntent().getParcelableExtra("file");
        Airline lufthansa = null;//new Airline(airlineName);
        try
        {
            lufthansa = TextParser.parsley(file);
        }
        catch (ParserException e)
        {
            Snackbar.make(findViewById(android.R.id.content), "[AftFlight Display] Airline not found <Err. #1>.", Snackbar.LENGTH_LONG).show();
            return;
        }
        catch (Exception e)
        {
            Snackbar.make(findViewById(android.R.id.content), "[AftFlight Display] Airline not found <Err. #1.>", Snackbar.LENGTH_LONG).show();
            return;
        }
        //lufthansa.setFlightsList(baggageClaim);
//        String srcSearch = getIntent().getStringExtra("srcSearch");
//        String destSearch = getIntent().getStringExtra("destSearch");
        //lufthansa.setFlightsList(baggageClaim);

        airlineDisplayNameView.setText(airlineName);

        if (lufthansa == null)
        {
            Snackbar.make(findViewById(android.R.id.content), "[AftFlight Display] Airline initially found, but was unable to be loaded!", Snackbar.LENGTH_LONG).show();
            return;
        }

        Intent displayResultIntent = new Intent();
        displayResultIntent.putExtra("airlineSearchToDisplayName", airlineName);
        PrettyPrinter xerox = null;

        if (searchMode)// && srcSearch != null && destSearch != null)
        {
            // DEBUG
            Log.d("AftFlightDisplay", "Search Mode: " + searchMode + " | " + srcSearch + " | " + destSearch);
            // DEBUG
            xerox = new PrettyPrinter(null, false, srcSearch, destSearch);
        }
        else
        {
            // DEBUG
            Log.d("AftFlightDisplay", "Search Mode: " + searchMode);
            // DEBUG
            //PrettyPrinter
            xerox = new PrettyPrinter(null, false);
        }
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
