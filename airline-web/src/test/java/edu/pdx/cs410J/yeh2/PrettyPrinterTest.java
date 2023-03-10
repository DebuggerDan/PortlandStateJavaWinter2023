package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PrettyPrinterTest {

//    /**
//     * A helper function to act as a packaged executable function for {@code assertThrows} that helps to create & test Flight creation!
//     * @see FlightTest
//     * @param flightNumber The flight number of the flight.
//     * @param src The source 3-letter code of the flight.
//     * @param depart The departure time-and-date of the flight.
//     * @param dest The destination 3-letter code of the flight.
//     * @param arrive The arrival time-and-date of the flight.
//     * @param runway The to-be-created & tested (in try-catch) for validity!
//     * @return runway (if successful!)
//     * @throws ParseException If there was an error from parsing timestamp(s)!
//     */
//    protected static Flight air_traffic_controller(String flightNumber, String src, String depart, String dest, String arrive, Flight runway) throws ParseException
//    {
//        try
//        {
//            runway = new Flight(flightNumber, src, depart, dest, arrive);
//        }
//        catch (ParseException t00)
//        {
//            throw new ParseException("ATC: Looks like there was an error during the test [flight]: ", t00.getErrorOffset());
//        }
//
//        return runway;
//    }

    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @param txtfile The text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
     * (from TextDumperTest)
     */
    private String reader(String txtfile) throws IOException
    {
        StringBuilder result = new StringBuilder();
        //result.append("");

        File read_file = new File(txtfile);
        FileReader file_read = new FileReader(read_file);

        try (BufferedReader read_buffer = new BufferedReader(file_read))
        {
            String currline = read_buffer.readLine();

            while (currline != null)
            {
                result.append(currline);
                currline = read_buffer.readLine();

                if (currline != null)
                {
                    result.append("\n");
                }
            }
        }
        catch (IOException m1)
        {
            //System.out.println("Error! README not found!", m1);
        }

        File alright_time_to = new File(txtfile);
        alright_time_to.delete();

        return result.toString();
    }

    /**
     * A test that tests airline pretty-dumping.
     * @throws IOException If file was unable to be processed.
     */
    @Test
    void airlineNameIsPrettyDumpedInTextFormat() throws IOException, ParseException {
        String filename = "pp_test.txt";
        String airlineName = "Lufthansa";
        String flightNumber = "123";
        String src = "PDX";
        String depart = "02/04/2023 6:51 am";
        String dest = "SEA";
        String arrive = "02/04/2023 7:00 am";

        Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
        Airline airline = new Airline(airlineName, testdrive);

        //StringWriter sw = new StringWriter();
        PrettyPrinter xerox = new PrettyPrinter(filename);//sw.toString());
//        try {
//            xerox.dump(airline);
//        } catch (IOException e1) {
//            throw new RuntimeException(e1);
//        }

        //String text = reader(filename);//sw.toString();

        //assertThat(text, containsString("Lufthansa"));// +
                //"\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));

    }

    /**
     * A test that tests airline pretty-printing.
     */
    @Test
    void airlineNameIsPrintedPretty() throws IOException, ParseException {
        //String filename = "dump_test.txt";
        String airlineName = "Lufthansa";
        String flightNumber = "123";
        String src = "PDX";
        String depart = "02/04/2023 6:51 am";
        String dest = "SEA";
        String arrive = "02/04/2023 7:00 am";

        Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
        Airline airline = new Airline(airlineName, testdrive);

        //StringWriter sw = new StringWriter();
        PrettyPrinter xerox = new PrettyPrinter();//sw.toString());
//        try {
//            xerox.dump(airline);
//        } catch (IOException e2) {
//            throw new RuntimeException(e2);
//        }

        //xerox.dump(airline);
        //String text = xerox.getPlottedPrint();
        //String text = reader(filename);//sw.toString();

        //assertThat(text, containsString("Thank you for using the PrettyPrinter!"));

    }

    /**
     * A test that tests the different PrettyPrinter constructors, e.g. {@code PrettyPrinter(String, Boolean)} constructor!
     */
    @Test
    void createPrettyPrinterConstructors() throws IllegalArgumentException
    {
        PrettyPrinter xerox1 = new PrettyPrinter("delta", false);
        PrettyPrinter xerox2 = new PrettyPrinter(null, true);

        //File test_file1 = new File("test.txt");
        File test_file2 = null;

        //PrettyPrinter brother1 = null;// PrettyPrinter(test_file1);
        PrettyPrinter brother2 = null;

        //assertThrows(IllegalArgumentException.class, (Executable) (brother1 = new PrettyPrinter(test_file1)));
        //assertThrows(IllegalArgumentException.class, (Executable) (brother2 = new PrettyPrinter(test_file2)));

    }

//    /**
//     * A parse-to-dump tester.
//     * @param tempDir
//     * @throws IOException
//     * @throws ParserException
//     */
//    @Test
//    void canParseTextWrittenByPrettyPrinter(@TempDir File tempDir) throws IOException, ParserException {
//        String airlineName = "Lufthansa";
//        Airline airline = new Airline(airlineName);
//
//        //File test_file = new File("test.txt");
//        PrettyPrinter xerox = new PrettyPrinter("test.txt");
//        xerox.dump(airline);
//
//        TextParser parser = new TextParser("test.txt");
//        Airline read = parser.parse();
//        assertThat(read.getName(), equalTo(airlineName));
//        //test_file.deleteOnExit();
//    }


}
