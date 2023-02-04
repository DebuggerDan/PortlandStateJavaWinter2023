package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineDumper;

import java.util.Collection;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

/**
 * A text-dumper based on the <code>AirlineDumper</code> interface, for Project #2.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps it into a text file.
 */
public class TextDumper implements AirlineDumper<Airline> {
//  private final StringWriter writer;
  private final String file_name;
  protected File currfile = null;

  public TextDumper(StringWriter cw)
  {
    //this.writer = writer;
    this.file_name = cw.toString();
  }

  /**
   * Create a TextDumper for a specific file name in-mind, but checks if it is an invalid file name.
   * @param name The name of the file to be dumped to.
   * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
   */
  public TextDumper(String name)
          throws IllegalArgumentException
  {
    if (name.isEmpty())
    {
      throw new IllegalArgumentException("Sorry, looks like the name of the file was incorrect.");
    }
    else
    {
      this.file_name = name;
    }
  }

  /**
   * Create a TextDumper for a specific file in-mind, but checks if it is an invalid file.
   * @param file The name of the file to be dumped to.
   * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
   */
  public TextDumper(File file) throws IllegalArgumentException
  {
    if (!file.exists())
    {
      throw new IllegalArgumentException("Sorry, looks like the name of the file was incorrect.");
    }
    else
    {
      this.file_name = file.getName();
      //this.currfile = file;
    }
  }

  /**
   * The main TextDumper dumper function that dumps the contents of an airline (& its flights) to a text file.
   * @param airline The airline that will have its containing flights dumped to a text file.
   * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
   */
  @Override
  public void dump(Airline airline) throws IOException
  {
//    try (
//      PrintWriter pw = new PrintWriter(this.file_name)
//      ) {
//      pw.println(airline.getName());
//
//      pw.flush();
//    }
    if (airline == null)
    {
      throw new IOException("Airline given was blank!");
    }
    File thefile = new File(this.file_name);

    FileWriter fwriter = new FileWriter(thefile);
    PrintWriter printer = new PrintWriter(fwriter);

    Collection<Flight> flightDump = airline.getFlights();
    String airline_name = airline.getName();

    printer.println(airline_name);

    for (Flight runway : flightDump)
    {
      printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
    }
    printer.close();
  }
}
