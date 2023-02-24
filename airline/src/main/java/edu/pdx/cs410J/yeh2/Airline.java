package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AbstractAirline;
import java.io.*;
import java.util.*;
import javax.xml.parsers.*;
import org.w3c.dom.Element;
import org.w3c.dom.*;
import org.xml.sax.*;

//import java.util.Collection;
//import java.util.LinkedList;

import java.text.ParseException;
import java.util.*;
//import java.util.TreeSet;


/**
 * The <code>Airline</code> class has (should have collection) a {@code TreeSet<Flight><Flight>},
 * ...which is a linked-list of <code>flight</code>-classes.
 */
public class Airline extends AbstractAirline<Flight> {
  private String name = "N/A";
  //private LinkedList<Flight> flights = null;
  private Collection<Flight> flights;
  private Collection flightsXML = new ArrayList<>();

  //private Collection<Flight> flights = null;

  //private Flight head = null;
  protected int flightnum;

  /**
   * Implements the {@code Comparator<Flight>} as implemented in the Flight class.
   * @see Flight
   */
//  static class air_traffic_controller implements Comparator<Flight>
//  {
//    /**
//     * Compares two flights for sorting stuffs.
//     * <p>
//     *     Provides a way to compare two <code>Flight/code> objects based on the following:
//     *     1.) Firstly, by alphabetical order - based on source airport-code (ignoring case).
//     *     2.) [If same source airport-codes, then] secondly, by chronological order - based on departure time.
//     *     3.) [If both have the same source airport-code & take-off time], then they will be considered "equal."
//     * </p>
//     * @param gateA Flight #1
//     * @param gateB Flight #2
//     * @return <p>1 Case I., if the current Flight object should be first.
//     *      1 Case II., if the runway (second flight) should be first.
//     *      0 Case III., if both objects are equal.</p>
//     */
//    public int compare(Flight gateA, Flight gateB)
//    {
//      //int result = gateA.compareTo(gateB);
////      if (result == 404)
////      {
////        System.err.println("ATC confirms the error shown regarding conflicting flight comparisons above this message.");
////      }
//      return gateA.compareTo(gateB);//result;
//    }
//  }

  /*
   * An <code>Airline</code> constructor for six (6) raw {@code args[]} parameters.
   * @param airline The name of the airline.
   * @param flightNumber The flight number (of the first flight).
   * @param src The source 3-letter code (of the first flight).
   * @param depart The departure time-and-date (of the first flight).
   * @param dest The destination 3-letter code (of the first flight).
   * @param arrive The arrival time-and-date (of the first flight).
   */
//  public Airline(String airline, String flightNumber, String src, String depart, String dest, String arrive)
//  {
//    this.name = airline;
//    this.flights = new LinkedList<Flight>();
//    Flight firstFlight = new Flight(flightNumber, src, depart, dest, arrive);
//    addFlight(firstFlight);
//  }

  /**
   * An <code>Airline</code> constructor for six (6) raw {@code args[]} parameters.
   * @param airline The name of the airline.
   * @param earlyBird The first flight, that will be added to the airline's list o' flights!
   * @throws IllegalArgumentException If the {@code Flight earlyBird} is empty!
   */
  public Airline(String airline, Flight earlyBird) throws IllegalArgumentException
  {
    if (earlyBird.getSource() == null)
    {
      throw new IllegalArgumentException("Uh oh, earlyBird was empty!");
    }
    this.name = airline;
    this.flights = new LinkedList<Flight>();
    //this.flights = new TreeSet<Flight>(new air_traffic_controller());

    //Flight firstFlight = new Flight(earlyBird);//flightNumber, src, depart, dest, arrive);
    this.addFlight(earlyBird);
  }

  /**
   * An <code>Airline</code> constructor
   * @param name The name of the <code>Airline</code>!
   */
  public Airline(String name) {
    this.name = name;
    this.flights = new LinkedList<Flight>();
    //this.flights = new TreeSet<Flight>(new air_traffic_controller());
    this.flightnum = 0;
  }

  /**
   * An <code>Airline</code> Element-specific constructor
   * For Project #4!
   * @see XmlParser
   * @see Project4
   * @see "xml-2x2.pdf, page 35"
   * @param root The root of the <code>XML</code> parsed-file!
   */
//  public Airline(Element root) {
//    NodeList flightXML = root.getChildNodes();
//    this.name = root.getAttribute("name");
//    this.flights = new TreeSet<Flight>(new air_traffic_controller());
//    this.flightnum = 0;
//
//    for (int idx = 0; idx != flightXML.getLength(); idx++)
//    {
//      if (flightXML.item(idx) instanceof Element)
//      {
//        Element runway = (Element) flightXML.item(idx);
//        if (runway.getNodeName().equals("flight"))
//        {
//          Flight curr = null;
//          this.flightsXML.add(curr);
//          //curr = new Flight(runway.getAttribute("number"), runway.getAttribute("src"), runway.getAttribute("depart"), runway.getAttribute("dest"), runway.getAttribute("arrive"));
//          //this.addFlight(curr);
//          this.flightnum++;
//
//            //System.err.println("[Flight XML Constructor Error]: " + e1.getMessage());
//        }
//      }
//    }
//
//    //this.flights = new LinkedList<Flight>();
//    this.flights = new TreeSet<Flight>(new air_traffic_controller());
//
//    NodeList flightXML2
//
//    //this.flightnum = 0;
//  }
  /**
   * Returns an <code>Integer</code>-count of flights in the {@code TreeSet<Flight>} of the <code>Airline</code>.
   * @return The number of flights in the <code>Airline</code>.
   */
  public Integer getFlightNum()
  {
    //int flights = this.flightnum;
    return this.flightnum;//flights;
  }

  /**
   * Returns the name of the <code>Airline</code> as a <code>string</code>.
   * @return name The name of the <code>Airline</code>.
   */
  @Override
  public String getName() {
    return this.name;
  }

  /**
   * Adds a flight to the {@code this.flights}, which is a {@code TreeSet<Flight><Flight>}.
   * @param curr The flight to be added.
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
    this.flightnum++;
    this.flights.add(curr);
  }

  /*
   * Adds a flight to the {@code this.flights}, which is a {@code TreeSet<Flight><Flight>}.
   * @param specifications A {@code String[]} array of specifications for a new flight!
   */
//  public void addFlight(String[] specifications) {
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
//    this.flightnum++;
//    Flight plane = new Flight(specifications);
//    this.flights.add(plane);
//  }

  /**
   * Prints all flights within the {@code this.flights} ({@code TreeSet<Flight><Flight>}).
   */
//  public void printAll()
//  {
////    if (this.head == null)
////    {
////      System.out.print("\nDone! (or no flights)");
////    }
////    else
////    {
////      head.print();
////      this.head = head.getNext();
////      printAll();
////    }
//    //for (Object plane : this.flights)
//    for (Flight plane : this.flights) {
//      plane.print();
//    }
//  }

  /**
   * Prints all flights within the {@code this.flights} ({@code TreeSet<Flight><Flight>}).
   * (This version of printAll invokes the flight.toString() method for Project #1.)
   * (see Project1)
   * @param airline_name The name of the airline!
   */
  public void printAllString(String airline_name)
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
    //for (Object plane : this.flights)
    for (Flight plane : this.flights) {
      String flight_line = plane.toString();
      System.out.println("Airline: " + airline_name + ", Flight Information: " + flight_line);
    }
  }

  /**
   * Returns {@code this.flights} of the <code>Airline</code>, as ({@code Collection<Flight>}).
   * @return schedule {@code LinkedList<Flight>}
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
    //LinkedList<Flight> schedule = new LinkedList<Flight>(this.flights);

    //TreeSet<Flight> schedule = new TreeSet<> (new air_traffic_controller());
    //return schedule;
    return this.flights;
  }
}
