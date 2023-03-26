package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;

import static edu.pdx.cs410J.web.HttpRequestHelper.Response;
import static edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import static java.net.HttpURLConnection.HTTP_OK;

/**
 * A helper class for accessing the rest client.  Note that this class provides
 * an example of how to make gets and posts to a URL.  You'll need to change it
 * to do something other than just send AftFlight entries.
 * Details:
 *  – <code>{@literal http://host:port/airline/flights?airline=name}</code>
 *      * GET returns all flights in the airline formatted using the XmlDumper
 *      * POST creates a new flight from the {@code HTTP request} parameters airline, flightNumber,
 *      src, depart, dest, and arrive. If the airline does not exist, a new one should be
 *      created.
 *  – <code>{@literal http://host:port/airline/flights?airline=name&src=airport&dest=airport}</code>
 *      * GET returns all of given airline’s flights (in the format used by XmlDumper) that originate
 *      at the src airport and terminate at the dest airport.
 *  – Note that, unlike previous assignments, the web application must support multiple airlines.
 *
 */
public class AirlineRestClient extends HttpRequestHelper
{
    private static final String WEB_APP = "airline";
    private static final String SERVLET = "flights";

    static final String AIRLINE_PARAMETER = "airline";
    static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
    static final String SRC_PARAMETER = "src";
    static final String DEPART_PARAMETER = "depart";
    static final String DEST_PARAMETER = "dest";
    static final String ARRIVE_PARAMETER = "arrive";

    private final HttpRequestHelper http;

    private final String url;

    /**
     * Creates a client to the AftFlight Airline REST service running on the given host and port
     * @param hostName The name of the host
     * @param port The port
     */
    public AirlineRestClient( String hostName, int port )
    {
        super(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET));
        this.url = String.format( "http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET );
        //this.url = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET));
        //this.http = new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET));

        // Note to Self: look into {@link https://ordina-jworks.github.io/security/2019/08/14/Using-Lets-Encrypt-Certificates-In-Java.html#automating-the-renewal-process}
        // for HTTPS automagically renewed LetsEncrypt SSL certificates (would have to work flawlessly universally OS-wise to include in Project, though)

        http = null;
    }

//    /**
//     * Creates a client to the AftFlight Airline REST service running on the given host and port
//     * @param hostName The name of the host
//     * @param port The port
//     */
//    public AirlineRestClient( String urlString )
//    {
//        super(urlString = String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET));
//
//        this.url = urlString;
//
//        this.http = new HttpRequestHelper(String.format("http://%s:%d/%s/%s", hostName, port, WEB_APP, SERVLET));
//
//        // Note to Self: look into {@link https://ordina-jworks.github.io/security/2019/08/14/Using-Lets-Encrypt-Certificates-In-Java.html#automating-the-renewal-process}
//        // for HTTPS automagically renewed LetsEncrypt SSL certificates (would have to work flawlessly universally OS-wise to include in Project, though)
//
//    }

    @VisibleForTesting
    AirlineRestClient(HttpRequestHelper http) {
        super(http.toString());
        this.http = http;
        this.url = http.toString();
    }

    /**
     * Returns ({@code HTTP GET}) all AftFlight entries from the server
     */
    public Map<String, String> getAllDictionaryEntries() throws IOException, ParserException {
        Response response = http.get(Map.of());
        throwExceptionIfNotOkayHttpStatus(response);

        TextParser parser = new TextParser(new StringReader(response.getContent()));
        return parser.parse();
    }

    /**
     * Returns ({@code HTTP GET}) the parameters of the <code>Airline</code>(s) that match for the given <code>Airline</code> name.
     * @param airline The name of the <code>Airline</code> to be searched for, to be made for the {@code HTTP GET} request!
     * @param src (Optional) The source airport code to be searched for, to be made for the {@code HTTP GET} request!
     * @param dest (Optional) The destination airport code to be searched for, to be made for the {@code HTTP GET} request!
     * @throws IOException If there is an IO-specific error with the {@code HTTP GET} request parameters!
     * @throws ParserException If there is a parsing-specific error with the {@code HTTP GET} request parameters!
     */
    public String getFlightEntries(String airline, String src, String dest) throws IOException, ParserException {
        //Response response = http.get(Map.of(AIRLINE_PARAMETER, airline));
        Response response = null;//get(this.url, Map.of("airline", airline, "src", src, "dest", dest));

        if (src == null || dest == null)
        {
            response = get(Map.of(AIRLINE_PARAMETER, airline));
        }
        else
        {
            response = get(Map.of(AIRLINE_PARAMETER, airline, SRC_PARAMETER, src, DEST_PARAMETER, dest));
        }

        throwExceptionIfNotOkayHttpStatus(response);
        String test = response.getContent();
        return test;
        //String content = response.getContent();

        //TextParser parser = new TextParser(new StringReader(content));
        //return parser.parse().get(word);
    }

    /**
     * Returns ({@code HTTP GET}) the parameters for the <code>Airline</code>(s) that match for the given <code>Airline</code> & flights that match BOTH <code>src</code> & <code>dest</code> airport-codes.
     * @param airline The name of the <code>Airline</code> to be searched for, to be made for the {@code HTTP GET} request!
     * @param src The source airport code to be searched for, to be made for the {@code HTTP GET} request!
     * @param dest The destination airport code to be searched for, to be made for the {@code HTTP GET} request!
     * @throws IOException If there is an IO-specific error with the {@code HTTP GET} request parameters!
     * @throws ParserException If there is a parsing-specific error with the {@code HTTP GET} request parameters!
     */
//    public String getSpecificFlightEntries(String airline, String src, String dest) throws IOException, ParserException {
//        Response response = http.get(Map.of(AIRLINE_PARAMETER, airline, SRC_PARAMETER, src, DEST_PARAMETER, dest));
//        throwExceptionIfNotOkayHttpStatus(response);
//        return response.getContent();
////        String content = response.getContent();
////
////        TextParser parser = new TextParser(new StringReader(content));
////        return parser.parse().get(word);
//    }

    /**
     * Adds ({@code HTTP POST}) a new AftFlight entry to the server
     * @param airline The name of the <code>Airline</code> for the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @param flightNumber The flight number of the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @param src The source airport code of the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @param depart The departure date/time of the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @param dest The destination airport code of the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @param arrive The arrival date/time of the new <code>Flight</code>, to be made for the {@code HTTP POST} request!
     * @throws IOException If there is an error with the {@code HTTP POST} request parameters!
     */
    public String addFlightEntry(String airline, String flightNumber, String src, String depart, String dest, String arrive) throws IOException {
        Response response = post(Map.of(AIRLINE_PARAMETER, airline, FLIGHTNUMBER_PARAMETER, flightNumber, SRC_PARAMETER, src, DEPART_PARAMETER, depart, DEST_PARAMETER, dest, ARRIVE_PARAMETER, arrive));
        throwExceptionIfNotOkayHttpStatus(response);
        return response.getContent();
    }

    public void removeAllDictionaryEntries() throws IOException {
        Response response = delete(Map.of());
        throwExceptionIfNotOkayHttpStatus(response);
    }

    private void throwExceptionIfNotOkayHttpStatus(Response response) {
        int code = response.getHttpStatusCode();
        if (code != HTTP_OK) {
            String message = response.getContent();
            throw new RestException(code, message);
        }
    }

    static class AirlineRestException extends RuntimeException
    {
        AirlineRestException(int httpstatus)
        {
            super("Beep boop, HTTP Status: " + httpstatus);
        }
    }

}
