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

import java.io.File;

import edu.pdx.cs410J.ParserException;

public class AftflightSearch extends AppCompatActivity {
    EditText searchAirlineNameText = findViewById(R.id.search_aftflight_airlineNameEdit);
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

    private void scrollViewUpdate(String scroll)
    {
        LinearLayout searchResultMainLinearLayout = findViewById(R.id.search_aftflight_flightLinearListView);
        searchResultMainLinearLayout.removeAllViews();

        TextView searchResultTextView = new TextView(this);
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
            searchAirlineNameText.setText(searchName);
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

        EditText srcSearchText = findViewById(R.id.search_aftflight_srcEdit);
        EditText destSearchText = findViewById(R.id.search_aftflight_destEdit);
        Button searchButton = findViewById(R.id.search_aftflight_searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String srcSearch = srcSearchText.getText().toString();
                String destSearch = destSearchText.getText().toString();
                String searchName = searchAirlineNameText.getText().toString();

                if (searchName.isEmpty())
                {
                    Snackbar.make(v, "Please enter an airline name!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }

//                Intent searchNameShare = new Intent(AftflightSearch.this, AftflightDisplay.class);
//                searchNameShare.putExtra("airlineSearchToDisplayName", searchName);
//                startActivity(searchNameShare);

                Airline lufthansa = null;
                Flight runway = null;
                File path = v.getContext().getFilesDir();
                File file = new File(path, searchName);
                TextParser parsley = new TextParser(file);
                Intent searchShare = new Intent(AftflightSearch.this, AftflightDisplay.class);

                try
                {
                    lufthansa = parsley.parse();
                }
                catch (ParserException e5)
                {
                    Snackbar.make(v, "ParserException: " + e5.getMessage(), Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                if (lufthansa == null)
                {
                    Snackbar.make(v, "Airline \"" + searchName + "\" does not exist!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
                else
                {
                    if (destSearch.isEmpty() || srcSearch.isEmpty())
                    {
                        searchShare.putExtra("airlineSearchToDisplayName", searchName);
                        searchShare.putExtra("foundAirline", lufthansa);
                        searchShare.putExtra("specificSearch", false);

                        startActivityForResult(searchShare, 1);
                    }
                    else
                    {
                        searchShare.putExtra("airlineSearchToDisplayName", searchName);
                        searchShare.putExtra("foundAirline", lufthansa);
                        searchShare.putExtra("srcSearchToDisplay", srcSearch);
                        searchShare.putExtra("destSearchToDisplay", destSearch);
                        searchShare.putExtra("specificSearch", true);

                        startActivityForResult(searchShare, 1);
                    }
                }
            }
        });
    }
}
