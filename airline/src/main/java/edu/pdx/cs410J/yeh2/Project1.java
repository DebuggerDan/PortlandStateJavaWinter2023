package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

/**
 * The main class for the CS410J airline Project
 * Creates an airline, lufthansa (can be named differently internally of course), that creates a flight(s), then prints all flight(s) information to console.
 * Note: Project is massively incomplete, will most likely re-submit for regrading, my apologies in advance to the wonderful grader.
 */
public class Project1 {

  @VisibleForTesting
  static boolean isValidDateAndTime(String dateAndTime) {
    return true;
  }

  public static void main(String[] args) {
    //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath



    Airline lufthansa = new Airline(args[0], args[1], args[2], args[3], args[4], args[5]);
    //AirlineParser read = new AirlineParser();

    //TextParser parser = new TextParser(lufthansa);
    //TextDumper dumper = new TextDumper();
    //lufthansa.parse();

    if (args == null) {
      System.err.println("Missing command line arguments");
    }

    for (String arg : args) {
        System.out.println(arg);
      }

    lufthansa.printAll();

  }

}