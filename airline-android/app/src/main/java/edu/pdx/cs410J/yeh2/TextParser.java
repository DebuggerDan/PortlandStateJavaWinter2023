package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;
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
 * A text parser based on the <code>AirlineParser</code> interface, for Project #2.
 * Reads the contents of a specified text-file, creates an <code>airline</code>, including its <code>flight</code>s.
 */
public class TextParser implements AirlineParser<Airline> {
  //private final Reader reader;
  private String file_name;

  protected FileReader parse;
  protected File parsedfile;
  protected Airline lufthansa;

  // Using coreAPI, pages 92 ~ 104 on date, calendar, & variable-length args

  /**
   * <p>
   * Creates a valid formatted for a valid time-and-date format as specified, e.g. mm/dd/yyyy hh:mm
   * Based on: 1. {@code public static final String Timestamp_Format = "MM/dd/yyyy HH:mm";}
   * and 2. {@code public static final DateFormat testDate = new SimpleDateFormat(Timestamp_Format, Locale.US);}
   * </p>
   *
   * Using coreAPI, pages 97 ~ 100 on DateFormat & SimpleDateFormat
   * @see java.text.DateFormat
   * @see java.text.SimpleDateFormat
   *
   * @param date The first half of the bi-string combo-to-be!
   * @param time The second half of the bi-string combo-to-be!
   * @return timestamp The <code>Date</code> formatted bi-string timestmap combo!
   * @throws IllegalArgumentException If there is an invalid formatted time-and-date bi-string combo!
   *
   */

//    public static Date timeStamper(String date, String time) throws IllegalArgumentException
//    {
//        String Timestamp_Format = "MM/dd/yyyy HH:mm";
//        DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
//        StringBuilder postage = new StringBuilder();
//        postage.append(date);
//        postage.append(" ");
//        postage.append(time);
//        //postage.append(date + " " + time);
//
//        String stamp = postage.toString();
//        Date timestamp = null;
//
//        try
//        {
//            timestamp = TStamp.parse(stamp);
//        }
//        catch (ParseException m00)
//        {
//            throw new IllegalArgumentException("Hmm, looks like a invalid time-and-date stamp attempt: ", m00);
//        }
//
//
//        return timestamp;
//    }

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

  public TextParser(File file, Boolean check) throws IllegalArgumentException, IOException {
    if (!file.exists()) {
      if (!check) {
        if (!file.createNewFile()) {
          throw new IllegalArgumentException("Name of the file is invalid!");
        }
      }
      else
      {

        throw new IllegalArgumentException("File does not exist!");
      }
    }


    //else
    //{
    this.file_name = file.getName();
    this.parsedfile = file;
    //}


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
    /*
     * Time-and-Date Format stuffs - from coreAPI, pages 92 ~ 104.
     *
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     */
    String Timestamp_Format = "MM/dd/yyyy HH:mm a";
    DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
    String currstring = null;
    FileReader parsely = null;
    try
    {

      //parsedfile = new File(this.file_name);
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

          if (currargs.length > 9) {
            throw new IllegalArgumentException("Need at least 9 arguments for airlines/flights!");
          }
          else
          {
            if (currargs.length < 9)
            {
              throw new IllegalArgumentException("There can only be 9 arguments max per airline/flights!");
            }
          }
          //String stamp1 = null;
          //String stamp2 = null;
//                    /*
//                     * Attempts to create the formatted time-and-date for departure time.
//                     */
//                    try
//                    {
//                        stamp1 = TStamp.format(timeStamper(currargs[3], currargs[4]));
//                        //runway = new Flight(landing);
//                    }
//                    catch (IllegalArgumentException m4a)
//                    {
//                        System.err.println("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//                        throw new ParserException("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//
//                        // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//                    catch (ArrayIndexOutOfBoundsException m4b)
//                    {
//                        System.err.println("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//                        throw new ParserException("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//
//                        // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//
//                    /*
//                     * Attempts to create the formatted time-and-date for arrival time.
//                     */
//                    try
//                    {
//                        stamp2 = TStamp.format(timeStamper(currargs[6], currargs[7]));
//                    }
//                    catch (IllegalArgumentException m5a)
//                    {
//                        System.err.println("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]);
//                        throw new ParserException(("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]));
//
//                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//                    catch (ArrayIndexOutOfBoundsException m4b)
//                    {
//                        System.err.println("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]);
//                        throw new ParserException(("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]));
//
//                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }

          //Flight runway = new Flight(currargs);

                    /*
                    Legacy Project #2 date-stamp formatting
                    String date1 = currargs[2] + currargs[3];
                    //String date2 = currargs[5] + currargs[6];
                    */

          /*
           * date1 & date2 represents the tri-string combo strings for our two full timestamps!
           * @see Project3
           */
          String date1 = currargs[3] + " " + currargs[4] + " " + currargs[5];// + " " + currargs[4];
          String date2 = currargs[7] + " " + currargs[8] + " " + currargs[9];// + " " + currargs[6];
          Flight runway = null;

          try
          {
            runway = new Flight(currargs[0], currargs[1], date1, currargs[4], date2);
          }
          catch (ParseException e8)
          {
            throw new ParserException("[TextParser Initialization Error]" + e8.getErrorOffset());
          }

          // Input-Validation #5: If flight arrival is before the departure time:
          if (runway.getFlightTime() < 0)
          {
            throw new ParserException("(flight-minute calculation during parsing process)");
          }

          //Flight runway = new Flight(currargs[1], currargs[2], stamp1, currargs[5], stamp2);
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
      throw new ParserException("Parsing error detected [IO - Parse]: ", e3);//e3.getCause());
    }

    catch (IllegalArgumentException e4)
    {
      throw new ParserException("Parsing error detected [Bad Arg - Parse]: ", e4);//e4.getCause());
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

  /**
   * The Android (Text)parse[r]() function.
   * @return <code>Airline</code> Provides the airline with its associated, parsed-in flights.
   * @throws ParserException For parser-specific errors.
   */
  public static Airline parsley(File file) throws ParserException {
    //BufferedReader buffer = null;
    /*
     * Time-and-Date Format stuffs - from coreAPI, pages 92 ~ 104.
     *
     * @see java.text.DateFormat
     * @see java.text.SimpleDateFormat
     */
    String Timestamp_Format = "MM/dd/yyyy HH:mm a";
    DateFormat TStamp = new SimpleDateFormat(Timestamp_Format, Locale.US);
    String currstring = null;
    FileReader parsely = null;
    try
    {

      //parsedfile = new File(file);
      parsely = new FileReader(file);

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

          if (currargs.length > 7) {
            throw new IllegalArgumentException("Need at least 7 arguments for airlines/flights!");
          }
          else
          {
            if (currargs.length < 7)
            {
              throw new IllegalArgumentException("There can only be 7 arguments max per airline/flights!");
            }
          }
          //String stamp1 = null;
          //String stamp2 = null;
//                    /*
//                     * Attempts to create the formatted time-and-date for departure time.
//                     */
//                    try
//                    {
//                        stamp1 = TStamp.format(timeStamper(currargs[3], currargs[4]));
//                        //runway = new Flight(landing);
//                    }
//                    catch (IllegalArgumentException m4a)
//                    {
//                        System.err.println("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//                        throw new ParserException("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//
//                        // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//                    catch (ArrayIndexOutOfBoundsException m4b)
//                    {
//                        System.err.println("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//                        throw new ParserException("Error when attempting to parse the departure time & date arguments, " + currargs[3] + " and " + currargs[4]);
//
//                        // Graceful Error: Departure Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//
//                    /*
//                     * Attempts to create the formatted time-and-date for arrival time.
//                     */
//                    try
//                    {
//                        stamp2 = TStamp.format(timeStamper(currargs[6], currargs[7]));
//                    }
//                    catch (IllegalArgumentException m5a)
//                    {
//                        System.err.println("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]);
//                        throw new ParserException(("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]));
//
//                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }
//                    catch (ArrayIndexOutOfBoundsException m4b)
//                    {
//                        System.err.println("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]);
//                        throw new ParserException(("Error when attempting to parse the arrival time & date arguments, " + currargs[6] + " and " + currargs[7]));
//
//                        // Graceful Error: Arrival Time & Date Argument(s) not formatted correctly!
//                        //return;
//                    }

          //Flight runway = new Flight(currargs);

                    /*
                    Legacy Project #2 date-stamp formatting
                    String date1 = currargs[2] + currargs[3];
                    //String date2 = currargs[5] + currargs[6];
                    */

          /*
           * date1 & date2 represents the tri-string combo strings for our two full timestamps!
           * @see Project3
           */
          String date1 = currargs[3] + " " + currargs[4] + " " + currargs[5];// + " " + currargs[4];
          String date2 = currargs[7] + " " + currargs[8] + " " + currargs[9];// + " " + currargs[6];
          Flight runway = null;

          try
          {
            runway = new Flight(currargs[0], currargs[1], date1, currargs[4], date2);
          }
          catch (ParseException e8)
          {
            throw new ParserException("[TextParser Initialization Error]" + e8.getErrorOffset());
          }

          // Input-Validation #5: If flight arrival is before the departure time:
          if (runway.getFlightTime() < 0)
          {
            throw new ParserException("(flight-minute calculation during parsing process)");
          }

          //Flight runway = new Flight(currargs[1], currargs[2], stamp1, currargs[5], stamp2);
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
      throw new ParserException("Parsing error detected [IO - Parsley]: ", e3);//e3.getCause());
    }

    catch (IllegalArgumentException e4)
    {
      throw new ParserException("Parsing error detected [Bad Args - Parsley]: ", e4);//e4.getCause());
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

//package edu.pdx.cs410J.yeh2;
//
//import edu.pdx.cs410J.ParserException;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.Reader;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class TextParser {
//  private final Reader reader;
//
//  public TextParser(Reader reader) {
//    this.reader = reader;
//  }
//
//  public Map<String, String> parse() throws ParserException {
//    Pattern pattern = Pattern.compile("(.*) : (.*)");
//
//    Map<String, String> map = new HashMap<>();
//
//    try (
//      BufferedReader br = new BufferedReader(this.reader)
//    ) {
//
//      for (String line = br.readLine(); line != null; line = br.readLine()) {
//        Matcher matcher = pattern.matcher(line);
//        if (!matcher.find()) {
//          throw new ParserException("Unexpected text: " + line);
//        }
//
//        String word = matcher.group(1);
//        String definition = matcher.group(2);
//
//        map.put(word, definition);
//      }
//
//    } catch (IOException e) {
//      throw new ParserException("While parsing dictionary", e);
//    }
//
//    return map;
//  }
//}
