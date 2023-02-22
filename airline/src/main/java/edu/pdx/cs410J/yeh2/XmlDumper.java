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
import java.io.PrintStream;

import java.io.IOException;
//import java.util.TreeSet;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.DefaultHandler;
import java.util.Calendar;
//import jaxb.*;

/**
 * An XML-dumper based on the <code>XmlDumper</code> interface, for Project #4.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps it into a XML file.
 */
public class XmlDumper implements AirlineDumper<Airline> {
    private final static AirlineXmlHelper tower = new AirlineXmlHelper();
    /**
     * {@code PrintStream DomoOhNo} is a <code>PrintStream</code>-object that will store DOM-related errors from dumping!
     * @see "xml-2x2.pdf, page 47"
     */
    private static PrintStream DomoOhNo = System.err;

    //private final static String publicID = null; //since only SystemID is required to be referenced
    //private final static String privateID = null;

    protected String file_name = null;

    /**
     * Create a <code>XML</code>Dumper for a specific file name in-mind, but checks if it is an invalid file name.
     * @param name The name of the file to be dumped to.
     * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
     */
    public XmlDumper(String name) throws IllegalArgumentException
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
     * Project #4: <code>XML</code> Dumping!
     * The main <code>XML</code>Dumper function that dumps the contents of an airline (and its flights) to a <code>XML</code>-file.
     * @param lufthansa The airline that will have its containing flights dumped to a <code>XML</code>-file.
     * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
     */
    @Override
    public void dump(Airline lufthansa) throws IOException
    {
        if (lufthansa == null)
        {
            throw new IOException("Airline given was blank!");
        }

        Document itinerary = null;
        File thefile = null;

        try
        {
            thefile = new File(this.file_name);
        }
        catch (NullPointerException e1)
        {
            System.err.println("Sorry, looks like the file name was invalid.");
            return;
        }

        try
        {
            // @see "xml-2x2.pdf, page 45 through 48"
            DocumentBuilderFactory airTrafficControl = DocumentBuilderFactory.newInstance();
            airTrafficControl.setValidating(true);

            DocumentBuilder trafficTower = airTrafficControl.newDocumentBuilder();
            trafficTower.setErrorHandler(tower);
            trafficTower.setEntityResolver(tower);

            DOMImplementation radar = trafficTower.getDOMImplementation();

            DocumentType greenRadar = radar.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);

            itinerary = radar.createDocument(null, "airline", greenRadar);
        }
        catch (ParserConfigurationException e2)
        {
            //System.err.println("[XML Parser Configuration Error]" + e2.getMessage());
            this.DomoOhNo.println("[XML Parser Configuration Error]" + e2.getMessage());
            return;
        }
        catch (DOMException e3)
        {
            //System.err.println("[XML DOM-Related Error]" + e3.getMessage());
            this.DomoOhNo.println("[XML DOM-Related Error]" + e3.getMessage());
            return;
        }




        FileWriter filewrite = new FileWriter(thefile);

        //PrintWriter printer = new PrintWriter(filewrite); // since Project #4 is going to dump the stuff into XML format instead of text-file dumpin'

        Collection<Flight> flightDump = lufthansa.getFlights();
        String airline_name = lufthansa.getName();

        //printer.println(airline_name);

        for (Flight runway : flightDump)
        {
            //printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
        }
        //printer.close();
    }
}