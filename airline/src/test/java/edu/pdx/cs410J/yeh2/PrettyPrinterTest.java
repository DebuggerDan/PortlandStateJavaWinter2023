package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class PrettyPrinterTest {

    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @param txtfile The text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
     * @see TextDumperTest
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
    void airlineNameIsPrettyDumpedInTextFormat() throws IOException {
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
        try {
            xerox.dump(airline);
        } catch (IOException e1) {
            throw new RuntimeException(e1);
        }

        String text = reader(filename);//sw.toString();

        assertThat(text, containsString("Lufthansa"));// +
                //"\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));

    }

    /**
     * A test that tests airline pretty-printing.
     */
    @Test
    void airlineNameIsPrintedPretty() throws IOException {
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
        try {
            xerox.dump(airline);
        } catch (IOException e2) {
            throw new RuntimeException(e2);
        }

        xerox.dump(airline);
        String text = xerox.getPlottedPrint();
        //String text = reader(filename);//sw.toString();

        assertThat(text, containsString("Thank you for using the PrettyPrinter!\n" +
                "For airline, 'Lufthansa', we have the following flight(s) scheduled...\n" +
                "\n" +
                "-----\n"));

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
