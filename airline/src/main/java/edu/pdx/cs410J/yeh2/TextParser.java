package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
//import edu.pdx.cs410J.AirlineDumper;

import javax.swing.text.html.parser.Parser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.io.File;
import java.io.FileReader;

import java.io.FileNotFoundException;

import java.io.IOException;

/**
 * A text parser based on the <code>AirlineParser</code> interface, for Project #2.
 * Reads the contents of a specified text-file, creates an <code>airline</code>, including its <code>flight</code>s.
 */
public class TextParser implements AirlineParser<Airline> {
    //private final Reader reader;
    private String file_name;

    protected FileReader parse;
    protected File parsedfile;
    protected Airline lufthansa;

//    public TextParser(Reader reader) {
//        this.reader = reader;
//    }

    /**
     * Constructs a new TextParser object with the given file name, checks if it exists, but if it does not, throws
     * illegal argument exception, but if it exists, then set the name of the file to the constructed TextParser's file name.
     *
     * @param filename The name of the file to be parsed.
     * @throws IllegalArgumentException If invalid file-names are given!
     */
    public TextParser(String filename) throws IllegalArgumentException {
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
     * Constructs a new TextParser object with the given file, checks if it exists, but if it does not, throws
     * illegal argument exception, but if it exists, then set file to the constructed TextParser's file.
     *
     * @param file The file to be parsed.
     * @throws IllegalArgumentException If the file-path/name is invalid.
     */

    public TextParser(File file) throws IllegalArgumentException {
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
     * The main (Text)parse[r]() function.
     * @return <code>Airline</code> Provides the airline with its associated, parsed-in flights.
     * @throws ParserException For parser-specific errors.
     */
    @Override
    public Airline parse() throws ParserException {
        //BufferedReader buffer = null;
        String currstring = null;
        FileReader parsely = null;
        try
        {

            parsedfile = new File(this.file_name);
            parsely = new FileReader(parsedfile);

        }
        catch (FileNotFoundException e2)
        {
            Airline concord = null;
            return concord;
            //throw new ParserException("File not found!");
//            try (BufferedReader buffer = new BufferedReader(this.parse))
//            {
//                TextDumper dumper = new TextDumper(this.file_name);
//                Airline emptyAirline = new Airline("Empty Airline");
//                dumper.dump(emptyAirline);
//                return emptyAirline;
//            }
//            catch (IOException e5)
//            {
//                throw new ParserException("Empty-Airline Parsing error detected: ", e5);//e3.getCause());
//            }
//            catch (IllegalArgumentException e6)
//            {
//                throw new ParserException("Empty-Airline Parsing error detected: ", e6);//e4.getCause());
//            }
            //return null;
            //System.out.println("Looks like this file was not found:", e2);
            //throw new ParserException("File does not exist!", e2);
        }
        //String airlineName = br.readLine()
        Airline gate = null;// = new Airline();

        try (BufferedReader buffer = new BufferedReader(parsely)){

            //BufferedReader test1 = null;
            //buffer = new BufferedReader(this.parse);
            //String first_airline_line = null;

            String first_airline_line = buffer.readLine();
            String line_buffer = buffer.readLine();

            currstring = line_buffer;

            if (first_airline_line != null)
            {
                String[] first_airline_name_args = first_airline_line.split("\\s*,\\s*");
                gate = new Airline(first_airline_name_args[0]);
            }

//
//            if (line_buffer != null) {
//                //this.lufthansa = new Airline(first);
//                String[] first_airline_arg = line_buffer.split("\\s*,\\s*");
//
//                if (first_airline_arg.length != 4)
//                {
//                    throw new IllegalArgumentException("Invalid number of parameters!");
//                }
//
//                gate = new Airline(first_airline_arg[0]);
//                currstring = buffer.readLine();
//            }
//            else
//            {
//                throw new ParserException("Empty file!");
//            }
//        } catch (Exception e3) {
//            throw new RuntimeException("Uh oh. looks like there was a Runtime Exception:", e3);
//        }
            //buffer = new BufferedReader(this.parse);
            //StringBuffer bufferline = new StringBuffer();
            //try {
            try {
                while (currstring != null) {

                    //currstring = buffer.readLine();

                    String[] currargs = currstring.split("\\s*,\\s*");

                    if (currargs.length > 5) {
                        throw new IllegalArgumentException("Need at least 5 arguments for airlines!");
                    }
                    else
                    {
                        if (currargs.length < 5)
                        {
                            throw new IllegalArgumentException("There can only be 5 arguments max per airline!");
                        }
                    }
                    Flight runway = new Flight(currargs);
                    //this.lufthansa.add
                    currstring = buffer.readLine();

                    if (gate.getName() == null) {
                        throw new ParserException("Missing airline name!");
                    }

                    try {
                        gate.addFlight(runway);
                    }
                    catch (NullPointerException e6)
                    {
                        throw new ParserException("Hmm, looks like there was an issue with adding the following flight: ", e6);
                    }

                    while (currstring != null && currstring.isEmpty())
                    {
                        currstring = buffer.readLine();
                    }

                }
            } catch (NullPointerException e5)
            {
                throw new ParserException("Error when parsing through the text file!", e5);
            }
//        } catch (RuntimeException e4) {
//            throw new RuntimeException("Uh oh. looks like there was a Runtime Exception:", e4);
//        }
//        return new Airline(airlineName);

        }

        catch (IOException e3)
        {
            throw new ParserException("Parsing error detected: ", e3);//e3.getCause());
        }

        catch (IllegalArgumentException e4)
        {
            throw new ParserException("Parsing error detected: ", e4);//e4.getCause());
        }

//        finally
//        {
//            if (buffer != null)
//            {
//                try
//                {
//                    buffer.close();
//                }
//                catch (IOException e7)
//                {
//                    throw new ParserException("Hmm, looks like the buffer ain't closing: ", e7);
//                }
//            }
//        }

        return gate;
    }
}
