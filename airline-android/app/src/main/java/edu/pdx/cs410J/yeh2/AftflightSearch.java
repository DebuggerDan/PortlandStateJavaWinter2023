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

import edu.pdx.cs410J.ParserException;

public class AftflightSearch extends AppCompatActivity {
    EditText searchAirlineNameText = findViewById(R.id.search_aftflight_airlineNameEdit);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_aftflight);
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

                        startActivity(searchShare);
                    }
                    else
                    {
                        searchShare.putExtra("airlineSearchToDisplayName", searchName);
                        searchShare.putExtra("foundAirline", lufthansa);
                        searchShare.putExtra("srcSearchToDisplay", srcSearch);
                        searchShare.putExtra("destSearchToDisplay", destSearch);
                        searchShare.putExtra("specificSearch", true);

                        startActivity(searchShare);
                    }
                }
            }
        });
    }
}
