package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Iterator;
//import java.util.LinkedList;

import java.text.*;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;

//import java.util.Collection;
//import edu.pdx.cs410J.ParserException;


public class Project2 {

    /**
     * A function that displays txt files!
     * @param txtfile
     * @param Option
     */
    public static void displayer(String txtfile, Integer Option)
    {

        if (Option == 1)
            /* If <code>Option</code> is 1, then display as a README file/parameter (where three lines are cut to only display
             * the command-line interface*/
        {
            try (InputStream displayme = Project2.class.getResourceAsStream(txtfile)) {
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
            try (InputStream displayme = Project2.class.getResourceAsStream(txtfile)) {
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
     * Time-and-Date Format stuffs - from coreAPI, pages 92 ~ 104.
     *
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     */
    public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";
    public static DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);

    // Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat

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
    static void isValidDateAndTime(String dateAndTime) throws IllegalArgumentException
    {
        Date test = null;
        try
        {
            test = TStamp.parse(dateAndTime);
        }
        catch (ParseException m0)
        {
            throw new IllegalArgumentException("Hmmm, looks like the following was not a valid mm/dd/yyyy hh:mm time-and-date: ", m0);
        }

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
     * @param date The first half of the bi-string combo-to-be!
     * @param time The second half of the bi-string combo-to-be!
     * @return timestamp The <code>Date</code> formatted bi-string timestmap combo!
     * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
     *
     */

    public static Date timeStamper(String date, String time) throws IllegalArgumentException
    {
        StringBuilder postage = new StringBuilder();
        postage.append(date);
        postage.append(" ");
        postage.append(time);
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
     * The main class for the CS410J Project #2: TextFile
     * usage: java -jar target/airline-2023.0.0.jar [options] <args>
     *
     * @param args
     *      <p>
     *      <code>args</code> are (in this order):
     *          airline      The name of the airline
     *          flightNumber     The flight number
     *          src      Three-letter code of departure airport
     *          depart      Departure date and time (24-hour time)
     *          dest     Three-letter code of arrival airport
     *          arrive      Arrival date and time (24-hour time)
     *      options are (options may appear in any order))
     *          -textFile      file Where to read/write the airline info
     *          -print      Prints a description of the new flight
     *          -README     Prints a README for this project and exits
     *      Date and time should be in the format: mm/dd/yyyy hh:mm
     *      </p>
     */

    public static void main(String[] args) {
        //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        Flight runway = null;
        Airline lufthansa = null;
        String file_name = null;

        // This boolean will be used to indicate the creation of a new, but blank Airline (e.g. Blank Airline)
        boolean fresh = false;
        int freshnum = 0;

        int print_option_num = 0;
        int readme_option_num = 0;
        int argnum = 0;

        StringBuilder yarn = null;
        // For temporary string concatenation stuffs

        // String[] filelist = null;
        // Collection<>

        List<String> arglist = new LinkedList<String>();
        LinkedList<String> filelist = new LinkedList<String>();

        final String readme_file = "README2.txt";

        // if (args == null) // args will never equal null, but it can have 0 arguments, I think
        if (args.length == 0)
        {
            // lufthansa = new Airline("N/A");
            displayer(readme_file, 1);
            /* Assuming that this function, that uses the Resources API
             * is permitted as it is only displaying a static text file,
             * specific for the README/no-args-default-command-line-interface displaying of text.
             */
        }

        for (int idx = 0; idx != args.length; idx++)
        {
            String current_arg = args[idx];

            if (current_arg.startsWith("-"))
            {
                String action = current_arg.substring(1);

                switch (action.toLowerCase())
                {
                    case "textFile":
                        file_name = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (file_name.isEmpty())
                        {
                            file_name = "Empty Airline";
                        }
                        if (file_name.startsWith("-"))
                        {
                            //file_name = "-";
                            System.err.println("Hmm, a invalid file name was detected for the textFile (valid names include, e.g. lufthansa.txt)!\nTreating as empty name, creating default Empty Airline!");
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

                            //System.exit(1);
                        }
                        filelist.add(file_name);
//                        try {
//                            TextParser parsley = new TextParser(file_name);
//
//                            lufthansa = parsley.parse();
//
//                            if (lufthansa == null)
//                            {
//                                lufthansa = new Airline(args[0+idx], args[10+idx], args[20+idx], args[30+idx], args[40+idx], args[50+idx]);
//                            }
//
//                        }
//                        catch (ParserException m3)
//                        {
//                            lufthansa = new Airline(args[0]);
//                        }

                        break;

                    case "readme":
                        //displayer(readme_file, 0);
                        /* Assuming that this function, that uses the Resources API
                         * is permitted as it is only displaying a static text file,
                         * specific for the README/no-args-default-command-line-interface displaying of text.
                         */
//                        try (InputStream readme = Project2.class.getResourceAsStream("README2.txt")) {
//                            InputStreamReader reader_me = new InputStreamReader(readme);
//
//                            try (BufferedReader readme_buffer = new BufferedReader(reader_me)) {
//                                String readme_line = readme_buffer.readLine();
//
//                                while (readme_line != null) {
//                                    System.out.println(readme_line);
//                                    readme_line = readme_buffer.readLine();
//                                }
//                            }
//                        } catch (IOException m1) {
//                            //System.out.println("Error! README not found!", m1);
//                        }

                        //System.exit(1);

                        readme_option_num++;
                        // While I realize allowing the user to print the README multiple times may be kind of redundant
                        // and/or something that may become so, playing around with iterative option-handling now,
                        // just in case future projects may require repetitive options-handling stuffs.
                        break;

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

        if (argnum != 8)
        {
            System.err.println("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline.");
            fresh = true;
        }

        /**
         * <p>
         *     Self-Note #1: Argument-array Key:
         *          {@code String airline = landing[0];}
         *
         *          {@code String flightNumber = landing[1];}
         *          {@code String src = landing[2]}
         *          {@code String depart = landing[3] + " " + landing[4];} Since Time-and-Date Stamps are two-ply args!
         *              E.g.{@code landing[3] + " " + landing[4];} = {@code "10/20/3040" + " " + "10:20";}
         *              ! ...but dan, make sure to (first implement, then) use <code>isValidDateAndTime(String dateAndTime)</code> on time-stamp <code>strings</code> first!
         *          {@code String dest = landing[5];
         *          {@code String arrive = landing[6] + " " + landing[7];}
         * </p>
         */

        // Option A.) Handling [multiple possibly] -README option(s)
        while (readme_option_num != 0)
        {
            /* Assuming that this function, that uses the Resources API
             * is permitted as it is only displaying a static text file,
             * specific for the README/no-args-default-command-line-interface displaying of text.
             */
            displayer(readme_file, 0);
            readme_option_num--;
        }

        String[] landing = unthneed(arglist);
        String gate = null;
        String taxi = null;

        /**
         * Attempts to create the formatted time-and-date for departure time.
         */
        try
        {
            gate = TStamp.format(timeStamper(landing[3], landing[4]));
            //runway = new Flight(landing);
        }
        catch (IllegalArgumentException m4)
        {
            System.err.println("Error when attempting to formatting the destination time & date arguments, " + landing[3] + " and " + landing[4]);
            // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
            System.exit(1);
        }

        /**
         * Attempts to create the formatted time-and-date for arrival time.
         */
        try
        {
            taxi = TStamp.format(timeStamper(landing[6], landing[7]));
        }
        catch (IllegalArgumentException m5)
        {
            System.err.println("Error when attempting to formatting the arrival time & date arguments, " + landing[6] + " and " + landing[7]);
            // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
            System.exit(1);
        }

        runway = new Flight(landing[1], landing[2], gate, landing[5], taxi);

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
                            System.err.println("Oh noes! The command-line Airline name specified ('" + landing[0] + "') does not match the Airline name on-file!\nThe file, instead specifies an Airline name of, '" + airlineFileName);

                            // Graceful Exit: If airline names of command-line argument & the file do not match!
                            System.exit(1);
                        }
                        else
                        {
                            // Saving the new command-line specified flight into the airline!
                            Flight sky = new Flight(runway);
                            lufthansa.addFlight(sky);
                        }

                    }
                    else if (lufthansa == null)
                    {
                        System.out.println("Looks like " + fileStrings[idx] + " was not found!\nNo worries, we'll make ya a new text file with your command-line specifications, no problem!");

                        Flight sky = new Flight(runway);
                        lufthansa = new Airline(landing[0], sky);
                    }

                    /**
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

        // Option C.) Handling (possible multiple!) -print option(s)
        for (int idx = 0; idx != print_option_num; idx++)
        {
            if (lufthansa == null)
            {
                try
                {
                    Flight sky = new Flight(runway);
                    lufthansa = new Airline(landing[0], sky);
                }
                catch (IllegalArgumentException m6)
                {
                    System.err.println("Uh oh, looks like the flight data was empty when attempting to be added to the airline!");
                }
            }
        }
    }

    //args[0], args[1], args[2], args[3], args[4], args[5]);
    //AirlineParser read = new AirlineParser();
    //TextParser parser = new TextParser(lufthansa);
    //TextDumper dumper = new TextDumper();



//        if (args == null) {
//            System.err.println("Missing command line arguments");
//        }

//        try {
//            for (String arg : args) {
//                System.out.println(arg);
//
//                if (arg.startsWith("-")) {
//                    String parameter = arg.substring(1);
//                    if (parameter.equals("print")) {
//                        lufthansa.printAll();
//                    }
//                    if (parameter.equals("README")) {
//                        //System.out.println("Welcome to Project 1, created by Dan Jang for CS410P: Advanced Java Programming!");
//                        //System.out.println("This project focuses on extended classes & a bit more complex commandline parsing stuffs.");
//                        try (InputStream readme = Project1.class.getResourceAsStream("README2.txt")) {
//                            InputStreamReader readmereader = new InputStreamReader(readme);
//
//                            try (BufferedReader readmebuffer = new BufferedReader(readmereader)) {
//                                String readmeline = readmebuffer.readLine();
//
//                                while (readmeline != null) {
//                                    System.out.println(readmeline);
//                                    readmeline = readmebuffer.readLine();
//                                }
//                            }
//                        } catch (IOException m1) {
//                            //System.out.println("Error! README not found!", m1);
//                        }
//                        System.exit(1);
//                    } else {
//                        System.err.println("Invalid airline program parameter, o noes! (Parameter Specified: " + parameter + ")");
//
//                    }
//                } else {
//                    argnum++;
//                }
//            }
//        }
//        catch (NullPointerException m2)
//        {
//            System.exit(1);
//        }
//    if (argnum != 6)
//    {
//        System.err.println("Missing command line arguments");
//    }
////
//}
}