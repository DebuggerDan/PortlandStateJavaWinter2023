package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractFlight;

/**
 * This class implements Flight with a name, flightNumber, src, depart(ure time & date),
 * destination (time & date), and arrive(-e+al time&date)
 *
 */
public class Flight extends AbstractFlight {
  private Flight next = null;
  protected String flightNumber = "69";
  protected String src;
  protected String depart;
  protected String dest;
  protected String arrive;

  public Flight(String flightNumber, String src, String depart, String dest, String arrive)
  {
    this.flightNumber = flightNumber;
    this.src = src;
    this.depart = depart;
    this.dest = dest;
    this.arrive = arrive;
  }

  public void print()
  {
    System.out.print(this.flightNumber);
    System.out.print(this.src);
    System.out.print(this.depart);
    System.out.print(this.dest);
    System.out.print(this.arrive);
  }

  /**
   * Sets the next flight node.
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
   * Returns current flight's departure (three-letter code) or "N/A" if blank!
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
   * Returns current flight's destination (three-letter code) or "N/A" if blank!
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
    /*
    if (this.arrive == null || this.arrive.equals("N/A"))
    {
      return "N/A";
    }
    else
    {
      return this.arrive;
    }
    */
    throw new UnsupportedOperationException("This method is not implemented yet");
  }


}
