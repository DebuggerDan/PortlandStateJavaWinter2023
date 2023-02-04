package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The main class for the CS410J TextFile Project (#2)
 * usage: java -jar target/airline-2023.0.0.jar [options] <args>
 *      args are (in this order):
 *          airline      The name of the airline
 *          flightNumber     The flight number
 *          src      Three-letter code of departure airport
 *          depart      Departure date and time (24-hour time)
 *          dest     Three-letter code of arrival airtport
 *          arrive      Arrival date and time (24-hour time)
 *      options are (options may appear in any order))
 *          -textFile      file Where to read/write the airline info
 *          -print      Prints a description of the new flight
 *          -README     Prints a README for this project and exits
 *      Date and time should be in the format: mm/dd/yyyy hh:mm
 *
 */
public class Project2 {

    @VisibleForTesting
    static boolean isValidDateAndTime(String dateAndTime) {
        return true;
    }

    public static void main(String[] args) {
        //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        int argnum = 0;
        Airline lufthansa = null;
        if (args == null)
        {
            lufthansa = new Airline("N/A");
        }

        lufthansa = new Airline("N/A");//args[0], args[1], args[2], args[3], args[4], args[5]);
        //AirlineParser read = new AirlineParser();
        //TextParser parser = new TextParser(lufthansa);
        //TextDumper dumper = new TextDumper();

        

        if (args == null) {
            System.err.println("Missing command line arguments");
        }

        for (String arg : args) {
            System.out.println(arg);

            if (arg.startsWith("-"))
            {
                String parameter = arg.substring(1);
                if (parameter.equals("print"))
                {
                    lufthansa.printAll();
                }
                if (parameter.equals("README"))
                {
                    //System.out.println("Welcome to Project 1, created by Dan Jang for CS410P: Advanced Java Programming!");
                    //System.out.println("This project focuses on extended classes & a bit more complex commandline parsing stuffs.");
                    try (InputStream readme = Project1.class.getResourceAsStream("README2.txt"))
                    {
                        InputStreamReader readmereader = new InputStreamReader(readme);

                        try (BufferedReader readmebuffer = new BufferedReader(readmereader))
                        {
                            String readmeline = readmebuffer.readLine();

                            while (readmeline != null)
                            {
                                System.out.println(readmeline);
                                readmeline = readmebuffer.readLine();
                            }
                        }
                    }
                    catch (IOException m1)
                    {
                        //System.out.println("Error! README not found!", m1);
                    }
                }
                else
                {
                    System.err.println("Invalid airline program, o noes! (Option Specified: " + parameter + ")");

                }
            }
            else
            {
                argnum++;
            }
        }

        if (argnum != 6)
        {
            System.err.println("Missing command line arguments");
        }

    }

}