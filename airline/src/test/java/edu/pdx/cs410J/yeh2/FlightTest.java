package edu.pdx.cs410J.yeh2;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests for the {@link Flight} class.
 *
 * You'll need to update these unit tests as you build out you program.
 */
public class FlightTest {

  /**
   * This unit test has been modified (likely deleted) as I have implemented
   * my project.
   */
  @Test
  void getArrivalStringIsImplemented() {
    Flight flight = new Flight("123", "YES", "4/20/2023 04:20", "LOL", "6/09/2023 13:37");
    //assertThrows(UnsupportedOperationException.class, flight::getArrivalString);
    assertThat(flight.getArrivalString(), equalTo("6/09/2023 13:37"));
  }

  /**
   * This unit test will need to be modified (likely deleted) as you implement
   * your project.
   */
  @Test
  void initiallyAllFlightsHaveTheSameNumber() {
    Flight flight = new Flight("123", "PDX", "4/20/2023 04:20", "XDP", "6/09/2023 13:37");
    assertThat(flight.getNumber(), equalTo(123));
  }

//  @Test
//  void forProject1ItIsOkayIfGetDepartureTimeReturnsNull() {
//    Flight flight = new Flight("69", "YES", "69:04:20", "LOL", "69:42:39");
//    assertThat(flight.getDeparture(), is(nullValue()));
//  }

  /**
   * Test for departure time.
   */
  @Test
  void forProject2GetDepartureTime() {
    Flight flight = new Flight("123", "PDX", "4/20/2023 04:20", "XDP", "6/09/2023 13:37");
    assertThat(flight.getDepartureString(), equalTo("4/20/2023 04:20"));
  }
  
}
