package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineDumper;

//import java.util.Collection;
import java.io.*;
import java.util.Collection;
//import java.util.LinkedList;

//import java.lang.StringBuilder;
//import java.io.Writer;

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

    private String file_name = null;
    private String src = null;
    private String dest = null;
    private File currfile = null;
    private FileWriter filexmler = null;
    private Boolean searchSpecific = false;
    private Boolean directFile = false;
    private Boolean shakespeare = false;
    private PrintWriter poet = null;

    /**
     * Project #5: <code>XML</code>-formatted {@code HTTP requests}!
     * Create a <code>XML</code>Dumper for a specific poet ({@code Writer}).
     * @param poet The poet ({@code Writer}) to be dumped to.
     * @param src The -search src-airport code to find!
     * @param dest The -search dest-airport code to find!
     * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
     * @see AirlineServlet
     * @see Project5
     */
    public XmlDumper(PrintWriter poet, String src, String dest)// throws IllegalArgumentException
    {
//        if (poet.toString() == null || poet.toString().isEmpty()
//        {
//            throw new IllegalArgumentException("Sorry, looks like the name of the file was incorrect.");
//        }
//        else
//        {
        this.poet = poet;

        if (src == null || dest == null)
        {
            this.shakespeare = true;
            this.searchSpecific = false;
            this.src = null;
            this.dest = null;
        }

        else
        {
            this.shakespeare = true;
            this.searchSpecific = true;
            this.src = src;
            this.dest = dest;
        }
//        }
    }

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
            this.poet = null;
        }
    }

    /**
     * Create a <code>XML</code>Dumper for a specific file , but checks if it is an invalid file.
     * @param file The file to be dumped to.
     * @throws IllegalArgumentException If the file name is invalid, it throws an Illegal Argument exception thingy.
     */
    public XmlDumper(File file) throws IllegalArgumentException, IOException
    {
        //if (!file.exists())
        if (file == null)
        {
            throw new IllegalArgumentException("Sorry, looks like the file was blank.");
        }
//            if (!file.createNewFile())
//            {
//                throw new IllegalArgumentException("Sorry, looks like the file is invalid.");
//            }
            //this.file_name = file.getName();
        this.currfile = file;
        this.directFile = true;
        this.shakespeare = false;
        this.poet = null;

        //throw new IllegalArgumentException("Sorry, looks like the file is invalid.");
        //else
        //{

//            try
//            {
//                this.filexmler = new FileWriter(file);
//            }
//            catch (IOException xml1)
//            {
//                throw new IOException("Sorry, looks like the file was unable to be written to [XMLDumper]: " + xml1.getMessage());
//            }
        //this.currfile = file;
            //this.poet = null;
        //this.directFile = true;
        //}
        //this.poet = null;

    }

    /**
     * Project #5: <code>XML</code>-String dumper thingy!
     */

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
        int directFlights = 0;

        if (!this.shakespeare)
        {
            if (this.directFile)
            {
                if (!this.currfile.exists())
                {
                    if (!this.currfile.createNewFile())
                    {
                        throw new IOException("[File Initialization Error #1 in XmlDumper <Project #6>] Sorry, looks like the file is invalid.");
                    }
                    this.file_name = this.currfile.getName();
                }
                else
                {
                    thefile = this.currfile;
                }
            }
            try
            {
                thefile = new File(this.file_name);
            }
            catch (NullPointerException e1)
            {
                System.err.println("[File Initialization Error #2 in XmlDumper <Project #6>] Sorry, looks like the file name was invalid.");
                return;
            }
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
            DomoOhNo.println("[XML Parser Configuration Error]" + e2.getMessage());
            return;
        }
        catch (DOMException e3)
        {
            //System.err.println("[XML DOM-Related Error, Stage B]" + e3.getMessage());
            DomoOhNo.println("[XML DOM-Related Error, Stage A]" + e3.getMessage());
            //return;
        }
        // Boeing = root element
        Element boeing = null;
        try
        {
            //boeing = itinerary.createElement("airline");//itinerary.getDocumentElement();
            boeing = itinerary.getDocumentElement();
            //itinerary.appendChild(boeing);

            Element airlineName = itinerary.createElement("name");
            boeing.appendChild(airlineName);
            airlineName.appendChild(itinerary.createTextNode(lufthansa.getName()));
        }
        catch (DOMException e4)
        {
            //System.err.println("[XML DOM-Related Error, Stage B]" + e4.getMessage());
            DomoOhNo.println("[XML DOM-Related Error, Stage B]" + e4.getMessage());
            //return;
        }

        //FileWriter filewrite = new FileWriter(thefile);
        //PrintWriter printer = new PrintWriter(filewrite); // since Project #4 is going to dump the stuff into XML format instead of text-file dumpin'

        Collection<Flight> flightDump = lufthansa.getFlights();
        //String airline_name = lufthansa.getName();

        //printer.println(airline_name);

        if (this.searchSpecific)
        {
            directFlights = 0;
            for (Flight runway : flightDump)
            {
                if (!runway.getSource().equals(this.src) || !runway.getDestination().equals(this.dest))
                {
                    continue;
                }
                else
                {
                    directFlights++;
                }
                //{
                try {
                    Element runwayFlight = itinerary.createElement("flight");
                    boeing.appendChild(runwayFlight);

                    Element flightNumber = itinerary.createElement("number");
                    runwayFlight.appendChild(flightNumber);
                    flightNumber.appendChild(itinerary.createTextNode(String.valueOf(runway.getNumber())));

                    Element flightsrc = itinerary.createElement("src");
                    runwayFlight.appendChild(flightsrc);
                    flightsrc.appendChild(itinerary.createTextNode(runway.getSource()));

                    Element flightdep = itinerary.createElement("depart");
                    runwayFlight.appendChild(flightdep);

                    Element flightdep_date = itinerary.createElement("date");
                    flightdep.appendChild(flightdep_date);
                    flightdep_date.setAttribute("day", String.valueOf(runway.getDepartureXml().get(Calendar.DAY_OF_MONTH)));
                    flightdep_date.setAttribute("month", String.valueOf(runway.getDepartureXml().get(Calendar.MONTH)));
                    flightdep_date.setAttribute("year", String.valueOf(runway.getDepartureXml().get(Calendar.YEAR)));

                    Element flightdep_time = itinerary.createElement("time");
                    flightdep.appendChild(flightdep_time);
                    flightdep_time.setAttribute("hour", String.valueOf(runway.getDepartureXml().get(Calendar.HOUR_OF_DAY)));
                    flightdep_time.setAttribute("minute", String.valueOf(runway.getDepartureXml().get(Calendar.MINUTE)));

                    Element flightdest = itinerary.createElement("dest");
                    runwayFlight.appendChild(flightdest);
                    flightdest.appendChild(itinerary.createTextNode(runway.getDestination()));

                    Element flightarrive = itinerary.createElement("arrive");
                    runwayFlight.appendChild(flightarrive);

                    Element flightarrive_date = itinerary.createElement("date");
                    flightarrive.appendChild(flightarrive_date);
                    flightarrive_date.setAttribute("day", String.valueOf(runway.getArrivalXml().get(Calendar.DAY_OF_MONTH)));
                    flightarrive_date.setAttribute("month", String.valueOf(runway.getArrivalXml().get(Calendar.MONTH)));
                    flightarrive_date.setAttribute("year", String.valueOf(runway.getArrivalXml().get(Calendar.YEAR)));

                    Element flightarrive_time = itinerary.createElement("time");
                    flightarrive.appendChild(flightarrive_time);
                    flightarrive_time.setAttribute("hour", String.valueOf(runway.getArrivalXml().get(Calendar.HOUR_OF_DAY)));
                    flightarrive_time.setAttribute("minute", String.valueOf(runway.getArrivalXml().get(Calendar.MINUTE)));


                }
                catch (DOMException e7)
                {
                    //System.err.println("[XML DOM-Related Error, Stage C]" + e7.getMessage());
                    DomoOhNo.println("[XML DOM-Related Error, Stage C.II {Project #5; during src+dest specific search}]" + e7.getMessage());
                    //return;
                }
                //printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
                //}

            }
        }

        else// if (!this.searchSpecific)
        {
            for (Flight runway : flightDump)
            {
                try {
                    Element runwayFlight = itinerary.createElement("flight");
                    boeing.appendChild(runwayFlight);

                    Element flightNumber = itinerary.createElement("number");
                    runwayFlight.appendChild(flightNumber);
                    flightNumber.appendChild(itinerary.createTextNode(String.valueOf(runway.getNumber())));

                    Element flightsrc = itinerary.createElement("src");
                    runwayFlight.appendChild(flightsrc);
                    flightsrc.appendChild(itinerary.createTextNode(runway.getSource()));

                    Element flightdep = itinerary.createElement("depart");
                    runwayFlight.appendChild(flightdep);

                    Element flightdep_date = itinerary.createElement("date");
                    flightdep.appendChild(flightdep_date);
                    flightdep_date.setAttribute("day", String.valueOf(runway.getDepartureXml().get(Calendar.DAY_OF_MONTH)));
                    flightdep_date.setAttribute("month", String.valueOf(runway.getDepartureXml().get(Calendar.MONTH)));
                    flightdep_date.setAttribute("year", String.valueOf(runway.getDepartureXml().get(Calendar.YEAR)));

                    Element flightdep_time = itinerary.createElement("time");
                    flightdep.appendChild(flightdep_time);
                    flightdep_time.setAttribute("hour", String.valueOf(runway.getDepartureXml().get(Calendar.HOUR_OF_DAY)));
                    flightdep_time.setAttribute("minute", String.valueOf(runway.getDepartureXml().get(Calendar.MINUTE)));

                    Element flightdest = itinerary.createElement("dest");
                    runwayFlight.appendChild(flightdest);
                    flightdest.appendChild(itinerary.createTextNode(runway.getDestination()));

                    Element flightarrive = itinerary.createElement("arrive");
                    runwayFlight.appendChild(flightarrive);

                    Element flightarrive_date = itinerary.createElement("date");
                    flightarrive.appendChild(flightarrive_date);
                    flightarrive_date.setAttribute("day", String.valueOf(runway.getArrivalXml().get(Calendar.DAY_OF_MONTH)));
                    flightarrive_date.setAttribute("month", String.valueOf(runway.getArrivalXml().get(Calendar.MONTH)));
                    flightarrive_date.setAttribute("year", String.valueOf(runway.getArrivalXml().get(Calendar.YEAR)));

                    Element flightarrive_time = itinerary.createElement("time");
                    flightarrive.appendChild(flightarrive_time);
                    flightarrive_time.setAttribute("hour", String.valueOf(runway.getArrivalXml().get(Calendar.HOUR_OF_DAY)));
                    flightarrive_time.setAttribute("minute", String.valueOf(runway.getArrivalXml().get(Calendar.MINUTE)));


                }
                catch (DOMException e5)
                {
                    //System.err.println("[XML DOM-Related Error, Stage C]" + e5.getMessage());
                    DomoOhNo.println("[XML DOM-Related Error, Stage C]" + e5.getMessage());
                    //return;
                }
                //printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
            }
        }
        // If we are saving the XML results to an XML file.
        if (!this.shakespeare)
        {
            if (this.searchSpecific)
            {
                if (directFlights == 0) {
                    System.out.println("[XML Dumper {Project #5]] There were no direct flights found between '" + this.src + "' and '" + this.dest + "'.");
                    return;
                }
            }
            try
            {
                DOMSource cybertron = new DOMSource(itinerary);
                //StreamResult prism = new StreamResult(System.out);//new StreamResult(thefile);
//                StreamResult darksideofthemoon = new StreamResult(this.poet);
//
//                TransformerFactory allSpark = TransformerFactory.newInstance();
//                Transformer optimusPamAm = allSpark.newTransformer();
//
//                optimusPamAm.setOutputProperty(OutputKeys.INDENT, "yes");
//                optimusPamAm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
//                // Debug for preliminary Project #5 integration tests
//                //optimusPamAm.transform(cybertron, prism);
//                optimusPamAm.transform(cybertron, darksideofthemoon);
//
//                this.poet.flush();
//                this.poet.close();
                save(cybertron);

            }
            catch (TransformerException e7)
            {
                System.err.println("[XML Dumper, Transformer Error {Project #5 - Non-Shakespeare Mode}]" + e7.getMessage());
                return;
            }
            //printer.close();
        }
        // If we are saving the XML results to be returned to the client via the HTTP GET response's getWriter (PrintWriter) object.
        else if (this.shakespeare)
        {
            if (this.searchSpecific)
            {
                if (directFlights == 0) {
                    //System.out.println("[XML Dumper {Project #5]] There were no direct flights found between '" + this.src + "' and '" + this.dest + "'.");
                    poet = null;
                    return;
                }
            }
            if (this.poet != null) {
                try {
                    DOMSource cybertron = new DOMSource(itinerary);
                    //StreamResult prism = new StreamResult(System.out);//new StreamResult(thefile);
                    StreamResult darksideofthemoon = new StreamResult(this.poet);

                    TransformerFactory allSpark = TransformerFactory.newInstance();
                    Transformer optimusPamAm = allSpark.newTransformer();

                    optimusPamAm.setOutputProperty(OutputKeys.INDENT, "yes");
                    optimusPamAm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
                    // Debug for preliminary Project #4 integration tests
                    //optimusPamAm.transform(cybertron, prism);
                    optimusPamAm.transform(cybertron, darksideofthemoon);

                } catch (TransformerException e6) {
                    System.err.println("[XML Dumper, Transformer Error {Shakepeare Mode}]" + e6.getMessage());
                    return;
                }
                //printer.close();
            }
        }

        //
    }

    public void save(DOMSource cybertron) throws TransformerException, IOException
    {
        TransformerFactory allSpark = TransformerFactory.newInstance();
        Transformer optimusPamAm = allSpark.newTransformer();

        optimusPamAm.setOutputProperty(OutputKeys.INDENT, "yes");
        optimusPamAm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);

        try (FileWriter thefile = new FileWriter(this.currfile))
        {
            StreamResult darksideofthemoon = new StreamResult(thefile);
            optimusPamAm.transform(cybertron, darksideofthemoon);
        }
        catch (IOException e8)
        {
            System.err.println("[XML Dumper, Transformer Error]" + e8.getMessage());
        }
    }

//    /**
//     * Project #5: <code>XML</code>-formatted {@code HTTP requests}!
//     * Dumps the given Airline object as a <code>XML</code>-format!
//     * @param lufthansa The <code>Airline</code> object to be dumped into <code>XML</code> format!
//     * @return concorde The <code>XML</code>-formatted, dumped <code>Airline</code> for the {@code HTTP request}!
//     * @throws IOException If the given <code>Airline</code> object is blank and/or if there are <code>XML</code>-dumping related stuffz!
//     * @see AirlineServlet
//     */
//    public static String dumpMail(Airline lufthansa) throws IOException
//    {
//        {
//            if (lufthansa == null)
//            {
//                throw new IOException("Airline given was blank!");
//            }
////
//            Document itinerary = null;
////            File thefile = null;
//
////            try
////            {
////                thefile = new File(this.file_name);
////            }
////            catch (NullPointerException e1)
////            {
////                System.err.println("Sorry, looks like the file name was invalid.");
////                return;
////            }
//
//            try
//            {
//                // @see "xml-2x2.pdf, page 45 through 48"
//                DocumentBuilderFactory airTrafficControl = DocumentBuilderFactory.newInstance();
//                airTrafficControl.setValidating(true);
//
//                DocumentBuilder trafficTower = airTrafficControl.newDocumentBuilder();
//                trafficTower.setErrorHandler(tower);
//                trafficTower.setEntityResolver(tower);
//
//                DOMImplementation radar = trafficTower.getDOMImplementation();
//
//                DocumentType greenRadar = radar.createDocumentType("airline", AirlineXmlHelper.PUBLIC_ID, AirlineXmlHelper.SYSTEM_ID);
//
//                itinerary = radar.createDocument(null, "airline", greenRadar);
//            }
//            catch (ParserConfigurationException e2)
//            {
//                //System.err.println("[XML Parser Configuration Error]" + e2.getMessage());
//                DomoOhNo.println("[XML Parser Configuration Error]" + e2.getMessage());
//                return null;
//            }
//            catch (DOMException e3)
//            {
//                //System.err.println("[XML DOM-Related Error, Stage B]" + e3.getMessage());
//                DomoOhNo.println("[XML DOM-Related Error, Stage A]" + e3.getMessage());
//                //return;
//            }
//            // Boeing = root element
//            Element boeing = null;
//            try
//            {
//                //boeing = itinerary.createElement("airline");//itinerary.getDocumentElement();
//                boeing = itinerary.getDocumentElement();
//                //itinerary.appendChild(boeing);
//
//                Element airlineName = itinerary.createElement("name");
//                boeing.appendChild(airlineName);
//                airlineName.appendChild(itinerary.createTextNode(lufthansa.getName()));
//            }
//            catch (DOMException e4)
//            {
//                //System.err.println("[XML DOM-Related Error, Stage B]" + e4.getMessage());
//                DomoOhNo.println("[XML DOM-Related Error, Stage B]" + e4.getMessage());
//                //return;
//            }
//
//            //FileWriter filewrite = new FileWriter(thefile);
//            //PrintWriter printer = new PrintWriter(filewrite); // since Project #4 is going to dump the stuff into XML format instead of text-file dumpin'
//
//            Collection<Flight> flightDump = lufthansa.getFlights();
//            //String airline_name = lufthansa.getName();
//
//            //printer.println(airline_name);
//
//            for (Flight runway : flightDump)
//            {
//                try {
//                    Element runwayFlight = itinerary.createElement("flight");
//                    boeing.appendChild(runwayFlight);
//
//                    Element flightNumber = itinerary.createElement("number");
//                    runwayFlight.appendChild(flightNumber);
//                    flightNumber.appendChild(itinerary.createTextNode(String.valueOf(runway.getNumber())));
//
//                    Element flightsrc = itinerary.createElement("src");
//                    runwayFlight.appendChild(flightsrc);
//                    flightsrc.appendChild(itinerary.createTextNode(runway.getSource()));
//
//                    Element flightdep = itinerary.createElement("depart");
//                    runwayFlight.appendChild(flightdep);
//
//                    Element flightdep_date = itinerary.createElement("date");
//                    flightdep.appendChild(flightdep_date);
//                    flightdep_date.setAttribute("day", String.valueOf(runway.getDepartureXml().get(Calendar.DAY_OF_MONTH)));
//                    flightdep_date.setAttribute("month", String.valueOf(runway.getDepartureXml().get(Calendar.MONTH)));
//                    flightdep_date.setAttribute("year", String.valueOf(runway.getDepartureXml().get(Calendar.YEAR)));
//
//                    Element flightdep_time = itinerary.createElement("time");
//                    flightdep.appendChild(flightdep_time);
//                    flightdep_time.setAttribute("hour", String.valueOf(runway.getDepartureXml().get(Calendar.HOUR_OF_DAY)));
//                    flightdep_time.setAttribute("minute", String.valueOf(runway.getDepartureXml().get(Calendar.MINUTE)));
//
//                    Element flightdest = itinerary.createElement("dest");
//                    runwayFlight.appendChild(flightdest);
//                    flightdest.appendChild(itinerary.createTextNode(runway.getDestination()));
//
//                    Element flightarrive = itinerary.createElement("arrive");
//                    runwayFlight.appendChild(flightarrive);
//
//                    Element flightarrive_date = itinerary.createElement("date");
//                    flightarrive.appendChild(flightarrive_date);
//                    flightarrive_date.setAttribute("day", String.valueOf(runway.getArrivalXml().get(Calendar.DAY_OF_MONTH)));
//                    flightarrive_date.setAttribute("month", String.valueOf(runway.getArrivalXml().get(Calendar.MONTH)));
//                    flightarrive_date.setAttribute("year", String.valueOf(runway.getArrivalXml().get(Calendar.YEAR)));
//
//                    Element flightarrive_time = itinerary.createElement("time");
//                    flightarrive.appendChild(flightarrive_time);
//                    flightarrive_time.setAttribute("hour", String.valueOf(runway.getArrivalXml().get(Calendar.HOUR_OF_DAY)));
//                    flightarrive_time.setAttribute("minute", String.valueOf(runway.getArrivalXml().get(Calendar.MINUTE)));
//
//
//                }
//                catch (DOMException e5)
//                {
//                    //System.err.println("[XML DOM-Related Error, Stage C]" + e5.getMessage());
//                    DomoOhNo.println("[XML DOM-Related Error, Stage C]" + e5.getMessage());
//                    //return;
//                }
//                //printer.println(runway.getNumber() + ", " + runway.getSource() + ", " + runway.getDepartureString() + ", " + runway.getDestination() + ", " + runway.getArrivalString());
//            }
//
//            try
//            {
//                DOMSource cybertron = new DOMSource(itinerary);
//                //StreamResult prism = new StreamResult(System.out);//new StreamResult(thefile);
//                StreamResult darksideofthemoon = new StreamResult(thefile);
//
//                TransformerFactory allSpark = TransformerFactory.newInstance();
//                Transformer optimusPamAm = allSpark.newTransformer();
//
//                optimusPamAm.setOutputProperty(OutputKeys.INDENT, "yes");
//                optimusPamAm.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, AirlineXmlHelper.SYSTEM_ID);
//                // Debug for preliminary Project #4 integration tests
//                //optimusPamAm.transform(cybertron, prism);
//                optimusPamAm.transform(cybertron, darksideofthemoon);
//
//            }
//            catch (TransformerException e6)
//            {
//                System.err.println("[XML Dumper, Transformer Error]" + e6.getMessage());
//                return null;
//            }
//            //printer.close();
//        }
//    }
}