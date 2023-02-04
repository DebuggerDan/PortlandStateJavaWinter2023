package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractAirline;

import java.util.Collection;

// Using a built-in linked list instead of trying to create my own from scratch:
import java.util.LinkedList;


/**
 * The airline class has (should have collection) a linked list of Flight classes.
 */
public class Airline extends AbstractAirline<Flight> {
  private String name = "N/A";
  private Collection<Flight> flights = null;

  //private Flight head = null;
  protected int flightnum;

  public Airline(String airline, String flightNumber, String src, String depart, String dest, String arrive)
  {
    this.name = airline;
    this.flights = new LinkedList<Flight>();
    Flight firstFlight = new Flight(flightNumber, src, depart, dest, arrive);
    addFlight(firstFlight);
  }

  public Airline(String name) {
    this.name = name;
    this.flights = new LinkedList<Flight>();
    this.flightnum = 0;
  }

  /**
   * Returns the name of the airline.
   * @return name
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a flight to the linked list (should be collection)
   *
   */
  @Override
  public void addFlight(Flight curr) {
//    if (this.head == null)
//    {
//      this.head = curr;
//    }
//    if (this.head.getNext() == null)
//    {
//      this.head.setNext(curr);
//      this.head = curr;
//    }
//    else
//    {
//      this.addFlight(curr.getNext());
//    }
    this.flights.add(curr);
  }
/**
 * Prints all flights.
 */
  public void printAll()
  {
//    if (this.head == null)
//    {
//      System.out.print("\nDone! (or no flights)");
//    }
//    else
//    {
//      head.print();
//      this.head = head.getNext();
//      printAll();
//    }
  }

  /**
   * Should return the flights but currently prints all the flights.
   *
   */
  @Override
  public Collection<Flight> getFlights() {
//    if (this.head == null)
//    {
//      System.out.print("\nDone! (or no flights)");
//    }
//    else
//    {
//      head.print();
//      this.head = head.getNext();
//      printAll();
//    }
//
//    return null;
    //throw new UnsupportedOperationException("This method is not implemented yet");
    // if (this.flights == null)
    // {
    //    System.err.println("No flights o noes!");
    //    return null;
    //
    //  }
    return this.flights;
  }
}
