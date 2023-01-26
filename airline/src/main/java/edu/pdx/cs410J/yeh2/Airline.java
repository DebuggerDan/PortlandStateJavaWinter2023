package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

public class Airline extends AbstractAirline<Flight> {
  private String name = "N/A";
  private Flight head = null;
  protected int flights;

  public Airline(String airline, String flightNumber, String src, String depart, String dest, String arrive)
  {
    this.name = "Blank!";
    this.flights = 0;
    Flight firstFlight = new Flight(flightNumber, src, depart, dest, arrive);
    addFlight(firstFlight);
  }

  public Airline(String name) {
    this.name = name;
    this.flights = 0;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void addFlight(Flight curr) {
    if (this.head == null)
    {
      this.head = curr;
    }
    else
    {
      addFlight(curr.getNext());
    }
  }

  public void printAll()
  {
    if (this.head == null)
    {
      System.out.print("\nDone! (or no flights)");
    }
    else
    {
      head.print();
      this.head = head.getNext();
      printAll();
    }
  }

  @Override
  public Collection<Flight> getFlights() {
    throw new UnsupportedOperationException("This method is not implemented yet");
  }
}
