package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.*;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class AftflightSearch extends AppCompatActivity {

    private String searchAirlineName = null;
    private String scroll = null;

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

    private void scrollViewUpdate(String scroll)
    {
        LinearLayout searchResultMainLinearLayout = findViewById(R.id.search_aftflight_flightLinearListView);
        searchResultMainLinearLayout.removeAllViews();
        searchResultMainLinearLayout.invalidate();
        //helpView.invalidate();

        TextView searchResultTextView = new TextView(this);
        //searchResultTextView.invalidate();
        searchResultTextView.setText(scroll);
        searchResultTextView.setTextSize(20);
        searchResultTextView.setPadding(0, 0, 0, 20);
        searchResultMainLinearLayout.addView(searchResultTextView);
    }

    @Override
    protected void onActivityResult(int reqCode, int resCode, Intent parchment)
    {
        super.onActivityResult(reqCode, resCode, parchment);

        if (resCode == RESULT_OK && reqCode == 1)
        {
            String searchName = parchment.getStringExtra("airlineSearchToDisplayName");
            //searchAirlineNameText.setText(searchName);
            scroll = parchment.getStringExtra("displayResult");

            scrollViewUpdate(scroll);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_aftflight);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        EditText searchAirlineNameText = findViewById(R.id.search_aftflight_airlineNameEdit);
        EditText srcSearchText = findViewById(R.id.search_aftflight_srcEdit);
        EditText destSearchText = findViewById(R.id.search_aftflight_destEdit);
        Button searchButton = findViewById(R.id.search_aftflight_searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcSearch = srcSearchText.getText().toString();
                String destSearch = destSearchText.getText().toString();
                String searchName = searchAirlineNameText.getText().toString();
                Boolean specificSearch = false;

                if (searchName.isEmpty())
                {
                    searchAirlineNameText.setError("Please enter an airline name!");
                    Snackbar.make(v, "Please enter an airline name!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (srcSearch.isEmpty() && !destSearch.isEmpty())
                {
                    srcSearchText.setError("Please enter a source airport! (to use src-to-destination search)");
                    Snackbar.make(v, "Please enter a source airport!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                if (!srcSearch.isEmpty() && destSearch.isEmpty())
                {
                    destSearchText.setError("Please enter a destination airport (to use src-to-destination search)!");
                    Snackbar.make(v, "Please enter a destination airport!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                
                if (!srcSearch.isEmpty() && !destSearch.isEmpty())
                {
                    if (srcSearch.length() != 3)
                    {
                        if (srcSearch.length() < 3)
                        {
                            srcSearchText.setError("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + srcSearch);
                            Snackbar.make(v, "Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + srcSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[2]);
                        }
                        else
                        {
                            srcSearchText.setError("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + srcSearch);
                            Snackbar.make(v, "Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + srcSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[2]);
                        }
                        // Graceful Exit: If the source airport code is not 3-digits.
                        return;
                    }
                    if (destSearch.length() != 3)
                    {
                        if (destSearch.length() < 3)
                        {
                            destSearchText.setError("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + destSearch);
                            Snackbar.make(v, "Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + destSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[6]);
                        }
                        else
                        {
                            destSearchText.setError("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + destSearch);
                            Snackbar.make(v, "Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + destSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[6]);
                        }
                        // Graceful Exit: If the destination airport code is not 3-digits.
                        return;
                    }

                    // Input-Validation #2b & #2c {from Project #4}: Check if both src & dest airport codes include numbers, if so, then error!.
                    char[] srcCodeTest = srcSearch.toCharArray();
                    char[] destCodeTest = destSearch.toCharArray();

                    for (char srcChar : srcCodeTest)
                    {
                        if (Character.isDigit(srcChar))
                        {
                            srcSearchText.setError("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + srcSearch);
                            Snackbar.make(v, "Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + srcSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + landing[2]);
                            // Graceful Exit: If the source airport code has numbers.
                            return;
                        }
                    }

                    for (char destChar : destCodeTest)
                    {
                        if (Character.isDigit(destChar))
                        {
                            destSearchText.setError("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + destSearch);
                            Snackbar.make(v, "Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + destSearch, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[6]);
                            // Graceful Exit: If the destination airport code has numbers.
                            return;
                        }
                    }

                    srcSearch = srcSearch.toUpperCase();
                    destSearch = destSearch.toUpperCase();

                    // Input-Validation #6 {from Project #4}: Check the AirportNames database if the airport codes actually exist!
                    if (AirportNames.getName(srcSearch) == null)
                    {
                        srcSearchText.setError("Uh oh, looks like the source airport code, '" + srcSearch + "', was not found in our airport-names database!");
                        Snackbar.make(v, "Uh oh, looks like the source airport code, '" + srcSearch + "', was not found in our airport-names database!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("Uh oh, looks like the source airport code, '" + landing[2] + "', was not found in our airport-names database!");
                        // Graceful Exit: If the source airport code was not found in AirportNames!
                        return;
                    }
                    if (AirportNames.getName(destSearch) == null)
                    {
                        destSearchText.setError("Uh oh, looks like the destination airport code, '" + destSearch + "', was not found in our airport-names database!");
                        Snackbar.make(v, "Uh oh, looks like the destination airport code, '" + destSearch + "', was not found in our airport-names database!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("Uh oh, looks like the destination airport code, '" + landing[6] + "', was not found in our airport-names database!");
                        // Graceful Exit: If the destination airport code was not found in AirportNames!
                        return;
                    }

                    specificSearch = true;
                }

//                Intent searchNameShare = new Intent(AftflightSearch.this, AftflightDisplay.class);
//                searchNameShare.putExtra("airlineSearchToDisplayName", searchName);
//                startActivity(searchNameShare);

                Airline lufthansa = null;
                Flight runway = null;
                //File path = v.getContext().getFilesDir();
                //File file = new File(path, searchName);

                try
                {
                    String lowerCaseSearchAirlineName = searchName.toLowerCase();
                    searchName = lowerCaseSearchAirlineName.substring(0, 1).toUpperCase() + lowerCaseSearchAirlineName.substring(1);
                }
                catch (Exception e9)
                {
                    searchAirlineNameText.setError("Error when trying to capitalize the airline name (Airline name might be too short?): " + e9.getMessage());
                    Snackbar.make(v, "Error when trying to capitalize the airline name (Airline name might be too short?): " + e9.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

                File file = aftFlightFile(v, searchName, ".txt");
//                TextParser parsley = null;
//
//                try
//                {
//                    parsley = new TextParser(file, true);
//                }
//                catch (IOException e8)
//                {
//                    searchAirlineNameText.setError("Parsing IO Exception: " + e8.getMessage());
//                    Snackbar.make(v, "Parsing IO Exception: " + e8.getMessage(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }
//                catch (IllegalArgumentException e10)
//                {
//                    searchAirlineNameText.setError("Parsing IllegalArgumentException: " + e10.getMessage());
//                    Snackbar.make(v, "Parsing IllegalArgumentException: " + e10.getMessage(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }
//                catch (Exception e11)
//                {
//                    searchAirlineNameText.setError("Parsing Exception: " + e11.getMessage());
//                    Snackbar.make(v, "Parsing Exception: " + e11.getMessage(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
//                    return;
//                }

                Intent searchShare = new Intent(AftflightSearch.this, AftflightDisplay.class);
                
                try {
                    //TextParser parsley = new TextParser(file);
                    //lufthansa = TextParser.parsley(file);
                    lufthansa = TextParser.parsley(file);
                }
                catch (ParserException e5)
                {
                    Snackbar.make(v, "TextParserException: " + e5.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                catch (NullPointerException | IllegalArgumentException e9)
                {
//                    Snackbar.make(v, "Parsley NullPointerException (file did not exist): " + e9.getMessage(), Snackbar.LENGTH_LONG)
//                            .setAction("Action", null).show();
                    Snackbar.make(v, "Airline \"" + searchName + "\" does not exist!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                if (lufthansa == null)
                {
                    Snackbar.make(v, "Airline \"" + searchName + "\" does not exist!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                else
                {
                    if (!specificSearch)
                    {
                        searchShare.putExtra("airlineSearchToDisplayName", searchName);
                        //searchShare.putExtra("foundAirline", (Parcelable) lufthansa);
                        //searchShare.putExtra("v", v);
                        //ArrayList<Flight> cargo = lufthansa.getFlightPlan();
                        //searchShare.putExtra("foundFlights", cargo);
//                        searchShare.putExtra("srcSearchToDisplay", null);
//                        searchShare.putExtra("destSearchToDisplay", null);
                        searchShare.putExtra("specificSearch", false);
                        //searchShare.putExtra("foundFile", file);

                        startActivityForResult(searchShare, 1);
                        Toast toaster = Toast.makeText(getApplicationContext(), "Displaying all flights for \"" + searchName + "\"!", Toast.LENGTH_LONG);
                        toaster.show();
                    }
                    else
                    {
                        searchShare.putExtra("airlineSearchToDisplayName", searchName);
                        //searchShare.putExtra("foundAirline", (Parcelable) lufthansa);
                        //ArrayList<Flight> cargo = lufthansa.getFlightPlan();
                        //searchShare.putExtra("foundFlights", cargo);
                        searchShare.putExtra("srcSearchToDisplay", srcSearch);
                        searchShare.putExtra("destSearchToDisplay", destSearch);
                        searchShare.putExtra("specificSearch", true);
                        //searchShare.putExtra("foundFile", file);

                        startActivityForResult(searchShare, 1);
                        Toast toaster = Toast.makeText(getApplicationContext(), "Displaying specific \"" + searchName + "\" flights, from \"" + srcSearch + "\" to \"" + destSearch + "\" airports!", Toast.LENGTH_LONG);
                        toaster.show();
                    }


                    searchAirlineNameText.getText().clear();
                    srcSearchText.getText().clear();
                    destSearchText.getText().clear();
                }
            }
        });
    }
}
