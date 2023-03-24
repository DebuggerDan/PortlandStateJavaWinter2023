//package edu.pdx.cs410J.yeh2;
//import org.junit.jupiter.api.Test;
////import org.junit.jupiter.api.*;
//
//import java.text.DateFormat;
//import java.util.Locale;
//import java.util.Calendar;
//
//import static org.hamcrest.CoreMatchers.*;
//import static org.hamcrest.MatcherAssert.assertThat;
////import static org.junit.jupiter.api.Assertions.assertThrows;
////import static org.junit.jupiter.api.Assertions.*;
//
//import java.text.ParseException;
////import edu.pdx.cs410J.ParserException;
//
///*
// * For Tests #6 & #7 for testing invalid departure / arrival dates, respectively.
// * @see Flight
// * @see Flight(String flightNumber, String src, String depart, String dest, String arrive)
// * @see Flight(String[] args)
// */
//
///**
// * Unit tests for the {@link Flight} class.
// * You'll need to update these unit tests as you build out you program.
// */
//public class FlightTest {
//
////  /**
////   * A helper function to act as a packaged executable function for {@code assertThrows} that helps to create & test Flight creation!
////   * @param flightNumber The flight number of the flight.
////   * @param src The source 3-letter code of the flight.
////   * @param depart The departure time-and-date of the flight.
////   * @param dest The destination 3-letter code of the flight.
////   * @param arrive The arrival time-and-date of the flight.
////   * @throws ParseException If there was an error from parsing timestamp(s)!
////   */
////  public static void air_traffic_controller(String flightNumber, String src, String depart, String dest, String arrive) throws ParseException
////  {
//////    try
//////    {
////      Flight test = new Flight(flightNumber, src, depart, dest, arrive);
//////    }
//////    catch (ParseException t00)
//////    {
//////      throw new ParseException("ATC: Looks like there was an error during the test [flight]: ", t00.getErrorOffset());
//////    }
////
////    //return runway;
////  }
//
//  protected static DateFormat date_format = DateFormat.getDateTimeInstance(3, 3, Locale.US);
//  /**
//   * Test #1 for correct DateFormat.SHORT->String output for arrival time.
//   * @throws ParseException If flight details were not able to be parsed!
//   */
//  @Test
//  void forProject3GetArrivalString() throws ParseException {
//
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
//    assertThat(flight.getArrivalString(), equalTo("6/9/23, 1:37 PM"));
//  }
//
//  /**
//   * Test #2 for correct date output for arrival time.
//   * @throws ParseException If flight details were not able to be parsed!
//   */
//  @Test
//  void forProject3GetArrivalDate() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
//    String dateTest = date_format.format(flight.getArrivalDate());
//    assertThat(dateTest, equalTo("6/9/23, 1:37 PM"));
//  }
//
//  /**
//   * Test #3 for correct DateFormat.SHORT->String output for departure time.
//   * @throws ParseException If flight details were not able to be parsed!
//   */
//  @Test
//  void forProject3GetDepartureString() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    assertThat(flight.getDepartureString(), equalTo("4/20/23, 4:20 PM"));
//  }
//
//  /**
//   * Test #4 for correct date output for departure time.
//   * @throws ParseException If flight details were not able to be parsed!
//   */
//  @Test
//  void forProject3GetDepartureDate() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    String dateTest = date_format.format(flight.getDepartureDate());
//    assertThat(dateTest, equalTo("4/20/23, 4:20 PM"));
//  }
//
//  /**
//   * This unit test #5 will need to be modified (likely deleted) as you implement
//   * your project.
//   * @throws ParseException If flight details were not able to be parsed!
//   */
//  @Test
//  void initiallyAllFlightsHaveTheSameNumber() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    assertThat(flight.getNumber(), equalTo(123));
//  }
//
////  @Test
////  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
////    Flight flight = new Flight("69", "YES", "69:04:20", "LOL", "69:42:39");
////    assertThat(flight.getDeparture(), is(nullValue()));
////  }
//
//  /**
//   * Test #6 with incorrect departure time.
//   * @throws ParseException If departure time cannot be parsed!
//   */
//  @Test//(expected = ParserException.class) silly me, the expected attribute was from previous JUnit version, 4, but we are currently using JUnit version 5
//  void forProject3testIncorrectDepartureDate() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "02224/20/20222223 4:22222220 pm", "XDP", "06/09/2023 1:37 pm");
//    //String dateTest = date_format.format(flight.getDepartureDate());
//    //assertThrows(ParserException.class, );
//    //assertThat(dateTest, equalTo("4/20/23, 4:20 PM"));
//    //assertThrows(ParseException.class, () -> air_traffic_controller("123", "PDX", "02224/20/20222223 4:22222220 pm", "XDP", "06/09/2023 1:37 pm"));
//  }
//
//  /**
//   * Test #7 with incorrect arrival time.
//   * @throws ParseException If arrival time cannot be parsed!
//   */
//  @Test//(expected = ParserException.class)
//  void forProject3testIncorrectArrivalDate() throws ParseException  {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "2316/09/23120223 1:37 pm");
//    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
//    //String dateTest = date_format.format(flight.getArrivalDate());
//    //assertThat(dateTest, equalTo("6/9/23, 1:37 PM"));
//    //(ParseException.class, () -> air_traffic_controller("123", "PDX", "04/20/2023 4:20 pm", "XDP", "2316/09/23120223 1:37 pm"));
//  }
//
//  /**
//   * Test #8 for correct Calendar for valid departure time.
//   * @throws ParseException If departure time cannot be parsed!
//   */
//  @Test
//  void forProject3GetDepartureCalendar() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    Calendar dateTest = flight.getDepartureXml();
//    String dateCalendarTest = date_format.format(dateTest.getTime());
//    assertThat(dateCalendarTest, equalTo("4/20/23, 4:20 PM"));
//  }
//
//  /**
//   * Test #9 for correct Calendar for valid arrival time.
//   * @throws ParseException If arrival time cannot be parsed!
//   */
//  @Test
//  void forProject3GetArrivalCalendar() throws ParseException {
//    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
//    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
//    Calendar dateTest = flight.getArrivalXml();
//    String dateCalendarTest = date_format.format(dateTest.getTime());
//    assertThat(dateCalendarTest, equalTo("6/9/23, 1:37 PM"));
//  }
//
//
//}
