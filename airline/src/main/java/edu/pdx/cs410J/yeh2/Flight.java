package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractFlight;

import java.text.ParseException;
import java.util.Date;
import java.text.DateFormat;
import java.util.Locale;

/**
 * This class implements Flight with a name, flightNumber, src, depart(ure time and date),
 * destination (time and date), and arrive(-e+al time and date). Extends AbstractFlight.
 *
 */
public class Flight extends AbstractFlight {
  protected static DateFormat date_format = DateFormat.getDateTimeInstance(3, 3, Locale.US);
  private Flight next = null;
  protected String flightNumber = "123";
  protected String src = null;
  protected Date depart = null;
  protected String dest = null;
  protected Date arrive = null;



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
    //this.depart = depart;
    try
    {
      this.depart = date_format.parse(depart);
    }
    catch (ParseException e1)
    {
      System.err.println("Error when attempting to formatting the departure time & date combo-argument: " + depart);
      return;
    }

    this.dest = dest;
    //this.arrive = arrive;
    //Date temp = date_format.parse(dest);

    try
    {
      this.arrive = date_format.parse(arrive);
    }
    catch (ParseException e2)
    {
      System.err.println("Error when attempting to formatting the arrival time & date combo-argument: " + arrive);
      //return;
    }
  }

  /*
   * A <code>Flight</code> constructor based on a {@code String[]}, an array of command-line string-parameters!
   * @param args A {@code String[]} based on command-line args[]!
   */
  public Flight(String[] args)
  {
    if (args.length == 5)
    {
      this.flightNumber = args[0];
      this.src = args[1];
//      this.depart = args[2];
      try
      {
        this.depart = date_format.parse(args[2]);
      }
      catch (ParseException e3)
      {
        System.err.println("Error when attempting to formatting the departure time & date combo-argument: " + args[2]);
        return;
      }
      this.dest = args[3];
//      this.arrive = args[4];
      try
      {
        this.arrive = date_format.parse(args[4]);
      }
      catch (ParseException e4)
      {
        System.err.println("Error when attempting to formatting the arrival time & date combo-argument: " + args[4]);
        //return;
      }
    }
  }

  /**
   * A <code>Flight</code> constructor that is created as a clone of another!
   * @param copilot The <code>Flight</code> clone source!
   */
  public Flight(Flight copilot)
  {
    if (copilot != null)
    {
      this.flightNumber = copilot.flightNumber;
      this.src = copilot.src;
      this.depart = copilot.depart;
      this.dest = copilot.dest;
      this.arrive = copilot.arrive;
      if (copilot.next != null)
      {
        this.next = copilot.next;
      }
    }
  }

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
  public Date getDestinationDate() {
    if (this.depart == null || this.depart.toString().equals("N/A"))
    {
      return null;
    }
    else
    {
      return this.depart;
    }
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's departure time-and-date timestamp or "N/A" if blank!
   * @return dest The date of the departure timestamp!
   */
  @Override
  public String getDepartureString() {
    if (this.depart == null || this.depart.toString().equals("N/A"))
    {
      return null;
    }
    else
    {
      // Format as java.text.DateFormat.SHORT;

      return date_format.format(this.depart);
    }
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's destination time-and-date timestamp or "N/A" if blank!
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
   * @return arrive
   */
  @Override
  public String getArrivalString() {

    if (this.arrive == null || this.arrive.toString().equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      //return this.arrive.toString();

      // Format as java.text.DateFormat.SHORT;
      return date_format.format(this.arrive);
    }

    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's arrival string (three-letter code) or "N/A" if blank!
   * @return arrive A string version of the arrival date-timestamp!
   */
  public Date getArrivalDate() {

    if (this.arrive == null || this.arrive.toString().equals("N/A"))
    {
      return null;
    }
    else
    {
      return this.arrive;
    }

    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

}
