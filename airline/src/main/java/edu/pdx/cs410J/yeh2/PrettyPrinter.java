package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineDumper;
import edu.pdx.cs410J.AirportNames;

//import java.util.Collection;
import java.io.*;
import java.util.LinkedList;

import java.lang.StringBuilder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

//import java.util.Collection;

import java.util.LinkedList;
import java.util.*;

/**
 * A {@code PrettyPrinter class} based on the <code>AirlineDumper</code> interface, for Project #3.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps the details, in a pretty manner, into a text file.
 * <p>
 *     1. Should use (Simple)DateFormat to make Dates look nice.
 *     2. Should use AirportNames to get airport names.
 *     3. Should include duration of each flight in minutes.
 * </p>
 */
public class PrettyPrinter implements AirlineDumper<Airline> {
    protected static DateFormat pretty_format = DateFormat.getDateTimeInstance(0, 0, Locale.US);

    //private static AirportNames iata = new AirportNames();
    //private final StringWriter writer;

    protected String file_name = null;
    protected Boolean save = false;
    protected String plotter = null;

    /*
    * A pretty ASCII art-picture of a airplane & clouds!
    * Credits & Author: "Small airplane w/clouds" by Joan Shark
    * Source: https://www.asciiart.eu/vehicles/airplanes
    */
    protected static String prettyASCII = "prettyASCII.txt";

    //protected String dump;
    //protected File currfile = null;

    public PrettyPrinter(StringWriter cw)
    {
        //this.writer = writer;
        this.file_name = cw.toString();//cw.toString();
    }

    /**
     * Create a PrettyPrinter to be, by-default, a print-only PrettyPrinter
     * This default constructor, since it has no parameters, will automatically set the PrettyPrinter to print to the command-line.
     */
    public PrettyPrinter()
    {
        this.save = false;
        this.file_name = null;
    }

    /**
     * A function that uses the <class>AirportNames</class> to retrieve string-mappings of 3-digit airport codes to airport names, if found!
     * <p>
     *     IATA, known as the International Air Transport Association, usually handles the 3-letter designation of airports internationally.
     *     Yet, particularly for the US, the FCC's own rules/designations may reign supreme...
     * </p>
     * @see <a href="https://youtu.be/jfOUVYQnuhw">"✈️ The Maddening Mess of Airport Codes! ✈️" by CGPGrey (YouTube Video)</a>
     *
     * @param airport_code A three-digit letter-based <code>airport_code</code>.
     * @return The name of the <code>airport_code</code>, if found, as a <code>String</code>! If not, return a <code>String</code> with the original three-letter <code>airport_code</code> with some prettyness.
     */
    public String IATA(String airport_code)// throws IllegalArgumentException
    {
        String result = AirportNames.getName(airport_code);
        if (result == null)
        {
            result = "'" + airport_code + "' (Unknown Airport Code)";
        }

        return result;
    }

    /**
     * Create a PrettyPrinter for a specific file name in-mind (and/or prints to screen), but checks if it is an invalid file name.
     * This constructor, since it only has a parameter for file name, will automatically set the PrettyPrinter to save to that file.
     * (vs. PrettyPrinter just printing to console)
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
            this.save = true;
        }
    }

    /**
     * Create a PrettyPrinter for a specific file name in-mind (and/or prints to screen), but checks if it is an invalid file name.
     * @param name The name of the file to be dumped to.
     * @param save Whether {@code PrettyPrinter()} should save to specified file or not.
     */
    public PrettyPrinter(String name, Boolean save)
            throws IllegalArgumentException
    {
        if (name == null || name.isEmpty() || !save)
        {
            this.file_name = null;
            this.save = false;
            //throw new IllegalArgumentException("Sorry, looks like the name of the file was incorrect.");
        }
        else
        {
            if (save)
            {
                this.file_name = name;
                this.save = true;
            }
//            else
//            {
//                this.file_name = null;
//            }
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
            this.save = true;
        } else {
            throw new IllegalArgumentException("Sorry, looks like the file already exists!");
            //this.currfile = file;
        }
    }

    /**
     * This function returns a string containing the prettified airplane informationz!
     * @return The prettified poster!
     * If the {@code PrettyPrinter} does not already have plotted infoz to return, then it will return null & display an error message!
     */
    public String getPlottedPrint()
    {
        String poster = null;
        if (this.plotter != null)
        {
            poster = this.plotter;
        }
        else
        {
            System.err.println("Error! The PrettyPrinter attempted to return a non-existent prettified airplane infoz string!");
        }

        return poster;
    }

    /**
     * <p>
     *     The main <code>PrettyPrinter function</code> that dumps the contents of an <code>Airline</code> (and its flights), prints it and/or saves it to file specified.
     *      * If save is true, then of course, dump() will save the prettified airline to the file within PrettyPrinter.
     *      * If not, then dump() will create a string that contains that prettified airline information.
     * </p>
     * @param lufthansa The airline that will have its containing flights printed to the command-line or to a file.
     * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
     */
    @Override
    public void dump(Airline lufthansa) throws IOException
    {
        if (lufthansa == null)
        {
            throw new IOException("Airline given was blank!");
        }

        File thefile = null;
        FileWriter filewrite = null;
        PrintWriter printer = null;
        StringWriter stringy = null;

        // PrettyPrinter Option A: Save to File
        if (this.file_name != null && this.save)
        {
            thefile = new File(this.file_name);

            filewrite = new FileWriter(thefile);

            printer = new PrintWriter(filewrite);
        }
        // PrettyPrinter Option B: Save a String (to String this.plotter), so as to print to command-line!
        else if (!this.save)
        {
            stringy = new StringWriter();
            printer = new PrintWriter(stringy);
        }
            //LinkedList<Flight> flightDump = lufthansa.getFlights();
            //TreeSet<Flight> flightDump = lufthansa.getFlights();
            Collection<Flight> flightDump = lufthansa.getFlights();
            String airline_name = lufthansa.getName();
            int flightnum = 1;
            //printer.println(airline_name);

            try (InputStream displayme = Project3.class.getResourceAsStream(prettyASCII)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();
                    int idx = 0;
                    while (display_line != null && idx != 2)
                    {
                        display_line = display_buffer.readLine();
                        idx--;
                    }

                    while (display_line != null) {
                        printer.println(display_line);
                        display_line = display_buffer.readLine();
                    }
                }
            } catch (IOException m1) {
                System.err.println("Error! prettyASCII.txt not found!");
            }

            String airport1 = null;
            String airport2 = null;

            String date1 = null;
            String date2 = null;

            DateFormat segment_format = new SimpleDateFormat("EEEE, MMMM d, yyyy '@' h:mm");
            DateFormat ampm_format = new SimpleDateFormat(" a");

            long flightTime;

            printer.println("Thank you for using the PrettyPrinter!");

            printer.println("For airline, '" + airline_name + "', we have the following flight(s) scheduled...\n");
            printer.println("-----");

        for (Flight runway : flightDump)
        {
            airport1 = IATA(runway.getSource());
            airport2 = IATA(runway.getDestination());
            //                date1 = (pretty_format.format(runway.getDepartureDate())).toLowerCase();
            //                date2 = (pretty_format.format(runway.getArrivalDate())).toLowerCase();

            date1 = segment_format.format(runway.getDepartureDate()) + (ampm_format.format(runway.getDepartureDate())).toLowerCase();
            date2 = segment_format.format(runway.getArrivalDate()) + (ampm_format.format(runway.getArrivalDate())).toLowerCase();
            flightTime = runway.getFlightTime();

            printer.println("#" + flightnum + ".) ");
            printer.println("Official Flight Number:\t" + runway.getNumber());
            printer.println("---");
            printer.println("Departing from...\t'" + airport1 + "', on\t" + date1);
            printer.println("To arrive at destination...\t'" + airport2 + "', on\t" + date2 + "\n");
            //              printer.println("@ " + date1 + "\t@ " + date2);
            if (flightTime == -1337) {
                printer.println("Approximate Flight Duration:\t" + "'N/A' (negative flight minutes detected)");
            } else {
                printer.println("Approximate Flight Duration:\t" + flightTime + " minute(s)!");
            }

            printer.println("---\n");

            //                        ", " + runway.getSource() +
            //                        ", " + runway.getDepartureString() +
            //                        ", " + runway.getDestination() +
            //                        ", " + runway.getArrivalString());
            flightnum++;
        }
        printer.close();

//        if (!this.save)
//        {
//            for (Flight runway : flightDump)
//            {
//                airport1 = IATA(runway.getSource());
//                airport2 = IATA(runway.getDestination());
//                //                date1 = (pretty_format.format(runway.getDepartureDate())).toLowerCase();
//                //                date2 = (pretty_format.format(runway.getArrivalDate())).toLowerCase();
//
//                date1 = segment_format.format(runway.getDepartureDate()) + (ampm_format.format(runway.getDepartureDate())).toLowerCase();
//                date2 = segment_format.format(runway.getArrivalDate()) + (ampm_format.format(runway.getArrivalDate())).toLowerCase();
//                flightTime = runway.getFlightTime();
//
//                System.out.println(flightnum + ".) " + "Flight Number: " + runway.getNumber());
//                System.out.println("---");
//                System.out.println("Departing from... '" + airport1 + "', on " + date1);
//                System.out.println("To arrive at destination... '" + airport2 + "', on " + date2 + "\n");
//                //              System.out.println("@ " + date1 + "\t@ " + date2);
//                if (flightTime == -1337) {
//                    System.out.println("Approximate Flight Duration (in minutes):\t" + "'N/A' (negative flight minutes detected)");
//                } else {
//                    System.out.println("Approximate Flight Duration (in minutes):\t" + flightTime);
//                }
//
//                System.out.println("---\n");
//
//                //                        ", " + runway.getSource() +
//                //                        ", " + runway.getDepartureString() +
//                //                        ", " + runway.getDestination() +
//                //                        ", " + runway.getArrivalString());
//                flightnum++;
//            }
//
//        }
//        printer.close();

            if (!this.save)
            {
                try
                {
                    this.plotter = stringy.toString();
                }
                catch (NullPointerException m2)
                {
                    System.err.println("Uh oh, looks like PrettyPrinter was not able to save the prettified print-string!");
                }
            }
    }

}