package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;

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

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;

/**
 * CS410P [Adv. Java Programming] - Project #5
 * The main class for Project #5 that parses the command line and communicates with the
 * Airline server using REST.
 * usage: java -jar target/airline-client.jar [options] <args>
 *  args are (in this order):
 *      airline The name of the airline
 *      flightNumber The flight number
 *      src Three-letter code of departure airport
 *      depart Departure date/time
 *      dest Three-letter code of arrival airport
 *      arrive Arrival date/time
 *  options are (options may appear in any order):
 *      -host hostname Host computer on which the server runs
 *      -port port Port on which the server is listening
 *      -search Search for flights
 *      -print Prints a description of the new flight
 *      -README Prints a README for this project and exits
 * @author Dan J
 */
public class Project5 {

    public static final String MISSING_ARGS = "Missing command line arguments";

    /**
     * Project #5 Timestamp Formatting:
     * {@code MM/dd/yyyy h:mm a}
     * E.g., "03/09/2023 1:20 pm"
     */
    protected static final String Timestamp_Format = "MM/dd/yyyy h:mm a";

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
            try (InputStream displayme = Project5.class.getResourceAsStream(txtfile)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();
                    int idx = 0;
                    while (display_line != null && idx != 5) // Additional line for Project #4
                    {
                        display_line = display_buffer.readLine();
                        idx++;
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
            try (InputStream displayme = Project5.class.getResourceAsStream(txtfile)) {
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
        Date timestamp;

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

    public static void main(String... args) {

        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);

        List<String> arglist = new LinkedList<>();
        //List<String> validOptions = Arrays.asList("host", "port", "search", "print", "readme");

        int argnum = 0;
        int print_option_num = 0;
        int port = 65537;
        // The highest TCP port number is 65535, so this default value, if not changed, should result in a port-specific error.

        Flight runway = null;
        Airline lufthansa = null;

        //String file_name = null;
        //String pretty_name = null;
        //String xml_name = null;
        String search_name = null;
        String hostName = null;
        String portString = null;
        String word = null;
        String definition = null;

        Boolean printoption = false;
        Boolean searchoption = false;
        Boolean hostport_eitheroption = false;

        if (args.length == 0)
        {
            //displayer(readme_file, 1);
            //System.out.println("The Project #5 command-line interface has been provided above.");
            usage("\"The Project #5 command-line interface has been provided above.\"");
            // Graceful Exit: No arguments passed to the program, thus, command-line interface will be shown using usage(String) & returned.
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
                    // Project #5.) Option V.) -readme
                    case "readme":
                        //displayer(readme_file, 0);
                        usage("CS410P [Adv. Java Programming] Project #5 utilizes the REST API, provided through the AirlineRestClient & AirlineServlet classes - utilizing HTTP requests as a REST-ful Web Service!\nProject #5 can store multiple Airlines, alongside their flights.\nIt can search its dictionary of airline's and containing flights for source & destination airports, search for matching airplane names, pretty-print all available flights, etc..");
                        return;
                        //readme_option_num++;
                        //break;

                    // Project #5.) Option III.) -search
                    case "search":
                        search_name = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (search_name.isEmpty())
                        {
                            System.err.println("Hmm, looks like the -search option was used, but there was no airline name specified!");
                            return;
                        }
                        if (search_name.startsWith("-"))
                        {
                            System.err.println("Hmm, an invalid airline name was detected for the -search option (valid airlines with matching names must exist in the airline dictionary)!");//\nTreating as empty name, creating default Empty Airline!");
                            return;
                        }
                        //xmllist.add(xml_name);
                        //xmloption = true;
                        break;

                    // Project #5.) Option I.) -host
                    case "host":
                        hostName = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (hostName.isEmpty())
                        {
                            System.err.println("Hmm, looks like the -host option was used, but there was no host name specified!");
                            return;
                        }
                        if (hostName.startsWith("-"))
                        {
                            System.err.println("Hmm, an invalid host name was detected for the -host option!");//\nTreating as empty name, creating default Empty Airline!");
                            return;
                        }
                        hostport_eitheroption = true;
                        break;

                    // Project #5.) Option II.) -port
                    case "port":
                        portString = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (portString.isEmpty())
                        {
                            System.err.println("Hmm, looks like the -port option was used, but there was no port number specified!");
                            return;
                        }
                        if (portString.startsWith("-"))
                        {
                            System.err.println("Hmm, an invalid port number was detected for the -port option!");//\nTreating as empty name, creating default Empty Airline!");
                            return;
                        }
                        hostport_eitheroption = true;
                        break;

                    // Project #5.) Option IV.) -print
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

        // Input Validation [Project #5] 1.) - If either -host or -port are specified, then check that both were specified.
        // If not, then error!
        if (hostport_eitheroption)
        {
            if (hostName == null) {
                usage( "Uh oh! Looks like the port was specified, but not the hostname - we need both!" );
                return;

            } else if ( portString == null) {
                usage( "Uh oh! Looks like the hostname was specified, but not the port number - we need both!" );
                return;
            }

            //int port;
            // Input Validation [Project #5] 1.1.) - If both -hostname & -port were specified, then check that the port can be successfully parsed into an valid integer!
            try {
                port = Integer.parseInt( portString );

            } catch (NumberFormatException ex) {
                usage("Port \"" + portString + "\" must be an integer");
                return;
            }

            /*
             * Input Validation [Project #5] 1.1.1) - Check that the port number is within the valid range as defined in the RFC 1700 (by IANA).
             * The range is defined as port numbers <code>0</code> through <code>65536</code>, inclusive.
             * @see <a href="https://tools.ietf.org/html/rfc1700">RFC 1700: Assigned Numbers (1994)</a>
             * @author Reynolds & Postel {@literal <iana@isi.edu>}
             */
            if (port < 0 || port > 65536)
            {
                usage("Hmmm, the specified port-number of '\"" + portString + "\"' was outside of the valid RFC 1700 range for TCP ports, which are 0 through 65536!");
                return;
            }
        }

        //        for (String arg : args) {
//            if (hostName == null) {
//                hostName = arg;
//
//            } else if ( portString == null) {
//                portString = arg;
//
//            } else if (word == null) {
//                word = arg;
//
//            } else if (definition == null) {
//                definition = arg;
//
//            } else {
//                usage("Extraneous command line argument: " + arg);
//            }
//        }

        AirlineRestClient client = new AirlineRestClient(hostName, port);

        String message;
        try {
            if (word == null) {
                // Print all word/definition pairs
                Map<String, String> dictionary = client.getAllDictionaryEntries();
                StringWriter sw = new StringWriter();
                PrettyPrinter pretty = new PrettyPrinter(sw);
                pretty.dump(dictionary);
                message = sw.toString();

            } else if (definition == null) {
                // Print all dictionary entries
                message = PrettyPrinter.formatDictionaryEntry(word, client.getDefinition(word));

            } else {
                // Post the word/definition pair
                client.addDictionaryEntry(word, definition);
                message = Messages.definedWordAs(word, definition);
            }

        } catch (IOException | ParserException ex ) {
            error("While contacting server: " + ex.getMessage());
            return;
        }

        System.out.println(message);
    }

    private static void error( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
    }

    /**
     * Prints usage information for this program and exits
     * @param message An error message to print
     */
    private static void usage( String message )
    {
        PrintStream err = System.err;
        err.println("** " + message);
        err.println();
        err.println("usage: java -jar Project5 [options] <args>\n" +
                "args are (in this order):\n" +
                "airline The name of the airline\n" +
                "flightNumber The flight number\n" +
                "src Three-letter code of departure airport\n" +
                "depart Departure date/time\n" +
                "dest Three-letter code of arrival airport\n" +
                "arrive Arrival date/time\n" +
                "options are (options may appear in any order):");
        err.println("-host hostname Host computer on which the server runs\n" +
                "-port port Port on which the server is listening\n" +
                "-search Search for flights\n" +
                "-print Prints a description of the new flight\n" +
                "-README Prints a README for this project and exits");
        err.println();
    }
}