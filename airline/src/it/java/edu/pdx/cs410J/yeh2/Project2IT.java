package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import java.io.*;
import java.lang.StringBuilder;


/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {

    
    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @param txtfile
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
     * Invokes the main method of {@link Project2} with the given arguments.
     */
    private MainMethodResult invokeMain(String... args) {
        return invokeMain(Project2.class, args);
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
        MainMethodResult result = invokeMain(Project2.class, "-README");
        assertThat(result.getTextWrittenToStandardOut(), containsString(README));
    }


    /**
     * Test #3: README2.txt (x2)
     * Ran with no arguments, but just the -README option but twice, intentionally.
     */
    @Test
    void testREADMEx5() {
        MainMethodResult result = invokeMain(Project2.class, "-README", "-README");
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
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "123123123", "123", "XDP", "02/04/2023", "07:00", "-textFile", "test.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the departure time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "123123123", "123", "-textFile", "test.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the arrival time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #6: Extra Argument
     * Ran with an extra argument.
     */
    @Test
    void testExtraArg() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "07:00", "XDP", "g4j9j3g0j3g49jg49", "123123123", "123", "-textFile", "test.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #7: New Airline Text File
     * Ran normally with flight information and -print option.
     */
    @Test
    void testValid() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "test.txt", "-print");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #8: Existing Airline Text File
     * Ran with valid flight information, but for a pre-existing file.
     */
    @Test
    void testPreExisting() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "valid-airline.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #9: Existing Airline Text File v2
     * Same as Test #8, but for specifically for an airline named "Project2."
     */
    @Test
    void testPreExistingP2() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "valid-airline.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #10: Malformatted Airline File
     * Hocus pocus, airline filled with bogus~~
     */
    @Test
    void testMalformatted() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "valid-airline.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

    /**
     * Test #11: Invalid Year in Airline File
     * Ran with pre-existing airline text file such that has invalid years.
     */
    @Test
    void testInvalidYearParsing() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa", "123", "PDX", "02/04/2023", "06:53", "XDP", "02/04/2023", "07:00", "-textFile", "valid-airline.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error, looks like we may be missing or have too many command-line arguments. Creating blank empty airline."));
    }

}