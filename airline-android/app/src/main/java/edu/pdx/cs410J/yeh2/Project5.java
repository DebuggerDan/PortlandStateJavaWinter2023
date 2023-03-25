//package edu.pdx.cs410J.yeh2;
//
//import edu.pdx.cs410J.ParserException;
//
//import java.io.*;
//
//import java.util.*;
///*
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Iterator;
//import java.util.LinkedList;
//*/
//
//import java.text.*;
//// import java.text.DateFormat;
//// import java.text.SimpleDateFormat;
//
//import edu.pdx.cs410J.AirportNames;
//
//import javax.swing.text.html.parser.Parser;
//import java.io.IOException;
//import java.util.Map;
//
///**
// * CS410P [Adv. Java Programming] - Project #5
// * Named: "AftFlight" (reference hint: an antonym of aft being 'Fore')
// * The main class for Project #5 that parses the command line and communicates with the
// * Airline server using REST.
// * usage: java -jar target/airline-client.jar [options] <args>
// *  args are (in this order):
// *      airline The name of the airline
// *      flightNumber The flight number
// *      src Three-letter code of departure airport
// *      depart Departure date/time
// *      dest Three-letter code of arrival airport
// *      arrive Arrival date/time
// *  options are (options may appear in any order):
// *      -host hostname Host computer on which the server runs
// *      -port port Port on which the server is listening
// *      -search Search for flights
// *      -print Prints a description of the new flight
// *      -README Prints a README for this project and exits
// * @author Dan J
// */
//public class Project5 {
//
//    public static final String MISSING_ARGS = "Missing command line arguments";
//
//    /**
//     * Project #5 Timestamp Formatting:
//     * {@code MM/dd/yyyy h:mm a}
//     * E.g., "03/09/2023 1:20 pm"
//     */
//    protected static final String Timestamp_Format = "MM/dd/yyyy h:mm a";
//
//    /**
//     * A function that displays txt files!
//     * @param txtfile A text file name!
//     * @param Option A number indicating the type of displaying!
//     */
////    public static void displayer(String txtfile, Integer Option)
////    {
////
////        if (Option == 1)
////            /* If <code>Option</code> is 1, then display as a README file/parameter (where three lines are cut to only display
////             * the command-line interface*/
////        {
////            try (InputStream displayme = Project5.class.getResourceAsStream(txtfile)) {
////                InputStreamReader display_me = new InputStreamReader(displayme);
////
////                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
////                    String display_line = display_buffer.readLine();
////                    int idx = 0;
////                    while (display_line != null && idx != 5) // Additional line for Project #4
////                    {
////                        display_line = display_buffer.readLine();
////                        idx++;
////                    }
////
////                    while (display_line != null) {
////                        System.out.println(display_line);
////                        display_line = display_buffer.readLine();
////                    }
////                }
////            } catch (IOException m1) {
////                //System.out.println("Error! README not found!", m1);
////            }
////        }
////        //if (Option == 0)
////        else
////            /* If Option != (n), where n is an valid, specific <code>Option</code> number corresponding to an If-Statement, then display whole file normally. */
////        {
////            try (InputStream displayme = Project5.class.getResourceAsStream(txtfile)) {
////                InputStreamReader display_me = new InputStreamReader(displayme);
////
////                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
////                    String display_line = display_buffer.readLine();
////
////                    while (display_line != null) {
////                        System.out.println(display_line);
////                        display_line = display_buffer.readLine();
////                    }
////                }
////            } catch (IOException m2) {
////                //System.out.println("Error! README not found!", m1);
////            }
////        }
////    }
//
//    /**
//     * <p>
//     * A whimsical, simple but a bit silly, 'unwraveler' function for <code>AbstractList</code>s,
//     * that "unthneeds" them into simple {@code String[]}, string-arrays.
//     * (From Project #2)
//     * </p>
//     * @param thneed {@code AbstractList<String>} In goes the list!
//     * @return tuft Out goes a {@code String[]} array, unraveled from the {@code AbstractList<String>} {@param thneed}!
//     */
//    public static String[] unthneed(List<String> thneed) // Dr. Seuss reference heh
//    {
//        String[] tuft = new String[thneed.size()];
//        Iterator<String> lorax = thneed.iterator();
////        int idx = 0;
////        for (Object truffula : thneed)
////        {
////            tuft[idx] = truffula;
////            idx++;
////        }
//        for (int truffula = 0; truffula != thneed.size(); truffula++)
//        {
//            if (lorax.hasNext())
//            {
//                tuft[truffula] = lorax.next();
//            }
//            else
//            {
//                System.err.println("An arwy truffula tree tuft! The Lorax says, check the that loop, For-where this came!");
//            }
//        }
//
//        return tuft;
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
//     * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
//     * @see java.text.DateFormat
//     * @see java.text.SimpleDateFormat
//     *
//     * @param date The first part of the tri-string combo-to-be!
//     * @param time The second part of the tri-string combo-to-be!
//     * @param ampm The third part of the tri-string combo-to-be!
//     * @return timestamp The <code>Date</code> formatted bi-string timestamp combo!
//     * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
//     *
//     */
//
//    public static Date timeStamper(String date, String time, String ampm) throws IllegalArgumentException
//    {
//        //String Timestamp_Format = "MM/dd/yyyy HH:mm";
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
//        StringBuilder postage = new StringBuilder();
//        postage.append(date);
//        postage.append(" ");
//        postage.append(time);
//        postage.append(" ");
//        postage.append(ampm.toUpperCase());
//        //postage.append(date + " " + time);
//
//        String stamp = postage.toString();
//        Date timestamp;
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
//        return timestamp;
//    }
//
//    public static void main(String... args) {
//
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
//
//        List<String> arglist = new LinkedList<>();
//        //List<String> validOptions = Arrays.asList("host", "port", "search", "print", "readme");
//
//        int argnum = 0;
//        int print_option_num = 0;
//        int port = 65537;
//        // The highest TCP port number is 65535, so this default value, if not changed, should result in a port-specific error.
//
//        Flight runway = null;
//        Airline lufthansa = null;
//
//        //String file_name = null;
//        //String pretty_name = null;
//        //String xml_name = null;
//        String search_name = null;
//        String hostName = null;
//        String portString = null;
//        String word = null;
//        String definition = null;
//
//        Boolean printoption = false;
//        Boolean searchoption = false;
//        Boolean hostport_eitheroption = false;
//
//        if (args.length == 0)
//        {
//            //displayer(readme_file, 1);
//            //System.out.println("The Project #5 command-line interface has been provided above.");
//            usage("\"The Project #5 command-line interface has been provided below.\"");
//            // Graceful Exit: No arguments passed to the program, thus, command-line interface will be shown using usage(String) & returned.
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
//                    // Project #5.) Option V.) -readme
//                    case "readme":
//                        //displayer(readme_file, 0);
//                        usage("CS410P [Adv. Java Programming] Project #5 utilizes the REST API, provided through the AirlineRestClient & AirlineServlet classes - utilizing HTTP requests as a REST-ful Web Service!\nProject #5 can store multiple Airlines, alongside their flights.\nIt can search its dictionary of airline's and containing flights for source & destination airports, search for matching airplane names, pretty-print all available flights, etc..");
//                        return;
//                        //readme_option_num++;
//                        //break;
//
//                    // Project #5.) Option III.) -search
//                    case "search":
////                        search_name = args[(args.length <= idx++) ? (idx-1) : idx];
////                        if (search_name.isEmpty())
////                        {
////                            searchUsage("Hmm, looks like the -search option was used, but there was no airline name specified!");
////                            return;
////                        }
////                        if (search_name.startsWith("-")) {
////                            searchUsage("Hmm, an invalid airline name was detected for the -search option (valid airlines with matching names must exist in the airline dictionary)!");//\nTreating as empty name, creating default Empty Airline!");
////                            return;
////                        }
//                        searchoption = true;
//                        break;
//
//                    // Project #5.) Option I.) -host
//                    case "host":
//                        hostName = args[(args.length <= idx++) ? (idx-1) : idx];
//                        if (hostName.isEmpty())
//                        {
//                            usage("Hmm, looks like the -host option was used, but there was no host name specified!");
//                            return;
//                        }
//                        if (hostName.startsWith("-"))
//                        {
//                            usage("Hmm, an invalid host name was detected for the -host option!");//\nTreating as empty name, creating default Empty Airline!");
//                            return;
//                        }
//                        hostport_eitheroption = true;
//                        break;
//
//                    // Project #5.) Option II.) -port
//                    case "port":
//                        portString = args[(args.length <= idx++) ? (idx-1) : idx];
//                        if (portString.isEmpty())
//                        {
//                            usage("Hmm, looks like the -port option was used, but there was no port number specified!");
//                            return;
//                        }
//                        if (portString.startsWith("-"))
//                        {
//                            usage("Hmm, an invalid port number was detected for the -port option!");//\nTreating as empty name, creating default Empty Airline!");
//                            return;
//                        }
//                        hostport_eitheroption = true;
//                        break;
//
//                    // Project #5.) Option IV.) -print
//                    case "print":
////                        if (lufthansa == null)
////                        {
////                            lufthansa = new Airline(args[0]);
////                        }
////                        lufthansa.printAll();
//                        print_option_num++;
//                        printoption = true;
//
//                        break;
//
//                    default:
//                        usage("Uh oh, looks like there was a invalid option used: " + action);
//                }
//            }
//            else
//            {
//                argnum++;
//
//                arglist.add(current_arg);
//            }
//        }
//
//        // Input Validation [Project #5] 4.) - If Project5 is ran without the -search option...
//        // Check the number of arguments passed to the program to be ten (10) arguments.
//        if (!searchoption)
//        {
//            // Input Validation [Project #5] 2.) -If there are more than 10 arguments, then error!
//            if (argnum < 10)
//            {
//                error("Uh oh, looks like there were less than than 10 arguments passed to the program!\nProject #5 requires 10 arguments if ran w/o the -search option!");
//                return;
//            }
//            else if (argnum > 10)
//            {
//                error("Uh oh, looks like there were more than 10 arguments passed to the program!\nProject #5 only requires 1 argument (IF ran w/ -search option; which itself has a max of 4 arguments) or a maximum of 10 arguments total (IF w/o -search)!");
//                return;
//            }
//        }
//
//        // Input Validation [Project #5] 1.) - If either -host or -port are specified, then check that both were specified.
//        // If not, then error!
//        if (hostport_eitheroption)
//        {
//            if (hostName == null) {
//                usage( "Uh oh! Looks like the port was specified, but not the hostname - we need both!" );
//                return;
//
//            } else if ( portString == null) {
//                usage( "Uh oh! Looks like the hostname was specified, but not the port number - we need both!" );
//                return;
//            }
//
//            //int port;
//            // Input Validation [Project #5] 1.1.) - If both -hostname & -port were specified, then check that the port can be successfully parsed into an valid integer!
//            try {
//                port = Integer.parseInt( portString );
//
//            } catch (NumberFormatException ex) {
//                usage("Port \"" + portString + "\" must be an integer");
//                return;
//            }
//
//            /*
//             * Input Validation [Project #5] 1.1.1) - Check that the port number is within the valid range as defined in the RFC 1700 (by IANA).
//             * The range is defined as port numbers <code>0</code> through <code>65536</code>, inclusive.
//             * @see <a href="https://tools.ietf.org/html/rfc1700">RFC 1700: Assigned Numbers (1994)</a>
//             * @author Reynolds & Postel {@literal <iana@isi.edu>}
//             */
//            if (port < 0 || port > 65536)
//            {
//                usage("Hmmm, the specified port-number of '\"" + portString + "\"' was outside of the valid RFC 1700 range for TCP ports, which are 0 through 65536!");
//                return;
//            }
//        }
//
//        //        for (String arg : args) {
////            if (hostName == null) {
////                hostName = arg;
////
////            } else if ( portString == null) {
////                portString = arg;
////
////            } else if (word == null) {
////                word = arg;
////
////            } else if (definition == null) {
////                definition = arg;
////
////            } else {
////                usage("Extraneous command line argument: " + arg);
////            }
////        }
//
//        String[] landing = unthneed(arglist);
//        String gate = null;
//        String taxi = null;
//
//        // Input Validation [Project #5] 3.) - For processing -search option & specific functionalities/methodologies:
//        // If -search is specified (if searchoption is true):
//        // Firstly, ensure that there is [at least] an airline parameter specified
//        // Secondly, for specific-handling of possible two (2) optional arguments, src & dest,
//        // ...Thirdly, ensure those three (3) arguments are valid/within -search specific range of arguments.
//        if (searchoption)
//        {
//            try
//            {
//                // Input Validation [Project #5] 3.1.) - Whilst -search option is specified...
//                // If airline name parameter is empty, then -search specific usage-error!
//                if (landing[0] == null)
//                {
//                    searchUsage("Uh oh, looks like you forgot to specify an airline name - you must at least specify the airline parameter when using the -search option (w/ source & destination airport code(s) as optional parameters)!");
//                    // Graceful Exit: If no airline name was specified.
//                    return;
//                }
//
//                // Input Validation [Project #5] 3.2.) - Whilst -search option is specified...
//                // If there are more than 3 arguments specified, then -search specific usage-error!
//                if (landing.length > 3)
//                {
//                    searchUsage("Uh oh, there were more than 3 arguments specified, but the maximum number of arguments with the -search option is 3 (airline name (+ two [2] optional src & dest airport code parameters!)");
//                    // Graceful Exit [Project #5]: If there were more than 3 arguments specified, whilst using the -search option.
//                    return;
//                }
//
//                // Input Validation [Project #5] 3.3.) - Whilst -search option is specified...
//                // If there are less than 3 arguments specified & it is not just one argument, then -search specific usage-error!
//                else if (landing.length < 3 && landing.length != 1)
//                {
//                    searchUsage("Uh oh, looks like there may be a airport code missing, as there was at least one argument specified.\nHowever, that also means there must be 3 arguments...\n...which would represent the airline name you wanted to search, the source airport code, & the destination airport code for a total of three (3) arguments (whilst using -search)!");
//                    // Graceful Exit [Project #5]: If there were less than 3 arguments specified (but not exactly 1 argument - which would possibly be correct, if it is the airline name), whilst using the -search option.
//                    return;
//                }
//
//                // Input Validation [Project #5 <- Project #4] 3.4.) - Validation of Airport Codes
//                /*
//                 * Input-Validation #3 {from Project #4}: Checking both src & dest airport codes is not 3-digits in characters.
//                 * landing[1] should equal src & landing[2] should equal dest.
//                 */
//
//                if (landing[1].length() != 3)
//                {
//                    if (landing[1].length() < 3)
//                    {
//                        searchUsage("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[1]);
//                    }
//                    else
//                    {
//                        searchUsage("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[1]);
//                    }
//                    // Graceful Exit: If the source airport code is not 3-digits.
//                    return;
//                }
//                if (landing[2].length() != 3)
//                {
//                    if (landing[2].length() < 3)
//                    {
//                        searchUsage("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[2]);
//                    }
//                    else
//                    {
//                        searchUsage("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[2]);
//                    }
//                    // Graceful Exit: If the destination airport code is not 3-digits.
//                    return;
//                }
//
//                // Input-Validation #2b & #2c {from Project #4}: Check if both src & dest airport codes include numbers, if so, then error!.
//                // Project #5 Retrograde Reintegration Note: Ensure landing[1] = src & landing[2] = dest, since -search modifies total arguments to either one (1) or (3)!
//                char[] srcCodeTest = landing[1].toCharArray();
//                char[] destCodeTest = landing[2].toCharArray();
//
//                for (char src : srcCodeTest)
//                {
//                    if (Character.isDigit(src))
//                    {
//                        searchUsage("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + landing[1]);
//
//                        // Graceful Exit: If the source airport code has numbers.
//                        return;
//                    }
//                }
//
//                for (char dest : destCodeTest)
//                {
//                    if (Character.isDigit(dest))
//                    {
//                        searchUsage("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[2]);
//
//                        // Graceful Exit: If the destination airport code has numbers.
//                        return;
//                    }
//                }
//
//                // Input-Validation #6 {from Project #4}: Check the AirportNames database if the airport codes actually exist!
//                if (AirportNames.getName(landing[1]) == null)
//                {
//                    searchUsage("Uh oh, looks like the source airport code, '" + landing[1] + "', was not found in our airport-names database!");
//                    // Graceful Exit: If the source airport code was not found in AirportNames!
//                    return;
//                }
//                if (AirportNames.getName(landing[2]) == null)
//                {
//                    searchUsage("Uh oh, looks like the destination airport code, '" + landing[2] + "', was not found in our airport-names database!");
//                    // Graceful Exit: If the destination airport code was not found in AirportNames!
//                    return;
//                }
//            }
//            catch (ArrayIndexOutOfBoundsException e3)
//            {
//                error("[AftFlight] Invalid or missing arguments detected for -search!");
//                return;
//            }
//
//        }
//
//        // Only if -search is not specified (if searchoption is false), ergo, where we must check all 10 of the arguments for a given command-line for a new airline with a new flight, etc.
//        else if (!searchoption)
//        {
//            /*
//             * Input-Validation #8 {from Project #4}: Verify that flight number is indeed, an actual number.
//             */
//            try
//            {
//                int testNum = Integer.parseInt(landing[1]);
//            }
//            catch (Exception m7)
//            {
//                usage("The flight number seems to be, well, not a integer!");
//                return;
//            }
//
//            /*
//             * Input-Validation #7 {from Project #4}: Check the Departure Date & Time formatting.
//             * Attempts to create the formatted time-and-date for departure time.
//             */
//            try
//            {
//                gate = TStamp.format(timeStamper(landing[3], landing[4], landing[5]));
//                //runway = new Flight(landing);
//            }
//            catch (IllegalArgumentException m4a)
//            {
//                usage("[Main Date Initialization #1a] Error when attempting to format the departure time & date arguments, " + landing[3] + ", " + landing[4] + ", and " + landing[5] + ".");
//                // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//                return;
//            }
//
//            /*
//             * Input-Validation #2 {from Project #4}: Check the Arrival Date & Time formatting.
//             * Attempts to create the formatted time-and-date for arrival time.
//             */
//            try
//            {
//                taxi = TStamp.format(timeStamper(landing[7], landing[8], landing[9]));
//            }
//            catch (IllegalArgumentException m5a)
//            {
//                usage("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, " + landing[7] + ", " + landing[8] + ", and " + landing[9] + ".");
//                // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//                return;
//            }
//
//            /*
//             * Input-Validation #3 {from Project #4}: Checking both src & dest airport codes is not 3-digits in characters.
//             * landing[2] should equal src & landing[6] should equal dest.
//             */
//
//            if (landing[2].length() != 3)
//            {
//                if (landing[2].length() < 3)
//                {
//                    usage("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: " + landing[2]);
//                }
//                else
//                {
//                    usage("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: " + landing[2]);
//                }
//                // Graceful Exit: If the source airport code is not 3-digits.
//                return;
//            }
//            if (landing[6].length() != 3)
//            {
//                if (landing[6].length() < 3)
//                {
//                    usage("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: " + landing[6]);
//                }
//                else
//                {
//                    usage("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: " + landing[6]);
//                }
//                // Graceful Exit: If the destination airport code is not 3-digits.
//                return;
//            }
//
//            // Input-Validation #2b & #2c {from Project #4}: Check if both src & dest airport codes include numbers, if so, then error!.
//            char[] srcCodeTest = landing[2].toCharArray();
//            char[] destCodeTest = landing[6].toCharArray();
//
//            for (char src : srcCodeTest)
//            {
//                if (Character.isDigit(src))
//                {
//                    usage("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + landing[2]);
//
//                    // Graceful Exit: If the source airport code has numbers.
//                    return;
//                }
//            }
//
//            for (char dest : destCodeTest)
//            {
//                if (Character.isDigit(dest))
//                {
//                    usage("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + landing[6]);
//
//                    // Graceful Exit: If the destination airport code has numbers.
//                    return;
//                }
//            }
//
//            // Input-Validation #6 {from Project #4}: Check the AirportNames database if the airport codes actually exist!
//            if (AirportNames.getName(landing[2]) == null)
//            {
//                usage("Uh oh, looks like the source airport code, '" + landing[2] + "', was not found in our airport-names database!");
//                // Graceful Exit: If the source airport code was not found in AirportNames!
//                return;
//            }
//            if (AirportNames.getName(landing[6]) == null)
//            {
//                usage("Uh oh, looks like the destination airport code, '" + landing[6] + "', was not found in our airport-names database!");
//                // Graceful Exit: If the destination airport code was not found in AirportNames!
//                return;
//            }
//
//            // Input-Validation #9 {from Project #4}: Try initializing the new flight as-is with the given command-line arguments.
//            try
//            {
//                runway = new Flight(landing[1], landing[2], gate, landing[6], taxi);
//            }
//            catch (ParseException m8)
//            {
//                error("Error when creating new temporary flight, specifically, when parsing invalid timestamps!");
//                return;
//            }
//            // Input-Validation #5: If flight arrival is before the departure time:
//            if (runway.getFlightTime() < 0)
//            {
//                // Graceful Exit: If negative flightTime (in minutes)
//                return;
//            }
//        }
//
//        AirlineRestClient client = new AirlineRestClient(hostName, port);
//
//        String message = null;
//
//        try {
//            // Project #5 Main Functionality-Pathway I.) The below if-statements infers that, if met, after all the input validation check stuff above for Project #5...
//            // ...an argnum of 1 or 3 infers a valid -search option with either one (1) argument for the airline name or three (3) arguments, for the airline name + two (2) src & dest airport-code args.
//            if (searchoption)
//            {
//                // Search for specific airline, then pretty print all word/definition pairs
//                // (aka, pretty print all flights within matching airline-name airline -> its corresponding flight-entries)
//                if (argnum == 1)
//                {
//                    //Map<String, String> dictionary = client.getAllDictionaryEntries();
////                    StringWriter sw = new StringWriter();
////                    PrettyPrinter pretty = new PrettyPrinter(sw);
////                    pretty.dump(dictionary);
////                    message = sw.toString();
//                    message = client.getFlightEntries(landing[0], null, null);
//                }
//                // Search for specific airline, then sub-search for matching src & dest airport codes, then pretty print all flights that match!
//                else if (argnum == 3)
//                {
//                    //Map<String, String> dictionary = client.getAllDictionaryEntries();
//                    //StringWriter sw = new StringWriter();
//                    //PrettyPrinter pretty = new PrettyPrinter(sw);
//                    //pretty.dump(dictionary);
//                    //message = sw.toString();
//                    message = client.getFlightEntries(landing[0], landing[1], landing[2]);
//                }
//                //System.out.println(message);
//
//                try
//                {
//                    File parchment = new File("parchment.xml");
//
//                    parchment.deleteOnExit();
//
//                    PrintWriter quill = new PrintWriter(parchment);
//
//                    quill.print(message);
//                    quill.close();
//
//                    XmlParser scroll = new XmlParser(parchment);
//                    lufthansa = scroll.parse();
//
//                    PrettyPrinter scribe = new PrettyPrinter(null, false);
//                    scribe.dump(lufthansa);
//                    System.out.println(scribe.getPlottedPrint());
//
//                }
//                catch (ParserException e4)
//                {
//                    error("[AftFlight] Error while parsing the XML file: " + e4.getMessage());
//                    return;
//                }
//            }
////            else if (definition == null) {
////                // Print all dictionary entries
////                message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));
////
////            }
//            //  Project #5 Main Functionality-Pathway II.) Add new Flight!
//            else if (!searchoption)
//            {
//                try
//                {
//                    message = client.addFlightEntry(landing[0], landing[1], landing[2], gate, landing[6], taxi);
//                }
//                catch (Exception e3)
//                {
//                    error("[AftFlight] Error while adding new flight entry to the server: " + e3.getMessage());
//                    return;
//                }
//                //client.addDictionaryEntry(word, definition);
//                //message = Messages.definedWordAs(word, definition);
//                if (printoption)
//                {
//                    for (int idx = 0; idx < print_option_num; idx++)
//                    {
//                        System.out.println(message);
//                        runway.print();
//                    }
//                }
//            }
//
//        } catch (IOException | ParserException e1n2 ) {
//            error("While contacting AftFlight instance, '" + hostName + ":" + port + "', this error occurred: " + e1n2.getMessage());
//            return;
//        }
//
//        //System.out.println(message);
//
//    }
//
//    private static void error( String message )
//    {
//        PrintStream err = System.err;
//        err.println("** " + message);
//    }
//
//    /**
//     * Prints usage information for this program and exits
//     * @param message An error message to print
//     */
//    private static void usage( String message )
//    {
//        PrintStream err = System.err;
//        err.println("** " + message);
//        err.println();
//        err.println("usage: java -jar target/airline-client.jar [options] <args>\n" +
//                "       args are (in this order):\n" +
//                "           airline The name of the airline\n" +
//                "           flightNumber The flight number\n" +
//                "           src Three-letter code of departure airport\n" +
//                "           depart Departure date/time\n" +
//                "           dest Three-letter code of arrival airport\n" +
//                "           arrive Arrival date/time\n" +
//                "       options are (options may appear in any order):");
//        err.println("           -host hostname Host computer on which the server runs\n" +
//                "           -port port Port on which the server is listening\n" +
//                "           -search Search for flights\n" +
//                "           -print Prints a description of the new flight\n" +
//                "           -README Prints a README for this project and exits");
//        err.println();
//    }
//
//    /**
//     * Specific-version of usage() for the -search option, prints usage information for this program and exits
//     * @param message An error message to print
//     */
//    private static void searchUsage( String message )
//    {
//        PrintStream err = System.err;
//        err.println("** " + message);
//        err.println();
//        err.println("\n<!>");
//        err.println("The following usage information is specifically tailored for using the -search option.<!>");
//        err.println("To see the general usage information, please run the program without any command-line arguments or options!");
//        err.println("<!>\n");
////        err.println("\n");
//        err.println("usage: java -jar Project5 [options] <args>\n" +
//                "       args are (in this order - when using -search, you MUST specify only airline-name argument OR all THREE arguments of airline-name, src, & dest):\n" +
//                "           airline The name of the airline\n" +
//                "           src Three-letter code of departure airport\n" +
//                "           dest Three-letter code of arrival airport\n" +
//                "       options are (options may appear in any order):");
//        err.println("           -host hostname Host computer on which the server runs\n" +
//                "           -port port Port on which the server is listening\n" +
//                "           -search Search for flights\n" +
//                "           -print Prints a description of the new flight\n" +
//                "           -README Prints a README for this project and exits");
//        err.println();
//    }
//
//}