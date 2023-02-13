package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;
/*
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.LinkedList;
*/

import java.text.*;
// import java.text.DateFormat;
// import java.text.SimpleDateFormat;

import edu.pdx.cs410J.AirportNames;


public class Project3 {

    /**
     * Project #3 Timestamp Formatting:
     * {@code MM/dd/yyyy HH:mm a}
     * E.g., "02/08/2023 01:20 pm"
     */
    //protected static DateFormat date_formatter = DateFormat.getDateTimeInstance(3, 3, Locale.US);
    protected static final String Timestamp_Format = "MM/dd/yyyy h:mm a";
    //protected static SimpleDateFormat date_format = new SimpleDateFormat(Timestamp_Format, Locale.US);

    /**
     * A function that displays txt files!
     * @param txtfile A text file name!
     * @param Option A number indicating the type of displaying!
     */
    public static void displayer(String txtfile, Integer Option)
    {

        if (Option == 1)
            /* If <code>Option</code> is 1, then display as a README file/parameter (where three lines are cut to only display
             * the command-line interface*/
        {
            try (InputStream displayme = Project3.class.getResourceAsStream(txtfile)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();
                    int idx = 0;
                    while (display_line != null && idx != 3)
                    {
                        display_line = display_buffer.readLine();
                        idx--;
                    }

                    while (display_line != null) {
                        System.out.println(display_line);
                        display_line = display_buffer.readLine();
                    }
                }
            } catch (IOException m1) {
                //System.out.println("Error! README not found!", m1);
            }
        }
        //if (Option == 0)
        else
            /* If Option != (n), where n is an valid, specific <code>Option</code> number corresponding to an If-Statement, then display whole file normally. */
        {
            try (InputStream displayme = Project3.class.getResourceAsStream(txtfile)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();

                    while (display_line != null) {
                        System.out.println(display_line);
                        display_line = display_buffer.readLine();
                    }
                }
            } catch (IOException m2) {
                //System.out.println("Error! README not found!", m1);
            }
        }
    }

    /**
     * <p>
     * A whimsical, simple but a bit silly, 'unwraveler' function for <code>AbstractList</code>s,
     * that "unthneeds" them into simple {@code String[]}, string-arrays.
     * (From Project #2)
     * </p>
     * @param thneed {@code AbstractList<String>} In goes the list!
     * @return tuft Out goes a {@code String[]} array, unraveled from the {@code AbstractList<String>} {@param thneed}!
     */
    public static String[] unthneed(List<String> thneed) // Dr. Seuss reference heh
    {
        String[] tuft = new String[thneed.size()];
        Iterator<String> lorax = thneed.iterator();
//        int idx = 0;
//        for (Object truffula : thneed)
//        {
//            tuft[idx] = truffula;
//            idx++;
//        }
        for (int truffula = 0; truffula != thneed.size(); truffula++)
        {
            if (lorax.hasNext())
            {
                tuft[truffula] = lorax.next();
            }
            else
            {
                System.err.println("An arwy truffula tree tuft! The Lorax says, check the that loop, For-where this came!");
            }
        }

        return tuft;
    }

    /**
     * <p>
     * Validates a given string for a valid time-and-date format as specified, e.g. mm/dd/yyyy hh:mm
     * Based on: 1. {@code public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";}
     * and 2. {@code public static final DateFormat testDate = new SimpleDateFormat(Timestamp_Format, Locale.US);}
     * </p>
     *
     * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     *
     * A void function (but a thrown exception infers 'false' of valid time-and-date format)
     * @param dateAndTime String
     * @throws IllegalArgumentException If there is an invalid formatted time-and-date (bi-)string( combo)!
     *
     */
    @VisibleForTesting
    static Boolean isValidDateAndTime(String dateAndTime) throws IllegalArgumentException
    {
        //String Timestamp_Format = "MM/dd/yyyy HH:mm";
        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
        Date test = null;
        try
        {
            test = TStamp.parse(dateAndTime);
        }
        catch (ParseException m0)
        {
            throw new IllegalArgumentException("Hmmm, looks like the following was not a valid mm/dd/yyyy hh:mm time-and-date: ", m0);
        }
        return true;
    }

    // Using coreAPI, pages 92 ~ 104 on date, calendar, & variable-length args

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
     * @param date The first part of the tri-string combo-to-be!
     * @param time The second part of the tri-string combo-to-be!
     * @param ampm The third part of the tri-string combo-to-be!
     * @return timestamp The <code>Date</code> formatted bi-string timestamp combo!
     * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
     *
     */

    public static Date timeStamper(String date, String time, String ampm) throws IllegalArgumentException
    {
        //String Timestamp_Format = "MM/dd/yyyy HH:mm";
        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
        StringBuilder postage = new StringBuilder();
        postage.append(date);
        postage.append(" ");
        postage.append(time);
        postage.append(" ");
        postage.append(ampm.toUpperCase());
        //postage.append(date + " " + time);

        String stamp = postage.toString();
        Date timestamp = null;

        try
        {
            timestamp = TStamp.parse(stamp);
        }
        catch (ParseException m00)
        {
            throw new IllegalArgumentException("Hmm, looks like a invalid time-and-date stamp attempt: ", m00);
        }

        return timestamp;
    }

    /**
     * The main class for the CS410J Project #3: PrettyPrinter
     * usage: java -jar target/airline-2023.0.0.jar [options] <args>
     *
     * @param args
     *      <p>
     *      <code>args</code> are (in this order):
     *          airline      The name of the airline
     *          flightNumber     The flight number
     *          src      Three-letter code of departure airport
     *          depart      Departure date and time (am/pm)
     *          dest     Three-letter code of arrival airport
     *          arrive      Arrival date and time (am/pm)
     *      options are (options may appear in any order))
     *          -pretty      Pretty print the airline’s flights to
     *                       a text file or standard out (file -)
     *          -textFile      file Where to read/write the airline info
     *          -print      Prints a description of the new flight
     *          -README     Prints a README for this project and exits
     *      </p>
     */

    public static void main(String[] args) {
        //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        Flight runway = null;
        Airline lufthansa = null;
        String file_name = null;

        // This boolean will be used to indicate the creation of a new, but blank Airline (e.g. Blank Airline)
        boolean fresh = false;
        // This boolean will be used to indicate if PrettyPrinter will be printing to a text file or have it print to a file!
        // True = save to file & False = only print to command-line
        boolean prettyoption = true;

        int freshnum = 0;

        int print_option_num = 0;
        int readme_option_num = 0;
        int argnum = 0;

        StringBuilder yarn = null;
        // For temporary string concatenation stuffs

        // String[] filelist = null;
        // Collection<>

        List<String> arglist = new LinkedList<>();
        LinkedList<String> filelist = new LinkedList<>();
        LinkedList<String> prettylist = new LinkedList<>();

        /*
         * Time-and-Date Format stuffs - from coreAPI, pages 92 ~ 104.
         *
         * @see java.text.DateFormat
         * @see java.text.SimpleDateFormat
         */
        //String Timestamp_Format = "MM/dd/yyyy HH:mm a";
        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);

        // Project #3 README3.txt file!
        final String readme_file = "README3.txt";

        // if (args == null) // args will never equal null, but it can have 0 arguments, I think
        if (args.length == 0)
        {
            // lufthansa = new Airline("N/A");
            displayer(readme_file, 1);
            System.out.println("The Project #3 command-line interface has been provided above.");
            /* Assuming that this function, that uses the Resources API
             * is permitted as it is only displaying a static text file,
             * specific for the README/no-args-default-command-line-interface displaying of text.
             */

            // Graceful Exit: No arguments passed to the program, thus, command-line interface is shown.
            return;
        }

        for (int idx = 0; idx != args.length; idx++)
        {
            String current_arg = args[idx];

            if (current_arg.startsWith("-"))
            {
                String action = current_arg.substring(1);

                switch (action.toLowerCase())
                {
                    case "readme":
                        displayer(readme_file, 0);
                        return;

                        //readme_option_num++;
                        //break;

                    case "textfile":
                        file_name = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (file_name.isEmpty())
                        {
                            //file_name = "Empty.txt";
                            System.err.println("Hmm, looks like the -textfile option was used, but there was no textfile specified!");
                            return;
                        }
                        if (file_name.startsWith("-"))
                        {
                            //file_name = "-";
                            System.err.println("Hmm, a invalid file name was detected for the -textFile option (valid names include, e.g. lufthansa.txt)!\nTreating as empty name, creating default Empty Airline!");
                            file_name = "Empty Airline";

                            freshnum++;

                            if (fresh) // If 2nd+ 'Fresh' Airline to be Created
                            {
                                yarn = new StringBuilder();
                                yarn.append("Empty Airline #");
                                yarn.append(freshnum);
                                file_name = yarn.toString();
                            }
                            fresh = true;

                            //return;
                        }
                        filelist.add(file_name);
                        break;

                    case "pretty":
                        String pretty_file = args[(args.length <= idx++) ? (idx-1) : idx];//.toLowerCase();
//                        if (pretty_file.equals("file"))
//                        {
//                            //pretty_file = "Empty Airline";
//                            System.out.println("We will write to the textFile specified!");
//                        }

                        if (pretty_file.isEmpty())
                        {
                            System.err.println("Hey there, looks like there was no filename [or a '-' to specify console pretty-printing only] specified after the -pretty option!");// +
                            // ", but that's okay, we will assume you would simply like a pretty console output (instead of a file!");
                            //prettyoption = false;
                            return;
                        }

                        if (pretty_file.startsWith("-"))
                        {
                            //file_option = "-";
                            if (pretty_file.equals("-"))
                            {
                                System.out.println("Alrighty, the PrettyPrinter will print to the command-line instead of writing to a file!");
                                prettyoption = false;
                            }
                            else
                            {
                                System.err.println("Hmm, an invalid file name was detected for the -pretty option (valid names include, e.g. lufthansa.txt)!");//\nTreating as empty name, creating default Empty Airline!");
                                //pretty_file = "Empty Airline";

//                                freshnum++;
//
//                                if (fresh) // If 2nd+ 'Fresh' Airline to be Created
//                                {
//                                    yarn = new StringBuilder();
//                                    yarn.append("Empty Airline #");
//                                    yarn.append(freshnum);
//                                    pretty_file = yarn.toString();
//                                }
//                                fresh = true;

                                return;
                            }
                        }
                        else
                        {
                            prettylist.add(pretty_file);
                        }
                        break;

//                    case "readme":
//                        //displayer(readme_file, 0);
//                        //return;
//
//                        readme_option_num++;
//                        break;

                    case "print":
//                        if (lufthansa == null)
//                        {
//                            lufthansa = new Airline(args[0]);
//                        }
//                        lufthansa.printAll();
                        print_option_num++;

                        break;

                    default:
                        System.err.println("Uh oh, looks like there was a invalid option used: " + action);
                }
            }
            else
            {
                argnum++;

                arglist.add(current_arg);
            }
        }

//        if (argnum != 10)
//        {
//            System.err.println("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline.");
//            fresh = true;
//        }
        if (argnum < 10)
        {
            // Graceful Exit: If there are missing command-line arguments.
            System.err.println("Error, looks like we may be missing command-line arguments.");// Creating blank empty airline.");
            return;
            //fresh = true;
        }
        else if (argnum > 10)
        {
            // Graceful Exit: If there are too many command-line arguments.
            System.err.println("Error, looks like we may have too many command-line arguments.");// Creating blank empty airline.");
            return;
        }

        /*
         * <p>
         * Self-Note #1: Argument-array Key:
         * {@code String airline = landing[0];}
         *
         * {@code String flightNumber = landing[1];}
         * {@code String src = landing[2]}
         * {@code String depart = landing[3] + " " + landing[4] + " " + landing[5];} Since Time-and-Date Stamps are tri-ply args!
         * E.g.{@code landing[3] + " " + landing[4] + " " + landing[5];} = {@code "10/20/3040" + " " + "10:20" + " " + "am";}
         * ! ...but dan, make sure to (first implement, then) use <code>isValidDateAndTime(String dateAndTime)</code> on time-stamp <code>strings</code> first!
         * {@code String dest = landing[6];
         * {@code String arrive = landing[7] + " " + landing[8] + " " + landing[9];}
         * </p>
         */

        // Option A.) Handling [multiple possibly] -README option(s)
//        if (readme_option_num > 0)
//        {
//            while (readme_option_num != 0)
//            {
//                displayer(readme_file, 0);
//                readme_option_num--;
//            }
//            //System.out.println("The Project #3 README has been provided above, (" + readme_option_num + ") times.");
//            return;
//        }
        String[] landing = unthneed(arglist);
        String gate = null;
        String taxi = null;

        // Input-Validation #1: Checking if flight number is an integer.
        try
        {
            int testNum = Integer.parseInt(landing[1]);
        }
        catch (Exception m7)
        {
            System.err.println("The flight number seems to be, well, not a integer!");
            return;
        }

        try
        {
            gate = TStamp.format(timeStamper(landing[3], landing[4], landing[5]));
            //runway = new Flight(landing);
        }
        catch (IllegalArgumentException m4a)
        {
            System.err.println("[Main Date Initialization #1] Error when attempting to formatting the departure time & date arguments, " + landing[3] + ", " + landing[4] + ", and " + landing[5] + ".");
            // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
            return;
        }
//        catch (ArrayIndexOutOfBoundsException m4b)
//        {
//            System.err.println("Error when attempting to formatting the departure time & date arguments, " + landing[3] + " and " + landing[4]);
//            // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//            return;
//        }

        /*
         * Input-Validation #2: Check Date & Time formatting.
         * Attempts to create the formatted time-and-date for arrival time.
         */
        try
        {
            taxi = TStamp.format(timeStamper(landing[7], landing[8], landing[9]));
        }
        catch (IllegalArgumentException m5a)
        {
            System.err.println("[Main Date Initialization #1] Error when attempting to formatting the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
            // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
            return;
        }
//        catch (ArrayIndexOutOfBoundsException m4b)
//        {
//            System.err.println("Error when attempting to formatting the arrival time & date arguments, " + landing[6] + " and " + landing[7]);
//            // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//            return;
//        }

        /*
         * Input-Validation #3: Checking if airport code is not 3-digits in characters.
         * landing[2] should equal src & landing[6] should equal dest.
         */

        if (landing[2].length() != 3)
        {
            if (landing[2].length() < 3)
            {
                System.err.println("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[2]);
            }
            else
            {
                System.err.println("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[2]);
            }
            // Graceful Exit: If the source airport code is not 3-digits.
            return;
        }
        if (landing[6].length() != 3)
        {
            if (landing[6].length() < 3)
            {
                System.err.println("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[6]);
            }
            else
            {
                System.err.println("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[6]);
            }
            // Graceful Exit: If the destination airport code is not 3-digits.
            return;
        }

        // Input-Validation #2b&c: Check if airport codes include numbers.
        char[] srcCodeTest = landing[2].toCharArray();
        char[] destCodeTest = landing[6].toCharArray();

        for (char src : srcCodeTest)
        {
            if (Character.isDigit(src))
            {
                System.err.println("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + landing[2]);

                // Graceful Exit: If the source airport code has numbers.
                return;
            }
        }

        for (char dest : destCodeTest)
        {
            if (Character.isDigit(dest))
            {
                System.err.println("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[6]);

                // Graceful Exit: If the destination airport code has numbers.
                return;
            }
        }

        // Input-Validation #6: Check the AirportNames database if the airport codes actually exist!
        if (AirportNames.getName(landing[2]) == null)
        {
            System.err.println("Uh oh, looks like the source airport code, '" + landing[2] + "', was not found in our airport-names database!");
            // Graceful Exit: If the source airport code was not found in AirportNames!
            return;
        }
        if (AirportNames.getName(landing[6]) == null)
        {
            System.err.println("Uh oh, looks like the destination airport code, '" + landing[6] + "', was not found in our airport-names database!");
            // Graceful Exit: If the destination airport code was not found in AirportNames!
            return;
        }

        try
        {
            runway = new Flight(landing[1], landing[2], gate, landing[6], taxi);
        }
        catch (ParseException m8)
        {
            System.err.println("Error when creating new temporary flight, specifically, when parsing invalid timestamps!");
            return;
        }
        // Input-Validation #5: If flight arrival is before the departure time:
        if (runway.getFlightTime() < 0)
        {
            // Graceful Exit: If negative flightTime (in minutes)
            return;
        }

        // int total_actions_num = (print_option_num + readme_option_num + filelist.size());//argnum);
        // int readmes = print_option_num;
        // int prints = readme_option_num;
        int filesnum = filelist.size();

        // Option B.) Handling [multiple possibly] -textFile parameters
        String[] fileStrings = unthneed(filelist);
        // for (int idx = 0; idx != total_actions_num; idx++)
        for (int idx = 0; idx != fileStrings.length; idx++)//filesnum; idx++)
        {

            if (filesnum != 0)
            {
                try
                {
                    TextParser air_traffic_control = new TextParser(fileStrings[idx]);
                    lufthansa = air_traffic_control.parse();

                    if (lufthansa != null)
                    {
                        System.out.println("'" + fileStrings[idx] + " was loaded successfully!");

                        String airlineFileName = lufthansa.getName();

                        if (!airlineFileName.equals(landing[0]))
                        {
                            System.err.println("Oh noes! The command-line Airline name specified ('" + landing[0] + "') does not match the Airline name on-file!\nThe file, instead specifies an Airline name of, '" + airlineFileName + ".'");

                            // Graceful Exit: If airline names of command-line argument & the file do not match!
                            return;
                        }
                        else
                        {
                            // Saving the new command-line specified flight into the airline!
                            Flight sky = new Flight(runway);
                            lufthansa.addFlight(sky);
                        }

                    }
                    else// if (lufthansa == null)
                    {
                        System.out.println("Looks like " + fileStrings[idx] + " was not found!\nNo worries, we'll make ya a new text file with your command-line specifications, no problem!");

                        Flight sky = new Flight(runway);
                        lufthansa = new Airline(landing[0], sky);
                    }

                    /*
                     * {@code TextDumper} in action!
                     */
                    System.out.println("Alrighty, proceeding to dump your new airline ('" + landing[0] + "') into a new file:\n" + fileStrings[idx]);

                    TextDumper baggage_truck = new TextDumper(fileStrings[idx]);

                    baggage_truck.dump(lufthansa);

                    System.out.println("Luggage has been dumped successfully (New flight dumped into Airline text-file) - Nice!");
                }
                catch (Exception m5)
                {
                    System.err.println("Error whilst processing textFile, '" + fileStrings[idx] + "' - Specific Error: " + m5.getMessage());
                }
            }
        }

        // Option A.) Handling [multiple possibly] -pretty parameters
        int prettynum = prettylist.size();

        String[] prettyStrings = unthneed(prettylist);
        String prettyString = null;

        // for (int idx = 0; idx != total_actions_num; idx++)
        if (prettyoption)
        {
            for (int idx = 0; idx != prettyStrings.length; idx++)//prettynum; idx++)
            {
                try
                {
                    if (prettynum != 0) {
                        /*
                         * {@code TextDumper} in action!
                         */
                        System.out.println("Alrighty, proceeding to pretty-dump your new airline ('" + landing[0] + "') into a new file:\n" + prettyStrings[idx]);

                        PrettyPrinter xerox = new PrettyPrinter(prettyStrings[idx], prettyoption);

                        xerox.dump(lufthansa);

                        System.out.println("Luggage has been pretty-dumped successfully (New flight dumped into Airline pretty-file) - Nice!");
                    }
                }
                catch (IOException m6)
                {
                    System.err.println("Error whilst processing -pretty file, '" + prettyStrings[idx] + "' - Specific Error: " + m6.getMessage());
                    // Graceful Exit: If PrettyPrinter has an error while processing a pretty_file!
                    return;
                }
            }
        }
        else
        {
            try
            {
                PrettyPrinter xerox = new PrettyPrinter();
                xerox.dump(lufthansa);
                prettyString = xerox.getPlottedPrint();
                System.out.print(prettyString);
            }
            catch (IOException m7)
            {
                System.err.println("Error whilst processing the -pretty airline!");
                // Graceful Exit: If PrettyPrinter has an error while processing the -pretty airline for printing!
                return;
            }
        }

        // Option C.) Handling (possible multiple!) -print option(s)
        // Prints the newly defined flight specifically by command-line!
        for (int idx = 0; idx != print_option_num; idx++)
        {
//            if (lufthansa != null)
//            {
//                lufthansa.printAll();
//            }
            runway.print();
        }
    }

    //args[0], args[1], args[2], args[3], args[4], args[5]);
    //AirlineParser read = new AirlineParser();
    //TextParser parser = new TextParser(lufthansa);
    //TextDumper dumper = new TextDumper();

}