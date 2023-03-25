package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
//import edu.pdx.cs410J.yeh2.AirlineXmlHelper;

//import edu.pdx.cs410J.AirlineDumper;

//import javax.swing.text.html.parser.Parser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.BufferedReader;
import java.io.IOException;
//import java.io.InputStreamReader;
//import java.io.Reader;

import java.io.File;
import java.io.FileReader;

import java.io.FileNotFoundException;

//import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
//import java.util.Date;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TreeSet;

/**
 * An XML-parser based on the <code>AirlineParser</code> interface, for Project #4.
 * Reads the contents of a specified <code>XML</code>-file, creates an <code>airline</code>, including its <code>flight</code>s.
 */
public class XmlParser implements AirlineParser<Airline> {
    private String file_name;

    protected FileReader parse;
    protected File parsedfile;
    protected Airline lufthansa;
    protected static DateFormat date_formatter = DateFormat.getDateTimeInstance(3, 3, Locale.US);
    protected static final String date_formatting = "MM/dd/yyyy h:mm a";
    protected static SimpleDateFormat date_format = new SimpleDateFormat(date_formatting, Locale.US);
    Document itinerary = null;

//    /**
//     * Returns a new date with the depart/arrive node, checks if it is valid, but if it does not, throws!
//     * @param xmlDate
//     * @return parsedDate
//     * @throws ParseException If the date-node's stuffz are invalid!
//     */
//    private Date xmlStamper(Node xmlDate) throws ParseException
//    {
//        Element xmlCal = (Element) xmlDate;
//        Date parsedDate = null;
//
//        String hourString = xmlCal.getAttribute("hour");
//        String minuteString = xmlCal.getAttribute("minute");
//        String dayString = xmlCal.getAttribute("day");
//        String monthString = xmlCal.getAttribute("month");
//        String yearString = xmlCal.getAttribute("year");
//
//        if (!hourString.isEmpty() && !minuteString.isEmpty() && !dayString.isEmpty() && !monthString.isEmpty() && !yearString.isEmpty()) {
//            int month = Integer.parseInt(monthString) - 1; // 0 -> January, 1 -> February, etc.
//            //int month = Integer.parseInt(monthString);
//            int hour = Integer.parseInt(hourString);
//            String amPm = hour >= 12 ? "PM" : "AM";
//            parsedDate = date_format.parse(monthString + "/" + dayString + "/" + yearString + " " + hourString + ":" + minuteString + " " + amPm);
//        } else {
//            throw new ParseException("[XmlStamper] Uh oh, looks like one or more date/time attributes were missing or empty.", 0);
//        }
//
//        return parsedDate;
//    }

    /**
     * Returns a new date with the depart/arrive node, checks if it is valid, but if it does not, throws!
     * @param xmlDate
     * @return parsedDate
     * @throws ParseException If the date-node's stuffz are invalid!
     */
    private String xmlGlue(Node xmlDate) throws ParseException
    {
        Element xmlCal = (Element) xmlDate;
        String parsedString = null;

        String hourString = xmlCal.getAttribute("hour");
        String minuteString = xmlCal.getAttribute("minute");
        String dayString = xmlCal.getAttribute("day");
        String monthString = xmlCal.getAttribute("month");
        String yearString = xmlCal.getAttribute("year");

        if (!hourString.isEmpty() && !minuteString.isEmpty() && !dayString.isEmpty() && !monthString.isEmpty() && !yearString.isEmpty()) {
            int month = Integer.parseInt(monthString) - 1; // 0 -> January, 1 -> February, etc.
            //int month = Integer.parseInt(monthString);
            String parsedMonthString = Integer.toString(month);
            int hour = Integer.parseInt(hourString);
            String amPm = hour >= 12 ? "pm" : "am";
            int hour_12 = hour % 12;
            if (hour_12 == 0)
            {
                hour_12 = 12;
            }
            String parsedHourString = Integer.toString(hour_12);
            parsedString = (parsedMonthString + "/" + dayString + "/" + yearString + " " + parsedHourString + ":" + minuteString + " " + amPm);
        } else {
            if (hourString.isEmpty())
            {
                hourString = "N/A";
            }
            if (minuteString.isEmpty())
            {
                minuteString = "N/A";
            }
            if (dayString.isEmpty())
            {
                dayString = "N/A";
            }
            if (monthString.isEmpty())
            {
                monthString = "N/A";
            }
            if (yearString.isEmpty())
            {
                yearString = "N/A";
            }
            throw new ParseException("[XmlStamper] Uh oh, looks like one or more date/time attributes were missing or empty." + "[Debug - <hh/mm/dd/mo(nth)/ye(ar)>]" + hourString + minuteString + dayString + monthString + yearString, 0);
        }

        return parsedString;
    }

    /**
     * Constructs a new XmlParser object with the given file name, checks if it exists, but if it does not, throws
     * illegal argument exception, but if it exists, then set the name of the file to the constructed XmlParser's file name.
     *
     * @param filename The name of the file to be parsed.
     * @throws IllegalArgumentException If invalid file-names are given!
     */
    public XmlParser(String filename) throws IllegalArgumentException {
        if (filename.isEmpty()) {
            throw new IllegalArgumentException("Name of the file is invalid!");
        }
        else
        {
            this.file_name = filename;
        }
        //try {

        //File test = new File(file);
        //testfile = new FileReader(test);

        //} catch (FileNotFoundException e1) {
        //    throw new IllegalArgumentException("Looks like this file does not exist:", e1);
        //}

    }

    /**
     * Constructs a new XmlParser object with the given file, checks if it exists, but if it does not, throws
     * illegal argument exception, but if it exists, then set file to the constructed XmlParser's file.
     *
     * @param file The file to be parsed.
     * @throws IllegalArgumentException If the file-path/name is invalid.
     */

    public XmlParser(File file) throws IllegalArgumentException {
        if (!file.exists()) {
            throw new IllegalArgumentException("Name of the file is invalid!");
        }
        else
        {
            this.file_name = file.getName();
        }

        //try {

        //File test = new File(file);
        //testfile = new FileReader(test);

        //} catch (FileNotFoundException e1) {
        //    throw new IllegalArgumentException("Looks like this file does not exist:", e1);
        //}
    }

    /**
     * Project #4: XML Parsing!
     * The main (XML)parse[r]() function that parses <code>XML</code> files & creates an airline based on the <code>XML</code>-file!.
     * @return <code>Airline</code> Provides the airline with its associated, parsed-in flights.
     * @throws ParserException For parser-specific errors, e.g. invalid XML file, XML file that has DTD non-conformation.
     * @see "xml-2x2.pdf, page 39"
     */
    @Override
    public Airline parse() throws ParserException {

//        String Timestamp_Format = "MM/dd/yyyy HH:mm";
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
        String currstring = null;
        FileReader parsely = null;
        try
        {
            parsedfile = new File(this.file_name);
            parsely = new FileReader(parsedfile);
        }
        catch (FileNotFoundException e5)
        {
            //throw new ParserException("[XmlParser Exception Type V.] " + e5.getMessage());
            return null;
        }

        try
        {
            DocumentBuilderFactory airTrafficControl = DocumentBuilderFactory.newInstance();
            airTrafficControl.setValidating(true);
            AirlineXmlHelper helper = new AirlineXmlHelper();

            DocumentBuilder trafficTower = airTrafficControl.newDocumentBuilder();
            trafficTower.setEntityResolver(helper);
            trafficTower.setErrorHandler(helper);
            itinerary = trafficTower.parse(parsedfile);
        }
        catch (ParserConfigurationException e1)
        {
            throw new ParserException("[XmlParser Exception Type I.] " + e1.getMessage());
        }
        catch (SAXException e2)
        {
            throw new ParserException("[XmlParser Exception Type II.] " + e2.getMessage());
        }
        catch (IOException e3)
        {
            throw new ParserException("[XmlParser Exception Type III.] " + e3.getMessage());
        }
        catch (Exception e4)
        {
            throw new ParserException("[XmlParser Exception Type IV.] " + e4.getMessage());
        }

        //Element boeing = (Element) itinerary.getChildNodes().item(1);
        Element boeing = itinerary.getDocumentElement();
        String airline_name = boeing.getElementsByTagName("name").item(0).getTextContent();

        //List<Flight> flights = new ArrayList<Flight>
        NodeList flightXML = boeing.getElementsByTagName("flight");
        Flight curr = null;
        for (int idx = 0; idx < flightXML.getLength(); idx++)
        {
            Element flight = (Element) flightXML.item(idx);
            String flight_number = flight.getElementsByTagName("number").item(0).getTextContent();
            String src = flight.getElementsByTagName("src").item(0).getTextContent();
            try
            {
                //Date depart = xmlStamper(flight.getElementsByTagName("depart").item(0));
                String depart = xmlGlue(flight.getElementsByTagName("depart").item(0));
                String dest = flight.getElementsByTagName("dest").item(0).getTextContent();
                //Date arrive = xmlStamper(flight.getElementsByTagName("arrive").item(0));
                String arrive = xmlGlue(flight.getElementsByTagName("arrive").item(0));
                curr = new Flight(flight_number, src, depart.toString(), dest, arrive.toString());
                lufthansa.addFlight(curr);
            }
            catch (ParseException e6)
            {
               System.out.println("[XmlParser Flight XML-List Parsing Error] " + e6.getMessage());
               return null;
            }
        }

        return lufthansa;
        //lufthansa = new Airline(boeing);



//        NodeList flightXML = root.getChildNodes();
//        this.name = root.getAttribute("name");
//        this.flights = new TreeSet<Flight>(new Airline.air_traffic_controller());
//        this.flightnum = 0;
//
//        for (int idx = 0; idx != flightXML.getLength(); idx++)
//        {
//            if (flightXML.item(idx) instanceof Element)
//            {
//                Element runway = (Element) flightXML.item(idx);
//                if (runway.getNodeName().equals("flight"))
//                {
//                    Flight curr = null;
//                    this.flightsXML.add(curr);
//                    //curr = new Flight(runway.getAttribute("number"), runway.getAttribute("src"), runway.getAttribute("depart"), runway.getAttribute("dest"), runway.getAttribute("arrive"));
//                    //this.addFlight(curr);
//                    this.flightnum++;
//
//                    //System.err.println("[Flight XML Constructor Error]: " + e1.getMessage());
//                }
//            }
//        }
//
//        //this.flights = new LinkedList<Flight>();
//        this.flights = new TreeSet<Flight>(new Airline.air_traffic_controller());
//
//        NodeList flightXML2
//
//        //this.flightnum = 0;


    }
}