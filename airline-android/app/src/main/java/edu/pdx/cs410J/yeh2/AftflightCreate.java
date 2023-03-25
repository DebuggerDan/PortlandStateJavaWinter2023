package edu.pdx.cs410J.yeh2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.content.Intent;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.*;
import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import edu.pdx.cs410J.AirportNames;
import edu.pdx.cs410J.ParserException;

public class AftflightCreate extends AppCompatActivity {
    String Timestamp_Format = "MM/dd/yyyy h:mm a";
    DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);

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
//            try
//            {
//                androidFile.createNewFile();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
            return new File(androidFile, airlineName);
        }
        else
        {
            File androidFile = view.getContext().getFilesDir();
//            try
//            {
//                androidFile.createNewFile();
//            }
//            catch (IOException e)
//            {
//                e.printStackTrace();
//            }
            return new File(androidFile, airlineName + extension);
        }
    }

    /**
     * <p>
     * Creates a valid formatted for a valid time-and-date format as specified, e.g. mm/dd/yyyy hh:mm
     * Based on: 1. {@code public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";}
     * and 2. {@code public static final DateFormat testDate = new SimpleDateFormat(Timestamp_Format, Locale.US);}
     * </p>
     *
     * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     *
     * @param timedate The Android timestamp to test!

     * @return timestamp The <code>Date</code> formatted bi-string timestamp combo!
     * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
     *
     */
    public static Date timeStamper(View view, String timedate) throws IllegalArgumentException
    {
        String Timestamp_Format = "MM/dd/yyyy h:mm a";
        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
        StringBuilder postage = new StringBuilder();
        postage.append(timedate);
        //postage.append(" ");
        //postage.append(time);
        //postage.append(" ");
        //postage.append(ampm.toUpperCase());
        //postage.append(date + " " + time);

        String stamp = postage.toString();
        stamp.toUpperCase();
        Date timestamp = null;

        try
        {
            timestamp = TStamp.parse(stamp);
        }
        catch (ParseException m00)
        {
            Snackbar.make(view, "Please enter a valid date and time!", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
            //throw new IllegalArgumentException("Hmm, looks like a invalid time-and-date stamp attempt: ", m00);
        }

        return timestamp;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creation_aftflight);

        if (getSupportActionBar() != null)
        {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

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
                    if (airlineName.isEmpty())
                    {
                        airlineNameCreateText.setError("Please enter an airline name!");
                    }
                    if (flightNumber.isEmpty())
                    {
                        flightNumberCreateText.setError("Please enter a flight number!");
                    }
                    if (src.isEmpty())
                    {
                        srcCreateText.setError("Please enter a source airport!");
                    }
                    if (departTime.isEmpty())
                    {
                        departTimeCreateText.setError("Please enter a departure time!");
                    }
                    if (dest.isEmpty())
                    {
                        destCreateText.setError("Please enter a destination airport!");
                    }
                    if (arriveTime.isEmpty())
                    {
                        arriveTimeCreateText.setError("Please enter an arrival time!");
                    }
                    Snackbar.make(view, "Please fill out all fields!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    return;
                }
                else
                {
                    Airline lufthansa = null;
                    Flight runway = null;
                    /*
                     * Input-Validation #8 {from Project #4}: Verify that flight number is indeed, an actual number.
                     */
                    try
                    {
                        int testNum = Integer.parseInt(flightNumber);
                    }
                    catch (Exception m7)
                    {
                        flightNumberCreateText.setError("The flight number seems to be, well, not a integer!");
                        //usage("The flight number seems to be, well, not a integer!");
                        Snackbar.make(view, "The flight number seems to be, well, not a integer!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        return;
                    }
                    String gate = null;
                    String taxi = null;

                    /*
                     * Input-Validation #7 {from Project #4}: Check the Departure Date & Time formatting.
                     * Attempts to create the formatted time-and-date for departure time.
                     */
                    try
                    {
                        gate = TStamp.format(timeStamper(view, departTime));
                        //runway = new Flight(landing);
                    }
                    catch (IllegalArgumentException m4a)
                    {
                        departTimeCreateText.setError("Please enter a valid departure date and time!");
                        Snackbar.make(view, "Please enter a valid departure date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //usage("[Main Date Initialization #1a] Error when attempting to format the departure time & date arguments, " + landing[3] + ", " + landing[4] + ", and " + landing[5] + ".");
                        // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
                        return;
                    }
                    catch (NullPointerException m4b)
                    {
                        departTimeCreateText.setError("Please enter a valid departure date and time!");
                        Snackbar.make(view, "Please enter a valid departure date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
                        return;
                    }
                    catch (Exception m4c)
                    {
                        departTimeCreateText.setError("Please enter a valid departure date and time!");
                        Snackbar.make(view, "Please enter a valid departure date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
                        return;
                    }

                    /*
                     * Input-Validation #2 {from Project #4}: Check the Arrival Date & Time formatting.
                     * Attempts to create the formatted time-and-date for arrival time.
                     */
                    try
                    {
                        taxi = TStamp.format(timeStamper(view, arriveTime));
                    }
                    catch (IllegalArgumentException m5a)
                    {
                        arriveTimeCreateText.setError("Please enter a valid arrival date and time!");
                        Snackbar.make(view, "Please enter a valid arrival date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
                        return;
                    }
                    catch (NullPointerException m5b)
                    {
                        arriveTimeCreateText.setError("Please enter a valid arrival date and time!");
                        Snackbar.make(view, "Please enter a valid arrival date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
                        return;
                    }
                    catch (Exception m5c)
                    {
                        arriveTimeCreateText.setError("Please enter a valid arrival date and time!");
                        Snackbar.make(view, "Please enter a valid arrival date and time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
                        return;
                    }

                    /*
                     * Input-Validation #3 {from Project #4}: Checking both src & dest airport codes is not 3-digits in characters.
                     * landing[2] should equal src & landing[6] should equal dest.
                     */

                    if (src.length() != 3)
                    {
                        if (src.length() < 3)
                        {
                            srcCreateText.setError("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + src);
                            Snackbar.make(view, "Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + src, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[2]);
                        }
                        else
                        {
                            srcCreateText.setError("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + src);
                            Snackbar.make(view, "Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + src, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[2]);
                        }
                        // Graceful Exit: If the source airport code is not 3-digits.
                        return;
                    }
                    if (dest.length() != 3)
                    {
                        if (dest.length() < 3)
                        {
                            destCreateText.setError("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + dest);
                            Snackbar.make(view, "Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + dest, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[6]);
                        }
                        else
                        {
                            destCreateText.setError("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + dest);
                            Snackbar.make(view, "Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + dest, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[6]);
                        }
                        // Graceful Exit: If the destination airport code is not 3-digits.
                        return;
                    }

                    // Input-Validation #2b & #2c {from Project #4}: Check if both src & dest airport codes include numbers, if so, then error!.
                    char[] srcCodeTest = src.toCharArray();
                    char[] destCodeTest = dest.toCharArray();

                    for (char srcChar : srcCodeTest)
                    {
                        if (Character.isDigit(srcChar))
                        {
                            srcCreateText.setError("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + src);
                            Snackbar.make(view, "Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + src, Snackbar.LENGTH_LONG)
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
                            destCreateText.setError("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + dest);
                            Snackbar.make(view, "Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + dest, Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            // usage("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[6]);
                            // Graceful Exit: If the destination airport code has numbers.
                            return;
                        }
                    }

                    src.toUpperCase();
                    dest.toUpperCase();

                    // Input-Validation #6 {from Project #4}: Check the AirportNames database if the airport codes actually exist!
                    if (AirportNames.getName(src) == null)
                    {
                        srcCreateText.setError("Uh oh, looks like the source airport code, '" + src + "', was not found in our airport-names database!");
                        Snackbar.make(view, "Uh oh, looks like the source airport code, '" + src + "', was not found in our airport-names database!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("Uh oh, looks like the source airport code, '" + landing[2] + "', was not found in our airport-names database!");
                        // Graceful Exit: If the source airport code was not found in AirportNames!
                        return;
                    }
                    if (AirportNames.getName(dest) == null)
                    {
                        destCreateText.setError("Uh oh, looks like the destination airport code, '" + dest + "', was not found in our airport-names database!");
                        Snackbar.make(view, "Uh oh, looks like the destination airport code, '" + dest + "', was not found in our airport-names database!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // usage("Uh oh, looks like the destination airport code, '" + landing[6] + "', was not found in our airport-names database!");
                        // Graceful Exit: If the destination airport code was not found in AirportNames!
                        return;
                    }

                    // Input-Validation #9 {from Project #4}: Try initializing the new flight as-is with the given command-line arguments.
                    try
                    {
                        runway = new Flight(flightNumber, src, gate, dest, taxi);
                    }
                    catch (ParseException m8)
                    {
                        Snackbar.make(view, "Error when creating new temporary flight, specifically, when parsing invalid timestamps!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        //error("Error when creating new temporary flight, specifically, when parsing invalid timestamps!");
                        return;
                    }
                    // Input-Validation #5: If flight arrival is before the departure time:
                    if (runway.getFlightTime() < 0)
                    {
                        departTimeCreateText.setError("Uh oh, looks like the flight arrival time is before the departure time!");
                        arriveTimeCreateText.setError("Uh oh, looks like the flight arrival time is before the departure time!");
                        Snackbar.make(view, "Uh oh, looks like the flight arrival time is before the departure time!", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        // Graceful Exit: If negative flightTime (in minutes)
                        return;
                    }

                    //File androidPath = view.getContext().getFilesDir();
                    //File file = new File(androidPath, airlineName);
                    File file = aftFlightFile(view, airlineName, ".txt");
//                    TextParser parsley = null;
//                    try
//                    {
//                        parsley = new TextParser(file, false);
//                    }
//                    catch (IOException e8)
//                    {
//                        Snackbar.make(view, "Parsing IO Exception: " + e8.getMessage(), Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
//                    }
                    boolean airlineExistence = true;
                    try {
                        //TextParser parsley = new TextParser(file);
                        lufthansa = TextParser.parsley(file);
                        //lufthansa = XmlParser.parsley(file);
                    } catch (ParserException | IllegalArgumentException e1) {
                        airlineExistence = false;
                        //e1.printStackTrace();
//                        Snackbar.make(view, "Looks like the file was invalid: ", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        //return;
                    }

                    //if (lufthansa == null) {
                    if (!airlineExistence || lufthansa == null) {
                        try
                        {
                            //runway = new Flight(flightNumber, src, departTime, dest, arriveTime);
                            lufthansa = new Airline(airlineName, runway);
                        }
                        catch (IllegalArgumentException e2)
                        {
                            Snackbar.make(view, e2.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }

                        //TextDumper xmldump = null;
                        TextDumper td = null;
                        try
                        {
                            //xmldump = new TextDumper(file);
                            //hp.dump(lufthansa);

//                            xmldump = new XmlDumper(file);
//                            xmldump.dump(lufthansa);
                            td = new TextDumper(file);
                            td.dump(lufthansa);
                        }
                        catch (IOException e9)
                        {
                            Snackbar.make(view, "[Dumper Debug #1] " + e9.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                        }

                        Toast benedict = Toast.makeText(getApplicationContext(), "Flight & new airline created for \"" + airlineName + "\"!", Toast.LENGTH_LONG);
                        benedict.show();

//                        Snackbar.make(view, "Flight created!", Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                    }
                    else
                    {
                        try
                        {
                            runway = new Flight(flightNumber, src, departTime, dest, arriveTime);
                        }
                        catch (ParseException e3)
                        {
                            Snackbar.make(view, "[Flight Runway Mishap!] " + e3.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }
                        lufthansa.addFlight(runway);
                        //PrettyPrinter xerox = new PrettyPrinter(file);
                        //XmlDumper xmldump = null;
                        TextDumper td = null;
                        try
                        {
                            //xmldump = new XmlDumper(file);
                            //xmldump.dump(lufthansa);
                            td = new TextDumper(file);
                            td.dump(lufthansa);
                            //xerox.dump(lufthansa);
                        }
                        catch (IOException e4)
                        {
                            Snackbar.make(view, "[Dumper Debug #2] " + e4.getMessage(), Snackbar.LENGTH_LONG)
                                    .setAction("Action", null).show();
                            return;
                        }

//                        Snackbar.make(view, "Flight created for pre-existing airline: \"" + airlineName, Snackbar.LENGTH_LONG)
//                                .setAction("Action", null).show();
                        Toast french = Toast.makeText(getApplicationContext(), "Flight created for pre-existing airline: \"" + airlineName + "\"!", Toast.LENGTH_LONG);
                        french.show();
                    }

                }

//                Intent aftflightCreation = new Intent(AftflightCreate.this, AftflightDisplay.class);
//                aftflightCreation.putExtra("airlineName", airlineName);
//                aftflightCreation.putExtra("flightNumber", flightNumber);
//                aftflightCreation.putExtra("src", src);
//                aftflightCreation.putExtra("departTime", departTime);
//                aftflightCreation.putExtra("dest", dest);
//                aftflightCreation.putExtra("arriveTime", arriveTime);
//                startActivity(aftflightCreation);

                airlineNameCreateText.getText().clear();
                flightNumberCreateText.getText().clear();
                srcCreateText.getText().clear();
                departTimeCreateText.getText().clear();
                destCreateText.getText().clear();
                arriveTimeCreateText.getText().clear();
            }
        });
    }

}
