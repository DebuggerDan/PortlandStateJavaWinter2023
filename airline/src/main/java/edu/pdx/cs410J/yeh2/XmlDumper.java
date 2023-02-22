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
 * An XML-dumper based on the <code>XmlDumper</code> interface, for Project #4.
 * Takes the contents of an <code>airline</code> (and its <code>flight</code>s) and dumps it into a XML file.
 */
public class XmlDumper implements AirlineDumper<Airline> {

    /**
     * Project #4: <code>XML</code> Dumping!
     * The main <code>XML</code>Dumper function that dumps the contents of an airline (and its flights) to a <code>XML</code>-file.
     * @param lufthansa The airline that will have its containing flights dumped to a <code>XML</code>-file.
     * @throws IOException If the file name is invalid, cannot be written to, or maybe if the airline is blank, etc., then throws a Input/Output Exception.
     */
    @Override
    public void dump(Airline lufthansa) throws IOException
    {

    }
}