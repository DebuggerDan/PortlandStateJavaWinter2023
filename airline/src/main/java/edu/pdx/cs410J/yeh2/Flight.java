package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractFlight;

/**
 * This class implements Flight with a name, flightNumber, src, depart(ure time and date),
 * destination (time and date), and arrive(-e+al time and date). Extends AbstractFlight.
 *
 */
public class Flight extends AbstractFlight {
  private Flight next = null;
  protected String flightNumber = "123";
  protected String src = null;
  protected String depart = null;
  protected String dest = null;
  protected String arrive = null;

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

  /**
   * A <code>Flight</code> constructor based on a {@code String[]}, an array of command-line string-parameters!
   * @param args A {@code String[]} based on command-line args[]!
   */
  public Flight(String[] args)
  {
    if (args.length == 5)
    {
      this.flightNumber = args[0];
      this.src = args[1];
      this.depart = args[2];
      this.dest = args[3];
      this.arrive = args[4];
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

  /**
   * Sets the next flight node.
   * @param curr The next flight.
   */
  public void setNext(Flight curr)
  {
    this.next = curr;

    //return this.next;
  }

  /**
   * Returns the next flight node.
   * @return Flight next
   */
  public Flight getNext()
  {
    return this.next;
  }

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
   * @return depart
   */
  @Override
  public String getDepartureString() {
    if (this.depart == null || this.depart.equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      return this.depart;
    }
    //throw new UnsupportedOperationException("This method is not implemented yet");
  }

  /**
   * Returns current flight's destination time-and-date timestamp or "N/A" if blank!
   * @return dest
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

    if (this.arrive == null || this.arrive.equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      return this.arrive;
    }

    //throw new UnsupportedOperationException("This method is not implemented yet");
  }


}
