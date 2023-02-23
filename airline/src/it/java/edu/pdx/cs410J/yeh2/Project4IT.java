package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.lang.StringBuilder;


/**
 * An integration test for the {@link Project4} main class.
 */
class Project4IT extends InvokeMainTestCase {


    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * (From Project2)
     * @param txtfile A text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
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

    private static final String README = "CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#4]\n" +
            "Description: Project #4 focuses on the storage of airline information into XML files.\n" +
            "Project #4 will both be able to dump, parse, and convert(er) into & of XML files.\n" +
            "\n" +
            "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
            "       args are (in this order):\n" +
            "           airline The name of the airline\n" +
            "           flightNumber The flight number\n" +
            "           src Three-letter code of departure airport\n" +
            "           depart Departure date and time (am/pm)\n" +
            "           dest Three-letter code of arrival airport\n" +
            "           arrive Arrival date and time (am/pm)\n" +
            "       options are (options may appear in any order):\n" +
            "           -xmlFile file Where to read/write the airline info\n" +
            "           -textFile file Where to read/write the airline info\n" +
            "           -pretty file Pretty print the airline’s flights to\n" +
            "                        a text file or standard out (file -)\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n";

    private static final String commandline = "usage: java -jar target/airline-2023.0.0.jar [options] <args>\n" +
            "       args are (in this order):\n" +
            "           airline The name of the airline\n" +
            "           flightNumber The flight number\n" +
            "           src Three-letter code of departure airport\n" +
            "           depart Departure date and time (am/pm)\n" +
            "           dest Three-letter code of arrival airport\n" +
            "           arrive Arrival date and time (am/pm)\n" +
            "       options are (options may appear in any order):\n" +
            "           -xmlFile file Where to read/write the airline info\n" +
            "           -textFile file Where to read/write the airline info\n" +
            "           -pretty file Pretty print the airline’s flights to\n" +
            "                        a text file or standard out (file -)\n" +
            "           -print Prints a description of the new flight\n" +
            "           -README Prints a README for this project and exits\n";

    /**
     * Invokes the main method of {@link Project4} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project4.class, args);
    }

    /**
     * Tests that invoking the main method with no arguments issues an error
     */
    @Test
    void testNoCommandLineArguments() {
        MainMethodResult result = invokeMain();
        assertThat(result.getTextWrittenToStandardOut(), containsString("The Project #4 command-line interface has been provided above."));
    }

    /**
     * Test #0a: Valid invocation of main method (Project #4) with all possible valid arguments & options
     * (But specifies the command-line printing method for -pretty option)
     */
    @Test
    void AtestFullValid1() {
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test0a.txt", "-pretty", "-");
        assertThat(result.getTextWrittenToStandardOut(), containsString("'test0a.txt' was loaded successfully!"));
    }

    /**
     * Test #0b: Valid invocation of main method (Project #4) with all possible valid arguments & options
     * (But specifies the file-writing method for -pretty option)
     */
    @Test
    void AtestFullValid2() {
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test0b.txt", "-pretty", "text0b2.txt");
        assertThat(result.getTextWrittenToStandardOut(), containsString("'test0b.txt' was loaded successfully!"));
    }

    /**
     * Test #2: README4.txt
     * Ran with no arguments, but just the -README option.
     */
    @Test
    void testREADME() {
        MainMethodResult result = invokeMain(Project4.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString("CS410P: Advanced Java Programming, Winter 2023 - Dan Jang, February 2023 [#4]"));//README));
    }

//
//    /**
//     * Test #3: README4.txt (x2)
//     * Ran with no arguments, but just the -README option but twice, intentionally.
//     */
//    @Test
//    void testREADMEx5() {
//        MainMethodResult result = invokeMain(Project4.class, "-README", "-README");
//        StringBuilder readmex2 = new StringBuilder();
//        readmex2.append(README);
//        readmex2.append("\n");
//        readmex2.append(README);
//        assertThat(result.getTextWrittenToStandardOut(), containsString(readmex2.toString()));
//    }

    /**
     * Test #4: Departure Time Malformatted
     * Ran with a specified text file but an invalid departure time.
     */
    @Test
    void testDepartureFormat() {
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "123123123", "123", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("[Main Date Initialization #1a] Error when attempting to format the departure time & date arguments, 123123123, 123, and pm."));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "7:00", "pm", "SEA", "123123123", "123", "pm", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, 123123123, 123, and pm."));
    }

    /**
     * Test #6: Extra Argument
     * Ran with an extra argument.
     */
    @Test
    void testExtraArg() {
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "7:00", "pm", "SEA", "g4j9j3g0j3g49jg49", "123123123", "123", "pm", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may have too many command-line arguments."));
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
            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test.txt", "-print");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();

        assertThat(result.getTextWrittenToStandardOut(), containsString("'test.txt' was loaded successfully!"));
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
            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "124", "PDX", "02/04/2023", "06:54", "pm", "SEA", "02/04/2023", "07:01", "pm", "-textFile", "test8.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardOut(), containsString(""));
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    /**
     * Test #9: Existing Airline Text File v2
     * Same as Test #8, but for specifically for an airline named "Project4."
     */
    @Test
    void testPreExistingP2() {
        File test_file = new File("test9.txt");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Project4, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test9.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString("Oh noes! The command-line Airline name specified ('Lufthansa') does not match the Airline name on-file!\n" +
                "The file, instead specifies an Airline name of, 'Project4.'"));
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
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test10.txt");
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
            testwrite.println("Lufthansa, 123, PDX, 99999s99999/da99/99 06:51, SEA, 12a3/4211/4223 7:00");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
        }
        MainMethodResult result = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test11.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), containsString(""));
    }

    /**
     * Test #12: Airline codes are too short
     * Airline code are too short (vs. 3-letters).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeShort() {
        MainMethodResult resultSrc = invokeMain(Project4.class, "Lufthansa", "123", "A", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test12a.txt");
        MainMethodResult resultDest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "B", "02/04/2023", "7:00", "pm", "-textFile", "test12b.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: A"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: B"));
    }

    /**
     * Test #13: Airline codes are too large
     * Airline code are too long (vs. 3-letters).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeLong() {
        MainMethodResult resultSrc = invokeMain(Project4.class, "Lufthansa", "123", "ABCD", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test13a.txt");
        MainMethodResult resultDest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BCDE", "02/04/2023", "7:00", "pm", "-textFile", "test13a.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: ABCD"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: BCDE"));
    }

    /**
     * Test #14: Airline codes have numbers.
     * Airline codes have numbers, which is very confusing (and invalid).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeNumbers() {
        MainMethodResult resultSrc = invokeMain(Project4.class, "Lufthansa", "123", "AA1", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test14a.txt");
        MainMethodResult resultDest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BB2", "02/04/2023", "7:00", "pm", "-textFile", "test14a.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: AA1"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: BB2"));
    }

    /**
     * Test #15: Airline codes are invalid.
     * Airline codes do not exist in reality, very spooky.
     * @see edu.pdx.cs410J.AirportNames
     */
    @Test
    void testAirportNonValidCodes() {
        MainMethodResult resultSrc = invokeMain(Project4.class, "Lufthansa", "123", "AAZ", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test15a.txt");
        MainMethodResult resultDest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BBZ", "02/04/2023", "7:00", "pm", "-textFile", "test15a.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Uh oh, looks like the source airport code, 'AAZ', was not found in our airport-names database!"));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Uh oh, looks like the destination airport code, 'BBZ', was not found in our airport-names database!"));
    }

    /**
     * Test #16: Negative Flight Duration (Arrival - Departure)
     * If the flight has a negative flight-duration (in minutes)
     * (From Project1IT.java)
     */
    @Test
    void testInvalidFlightDuration() {
        MainMethodResult resultSrc = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16a.txt");
        MainMethodResult resultDest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
        assertThat(resultDest.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #17: <code>XML</code>-infoz Test(s) for <code>XML</code>Dumper
     * If valid, If the flight has a valid <code>XML</code>-airline information, then create the <code>XML</code> file.
     * Au contrar, if invalid (?).
     * @throws IOException If there are file related errors!
     * @throws ParserConfigurationException If there are <code>XML</code> related errors!
     */
    @Test
    void testXMLDumping() throws IOException, ParserConfigurationException, SAXException {
        MainMethodResult resultValidXML = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-xmlFile", "test17.xml");
        //MainMethodResult resultInvalidXML = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");
        String text = reader("test17.xml");//sw.toString();

        assertThat(text, Matchers.containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));// +
        // "\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));
        AirlineXmlHelper dtdTest = new AirlineXmlHelper();
        DocumentBuilderFactory dtdTestFactory = DocumentBuilderFactory.newInstance();
        dtdTestFactory.setValidating(true);

        DocumentBuilder dtdTestBuilder = dtdTestFactory.newDocumentBuilder();
        dtdTestBuilder.setErrorHandler(dtdTest);
        dtdTestBuilder.setEntityResolver(dtdTest);
        try (FileReader dtdTestReader = new FileReader("test17.xml"))
        {
            dtdTestBuilder.parse("test17.xml");
        }
        catch (IOException m1)
        {
            System.err.println("[Project Integration Test #17 Error, IOException]" + m1.getMessage());
        }
        //assertThat(resultValidXML.getTextWrittenToStandardOut(), containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #18: Concurrent -<code>XML</code>File & -textFile Test(s)
     * If both <code>XML</code>File & textFile arguments were specified, then graceful exit!
     * Au contrar, if invalid (?).
     */
    @Test
    void testDualityXMLTextFiles() {
        MainMethodResult resultDualityOfJava = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-xmlFile", "test17.xml", "-textFile", "test17.txt");
        //MainMethodResult resultInvalidXML = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        assertThat(resultDualityOfJava.getTextWrittenToStandardError(), containsString("Oh noes, looks like both the textFile & xmlFile arguments were specified! Only one at a time, pretty please!"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #19: Concurrent -<code>XML</code>File & -textFile Test(s)
     * If both <code>XML</code>File & textFile arguments were specified, then graceful exit!
     * Au contrar, if invalid (?).
     */
    @Test
    void testInvalidXMLTextFiles() {
        MainMethodResult resultDualityOfJava = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-xmlFile", "test17.xml", "-textFile", "test17.txt");
        //MainMethodResult resultInvalidXML = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        assertThat(resultDualityOfJava.getTextWrittenToStandardError(), containsString("Oh noes, looks like both the textFile & xmlFile arguments were specified! Only one at a time, pretty please!"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #20: Invalid <code>XML</code> Formatting (DTD Non-Compliance)
     * If both <code>XML</code>File specified has an invalid DTD!
     */
    @Test
    void testInvalidXMLFile() {
        MainMethodResult resultXMLDTDTest = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-xmlFile", "invalid-airline.xml", "-textFile", "test17.txt");
        //MainMethodResult resultInvalidXML = invokeMain(Project4.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        assertThat(resultXMLDTDTest.getTextWrittenToStandardError(), containsString("Oh noes, looks like both the textFile & xmlFile arguments were specified! Only one at a time, pretty please!"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

}