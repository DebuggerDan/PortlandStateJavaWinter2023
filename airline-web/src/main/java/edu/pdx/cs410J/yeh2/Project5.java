package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.Map;

/**
 * CS410P [Adv. Java Programming] - Project #5
 * The main class for Project #5 that parses the command line and communicates with the
 * Airline server using REST.
 *
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

    public static void main(String... args) {
        String hostName = null;
        String portString = null;
        String word = null;
        String definition = null;

        for (String arg : args) {
            if (hostName == null) {
                hostName = arg;

            } else if ( portString == null) {
                portString = arg;

            } else if (word == null) {
                word = arg;

            } else if (definition == null) {
                definition = arg;

            } else {
                usage("Extraneous command line argument: " + arg);
            }
        }

        if (hostName == null) {
            usage( MISSING_ARGS );
            return;

        } else if ( portString == null) {
            usage( "Missing port" );
            return;
        }

        int port;
        try {
            port = Integer.parseInt( portString );

        } catch (NumberFormatException ex) {
            usage("Port \"" + portString + "\" must be an integer");
            return;
        }

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