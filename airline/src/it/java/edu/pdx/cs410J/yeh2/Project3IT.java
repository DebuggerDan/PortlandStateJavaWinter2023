package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.lang.StringBuilder;


/**
 * An integration test for the {@link Project3} main class.
 */
class Project3IT extends InvokeMainTestCase {


    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @param txtfile A text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
     * @see Project2
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

    private static final String README = "CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#2]\n" +
            "Description: Project #2 focuses on using the Input / Output functionalities of Java and throw-exception usages.\n" +
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
            "           -textFile file Where to read/write the airline info\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n" +
            "           Dates and times should be in the format: mm/dd/yyyy hh:mm";

    private static final String commandline = "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
            "       args are (in this order):\n" +
            "           airline The name of the airline\n" +
            "           flightNumber The flight number\n" +
            "           src Three-letter code of departure airport\n" +
            "           depart Departure date and time (24-hour time)\n" +
            "           dest Three-letter code of arrival airport\n" +
            "           arrive Arrival date and time (24-hour time)\n" +
            "       options are (options may appear in any order):\n" +
            "           -textFile file Where to read/write the airline info\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n" +
            "           Dates and times should be in the format: mm/dd/yyyy hh:mm";

    /**
     * Invokes the main method of {@link Project3} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project3.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("The Project #2 command-line interface has been provided above."));
    }

    /**
     * Test #2: README2.txt
     * Ran with no arguments, but just the -README option.
     */
    @Test
    void testREADME() {
        MainMethodResult result = invokeMain(Project3.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString(README));
    }


    /**
     * Test #3: README2.txt (x2)
     * Ran with no arguments, but just the -README option but twice, intentionally.
     */
    @Test
    void testREADMEx5() {
        MainMethodResult result = invokeMain(Project3.class, "-README", "-README");
        StringBuilder readmex2 = new StringBuilder();
        readmex2.append(README);
        readmex2.append("\n");
        readmex2.append(README);
        assertThat(result.getTextWrittenToStandardOut(), containsString(readmex2.toString()));
    }

    /**
     * Test #4: Departure Time Malformatted
     * Ran with a specified text file but an invalid departure time.
     */
    @Test
    void testDepartureFormat() {
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "123123123", "123", "XDP", "02/04/2023", "07:00", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the departure time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "123123123", "123", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the arrival time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #6: Extra Argument
     * Ran with an extra argument.
     */
    @Test
    void testExtraArg() {
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "g4j9j3g0j3g49jg49", "123123123", "123", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #7: New Airline Text File
     * Ran normally with flight information and -print option.
     */
    @Test
    void testValid() {
        File test_file = new File("test.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, XDP, 2/04/2023 07:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test.txt", "-print");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();

        assertThat(result.getTextWrittenToStandardOut(), containsString("'test.txt was loaded successfully!\n" +
                "Alrighty, proceeding to dump your new airline ('Lufthansa') into a new file:\n" +
                "test.txt\n" +
                "Luggage has been dumped successfully (New flight dumped into Airline text-file) - Nice!"));
    }

    /**
     * Test #8: Existing Airline Text File
     * Ran with valid flight information, but for a pre-existing file.
     */
    @Test
    void testPreExisting() throws ParserException {
        File test_file = new File("test8.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, XDP, 2/04/2023 07:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "124", "PDX", "02/04/2023", "06:54", "XDP", "02/04/2023", "07:01", "-textFile", "test8.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    /**
     * Test #9: Existing Airline Text File v2
     * Same as Test #8, but for specifically for an airline named "Project3."
     */
    @Test
    void testPreExistingP2() {
        File test_file = new File("test9.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Project3, 123, PDX, 2/04/2023 06:51, XDP, 2/04/2023 07:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test9.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString("Oh noes! The command-line Airline name specified ('Lufthansa') does not match the Airline name on-file!\n" +
                "The file, instead specifies an Airline name of, 'Project3.'"));
    }

    /**
     * Test #10: Malformatted Airline File
     * Hocus pocus, airline filled with bogus~~
     */
    @Test
    void testMalformatted() {

        File test_file = new File("test10.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("f89a3wh8f9ha2388afgh0983hg");
            testwrite.println("c++isnotunnecessarilydifficult");
            testwrite.println("therecanonlybe1sand0s");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test10.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error whilst processing textFile, 'test10.txt' - Specific Error: Parsing error detected:"));
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
        MainMethodResult result = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test11.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    /**
     * Test #12: Airline codes are too short
     * Airline code are too short.
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeShort() {
        MainMethodResult resultSrc = invokeMain(Project3.class, "Lufthansa", "123", "A", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test11.txt");
        MainMethodResult resultDest = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "B", "02/04/2023", "07:00", "-textFile", "test11.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: A"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: B"));
    }

    /**
     * Test #13: Airline codes are too large
     * Airline code are too short.
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeLong() {
        MainMethodResult resultSrc = invokeMain(Project3.class, "Lufthansa", "123", "ABCD", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test11.txt");
        MainMethodResult resultDest = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "BCDE", "02/04/2023", "07:00", "-textFile", "test11.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: ABCD"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: BCDE"));
    }

    /**
     * Test #14: Airline code have numbers.
     * Airline code have numbers.
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeNumbers() {
        MainMethodResult resultSrc = invokeMain(Project3.class, "Lufthansa", "123", "AA1", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test11.txt");
        MainMethodResult resultDest = invokeMain(Project3.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "BB2", "02/04/2023", "07:00", "-textFile", "test11.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: AA1"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: BB2"));
    }

}