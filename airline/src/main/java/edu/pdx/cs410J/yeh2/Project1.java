//package edu.pdx.cs410J.yeh2;
//
//import com.google.common.annotations.VisibleForTesting;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//
//import java.util.*;
//import java.text.*;
//
///**
// * The main class for the CS410J airline Project
// * usage: java -jar target/airline-2023.0.0.jar [options] <args>
// *      args are (in this order):
// *          airline      The name of the airline
// *          flightNumber     The flight number
// *          src      Three-letter code of departure airport
// *          depart      Departure date and time (24-hour time)
// *          dest     Three-letter code of arrival airtport
// *          arrive      Arrival date and time (24-hour time)
// *      options are (options may appear in any order)
// *          -print      Prints a description of the new flight
// *          -README     Prints a README for this project and exits
// *      Date and time should be in the format: mm/dd/yyyy hh:mm
// *
// */
//public class Project1 {
//
//    /**
//     * <p>
//     * Validates a given string for a valid time-and-date format as specified, e.g. mm/dd/yyyy hh:mm
//     * Based on: 1. {@code public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";}
//     * and 2. {@code public static final DateFormat testDate = new SimpleDateFormat(Timestamp_Format, Locale.US);}
//     * </p>
//     *
//     * (From Project #2)
//     * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
//     * @see java.text.DateFormat
//     * @see java.text.SimpleDateFormat
//     *
//     *
//     * A void function (but a thrown exception infers 'false' of valid time-and-date format)
//     * @param dateAndTime String
//     * @throws IllegalArgumentException If there is an invalid formatted time-and-date (bi-)string( combo)!
//     *
//     */
//    @VisibleForTesting
//    static Boolean isValidDateAndTime(String dateAndTime) throws IllegalArgumentException
//    {
////        String Timestamp_Format = "MM/dd/yyyy HH:mm";
////        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
////        Date test = null;
////        try
////        {
////            test = TStamp.parse(dateAndTime);
////        }
////        catch (ParseException m0)
////        {
////            throw new IllegalArgumentException("Hmmm, looks like the following was not a valid mm/dd/yyyy hh:mm time-and-date: ", m0);
////        }
//        return true;
//    }
//
//    // Using coreAPI, pages 92 ~ 104 on date, calendar, & variable-length args
//
//    /**
//     * <p>
//     * Creates a valid formatted for a valid time-and-date format as specified, e.g. mm/dd/yyyy hh:mm
//     * Based on: 1. {@code public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";}
//     * and 2. {@code public static final DateFormat testDate = new SimpleDateFormat(Timestamp_Format, Locale.US);}
//     * </p>
//     *
//     * (From Project #2)
//     *
//     * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
//     * @see java.text.DateFormat
//     * @see java.text.SimpleDateFormat
//     *
//     * @param date The first half of the bi-string combo-to-be!
//     * @param time The second half of the bi-string combo-to-be!
//     * @return timestamp The <code>Date</code> formatted bi-string timestmap combo!
//     * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
//     *
//     */
//    public static Date timeStamper(String date, String time) throws IllegalArgumentException
//    {
//        String Timestamp_Format = "MM/dd/yyyy HH:mm";
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
//        StringBuilder postage = new StringBuilder();
//        postage.append(date);
//        postage.append(" ");
//        postage.append(time);
//        //postage.append(date + " " + time);
//
//        String stamp = postage.toString();
//        Date timestamp = null;
//
//        try
//        {
//            timestamp = TStamp.parse(stamp);
//        }
//        catch (ParseException m00)
//        {
//            throw new IllegalArgumentException("Hmm, looks like a invalid time-and-date stamp attempt: ", m00);
//        }
//
//
//        return timestamp;
//    }
//
//    /**
//     * A function that displays txt files!
//     * (From Project #2)
//     * @param txtfile A text file name!
//     * @param Option A number indicating the type of displaying!
//     */
//    public static void displayer(String txtfile, Integer Option)
//    {
//
//        if (Option == 1)
//            /* If <code>Option</code> is 1, then display as a README file/parameter (where three lines are cut to only display
//             * the command-line interface*/
//        {
//            try (InputStream displayme = Project1.class.getResourceAsStream(txtfile)) {
//                InputStreamReader display_me = new InputStreamReader(displayme);
//
//                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
//                    String display_line = display_buffer.readLine();
//                    int idx = 0;
//                    while (display_line != null && idx != 3)
//                    {
//                        display_line = display_buffer.readLine();
//                        idx--;
//                    }
//
//                    while (display_line != null) {
//                        System.out.println(display_line);
//                        display_line = display_buffer.readLine();
//                    }
//                }
//            } catch (IOException m1) {
//                //System.out.println("Error! README not found!", m1);
//            }
//        }
//        //if (Option == 0)
//        else
//            /* If Option != (n), where n is an valid, specific <code>Option</code> number corresponding to an If-Statement, then display whole file normally. */
//        {
//            try (InputStream displayme = Project1.class.getResourceAsStream(txtfile)) {
//                InputStreamReader display_me = new InputStreamReader(displayme);
//
//                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
//                    String display_line = display_buffer.readLine();
//
//                    while (display_line != null) {
//                        System.out.println(display_line);
//                        display_line = display_buffer.readLine();
//                    }
//                }
//            } catch (IOException m2) {
//                //System.out.println("Error! README not found!", m1);
//            }
//        }
//    }
//
//    /**
//     * Project 1 takes in a {@code String[]}, creates an airline based on those parameters, and optionally prints the flight information.
//     * @param args
//     *      <p>
//     *      <code>args</code> are (in this order):
//     *          airline      The name of the airline
//     *          flightNumber     The flight number
//     *          src      Three-letter code of departure airport
//     *          depart      Departure date and time (24-hour time)
//     *          dest     Three-letter code of arrival airport
//     *          arrive      Arrival date and time (24-hour time)
//     *      options are (options may appear in any order))
//     *          -print      Prints a description of the new flight
//     *          -README     Prints a README for this project and exits
//     *      Date and time should be in the format: mm/dd/yyyy hh:mm
//     *      </p>
//     */
//    public static void main(String[] args) {
//        final String readme_file = "README.txt";
//        String Timestamp_Format = "MM/dd/yyyy HH:mm";
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
//
//        Airline lufthansa = null;
//        Flight runway = null;  // Refer to one of Dave's classes so that we can be sure it is on the classpath
//
//        int argnum = 0;
//        int readme_option_num = 0;
//        int print_option_num = 0;
//
////        if (args == null)
////        {
////            lufthansa = new Airline("N/A");
////        }
//
//        //lufthansa = new Airline("N/A");//args[0], args[1], args[2], args[3], args[4], args[5]);
//        //AirlineParser read = new AirlineParser();
//        //TextParser parser = new TextParser(lufthansa);
//        //TextDumper dumper = new TextDumper();
//
//        if (args.length == 0)
//        {
//            displayer(readme_file, 1);
//            System.out.println("The Project #1 command-line interface has been provided above.");
//
//            // Graceful Exit: No arguments passed to the program, thus, command-line interface is shown.
//            return;
//        }
//
//        for (int idx = 0; idx != args.length; idx++)
//        {
//            String current_arg = args[idx];
//
//            if (current_arg.startsWith("-"))
//            {
//                String action = current_arg.substring(1);
//
//                switch (action.toLowerCase())
//                {
//
//                    case "readme":
//                        displayer(readme_file, 0);
//
//                        //readme_option_num++;
//                        // While I realize allowing the user to print the README multiple times may be kind of redundant
//                        // and/or something that may become so, playing around with iterative option-handling now,
//                        // just in case future projects may require repetitive options-handling stuffs.
//                        return;
//
//                    case "print":
//
//                        print_option_num++;
//
//                        break;
//
//                    default:
//                        System.err.println("Uh oh, looks like there was a invalid option used: " + action);
//                        // Graceful Exit: If there are unknown option(s).
//                        return;
//                }
//            }
//            else
//            {
//                argnum++;
//
//                //arglist.add(current_arg);
//            }
//        }
//
//        if (argnum < 8)
//        {
//            // Graceful Exit: If there are missing command-line arguments.
//            System.err.println("Error, looks like we may be missing command-line arguments.");// Creating blank empty airline.");
//            return;
//            //fresh = true;
//        }
//        else if (argnum > 8)
//        {
//            // Graceful Exit: If there are too many command-line arguments.
//            System.err.println("Error, looks like we may have too many command-line arguments.");// Creating blank empty airline.");
//            return;
//        }
//
//        /*
//         * <p>
//         * Self-Note #1: Argument-array Key:
//         * {@code String airline = args[0];}
//         *
//         * {@code String flightNumber = args[1];}
//         * {@code String src = args[2]}
//         * {@code String depart = args[3] + " " + args[4];} Since Time-and-Date Stamps are two-ply args!
//         * E.g.{@code args[3] + " " + args[4];} = {@code "10/20/3040" + " " + "10:20";}
//         * ! ...but dan, make sure to (first implement, then) use <code>isValidDateAndTime(String dateAndTime)</code> on time-stamp <code>strings</code> first!
//         * {@code String dest = args[5];
//         * {@code String arrive = args[6] + " " + args[7];}
//         * </p>
//         */
//
//        // Option B.) Handling [multiple possibly] -README option(s)
////        if (readme_option_num > 0)
////        {
////            while (readme_option_num != 0)
////            {
////                /* Assuming that this function, that uses the Resources API
////                 * is permitted as it is only displaying a static text file,
////                 * specific for the README/no-args-default-command-line-interface displaying of text.
////                 */
////                displayer(readme_file, 0);
////                readme_option_num--;
////            }
////            //System.out.println("The Project #1 README has been provided above, (" + readme_option_num + ") times.");
////            return;
////        }
//
//        String[] landing = args;
//        String gate = null;
//        String taxi = null;
//
//        // Input-Validation #1: Checking if flight number is an integer.
//        try
//        {
//            int testNum = Integer.parseInt(landing[1]);
//        }
//        catch (Exception m7)
//        {
//            System.err.println("The flight number seems to be, well, not a integer!");
//            return;
//        }
//
//        try
//        {
//            gate = TStamp.format(timeStamper(landing[3], landing[4]));
//            //runway = new Flight(landing);
//        }
//        catch (IllegalArgumentException m4a)
//        {
//            System.err.println("Error when attempting to formatting the departure time & date arguments, " + landing[3] + " and " + landing[4]);
//            // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//            return;
//        }
////        catch (ArrayIndexOutOfBoundsException m4b)
////        {
////            System.err.println("Error when attempting to formatting the departure time & date arguments, " + landing[3] + " and " + landing[4]);
////            // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
////            return;
////        }
//
//        /*
//         * Input-Validation #2: Check Date & Time formatting.
//         * Attempts to create the formatted time-and-date for arrival time.
//         */
//        try
//        {
//            taxi = TStamp.format(timeStamper(landing[6], landing[7]));
//        }
//        catch (IllegalArgumentException m5a)
//        {
//            System.err.println("Error when attempting to formatting the arrival time & date arguments, " + landing[6] + " and " + landing[7]);
//            // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//            return;
//        }
////        catch (ArrayIndexOutOfBoundsException m4b)
////        {
////            System.err.println("Error when attempting to formatting the arrival time & date arguments, " + landing[6] + " and " + landing[7]);
////            // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
////            return;
////        }
//
//        /* Input-Validation #3: Checking if airport code is not 3-digits in characters.
//         * landing[2] should equal src & landing[5] should equal dest.
//         */
//
//        if (landing[2].length() != 3)
//        {
//            if (landing[2].length() < 3)
//            {
//                System.err.println("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[2]);
//            }
//            else
//            {
//                System.err.println("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[2]);
//            }
//            // Graceful Exit: If the source airport code is not 3-digits.
//            return;
//        }
//        if (landing[5].length() != 3)
//        {
//            if (landing[5].length() < 3)
//            {
//                System.err.println("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[5]);
//            }
//            else
//            {
//                System.err.println("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[5]);
//            }
//            // Graceful Exit: If the destination airport code is not 3-digits.
//            return;
//        }
//
//        // Input-Validation #2b&c: Check if airport codes include numbers.
////        StringBuilder srcCodeTest = new StringBuilder(landing[2]);
////        StringBuilder destCodeTest = new StringBuilder(landing[5]);
//        char[] srcCodeTest = landing[2].toCharArray();
//        char[] destCodeTest = landing[5].toCharArray();
//
//        for (char src : srcCodeTest)
//        {
//            if (Character.isDigit(src))
//            {
//                System.err.println("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + landing[2]);
//
//                // Graceful Exit: If the source airport code has numbers.
//                return;
//            }
//        }
//
//        for (char src : destCodeTest)
//        {
//            if (Character.isDigit(src))
//            {
//                System.err.println("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[5]);
//
//                // Graceful Exit: If the destination airport code has numbers.
//                return;
//            }
//        }
//
//        runway = new Flight(landing[1], landing[2], gate, landing[5], taxi);
//        lufthansa = new Airline(landing[0], runway);
//
//        // Option A.) Handling [possibly multiple] -print Option(s)
//        for (int idx = print_option_num; idx != 0; idx++)
//        {
//            lufthansa.printAllString(landing[0]);
//        }
//
//        /*
//         * Attempts to create the formatted time-and-date for departure time.
//         */
//
//
//    }
//
//}