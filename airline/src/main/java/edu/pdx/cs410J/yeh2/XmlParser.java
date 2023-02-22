package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
//import edu.pdx.cs410J.yeh2.AirlineXmlHelper;

//import edu.pdx.cs410J.AirlineDumper;

//import javax.swing.text.html.parser.Parser;
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
import java.util.Locale;

/**
 * An XML-parser based on the <code>AirlineParser</code> interface, for Project #4.
 * Reads the contents of a specified <code>XML</code>-file, creates an <code>airline</code>, including its <code>flight</code>s.
 */
public class XmlParser implements AirlineParser<Airline> {

    /**
     * Project #4: XML Parsing!
     * The main (XML)parse[r]() function that parses <code>XML</code> files & creates an airline based on the <code>XML</code>-file!.
     * @return <code>Airline</code> Provides the airline with its associated, parsed-in flights.
     * @throws ParserException For parser-specific errors, e.g. invalid XML file, XML file that has DTD non-conformation.
     */
    @Override
    public Airline parse() throws ParserException {
        return null;
    }
}