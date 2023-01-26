package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractFlight;

/**
 * This class implements Flight with a name, flightNumber, src, depart(ure time & date),
 * dest(ination time & date), and arrive(-e+al time&date)
 */
public class Flight extends AbstractFlight {
  private Flight next = null;
  protected String name;
  protected String flightNumber;
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

  public Flight setNext(Flight curr)
  {
    this.next = curr;

    return this.next;
  }

  public Flight getNext()
  {
    return this.next;
  }

  @Override
  public int getNumber() {
    return 42;
  }

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

  @Override
  public String getDepartureString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }

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

  @Override
  public String getArrivalString() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }


}
