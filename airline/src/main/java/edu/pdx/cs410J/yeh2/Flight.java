package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;
import java.util.Objects;

import java.util.Calendar;

/**
 * This class implements Flight with a name, flightNumber, src, depart(ure time and date),
 * destination (time and date), and arrive(-e+al time and date). Extends AbstractFlight.
 *
 */
public class Flight extends AbstractFlight {// implements Comparable<Flight> {
  protected static DateFormat date_formatter = DateFormat.getDateTimeInstance(3, 3, Locale.US);
  protected static final String date_formatting = "MM/dd/yyyy h:mm a";
  protected static SimpleDateFormat date_format = new SimpleDateFormat(date_formatting, Locale.US);
  private Flight next = null;
  protected String flightNumber = "123";
  private String src = null;
  //private Date depart = null;
  private String depart = null;
  private String dest = null;
  //private Date arrive = null;
  private String arrive = null;

  /**
   * A <code>Flight</code> constructor based on five (5) strings passed into it!
   * @param flightNumber The flight number of the flight.
   * @param src The source 3-letter code of the flight.
   * @param depart The departure time-and-date of the flight.
   * @param dest The destination 3-letter code of the flight.
   * @param arrive The arrival time-and-date of the flight.
   */
  public Flight(String flightNumber, String src, String depart, String dest, String arrive)
  {
    this.flightNumber = flightNumber;
    this.src = src;
    this.depart = depart;
    this.dest = dest;
    this.arrive = arrive;
  }

//  /** {@see Project4 Version}
//   * A <code>Flight</code> constructor based on five (5) strings passed into it!
//   * @param flightNumber The flight number of the flight.
//   * @param src The source 3-letter code of the flight.
//   * @param depart The departure time-and-date of the flight.
//   * @param dest The destination 3-letter code of the flight.
//   * @param arrive The arrival time-and-date of the flight.
//   */
//  public Flight(String flightNumber, String src, String depart, String dest, String arrive) throws ParseException
//  {
//
//    this.flightNumber = flightNumber;
//    this.src = src;
//    //this.depart = depart;
//    try
//    {
//      this.depart = date_format.parse(depart);
//    }
//    catch (ParseException e1)
//    {
//      //System.err.println("[Flight Construction, Type #1] Error when attempting to format the departure time & date combo-argument: " + depart);
//      //return;
//      throw new ParseException("[Flight Constructor, Type #1a] Error when attempting to format the departure time & date combo-argument.", e1.getErrorOffset());
//
//    }
//
//    this.dest = dest;
//    //this.arrive = arrive;
//    //Date temp = date_format.parse(dest);
//
//    try
//    {
//      this.arrive = date_format.parse(arrive);
//    }
//    catch (ParseException e2)
//    {
//      //System.err.println("[Flight Construction, Type #1] Error when attempting to formatting the arrival time & date combo-argument: " + arrive);
//      //return;
//      throw new ParseException("[Flight Constructor, Type #1b] Error when attempting to format the arrival time & date combo-argument.", e2.getErrorOffset());
//    }
//  }

  /*
   * A <code>Flight</code> constructor based on a {@code String[]}, an array of command-line string-parameters!
   * @param args A {@code String[]} based on command-line args[]!
   */
//  public Flight(String[] args) throws ParseException
//  {
//    if (args.length == 5)
//    {
//      this.flightNumber = args[0];
//      this.src = args[1];
////      this.depart = args[2];
//      try
//      {
//        this.depart = date_format.parse(args[2]);
//      }
//      catch (ParseException e3)
//      {
//        //System.err.println("[Flight Construction, Type #2a] Error when attempting to formatting the departure time & date combo-argument: " + args[2]);
//        //return;
//        throw new ParseException("[Flight Construction, Type #2a] Error when attempting to formatting the departure time & date combo-argument: ", e3.getErrorOffset());
//      }
//      this.dest = args[3];
////      this.arrive = args[4];
//      try
//      {
//        this.arrive = date_format.parse(args[4]);
//      }
//      catch (ParseException e4)
//      {
//        //System.err.println("[Flight Construction, Type #b] Error when attempting to formatting the arrival time & date combo-argument: " + args[4]);
//        //return;
//        throw new ParseException("[Flight Construction, Type #2b] Error when attempting to formatting the arrival time & date combo-argument: ", e4.getErrorOffset());
//
//      }
//    }
//  }

  /**
   * A <code>Flight</code> constructor that is created as a clone of another!
   * @param copilot The <code>Flight</code> clone source!
   */
//  public Flight(Flight copilot)
//  {
//    if (copilot != null)
//    {
//      this.flightNumber = copilot.flightNumber;
//      this.src = copilot.src;
//      this.depart = copilot.depart;
//      this.dest = copilot.dest;
//      this.arrive = copilot.arrive;
//      if (copilot.next != null)
//      {
//        this.next = copilot.next;
//      }
//    }
//  }

  /**
   * <p>
   *     Provides a way to compare two <code>Flight/code> objects based on the following:
   *     1.) Firstly, by alphabetical order - based on source airport-code (ignoring case).
   *     2.) [If same source airport-codes, then] secondly, by chronological order - based on departure time.
   *     3.) [If both have the same source airport-code & take-off time], then they will be considered "equal."
   * </p>
   * @see Project3
   * @param runway A second flight to be compared to the current <code>Flight</code> object that the {@code compareTo(runway)} is being run from.
   * @return <p>-1 Case I., if the current Flight object should be first.
   *      1 Case II., if the runway (second flight) should be first.
   *      0 Case III., if both objects are equal.</p>
   * @throws NullPointerException If the runway (second flight) is empty, throws the null pointer exception.
   */
//  @Override
//  public int compareTo(Flight runway) throws NullPointerException
//  {
//    //int result = 404;
//
//    // NullPointerException guard!
//    Objects.requireNonNull(runway);
//
//    //String secondSrc = runway.getSource();
//
//    // Sort Test #1: Based on alphabetical source airport-codes (case-insensitive)!
//    // Where, if firstTest is 0 = equal, if firstTest < 0 = first (originate) Flight is first, if firstTest > 0 = second flight is first;
//    int firstTest = this.src.toLowerCase().compareTo(runway.getSource().toLowerCase());//IgnoreCase(runway.getSource());//secondSrc);
//
//    if (firstTest < 0)
//    {
//      //result = -1;
//      return -1;
//    }
//
//    else if (firstTest > 0)
//    {
//      //result = 1;
//      return 1;
//    }
//
//    //if (firstTest == 0)
//    else
//    {
//
//      // Sort Test #2: Based on chronological take-off timestamps!
//      //Date secondDate = runway.getDepartureDate();
//
//      int secondTest = this.depart.compareTo(runway.getDepartureDate());//secondDate);
//
//      if (secondTest < 0)
//      {
//        //result = -1;
//        return -1;
//      }
//
//      else if (secondTest > 0)
//      {
//        //result = 1;
//        return 1;
//      }
//
//      //if (secondTest == 0)
//      else
//      {
//        //result = 0;
//        return 0;
//      }
//
//    }

//    if (result == 404)
//    {
//      System.err.println("Error! Comparator was not able to compare the two flights!");
//      return result;
//    }

    //return result;
//  }

  /**
   * Prints the information of the flight.
   */
  public void print()
  {
    System.out.print(this.flightNumber + ", ");
    System.out.print(this.src + ", ");
    System.out.print(this.depart + ", ");
    System.out.print(this.dest + ", ");
    System.out.print(this.arrive);
  }

  /*
   * Sets the next flight node.
   * @param curr The next flight.
   */
//  public void setNext(Flight curr)
//  {
//    this.next = curr;
//
//    //return this.next;
//  }

  /*
   * Returns the next flight node.
   * @return Flight next
   */
//  public Flight getNext()
//  {
//    return this.next;
//  }

  /**
   * Returns current flight number.
   * @return flightNumber
   */
  @Override
  public int getNumber() {
    //return 42;
    return Integer.parseInt(this.flightNumber);
  }

  /**
   * Returns current flight source (or "N/A" if blank!)
   * @return src
   */
  @Override
  public String getSource()
  {
    if (this.src == null || this.src.equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      return this.src;
    }
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's departure time-and-date timestamp or "N/A" if blank!
   * @return dest The date of the departure timestamp!
   */
//  public Date getDepartureDate() {
//    if (this.depart == null || this.depart.toString().equals("N/A"))
//    {
//      return null;
//    }
//    else
//    {
//      return this.depart;
//    }
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * Returns current flight's departure time-and-date timestamp or "N/A" if blank!
   * @return dest The date of the departure timestamp!
   */
  @Override
  public String getDepartureString() {
//    if (this.depart == null || this.depart.toString().equals("N/A"))
//    {
//      return null;
//    }
//    else
//    {
    // Format as java.text.DateFormat.SHORT;
    //String takeoff_string = date_formatter.format(this.depart);
    //return takeoff_string;
    return this.depart;
    //}
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * <p>
   *     Returns current flight's departure time-and-date as a five-part {@code String[]} array for easy XML parsing or null if blank!
   * </p>
   * @return dest The time & date (as five-part {@code String[]} array) of the departure timestamp!
   */
//  public String[] getDepartureXml() {
////    if (this.depart == null || this.depart.toString().equals("N/A"))
////    {
////      return null;
////    }
////    else
////    {
//    // Format as java.text.DateFormat.SHORT;
//    String takeoff_string = date_formatter.format(this.depart);
//    return takeoff_string;
//    //}
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * <p>
   *     Returns current flight's departure time-and-date as a <code>Calendar</code> object for easy XML parsing or null if blank!
   * </p>
   * @return dest The time & date (as a <code>Calendar</code> object) of the departure timestamp!
   */
//  public Calendar getDepartureXml() {
////    if (this.depart == null || this.depart.toString().equals("N/A"))
////    {
////      return null;
////    }
////    else
////    {
//    // Format as java.text.DateFormat.SHORT;
//   // String takeoff_string = date_formatter.format(this.depart);
//    Calendar takeoff_calendar = Calendar.getInstance();
//    takeoff_calendar.setTime(this.depart);
//    return takeoff_calendar;
//    //}
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * Returns current flight's destination three-letter airport code or "N/A" if blank!
   * @return dest A string version of the destination date-timestamp!
   */
  @Override
  public String getDestination() {
    if (this.dest == null || this.dest.equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      return this.dest;
    }
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's arrival string (three-letter code) or "N/A" if blank!
   * @return arrive A string version of the arrival date-timestamp!
   */
//  public Date getArrivalDate() {
//
//    if (this.arrive == null || this.arrive.toString().equals("N/A"))
//    {
//      return null;
//    }
//    else
//    {
//      return this.arrive;
//    }
//
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * Returns current flight's arrival string (three-letter code) or "N/A" if blank!
   * @return arrive
   */
  @Override
  public String getArrivalString() {

//    if (this.arrive == null || this.arrive.toString().equals("N/A"))
//    {
//      return "N/A";
//    }
//    else
//    {
    //return this.arrive.toString();

    // Format as java.text.DateFormat.SHORT;
    //String arrival_string = date_formatter.format(this.arrive);
    //return arrival_string;
    return this.arrive;
    //}

    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * <p>
   *     Returns current flight's arrival time-and-date as a five-part {@code String[]} array for easy XML parsing or "N/A" if blank!
   * </p>
   * @return arrive The time & date (as five-part {@code String[]} array) of the arrival timestamp!
   */
//  public String[] getArrivalXml() {
//
//    if (this.arrive == null || this.arrive.toString().equals("N/A"))
//    {
//      return null;
//    }
//    else
//    {
//      return this.arrive;
//    }
//
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * <p>
   *     Returns current flight's arrival time-and-date as a <code>Calendar</code> object for easy XML parsing or "N/A" if blank!
   * </p>
   * @return arrive The time & date (as a <code>Calendar</code> object) of the arrival timestamp!
   */
//  public Calendar getArrivalXml() {
//
////    if (this.arrive == null || this.arrive.toString().equals("N/A"))
////    {
////      return null;
////    }
////    else
////    {
//      //return this.arrive;
//        Calendar arrival_calendar = Calendar.getInstance();
//        arrival_calendar.setTime(this.arrive);
//        return arrival_calendar;
//   // }
//
//    //throw new UnsupportedOperationException("This method is not implemented yet");
//  }

  /**
   * A simple function that calculates flight minutes by dividing the difference between departure and arrival timestamps, dividing that difference by 1000, then that result as a whole by 60, to calculate the minutes from the milliseconds!
   * @return The flight time in minutes!
   * -1337 is returned if the flight_minutes where calculated to be negative, so as to indicate a calculation mismatch (since properly calculated flight time minutes should be greater than 0!)
   */
//  public long getFlightTime()
//  {
//
//    long flight_minutes = (((this.arrive.getTime() - this.depart.getTime()) / (1000)) / 60);
//    if (flight_minutes < 0)
//    {
//      System.err.println("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: " + flight_minutes);
//      //flight_minutes = -1337;
//    }
//
//    return flight_minutes;
//  }

}
