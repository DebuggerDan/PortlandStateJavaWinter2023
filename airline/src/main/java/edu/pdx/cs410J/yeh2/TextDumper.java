package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineDumper;

//import java.util.Collection;
import java.util.Collection;
//import java.util.LinkedList;

import java.io.PrintWriter;
import java.io.StringWriter;
//import java.lang.StringBuilder;
//import java.io.Writer;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
//import java.util.TreeSet;

/**
 * A text-dumper based on the <code>AirlineDumper</code> interface, for Project #2.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps it into a text file.
 */
public class TextDumper implements AirlineDumper<Airline> {
//  private final StringWriter writer;
  protected String file_name = null;
  //protected String dump;
  //protected File currfile = null;

  public TextDumper(StringWriter cw)
  {
    //this.writer = writer;
    this.file_name = cw.toString();//cw.toString();
  }

  /**
   * Create a TextDumper for a specific file name in-mind, but checks if it is an invalid file name.
   * @param name The name of the file to be dumped to.
   * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
   */
  public TextDumper(String name)
          throws IllegalArgumentException
  {
    if (name == null || name.isEmpty())
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
      this.file_name = file.getName();
    }
    else
    {
      throw new IllegalArgumentException("Sorry, looks like the file already exists!.");
      //this.currfile = file;
    }
  }

  /**
   * The main TextDumper dumper function that dumps the contents of an airline (and its flights) to a text file.
   * @param lufthansa The airline that will have its containing flights dumped to a text file.
   * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
   */
  @Override
  public void dump(Airline lufthansa) throws IOException
  {
//    try (
//      PrintWriter pw = new PrintWriter(this.file_name)
//      ) {
//      pw.println(airline.getName());
//
//      pw.flush();
//    }
    if (lufthansa == null)
    {
      throw new IOException("Airline given was blank!");
    }

//    PrintWriter printer = null;
    File thefile = new File(this.file_name);
//    FileWriter filewrite = null;
//
//    if (this.file_name != null)
//    {
//      thefile = new File(this.file_name);
//    }
//    else if (this.dump == null)
//    {
//      StringBuilder thefilename = new StringBuilder();
//      thefilename.append(lufthansa.getName());
//      thefilename.append(".txt");
//
//      thefile = new File(lufthansa.getName());
//    }
//    else if (this.dump != null)
//    {
//      thefile = new File(this.dump);
//    }

    FileWriter filewrite = new FileWriter(thefile);

    PrintWriter printer = new PrintWriter(filewrite);

//    if (this.dump != null)
//    {
//      printer = new PrintWriter(this.dump);
    Collection<Flight> flightDump = lufthansa.getFlights();
    String airline_name = lufthansa.getName();

    printer.println(airline_name);

    for (Flight runway : flightDump)
    {
      printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
    }
    printer.close();
  }
}
