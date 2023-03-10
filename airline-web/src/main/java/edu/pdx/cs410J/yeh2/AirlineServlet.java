package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

/**
 * This servlet, heavily modified from its original word-to-dictionary servlet-form, ultimately provides a REST API for working with multiple
 * <code>Airline</code>s mapped to their airline-name individually via <code>HashMap</code>ping, that each will contain their respective <code>Flight</code>s.
 * Coined as "AftFlight" (airline-flight dictionary, sort-of), containing <code>Airline</code>s (each mapped to their airline-names) and their <code>Flight</code>s.
 * @see Project5
 * @see <a href="https://foreflight.com/">IRL App Reference</a>
 * Details:
 *  – <code>http://host:port/airline/flights?airline=name</code>
 *      * GET returns all flights in the airline formatted using the XmlDumper
 *      * POST creates a new flight from the {@code HTTP request} parameters airline, flightNumber,
 *      src, depart, dest, and arrive. If the airline does not exist, a new one should be
 *      created.
 *  – <code>http://host:port/airline/flights?airline=name&src=airport&dest=airport</code>
 *      * GET returns all of given airline’s flights (in the format used by XmlDumper) that originate
 *      at the src airport and terminate at the dest airport.
 *  – Note that, unlike previous assignments, the web application must support multiple airlines.
 *
 */
public class AirlineServlet extends HttpServlet {
    //static final String WORD_PARAMETER = "word";
    static final String AIRLINE_PARAMETER = "airline";
    //static final String DEFINITION_PARAMETER = "definition";
    //static final String FLIGHT_PARAMETER = "flight";
    static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
    static final String SRC_PARAMETER = "src";
    static final String DEPART_PARAMETER = "depart";
    static final String DEST_PARAMETER = "dest";
    static final String ARRIVE_PARAMETER = "arrive";

    //private final Map<String, String> aftflight = new HashMap<>();
    private final Map<String, Airline> aftflight = new HashMap<>();

    /**
     * Handles an {@code HTTP GET} request from a client by writing the <code>Flight</code>s of the
     * airline-name matching <code>Airline</code> specified in the <code>airline</code> {@code HTTP} parameter to the {@code HTTP} response.
     * If the <code>airline</code> parameter is not specified, then the <code>missingRequiredParameter</code> function will be called!
     * @param request The {@code HTTP request}!
     * @param response The {@code HTTP response}!
     * @throws IOException If there is a problem writing the {@code HTTP response}!
     */
    @Override
    protected void doGet( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/xml" );

        String concorde = getParameter( AIRLINE_PARAMETER, request );
        // In the same naming-themery scheme of 'lufthansa', but for a plane that is super duper fast - via the speed of the Inter(WW)Webz
        String src = getParameter( SRC_PARAMETER, request );
        String dest = getParameter( DEST_PARAMETER, request );

        // Input Validation [Project #5] 5.) - If airline-name is not specified, use the <code>missingRequiredParameter</code> function!
        if (concorde == null)
        {
            missingRequiredParameter( response, AIRLINE_PARAMETER );
            return;
        }

        // Input Validation [Project #5] 6.1.) - If both src and dest are blank (but not airline-name), then infer Valid Case (for -search) I.): GET all flights of matching-name airline
        if (src == null && dest == null)
        {
            writeFlights(concorde, response);
            //writeSpecificDefinition(word, response);
        }
        // Input Validation [Project #5] 6.2.) - If only one of either src or dest are blank (but not airline-name), identify which one is null, then use the <code>missingRequiredParameter</code> function!
        else if (src != null || dest != null)
        {
            // Input Validation [Project #5] 6.2.1.) - If src is null, then use the <code>missingRequiredParameter</code> function to request the SRC airport-code parameter!
            if (src == null)
            {
                missingRequiredParameter( response, SRC_PARAMETER );
                return;
            }
            // Input Validation [Project #5] 6.2.2.) - If dest is null, then use the <code>missingRequiredParameter</code> function to request the DEST airport-code parameter!
            else if (dest == null)
            {
                missingRequiredParameter( response, DEST_PARAMETER );
                return;
            }
            //writeDefinition(word, response);
        }
        // Input Validation [Project #5] 6.3.) - If both src and dest are not blank, then infer Valid Case (for -search) II.): GET all flights of matching-name airline that match src & dest airport-codez
        else if (src != null && dest != null)
        {
            writeSpecificFlights(concorde, src, dest, response);
        }

//        if (word != null) {
//            //writeDefinition(word, response);
//            writeSpecificDefinition(word, response);
//        } else {
//            writeAllAftFlightEntries(response);
//        }
    }

    /**
     * Handles an {@code HTTP POST} request by creating a new <code>Flight</code> & storing the aftflight entry for the
     * <code>airline</code> parameter (name) - with that aforementioned <code>Flight</code>, being based on the <code>flightNumber</code>, <code>src</code>, <code>depart</code>, <code>dest</code>, and <code>arrive</code> request-parameters.
     * It writes the newly added <code>Flight</code>-based aftflight entry to the HTTP response.
     * @param request The {@code HTTP request}!
     * @param response The {@code HTTP response}!
     * @throws IOException If there is a problem writing the {@code HTTP response}!
     */
    @Override
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws IOException
    {
        response.setContentType( "text/xml" );


        String airline = getParameter(AIRLINE_PARAMETER, request );
        if (airline == null)
        {
            missingRequiredParameter( response, AIRLINE_PARAMETER );
            return;
        }

        String flightNumber = getParameter(FLIGHTNUMBER_PARAMETER, request );
        if (flightNumber == null)
        {
            missingRequiredParameter( response, FLIGHTNUMBER_PARAMETER );
            return;
        }

        String src = getParameter(SRC_PARAMETER, request );
        if (src == null)
        {
            missingRequiredParameter( response, SRC_PARAMETER );
            return;
        }

        String depart = getParameter(DEPART_PARAMETER, request );
        if (depart == null)
        {
            missingRequiredParameter( response, DEPART_PARAMETER );
            return;
        }

        String dest = getParameter(DEST_PARAMETER, request );
        if (dest == null)
        {
            missingRequiredParameter( response, DEST_PARAMETER );
            return;
        }

        String arrive = getParameter(ARRIVE_PARAMETER, request );
        if (arrive == null)
        {
            missingRequiredParameter( response, ARRIVE_PARAMETER );
            return;
        }

        //this.aftflight.put(word, definition);
        PrintWriter pw = response.getWriter();
        Airline lufthansa = this.aftflight.get(airline);

        if (lufthansa == null)
        {
            lufthansa = new Airline(airline);
            pw.println("[AftFlight] New airline has been created with the following name: '" + airline + ".'");
            this.aftflight.put(airline, lufthansa);
        }
        Flight runway = null;
        try
        {
            runway = new Flight(flightNumber, src, depart, dest, arrive);
        }
        catch (ParseException e1)
        {
//            pw.println("[AftFlight] Error: " + e1.getMessage());
//            pw.flush();
//            response.setStatus( HttpServletResponse.SC_BAD_REQUEST);
            // String newFlightError = String.format("[AftFlight] Error regarding parameter: \"%s\" !", e1.getMessage());
            errorRequiredParameter(response, e1.getMessage());
            return;
        }
        //pw.println(Messages.definedWordAs(word, airline));

        lufthansa.addFlight(runway);

        pw.println("[AftFlight] New flight added to airline '" + airline + "'!");
        pw.flush();

        response.setStatus( HttpServletResponse.SC_OK);
    }

    /**
     * Handles an {@code HTTP DELETE} request by removing all aftflight entries.
     * This behavior is exposed for testing purposes only.
     * It's probably not something that you'd want a real application to expose.
     * @param request The {@code HTTP request}!
     * @param response The {@code HTTP response}!
     * @throws IOException If there is a problem writing the {@code HTTP response}!
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/xml");

        this.aftflight.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allAftFlightEntriesDeleted());
        pw.flush();

        response.setStatus(HttpServletResponse.SC_OK);

    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void missingRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = Messages.missingRequiredParameter(parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes an error message about a missing parameter to the HTTP response.
     * The text of the error message is created by {@link Messages#missingRequiredParameter(String)}
     */
    private void errorRequiredParameter( HttpServletResponse response, String parameterName )
            throws IOException
    {
        String message = String.format("[AftFlight] Error regarding parameter: \"%s\" !", parameterName);
        response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, message);
    }

    /**
     * Writes the flights (into {@code XML} formatting) of the given airline-name to the HTTP response.
     * @param concorde The flight that should have its definition(s) to the HTTP response!
     * @param response The {@code HTTP response} to write the definition(s) to!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link XmlDumper}
     * @see Project5
     */
    private void writeFlights(String concorde, HttpServletResponse response) throws IOException {
        Airline lufthansa = this.aftflight.get(concorde);
        PrintWriter pw = response.getWriter();

        if (lufthansa == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find the airline named: '" + concorde + "'!");

        }
        else
        {
            //PrintWriter pw = response.getWriter();

            //Map<String, Airline> concordeFlights = Map.of(concorde, lufthansa);
            XmlDumper dumper = new XmlDumper(pw, null, null);
            dumper.dump(lufthansa);
            //pw.println(XmlDumper.dumpMail(lufthansa));

            response.setStatus(HttpServletResponse.SC_OK);
        }

        pw.flush();
    }

    /**
     * A modified version of writeDefinition that writes flights (into {@code XML} formatting) of airlines, that match src & dest airport-codes from those matching the given airline-name (airline-name) to the HTTP response.
     * @param concorde The airline that should have its src & dest matching specific flights written to the HTTP response!
     * @param response The {@code HTTP response} to write the flight(s) to!
     * @param src The src airport-code that should match the src airport-code of the flight(s) to be written to the HTTP response!
     * @param dest The dest airport-code that should match the dest airport-code of the flight(s) to be written to the HTTP response!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link XmlDumper}
     * @see Project5
     */
    private void writeSpecificFlights(String concorde, String src, String dest, HttpServletResponse response) throws IOException {
        Airline lufthansa = this.aftflight.get(concorde);
        PrintWriter pw = response.getWriter();

        if (lufthansa == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find the airline named: '" + concorde + "'!");

        }
        else
        {
            //PrintWriter pw = response.getWriter();

            //Map<String, Airline> concordeFlights = Map.of(concorde, lufthansa);
            XmlDumper dumper = new XmlDumper(pw, src, dest);
            dumper.dump(lufthansa);
            //pw.println(XmlDumper.dumpMail(lufthansa));

            response.setStatus(HttpServletResponse.SC_OK);
        }

        pw.flush();
    }

    /**
     * Writes all aftflight entries to the HTTP response.
     *
     * @param response The {@code HTTP response} to write the aftflight entries to!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link TextDumper}
     */
    private void writeAllAftFlightEntries(HttpServletResponse response ) throws IOException
    {
        PrintWriter pw = response.getWriter();
        TextDumper dumper = new TextDumper(pw);
        dumper.dump(aftflight);

        response.setStatus( HttpServletResponse.SC_OK );
    }

    /**
     * Returns the value of the {@code HTTP request} parameter with the given name.
     *
     * @param name The name of the parameter!
     * @param request The {@code HTTP request} source the parameterz from!
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
    Airline getAirline(String lufthansa) {
        return this.aftflight.get(lufthansa);
    }
}
