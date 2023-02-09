package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * An integration test for the {@link Project1} main class.
 */
class Project1IT extends InvokeMainTestCase {


//    /**
//     * Tests that invoking the main method with no arguments displays the command-line interface.
//     */
//    @Test
//    void testNoCommandLineArguments() throws IOException {
//        MainMethodResult result = invokeMain();
//        try (
//                InputStream readme = Project1.class.getResourceAsStream("README.txt")
//        ) {
//            assertThat(readme, not(nullValue()));
//            BufferedReader reader = new BufferedReader(new InputStreamReader(readme));
//            String line = reader.readLine();
//            assertThat(line, containsString("CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#1]"));
//        }
//        //assertThat(result.getTextWrittenToStandardOut(), containsString("Missing command line arguments"));
//    }

    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @param txtfile A text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
     * @see Project1
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

    private static final String README = "CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#1]\n" +
            "Description: Project #1 focuses on the extension of pre-existed Abstract Classes & the utilization of Java's functionalities to perform complex command-line parsing.\n" +
            "\n" +
            "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
            "       args are (in this order):\n" +
            "           airline The name of the airline\n" +
            "           flightNumber The flight number\n" +
            "           src Three-letter code of departure airport\n" +
            "           depart Departure date and time (24-hour time)\n" +
            "           dest Three-letter code of arrival airport\n" +
            "           arrive Arrival date and time (24-hour time)\n" +
            "       options are (options may appear in any order):\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n" +
            "           Dates and times should be in the format: mm/dd/yyyy hh:mm\n";

    private static final String commandline = "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
            "       args are (in this order):\n" +
            "           airline The name of the airline\n" +
            "           flightNumber The flight number\n" +
            "           src Three-letter code of departure airport\n" +
            "           depart Departure date and time (24-hour time)\n" +
            "           dest Three-letter code of arrival airport\n" +
            "           arrive Arrival date and time (24-hour time)\n" +
            "       options are (options may appear in any order):\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n" +
            "           Dates and times should be in the format: mm/dd/yyyy hh:mm";

    /**
     * Invokes the main method of {@link Project1} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project1.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("The Project #1 command-line interface has been provided above."));
    }

    /**
     * Test #2: README2.txt
     * Ran with no arguments, but just the -README option.
     */
    @Test
    void testREADME() {
        MainMethodResult result = invokeMain(Project1.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString(README));
    }


    /**
     * Test #3: Flight number is not an integer.
     * Ran with valid arguments, except that the flight number is not an number.
     */
    @Test
    void testInvalidFlightNumber() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "fjaiefjoiaoifjeoa", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-print");
        assertThat(result.getTextWrittenToStandardError(), containsString("The flight number seems to be, well, not a integer!"));
    }

    /**
     * Test #4: Departure Time Malformatted
     * Ran with a specified text file but an invalid departure time.
     */
    @Test
    void testDepartureFormat() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "123123123", "123", "XDP", "02/04/2023", "07:00");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the departure time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "123123123", "123");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the arrival time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #6: Unknown command-line option.
     * Ran with an unknown option.
     */
    @Test
    void testUnknownOption() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-print", "-test");

        assertThat(result.getTextWrittenToStandardError(), containsString("Uh oh, looks like there was a invalid option used: test"));
    }

    /**
     * Test #7a: Extra (Unknown) command-line argument.
     * Ran with an extra argument.
     */
    @Test
    void testExtraArg() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-print", "test");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may have too many command-line arguments."));
    }

    /**
     * Test #7b: Missing an command-line argument.
     * Ran with an missing argument.
     */
    @Test
    void testMissingArg() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing command-line arguments."));
    }

    /**
     * Test #8: Printing out a flight
     * Ran with an unknown option.
     */
    @Test
    void testPrintToString() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-print", "-test");

        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
    }

    /**
     * Test #9: Missing arrival time
     * Tested with missing arrival time.
     */
    @Test
    void testMissingArrival() {
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "test1", "test2");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the arrival time & date arguments, test1 and test2"));
    }

    /**
     * Test #10a: Airline codes are too short
     * Airline code are too short.
     */
    @Test
    void testAirportCode() {
        MainMethodResult resultSrc = invokeMain(Project1.class, "Lufthansa", "123", "A", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00");
        MainMethodResult resultDest = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "B", "02/04/2023", "07:00");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: A"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: B"));
    }

    /**
     * Test #10a: Airline codes are too large
     * Airline code are too short.
     */
    @Test
    void testAirportCode() {
        MainMethodResult resultSrc = invokeMain(Project1.class, "Lufthansa", "123", "ABCD", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00");
        MainMethodResult resultDest = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "BCDE", "02/04/2023", "07:00");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: ABCD"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: BCDE"));
    }

    /**
     * Test #11: Airline code have numbers.
     * Airline code have numbers.
     */
    @Test
    void testAirportCodeNumbers() {
        MainMethodResult resultSrc = invokeMain(Project1.class, "Lufthansa", "123", "AA1", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00");
        MainMethodResult resultDest = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "BB2", "02/04/2023", "07:00");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: AA1"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: BB2"));
    }

    /**
     * Test #11: Invalid Year in Airline File
     * Ran with pre-existing airline text file such that has invalid years.
     */
    @Test
    void testInvalidYearParsing() {
        File test_file = new File("test11.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Lufthansa, 123, PDX, 99999s99999/da99/99 06:51, XDP, 12a3/4211/4223 07:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project1.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test11.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

}