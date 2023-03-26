package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.web.HttpRequestHelper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer.MethodName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Integration test that tests the REST calls made by {@link AirlineRestClient}
 */
@TestMethodOrder(MethodName.class)
class AirlineRestClientIT {
  private static final String HOSTNAME = "localhost";
  private static final String PORT = System.getProperty("http.port", "8080");

  private AirlineRestClient newAirlineRestClient() {
    int port = Integer.parseInt(PORT);
    return new AirlineRestClient(HOSTNAME, port);
  }

  @Test
  void test0RemoveAllDictionaryEntries() throws IOException {
    AirlineRestClient client = newAirlineRestClient();
    client.removeAllDictionaryEntries();
  }

  @Test
  void test1EmptyServerContainsNoDictionaryEntries() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    assertThrows(HttpRequestHelper.RestException.class, () -> client.getFlightEntries("Lufthansa", null, null, null));
    //Map<String, String> dictionary = client.getAllDictionaryEntries();
    //assertThat(dictionary.size(), equalTo(0));
  }

  @Test
  void test2DefineOneWord() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "Lufthansa";
    String flightNumber = "69";
    String src = "PDX";
    String depart = "3/09/2023 3:35 am";
    String dest = "SAN";
    String arrive = "3/10/2023 4:47 am";
    client.addFlightEntry(airline, flightNumber, src, depart, dest, arrive);

    String lufthansa = client.getFlightEntries(airline, null, null, null);
    //assertThat(lufthansa, equalTo(""));
    assertThat(lufthansa, Matchers.containsString("<name>Lufthansa</name>"));// +
  }

  @Test
  void test4EmptyWordThrowsException() {
    AirlineRestClient client = newAirlineRestClient();
    String emptyString = "";

    HttpRequestHelper.RestException ex =
      assertThrows(HttpRequestHelper.RestException.class, () -> client.addFlightEntry(emptyString, emptyString, emptyString, emptyString, emptyString, emptyString));
    assertThat(ex.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_PRECON_FAILED));
    assertThat(ex.getMessage(), equalTo(Messages.missingRequiredParameter(AirlineServlet.AIRLINE_PARAMETER)));
  }

  /**
   * Querying tests!
   */
  @Test
  void Quesodilla() throws IOException, ParserException {
    AirlineRestClient client = newAirlineRestClient();
    String airline = "Lufthansa";
    String flightNumber = "69";
    String src = "PDX";
    String depart = "3/09/2023 3:35 am";
    String dest = "SAN";
    String arrive = "3/10/2023 4:47 am";
    String airline2 = "Concorde";
    String flightNumber2 = "420";
    String src2 = "PDX";
    String depart2 = "3/10/2023 4:35 am";
    String dest2 = "SAN";
    String arrive2 = "3/11/2023 5:47 am";
    String airline3 = "Lufthansa";
    String flightNumber3 = "421";
    String src3 = "PDX";
    String depart3 = "3/11/2023 5:35 am";
    String dest3 = "SAN";
    String arrive3 = "3/12/2023 7:47 am";
    client.addFlightEntry(airline, flightNumber, src, depart, dest, arrive);
    String query1 = client.getFlightEntries(airline, null, null, null);
    client.addFlightEntry(airline2, flightNumber2, src2, depart2, dest2, arrive2);
    String query2 = client.getFlightEntries(airline2, null, null, null);
    client.addFlightEntry(airline3, flightNumber3, src3, depart3, dest3, arrive3);
    String query3 = client.getFlightEntries(airline3, "PDX", "SAN", null);

    //assertThat(lufthansa, equalTo(""));
    assertThat(query1, Matchers.containsString("<name>Lufthansa</name>"));
    assertThat(query2, Matchers.containsString("<name>Concorde</name>"));
    assertThat(query3, Matchers.containsString("<name>Lufthansa</name>"));

  }


}
