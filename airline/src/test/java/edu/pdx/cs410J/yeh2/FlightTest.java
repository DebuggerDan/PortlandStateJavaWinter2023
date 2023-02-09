package edu.pdx.cs410J.yeh2;

import org.junit.jupiter.api.Test;

import java.text.DateFormat;
import java.util.Locale;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  protected static DateFormat date_format = DateFormat.getDateTimeInstance(3, 3, Locale.US);
  /**
   * Test for correct DateFormat.SHORT->String output for arrival time.
   */
  @Test
  void forProject3getArrivalString() {
    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
    assertThat(flight.getArrivalString(), equalTo("6/09/2023 1:37 PM"));
  }

  /**
   * Test for correct DateFormat.SHORT->String output for departure time.
   */
  @Test
  void forProject3GetDepartureString() {
    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
    assertThat(flight.getDepartureString(), equalTo("4/20/2023 4:20 PM"));
  }

  /**
   * Test for correct date output for arrival time.
   */
  @Test
  void forProject3getArrivalDate() {
    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
    String dateTest = date_format.format(flight.getArrivalDate());
    assertThat(dateTest, equalTo("6/09/23 1:37 PM"));
  }

  /**
   * Test for correct date output for departure time.
   */
  @Test
  void forProject3GetDepartureDate() {
    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
    String dateTest = date_format.format(flight.getDepartureDate());
    assertThat(dateTest, equalTo("4/20/23 4:20 PM"));
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  @Test
  void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight("123", "PDX", "04/20/2023 4:20 pm", "XDP", "06/09/2023 1:37 pm");
    assertThat(flight.getNumber(), equalTo(123));
  }

//  @Test
//  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
//    Flight flight = new Flight("69", "YES", "69:04:20", "LOL", "69:42:39");
//    assertThat(flight.getDeparture(), is(nullValue()));
//  }

  
}
