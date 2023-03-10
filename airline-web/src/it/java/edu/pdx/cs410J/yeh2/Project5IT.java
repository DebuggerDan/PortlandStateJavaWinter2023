package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.ParserException;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.MethodOrderer.MethodName;

/**
 * An integration test for {@link Project5} that invokes its main method with
 * various arguments
 */
@TestMethodOrder(MethodName.class)
class Project5IT extends InvokeMainTestCase {

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

    private static final String HOSTNAME = "localhost";
    private static final String PORT = System.getProperty("http.port", "8080");

    @Test
    void test0RemoveAllMappings() throws IOException {
      AirlineRestClient client = new AirlineRestClient(HOSTNAME, Integer.parseInt(PORT));
      client.removeAllDictionaryEntries();
    }

    @Test
    void test1NoCommandLineArguments() {
        MainMethodResult result = invokeMain( Project5.class );
        assertThat(result.getTextWrittenToStandardError(), containsString("** \"The Project #5 command-line interface has been provided below."));
    }

    @Test
    void test2EmptyServer() {
        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080");

        assertThat(result.getTextWrittenToStandardError(), equalTo("** Uh oh, looks like there were less than than 10 arguments passed to the program!\nProject #5 requires 10 arguments if ran w/o the -search option!\r\n"));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }


    @Test
    void test2bMoreThan10Arguments() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";
        MainMethodResult result = invokeMain( Project5.class, airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3, "test", "-host", "localhost", "-port", "8080");

        assertThat(result.getTextWrittenToStandardError(), equalTo("** Uh oh, looks like there were more than 10 arguments passed to the program!\nProject #5 only requires 1 argument (IF ran w/ -search option; which itself has a max of 4 arguments) or a maximum of 10 arguments total (IF w/o -search)!\r\n"));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }

    @Test
    void test3NoDefinitionsGivesError() {
        String airline = "Lufthansa";
//        String flightNumber = "69";
//        String src = "PDX";
//        String depart = "3/09/2023 3:35 am";
//        String dest = "SAN";
//        String arrive = "3/10/2023 4:47 am";
//        String word = "WORD";
        //try {
            //invokeMain(Project5.class, HOSTNAME, PORT, word);
        MainMethodResult result = invokeMain( Project5.class, airline, "-host", "localhost", "-port", "8080");
        assertThat(result.getTextWrittenToStandardError(), equalTo("** Uh oh, looks like there were less than than 10 arguments passed to the program!\nProject #5 requires 10 arguments if ran w/o the -search option!\r\n"));
            //fail("Should have thrown a RestException");

        //} catch (UncaughtExceptionInMain ex) {
        //    RestException cause = (RestException) ex.getCause();
         //   assertThat(cause.getHttpStatusCode(), equalTo(HttpURLConnection.HTTP_NOT_FOUND));
        //}
    }

    @Test
    void test4AddDefinition() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);

        assertThat(result.getTextWrittenToStandardError(), equalTo(""));

        String out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));

        result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline);

        assertThat(result.getTextWrittenToStandardError(), equalTo("** Uh oh, looks like there were less than than 10 arguments passed to the program!\nProject #5 requires 10 arguments if ran w/o the -search option!\r\n"));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));

        result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080");

        assertThat(result.getTextWrittenToStandardError(), equalTo("** Uh oh, looks like there were less than than 10 arguments passed to the program!\nProject #5 requires 10 arguments if ran w/o the -search option!\r\n"));

        out = result.getTextWrittenToStandardOut();
        assertThat(out, out, containsString(""));
    }

    /**
     * Test #2: README4.txt
     * Ran with no arguments, but just the -README option.
     */
    @Test
    void testREADME() {
        MainMethodResult result = invokeMain(Project5.class, "-README");
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("CS410P [Adv. Java Programming] Project #5 utilizes the REST API, provided through the AirlineRestClient & AirlineServlet classes - utilizing HTTP requests as a REST-ful Web Service!"));//README));
    }

//
//    /**
//     * Test #3: README4.txt (x2)
//     * Ran with no arguments, but just the -README option but twice, intentionally.
//     */
//    @Test
//    void testREADMEx5() {
//        MainMethodResult result = invokeMain(Project5.class, "-README", "-README");
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
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";
        MainMethodResult result = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, "123123123", "123", "pm", dest, arrive1, arrive2, arrive3);//"Lufthansa", "123", "PDX", "123123123", "123", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("[Main Date Initialization #1a] Error when attempting to format the departure time & date arguments, 123123123, 123, and pm."));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, dest, "123123123", "123", "pm");
        //MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "7:00", "pm", "SEA", "123123123", "123", "pm", "-textFile", "test.txt");

        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("[Main Date Initialization #1b] Error when attempting to format the arrival time & date arguments, 123123123, 123, and pm."));
    }

    /**
     * Test #6: Extra Argument
     * Ran with an extra argument.
     */
//    @Test
//    void testExtraArg() {
//        MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "7:00", "pm", "SEA", "g4j9j3g0j3g49jg49", "123123123", "123", "pm", "-textFile", "test.txt");
//
//        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Error, looks like we may have too many command-line arguments."));
//    }

    /**
     * Test #7: New Airline Text File
     * Ran normally with flight information and -print option.
     */
//    @Test
//    void testValid() {
//        File test_file = new File("test.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }
//        MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test.txt", "-print");
//        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
//        //Airline airline = parser.parse();
//        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
//        //test_file.deleteOnExit();
//
//        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString("'test.txt' was loaded successfully!"));
//    }

    /**
     * Test #8: Existing Airline Text File
     * Ran with valid flight information, but for a pre-existing file.
     */
//    @Test
//    void testPreExisting() throws ParserException {
//        File test_file = new File("test8.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }
//        MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "124", "PDX", "02/04/2023", "06:54", "pm", "SEA", "02/04/2023", "07:01", "pm", "-textFile", "test8.txt");
//        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
//        //Airline airline = parser.parse();
//        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
//        //test_file.deleteOnExit();
//        assertThat(result.getTextWrittenToStandardOut(), CoreMatchers.containsString(""));
//        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(""));
//    }

    /**
     * Test #9: Existing Airline Text File v2
     * Same as Test #8, but for specifically for an airline named "Project5."
     */
//    @Test
//    void testPreExistingP2() {
//        File test_file = new File("test9.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("Project4, 123, PDX, 2/04/2023 06:51, SEA, 2/04/2023 7:00");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }
//        MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test9.txt");
//        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
//        //Airline airline = parser.parse();
//        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
//        //test_file.deleteOnExit();
//        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Oh noes! The command-line Airline name specified ('Lufthansa') does not match the Airline name on-file!\n" +
//                "The file, instead specifies an Airline name of, 'Project5.'"));
//    }

    /**
     * Test #10a: Malformatted Hostname
     * Hocus pocus, hostname filled with bogus~~
     */
    @Test
    void testMalformattedHostname() {

//        File test_file = new File("test10.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("f89a3wh8f9ha2388afgh0983hg");
//            testwrite.println("c++isnotunnecessarilydifficult");
//            testwrite.println("therecanonlybe1sand0s");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }

        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "ihvwo4ijfgaoiweh", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        //MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test10.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("[AftFlight] Error while adding new flight entry to the server: ihvwo4ijfgaoiweh"));
    }

    /**
     * Test #1b.I.: Malformatted Port, Part 1
     * Hocus pocus, port number higher than it is possible!
     */
    @Test
    void testMalformattedPort() {

//        File test_file = new File("test10.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("f89a3wh8f9ha2388afgh0983hg");
//            testwrite.println("c++isnotunnecessarilydifficult");
//            testwrite.println("therecanonlybe1sand0s");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }

        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "99999", airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        //MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test10.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("Hmmm, the specified port-number of '\"99999\"' was outside of the valid RFC 1700 range for TCP ports"));
    }

    /**
     * Test #1b.I.: Malformatted Port, Part 2
     * Hocus pocus, port number not a number!
     */
    @Test
    void testMalformattedPort2() {

//        File test_file = new File("test10.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("f89a3wh8f9ha2388afgh0983hg");
//            testwrite.println("c++isnotunnecessarilydifficult");
//            testwrite.println("therecanonlybe1sand0s");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }

        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "notnumber", airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        //MainMethodResult result = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test10.txt");
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString("** Port \"notnumber\" must be an integer"));
    }

    /**
     * Test #11: What if airline name has quotation marks?
     * Ran with valid parameters except that the airline name has quotation marks.
     */
    @Test
    void testQuotationMarksForAirline() {
//        File test_file = new File("test11.txt");
//        try (PrintWriter testwrite = new PrintWriter(test_file))
//        {
//            testwrite.println("Lufthansa, 123, PDX, 99999s99999/da99/99 06:51, SEA, 12a3/4211/4223 7:00");
//        }
//        catch (FileNotFoundException t3)
//        {
//            throw new RuntimeException("Valid Test Text File was unable to be created: ", t3);
//        }
        String airline = "\"Lufthansa Airlines\"";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult result = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        //TextParser parser = new TextParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        //assertThat(airline.getName(), Matchers.equalTo("Lufthansa"));
        //test_file.deleteOnExit();
        assertThat(result.getTextWrittenToStandardError(), CoreMatchers.containsString(""));
    }

    /**
     * Test #12: Airline codes are too short
     * Airline code are too short (vs. 3-letters).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeShort() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult resultSrc = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, "A", depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        MainMethodResult resultDest = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, "B", arrive1, arrive2, arrive3);
        //MainMethodResult resultSrc = invokeMain(Project5.class, "Lufthansa", "123", "A", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test12a.txt");
        //MainMethodResult resultDest = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "B", "02/04/2023", "7:00", "pm", "-textFile", "test12b.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the source airport code is too short, it should be 3-digits of letters: A"));
        assertThat(resultDest.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the destination airport code is too short, it should be 3-digits of letters: B"));
    }

    /**
     * Test #13: Airline codes are too large
     * Airline code are too long (vs. 3-letters).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeLong() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult resultSrc = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, "ABCD", depart1, depart2, depart3, dest, arrive1, arrive2, arrive3);
        MainMethodResult resultDest = invokeMain( Project5.class, "-host", "localhost", "-port", "8080", airline, flightNumber, src, depart1, depart2, depart3, "BCDE", arrive1, arrive2, arrive3);
        //MainMethodResult resultSrc = invokeMain(Project5.class, "Lufthansa", "123", "ABCD", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm", "-textFile", "test13a.txt");
        //MainMethodResult resultDest = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BCDE", "02/04/2023", "7:00", "pm", "-textFile", "test13a.txt");

        assertThat(resultSrc.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the source airport code is too long, it should be 3-digits of letters: ABCD"));
        assertThat(resultDest.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the destination airport code is too long, it should be 3-digits of letters: BCDE"));
    }

    /**
     * Test #14: Airline codes have numbers.
     * Airline codes have numbers, which is very confusing (and invalid).
     * (From Project1IT.java)
     */
    @Test
    void testAirportCodeNumbers() {
        String airline = "Lufthansa";
        String flightNumber = "69";
        String src = "PDX";
        String depart1 = "3/09/2023";
        String depart2 = "3:35";
        String depart3 = "am";
        String dest = "SAN";
        String arrive1 = "3/10/2023";
        String arrive2  = "4:47";
        String arrive3 = "am";

        MainMethodResult resultSrc = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "AA1", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm");
        MainMethodResult resultDest = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BB2", "02/04/2023", "7:00", "pm");

        assertThat(resultSrc.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the source airport code has numbers(s), it should be 3-digits of letters only: AA1"));
        assertThat(resultDest.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the destination airport code has numbers(s), it should be 3-digits of letters only: BB2"));
    }

    /**
     * Test #15: Airline codes are invalid.
     * Airline codes do not exist in reality, very spooky.
     * @see edu.pdx.cs410J.AirportNames
     */
    @Test
    void testAirportNonValidCodes() {
        MainMethodResult resultSrc = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "AAZ", "02/04/2023", "6:53", "pm", "SEA", "02/04/2023", "7:00", "pm");
        MainMethodResult resultDest = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "PDX", "02/04/2023", "6:53", "pm", "BBZ", "02/04/2023", "7:00", "pm");

        assertThat(resultSrc.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the source airport code, 'AAZ', was not found in our airport-names database!"));
        assertThat(resultDest.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like the destination airport code, 'BBZ', was not found in our airport-names database!"));
    }

    /**
     * Test #16: Negative Flight Duration (Arrival - Departure)
     * If the flight has a negative flight-duration (in minutes)
     * (From Project1IT.java)
     */
    @Test
    void testInvalidFlightDuration() {
        MainMethodResult resultSrc = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "pm", "SEA", "02/04/2023", "7:00", "am");
        MainMethodResult resultDest = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am");

        assertThat(resultSrc.getTextWrittenToStandardError(), CoreMatchers.containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
        assertThat(resultDest.getTextWrittenToStandardError(), CoreMatchers.containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #17: <code>XML</code>-infoz Test(s) for <code>XML</code>Dumper
     * If valid, If the flight has a valid <code>XML</code>-airline information, then create the <code>XML</code> file.
     * Au contrar, if invalid (?).
     * @throws IOException If there are file related errors!
     * @throws ParserConfigurationException If there are <code>XML</code> related errors!
     */
//    @Test
//    void testXMLDumping() throws IOException, ParserConfigurationException, SAXException {
//        MainMethodResult resultValidXML = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-print");
//        //MainMethodResult resultInvalidXML = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");
//        String text = reader("test17.xml");//sw.toString();
//
//        assertThat(text, Matchers.containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));// +
//        // "\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));
//        AirlineXmlHelper dtdTest = new AirlineXmlHelper();
//        DocumentBuilderFactory dtdTestFactory = DocumentBuilderFactory.newInstance();
//        dtdTestFactory.setValidating(true);
//
//        DocumentBuilder dtdTestBuilder = dtdTestFactory.newDocumentBuilder();
//        dtdTestBuilder.setErrorHandler(dtdTest);
//        dtdTestBuilder.setEntityResolver(dtdTest);
//        try (FileReader dtdTestReader = new FileReader("test17.xml"))
//        {
//            dtdTestBuilder.parse("test17.xml");
//        }
//        catch (IOException m1)
//        {
//            System.err.println("[Project Integration Test #17 Error, IOException]" + m1.getMessage());
//        }
//        //assertThat(resultValidXML.getTextWrittenToStandardOut(), containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));
//        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
//    }

    /**
     * Test #18: -Search, Test 1: If airline name is missing!
     * If -search is used but airline name is missing!
     * Au contrar, if invalid (?).
     */
    @Test
    void testSearch1() {
        MainMethodResult resultSearch1 = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "-search");
        //MainMethodResult resultInvalidXML = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        assertThat(resultSearch1.getTextWrittenToStandardError(), CoreMatchers.containsString("[AftFlight] Invalid or missing arguments detected for -search!"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #19: -Search, Test 2: If airline name is there, but either src and/or dest airport codes are missing!
     * (and also if there are more parameters than those three max!)
     */
    @Test
    void testSearch2() {
        MainMethodResult resultSearch2i = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "-search", "Lufthansa");
        MainMethodResult resultSearch2ii = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "-search", "Lufthansa", "PDX");
        MainMethodResult resultSearch2iii = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "-search", "Lufthansa", "PDX", "SEA", "02/04/2023", "7:00", "am");
        //MainMethodResult resultInvalidXML = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");

        //assertThat(resultSearch2i.getTextWrittenToStandardError(), CoreMatchers.containsString("Oh noes, looks like both the textFile & xmlFile arguments were specified! Only one at a time, pretty please!"));
        assertThat(resultSearch2ii.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, looks like there may be a airport code missing, as there was at least one argument specified.\nHowever, that also means there must be 3 arguments...\n...which would represent the airline name you wanted to search, the source airport code, & the destination airport code for a total of three (3) arguments (whilst using -search)!"));
        assertThat(resultSearch2iii.getTextWrittenToStandardError(), CoreMatchers.containsString("Uh oh, there were more than 3 arguments specified, but the maximum number of arguments with the -search option is 3 (airline name (+ two [2] optional src & dest airport code parameters!)"));
        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
    }

    /**
     * Test #20: Invalid <code>XML</code> Formatting (DTD Non-Compliance)
     * If both <code>XML</code>File specified has an invalid DTD!
     */
//    @Test
//    void testInvalidXMLFile() {
//        MainMethodResult resultXMLDTDTest = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "2:53", "am", "SEA", "02/04/2023", "7:00", "am", "-xmlFile", "invalid-airline.xml");
//        //MainMethodResult resultInvalidXML = invokeMain(Project5.class, "Lufthansa", "123", "PDX", "02/04/2023", "4:34", "pm", "SEA", "02/04/2023", "7:00", "am", "-textFile", "test16b.txt");
//
//        assertThat(resultXMLDTDTest.getTextWrittenToStandardError(), CoreMatchers.containsString("Error whilst processing XML-file, 'invalid-airline.xml' - Specific Error: [XmlParser Exception Type II.] The content of element type \"depart\" is incomplete, it must match \"(date,time)\"."));
//        //assertThat(resultInvalidXML.getTextWrittenToStandardError(), containsString("Is this Back To The Future, but with flying? Because it looks like the total flight time is somehow negative: "));
//    }
}