package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.AirportNames;

import javax.servlet.ServletException;
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
    private final Map<String, Airline> aftflight = new HashMap<String, Airline>();

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
        response.setContentType("application/xml");

        String airline = getParameter( AIRLINE_PARAMETER, request );
        // In the same naming-themery scheme of 'lufthansa', but for a plane that is super duper fast - via the speed of the Inter(WW)Webz
        String src = getParameter( SRC_PARAMETER, request );
        String dest = getParameter( DEST_PARAMETER, request );

        // Input Validation [Project #5] 5.) - If airline-name is not specified, use the <code>missingRequiredParameter</code> function!
        if (airline == null)
        {
            missingRequiredParameter( response, AIRLINE_PARAMETER );
            return;
        }

        // Input Validation [Project #5] 6.1.) - If both src and dest are blank (but not airline-name), then infer Valid Case (for -search) I.): GET all flights of matching-name airline
        if (src == null && dest == null)
        {
            writeFlights(airline, response);
            //writeSpecificDefinition(word, response);
        }
        // Input Validation [Project #5] 6.2.) - If only one of either src or dest are blank (but not airline-name), identify which one is null, then use the <code>missingRequiredParameter</code> function!
        else if (src == null)
        {
            // Input Validation [Project #5] 6.2.1.) - If src is null, then use the <code>missingRequiredParameter</code> function to request the SRC airport-code parameter!

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

        // Input Validation [Project #5] 6.3.) - If both src and dest are not blank, then infer Valid Case (for -search) II.): GET all flights of matching-name airline that match src & dest airport-codez
        else if (!src.isEmpty() && !dest.isEmpty())
        {
            // Input Validation [Project #5] 6.3.1.) - If both src and dest are not blank, then infer Valid Case (for -search) II.): GET all flights of matching-name airline that match src & dest airport-codez
            //writeSpecificFlights(airline, src, dest, response);
            /*
             * Input-Validation #3 {from Project #4}: Checking both src & dest airport codes is not 3-digits in characters.
             * src should equal src & dest should equal dest.
             */

            if (src.length() != 3)
            {
                errorRequiredParameter(response, SRC_PARAMETER);

                // Graceful Exit: If the source airport code is not 3-digits.
                return;
            }
            if (dest.length() != 3)
            {
                errorRequiredParameter(response, DEST_PARAMETER);
                // Graceful Exit: If the destination airport code is not 3-digits.
                return;
            }

            // Input-Validation #2b & #2c {from Project #4}: Check if both src & dest airport codes include numbers, if so, then error!.
            char[] srcCodeTest = src.toCharArray();
            char[] destCodeTest = dest.toCharArray();

            for (char srcTest : srcCodeTest)
            {
                if (Character.isDigit(srcTest))
                {
                    //usage("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: " + src);
                    errorRequiredParameter(response, SRC_PARAMETER);
                    // Graceful Exit: If the source airport code has numbers.
                    return;
                }
            }

            for (char destTest : destCodeTest)
            {
                if (Character.isDigit(destTest))
                {
                    //usage("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: " + dest);
                    errorRequiredParameter(response, DEST_PARAMETER);
                    // Graceful Exit: If the destination airport code has numbers.
                    return;
                }
            }

            String srcTest = src.toUpperCase();
            String destTest = dest.toUpperCase();

            // Input-Validation #6 {from Project #4}: Check the AirportNames database if the airport codes actually exist!
            if (AirportNames.getName(srcTest) == null)
            {
                //usage("Uh oh, looks like the source airport code, '" + src + "', was not found in our airport-names database!");
                errorRequiredParameter(response, SRC_PARAMETER);
                // Graceful Exit: If the source airport code was not found in AirportNames!
                return;
            }
            if (AirportNames.getName(destTest) == null)
            {
                //usage("Uh oh, looks like the destination airport code, '" + dest + "', was not found in our airport-names database!");
                errorRequiredParameter(response, DEST_PARAMETER);
                // Graceful Exit: If the destination airport code was not found in AirportNames!
                return;
            }
            //writeSpecificFlights(airline, src, dest, response);
            //Airline concorde = null;
            Flight runway = null;
            try
            {
                runway = new Flight("999", src, "1/01/2000 12:00 am", dest, "1/02/2000 1:00 am");
            }
            catch (ParseException e2)
            {
                errorRequiredParameter(response, e2.getMessage());
            }

            writeSpecificFlights(airline, src, dest, response);
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
    protected void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException
    {
        response.setContentType("text/plain");

        String airline = getParameter(AIRLINE_PARAMETER, request );
        String flightNumber = getParameter(FLIGHTNUMBER_PARAMETER, request );
        String src = getParameter(SRC_PARAMETER, request );
        String depart = getParameter(DEPART_PARAMETER, request );
        String dest = getParameter(DEST_PARAMETER, request );
        String arrive = getParameter(ARRIVE_PARAMETER, request );
        
        if (airline == null)
        {
            missingRequiredParameter( response, AIRLINE_PARAMETER );
            return;
        }


        if (flightNumber == null)
        {
            missingRequiredParameter( response, FLIGHTNUMBER_PARAMETER );
            return;
        }


        if (src == null)
        {
            missingRequiredParameter( response, SRC_PARAMETER );
            return;
        }


        if (depart == null)
        {
            missingRequiredParameter( response, DEPART_PARAMETER );
            return;
        }


        if (dest == null)
        {
            missingRequiredParameter( response, DEST_PARAMETER );
            return;
        }


        if (arrive == null)
        {
            missingRequiredParameter( response, ARRIVE_PARAMETER );
            return;
        }

        //this.aftflight.put(word, definition);
        Airline lufthansa = this.aftflight.get(airline);
        PrintWriter pw = response.getWriter();

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
        pw.close();

        this.aftflight.put(airline, lufthansa);

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
        response.setContentType("application/xml");

        this.aftflight.clear();

        PrintWriter pw = response.getWriter();
        pw.println(Messages.allAftFlightEntriesDeleted());
        pw.flush();
        pw.close();

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
     * @param airline The flight that should have its definition(s) to the HTTP response!
     * @param response The {@code HTTP response} to write the definition(s) to!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link XmlDumper}
     * @see Project5
     */
    private void writeFlights(String airline, HttpServletResponse response) throws IOException {
        Airline lufthansa = this.aftflight.get(airline);

        if (lufthansa == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find the airline named: '" + airline + "'!");

        }
        else
        {
            //PrintWriter pw = response.getWriter();
            PrintWriter pw = response.getWriter();
            //Map<String, Airline> airlineFlights = Map.of(airline, lufthansa);
            XmlDumper dumper = new XmlDumper(pw, null, null);
            dumper.dump(lufthansa);
            //pw.println(XmlDumper.dumpMail(lufthansa));
            pw.flush();
            pw.close();
            response.setStatus(HttpServletResponse.SC_OK);
        }

        //pw.flush();
    }

    /**
     * A modified version of writeDefinition that writes flights (into {@code XML} formatting) of airlines, that match src & dest airport-codes from those matching the given airline-name (airline-name) to the HTTP response.
     * @param airline The airline that should have its src & dest matching specific flights written to the HTTP response!
     * @param response The {@code HTTP response} to write the flight(s) to!
     * @param src The src airport-code that should match the src airport-code of the flight(s) to be written to the HTTP response!
     * @param dest The dest airport-code that should match the dest airport-code of the flight(s) to be written to the HTTP response!
     * @throws IOException If there is an error writing to the HTTP response!
     * The text of the message is formatted with {@link XmlDumper}
     * @see Project5
     */
    private void writeSpecificFlights(String airline, String src, String dest, HttpServletResponse response) throws IOException {
        Airline lufthansa = this.aftflight.get(airline);
        PrintWriter pw = response.getWriter();
        if (src != null && dest != null)
        {
            String localSrc = new String(src);
            String localDest = new String(dest);
        }
        else if (src == null)
        {
            //response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing src parameter for airline: '" + airline + "'!");
            missingRequiredParameter(response, SRC_PARAMETER);
        }
        else if (dest == null)
        {
            //response.sendError(HttpServletResponse.SC_NOT_FOUND, "Missing dest parameter for airline: '" + airline + "'!");
            missingRequiredParameter(response, DEST_PARAMETER);
        }
        if (lufthansa == null)
        {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find the airline named: '" + airline + "'!");

        }
        else
        {
            //PrintWriter pw = response.getWriter();

            //Map<String, Airline> airlineFlights = Map.of(airline, lufthansa);
            XmlDumper dumper = new XmlDumper(pw, src, dest);
            dumper.dump(lufthansa);
            //pw.println(XmlDumper.dumpMail(lufthansa));
            pw.flush();
            pw.close();
            response.setStatus(HttpServletResponse.SC_OK);
        }

        //pw.flush();
    }

//    /**
//     * Writes all aftflight entries to the HTTP response.
//     *
//     * @param response The {@code HTTP response} to write the aftflight entries to!
//     * @throws IOException If there is an error writing to the HTTP response!
//     * The text of the message is formatted with {@link TextDumper}
//     */
//    private void writeAllAftFlightEntries(HttpServletResponse response ) throws IOException
//    {
//        PrintWriter pw = response.getWriter();
//        TextDumper dumper = new TextDumper(pw);
//        dumper.dump(aftflight);
//
//        response.setStatus( HttpServletResponse.SC_OK );
//    }

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
