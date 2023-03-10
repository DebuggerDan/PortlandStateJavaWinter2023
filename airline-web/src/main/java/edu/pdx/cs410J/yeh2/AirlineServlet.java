package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet ultimately provides a REST API for working with an
 * <code>Airline</code>.  However, in its current state, it is an example
 * of how to use HTTP and Java servlets to store simple dictionary of words
 * and their definitions.
 * Details:
 *  – <code>http://host:port/airline/flights?airline=name</code>
 *      * GET returns all flights in the airline formatted using the XmlDumper
 *      * POST creates a new flight from the HTTP request parameters airline, flightNumber,
 *      src, depart, dest, and arrive. If the airline does not exist, a new one should be
 *      created.
 *  – <code>http://host:port/airline/flights?airline=name&src=airport&dest=airport</code>
 *      * GET returns all of given airline’s flights (in the format used by XmlDumper) that originate
 *      at the src airport and terminate at the dest airport.
 *  – Note that, unlike previous assignments, the web application must support multiple airlines.
 *
 */
public class AirlineServlet extends HttpServlet {
    static final String WORD_PARAMETER = "word";
    //static final String DEFINITION_PARAMETER = "definition";
    static final String AIRLINE_PARAMETER = "airline";

    static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
    static final String SRC_PARAMETER = "src";
    static final String DEPART_PARAMETER = "depart";
    static final String DEST_PARAMETER = "dest";
    static final String ARRIVE_PARAMETER = "arrive";

    //private final Map<String, String> dictionary = new HashMap<>();
    private final Map<String, Airline> dictionary = new HashMap<>();

    /**
     * Handles an HTTP GET request from a client by writing the definition of the
     * word specified in the "word" HTTP parameter to the HTTP response.  If the
     * "word" parameter is not specified, all entries in the dictionary
     * are written to the HTTP response.
     * @param request The HTTP request!
     * @param response The HTTP response!
     * @throws IOException If there is a problem writing the HTTP response!
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String word = getParameter( WORD_PARAMETER, request );
        if (word != null) {
            writeDefinition(word, response);

        } else {
            writeAllDictionaryEntries(response);
        }
    }

    /**
     * Handles an HTTP POST request by storing the dictionary entry for the
     * "word" and "definition" request parameters.  It writes the dictionary
     * entry to the HTTP response.
     * @param request The HTTP request!
     * @param response The HTTP response!
     * @throws IOException If there is a problem writing the HTTP response!
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/plain" );

        String word = getParameter(WORD_PARAMETER, request );
        if (word == null)
        {
            missingRequiredParameter(response, WORD_PARAMETER);
            return;
        }

        String airline = getParameter(AIRLINE_PARAMETER, request );
        if ( airline == null)
        {
            missingRequiredParameter( response, AIRLINE_PARAMETER );
            return;
        }

        String flightNumber = getParameter(FLIGHTNUMBER_PARAMETER, request );
        if ( flightNumber == null)
        {
            missingRequiredParameter( response, FLIGHTNUMBER_PARAMETER );
            return;
        }

        String src = getParameter(SRC_PARAMETER, request );
        if ( src == null)
        {
            missingRequiredParameter( response, SRC_PARAMETER );
            return;
        }

        String depart = getParameter(DEPART_PARAMETER, request );
        if ( depart == null)
        {
            missingRequiredParameter( response, DEPART_PARAMETER );
            return;
        }

        String dest = getParameter(DEST_PARAMETER, request );
        if ( dest == null)
        {
            missingRequiredParameter( response, DEST_PARAMETER );
            return;
        }

        String arrive = getParameter(ARRIVE_PARAMETER, request );
        if ( arrive == null)
        {
            missingRequiredParameter( response, ARRIVE_PARAMETER );
            return;
        }

        //this.dictionary.put(word, definition);
        PrintWriter pw = response.getWriter();
        Airline lufthansa = this.dictionary.get(airline);

        if (lufthansa == null)
        {
            lufthansa = new Airline(airline);
            pw.println("New airline has been created with the following name: '" + airline + ".'");
        }

        pw.println(Messages.definedWordAs(word, airline));
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an HTTP DELETE request by removing all dictionary entries.  This
     * behavior is exposed for testing purposes only.  It's probably not
     * something that you'd want a real application to expose.
     * @param request The HTTP request!
     * @param response The HTTP response!
     * @throws IOException If there is an error writing to the HTTP response!
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");

        this.dictionary.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allDictionaryEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     *
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the definition of the given word to the HTTP response.
     * @param word The word that should have its definition(s) to the HTTP response!
     * @param response The HTTP response to write the definition(s) to!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeDefinition(String word, HttpServletResponse response) throws IOException {
        Airline lufthansa = this.dictionary.get(word);

        if (lufthansa == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find airline named: '" + word + "'!");

        } else {
            PrintWriter pw = response.getWriter();

            Map<String, Airline> wordDefinition = Map.of(word, lufthansa);
            TextDumper dumper = new TextDumper(pw);
            dumper.dump(wordDefinition);

            response.setStatus(HttpServletResponse.SC_OK);
        }
    }

    /**
     * Writes all dictionary entries to the HTTP response.
     *
     * @param response The HTTP response to write the dictionary entries to!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeAllDictionaryEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(dictionary);

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the HTTP request parameter with the given name.
     *
     * @param name The name of the parameter!
     * @param request The HTTP request source the parameterz from!
     * @return <code>null</code> if the value of the parameter is
     *         <code>null</code> or is the empty string
     */
    private String getParameter(String name, HttpServletRequest request) {
        String value = request.getParameter(name);
        if (value == null || "".equals(value)) {
            return null;

        } else {
            return value;
        }
    }

    @VisibleForTesting
    Airline getDefinition(String lufthansa) {
        return this.dictionary.get(lufthansa);
    }
}
