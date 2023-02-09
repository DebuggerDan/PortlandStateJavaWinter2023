package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

//import java.util.Collection;
import java.util.LinkedList;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.StringBuilder;
import java.io.Writer;

import java.io.File;
import java.io.FileWriter;

import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//import java.util.Collection;

import java.util.LinkedList;
import java.util.*;


/**
 * A {@code PrettyPrinter class} based on the <code>AirlineDumper</code> interface, for Project #2.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps the details, in a pretty manner, into a text file.
 * <p>
 *     1. Should use (Simple)DateFormat to make Dates look nice.
 *     2. Should use AirportNames to get airport names.
 *     3. Should include duration of each flight in minutes.
 * </p>
 */
public class PrettyPrinter implements AirlineDumper<Airline> {

    //  private final StringWriter writer;
    protected String file_name = null;
    //protected String dump;
    //protected File currfile = null;

    public PrettyPrinter(StringWriter cw)
    {
        //this.writer = writer;
        this.file_name = cw.toString();//cw.toString();
    }

    /**
     * Create a PrettyPrinter for a specific file name in-mind (and/or prints to screen), but checks if it is an invalid file name.
     * @param name The name of the file to be dumped to.
     * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
     */
    public PrettyPrinter(String name)
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
     * Create a PrettyPrinter for a specific file in-mind, but checks if it is an invalid file.
     * @param file The name of the file to be dumped to.
     * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
     */
    public PrettyPrinter(File file) throws IllegalArgumentException {
        if (!file.exists()) {
            this.file_name = file.getName();
        } else {
            throw new IllegalArgumentException("Sorry, looks like the file already exists!.");
            //this.currfile = file;
        }
    }

    /**
     * The main <code>PrettyPrinter  function</code> that dumps the contents of an <code>Airline</code> (and its flights), prints it and/or saves it to file specified.
     * @param lufthansa The airline that will have its containing flights printed to the command-line.
     * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
     */
    @Override
    public void dump(Airline lufthansa) throws IOException
    {
        if (lufthansa == null)
        {
            throw new IOException("Airline given was blank!");
        }

        File thefile = new File(this.file_name);


        FileWriter filewrite = new FileWriter(thefile);

        PrintWriter printer = new PrintWriter(filewrite);

        //LinkedList<Flight> flightDump = lufthansa.getFlights();
        TreeSet<Flight> flightDump = lufthansa.getFlights();
        String airline_name = lufthansa.getName();

        printer.println(airline_name);

        for (Flight runway : flightDump)
        {
            printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
        }
        printer.close();
    }

}