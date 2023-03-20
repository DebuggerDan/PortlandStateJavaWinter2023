package edu.pdx.cs410J.yeh2;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.*;

/**
 * A unit test for the {@link AirlineServlet}.  It uses mockito to
 * provide mock http requests and responses.
 */
public class AirlineServletTest {

  static final String AIRLINE_PARAMETER = "airline";
  //static final String DEFINITION_PARAMETER = "definition";
  //static final String FLIGHT_PARAMETER = "flight";
  static final String FLIGHTNUMBER_PARAMETER = "flightNumber";
  static final String SRC_PARAMETER = "src";
  static final String DEPART_PARAMETER = "depart";
  static final String DEST_PARAMETER = "dest";
  static final String ARRIVE_PARAMETER = "arrive";
  static final private AirlineServlet servlet = new AirlineServlet();

  @Test
  public void initiallyServletContainsNoDictionaryEntries() throws IOException {
    //AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    PrintWriter pw = mock(PrintWriter.class);

    when(response.getWriter()).thenReturn(pw);

    servlet.doGet(request, response);

    // Nothing is written to the response's PrintWriter
    verify(pw, never()).println(anyString());
    //verify(response).setStatus(HttpServletResponse.SC_OK);
    verify(response).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "[AftFlight] The required parameter \"airline\" is missing");
  }

  @Test
  public void addOneWordToDictionary() throws IOException, ServletException {
    //AirlineServlet servlet = new AirlineServlet();

    //String word = "TEST WORD";
    //String definition = "TEST DEFINITION";
    String airline = "Lufthansa";
    String flightNumber = "69";
    String src = "PDX";
    String depart = "3/09/2023 3:35 am";
    String dest = "SAN";
    String arrive = "3/10/2023 4:47 am";

    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);
    when(request.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);
    when(request.getParameter(FLIGHTNUMBER_PARAMETER)).thenReturn(flightNumber);
    when(request.getParameter(SRC_PARAMETER)).thenReturn(src);
    when(request.getParameter(DEPART_PARAMETER)).thenReturn(depart);
    when(request.getParameter(DEST_PARAMETER)).thenReturn(dest);
    when(request.getParameter(ARRIVE_PARAMETER)).thenReturn(arrive);

    // Use a StringWriter to gather the text from multiple calls to println()
    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response.getWriter()).thenReturn(pw);

    servlet.doPost(request, response);
    //servlet.doGet(request, response);

    //assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response).setStatus(statusCode.capture());

    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    assertThat(servlet.getAirline(airline).getName(), equalTo(airline));
  }

  @Test
  public void TestDictionary() throws IOException, ServletException {
    //AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request3 = mock(HttpServletRequest.class);
    HttpServletResponse response3 = mock(HttpServletResponse.class);

//    String word = "TEST WORD";
//    String definition = "TEST DEFINITION";
    String airline = "Lufthansa";
    String flightNumber = "69";
    String src = "PDX";
    String depart = "3/09/2023 3:35 am";
    String dest = "SAN";
    String arrive = "3/10/2023 4:47 am";

    when(request3.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);
    when(request3.getParameter(FLIGHTNUMBER_PARAMETER)).thenReturn(flightNumber);
    when(request3.getParameter(SRC_PARAMETER)).thenReturn(src);
    when(request3.getParameter(DEPART_PARAMETER)).thenReturn(depart);
    when(request3.getParameter(DEST_PARAMETER)).thenReturn(dest);
    when(request3.getParameter(ARRIVE_PARAMETER)).thenReturn(arrive);

    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response3.getWriter()).thenReturn(pw);

    servlet.doPost(request3, response3);

    HttpServletRequest request4 = mock(HttpServletRequest.class);
    HttpServletResponse response4 = mock(HttpServletResponse.class);

    when(request4.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);

    StringWriter sw2 = new StringWriter();
    PrintWriter pw2 = new PrintWriter(sw2, true);

    when(response4.getWriter()).thenReturn(pw2);


    servlet.doGet(request4, response4);

    //servlet.doPost(request, response);
    //servlet.doGet(request, response);

    //assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    verify(response4).setStatus(statusCode.capture());
    assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    assertThat(sw2.toString(), containsString(""));
    //assertThat(sw.toString(), containsString(src));
    //assertThat(sw.toString(), containsString(dest));

    //assertThat(servlet.getAirline(airline).getName(), equalTo(airline));
  }

  /**
   * Tests bad airline name in the GET requests!
   * @throws IOException
   * @throws ServletException
   */
  @Test
  public void TestBadAirline() throws IOException {
    HttpServletRequest request = mock(HttpServletRequest.class);
    HttpServletResponse response = mock(HttpServletResponse.class);

    String badAirline = "bruh";

    PrintWriter pw = mock(PrintWriter.class);
    when(response.getWriter()).thenReturn(pw);
    when(request.getParameter(AIRLINE_PARAMETER)).thenReturn(badAirline);

    servlet.doGet(request, response);

    verify(response).sendError(HttpServletResponse.SC_NOT_FOUND, "Unable to find the airline named: 'bruh'!");
  }

  /**
   * Tests bad airport codes in the GET requests!
   * @throws IOException
   * @throws ServletException
   */
  @Test
  public void TestBaddies() throws IOException, ServletException {
    //AirlineServlet servlet = new AirlineServlet();

    HttpServletRequest request3 = mock(HttpServletRequest.class);
    HttpServletResponse response3 = mock(HttpServletResponse.class);

//    String word = "TEST WORD";
//    String definition = "TEST DEFINITION";
    String airline = "Lufthansa";
    String flightNumber = "69";
    String src = "PDX";
    String depart = "3/09/2023 3:35 am";
    String dest = "SAN";
    String arrive = "3/10/2023 4:47 am";

    when(request3.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);
    when(request3.getParameter(FLIGHTNUMBER_PARAMETER)).thenReturn(flightNumber);
    when(request3.getParameter(SRC_PARAMETER)).thenReturn(src);
    when(request3.getParameter(DEPART_PARAMETER)).thenReturn(depart);
    when(request3.getParameter(DEST_PARAMETER)).thenReturn(dest);
    when(request3.getParameter(ARRIVE_PARAMETER)).thenReturn(arrive);

    StringWriter stringWriter = new StringWriter();
    PrintWriter pw = new PrintWriter(stringWriter, true);

    when(response3.getWriter()).thenReturn(pw);

    servlet.doPost(request3, response3);

//    HttpServletRequest request4 = mock(HttpServletRequest.class);
//    HttpServletResponse response4 = mock(HttpServletResponse.class);
//
//    String badline = "butt";
//    when(request4.getParameter(AIRLINE_PARAMETER)).thenReturn(badline);
//
//    StringWriter sw2 = new StringWriter();
//    PrintWriter pw2 = new PrintWriter(sw2, true);
//
//    when(response4.getWriter()).thenReturn(pw2);
//
//    servlet.doGet(request4, response4);
//    verify(response4).sendError(HttpServletResponse.SC_NOT_FOUND);

    HttpServletRequest request5 = mock(HttpServletRequest.class);
    HttpServletResponse response5 = mock(HttpServletResponse.class);

    PrintWriter pw3 = mock(PrintWriter.class);
    when(response5.getWriter()).thenReturn(pw3);

    String badsrc = "123";
    String baddest = "123";
    when(request5.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);
    when(request5.getParameter(SRC_PARAMETER)).thenReturn(badsrc);
    when(request5.getParameter(DEST_PARAMETER)).thenReturn(dest);

    //StringWriter sw3 = new StringWriter();
    //PrintWriter pw3 = new PrintWriter(sw3, true);
    servlet.doGet(request5, response5);

    verify(response5).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "[AftFlight] Error regarding parameter: \"src\" !");

    HttpServletRequest request6 = mock(HttpServletRequest.class);
    HttpServletResponse response6 = mock(HttpServletResponse.class);

//    String badsrc = "nop";
//    String baddest = "yes";

    PrintWriter pw4 = mock(PrintWriter.class);
    when(response6.getWriter()).thenReturn(pw4);

    when(request6.getParameter(AIRLINE_PARAMETER)).thenReturn(airline);
    when(request6.getParameter(SRC_PARAMETER)).thenReturn(src);
    when(request6.getParameter(DEST_PARAMETER)).thenReturn(baddest);

    //StringWriter sw4 = new StringWriter();
    //PrintWriter pw4 = new PrintWriter(sw4, true);
    servlet.doGet(request6, response6);

    verify(response6).sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "[AftFlight] Error regarding parameter: \"dest\" !");

    //verify(response4).setStatus(statusCode.capture());
    //assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_PRECONDITION_FAILED));
    //servlet.doPost(request, response);
    //servlet.doGet(request, response);

    //assertThat(stringWriter.toString(), containsString(Messages.definedWordAs(word, definition)));

    // Use an ArgumentCaptor when you want to make multiple assertions against the value passed to the mock
    //ArgumentCaptor<Integer> statusCode = ArgumentCaptor.forClass(Integer.class);
    //verify(response4).setStatus(statusCode.capture());
    //assertThat(statusCode.getValue(), equalTo(HttpServletResponse.SC_OK));

    //ssertThat(sw2.toString(), containsString(""));
    //assertThat(sw.toString(), containsString(src));
    //assertThat(sw.toString(), containsString(dest));

    //assertThat(servlet.getAirline(airline).getName(), equalTo(airline));
  }
}
