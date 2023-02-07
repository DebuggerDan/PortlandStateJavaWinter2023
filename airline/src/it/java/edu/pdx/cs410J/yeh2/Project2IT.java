package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import java.lang.StringBuilder;

import java.io.File;
import java.io.FileNotFoundException;


/**
 * An integration test for the {@link Project2} main class.
 */
class Project2IT extends InvokeMainTestCase {


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
        return invokeMain( Project2.class, args );
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
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa","123","PDX","123123123", "123", "XDP", "02/04/2023", "07:00","-textFile", "test.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the departure time & date arguments, 123123123 and 123"));
    }

    /**
     * Test #5: Arrival Time Malformatted
     * Ran with a specified text file but an invalid arrival time.
     */
    @Test
    void testArrivalFormat() {
        MainMethodResult result = invokeMain(Project2.class, "Lufthansa","123","PDX","02/04/2023", "07:00", "XDP", "123123123", "123","-textFile", "test.txt");
        //StringBuilder readmex2 = new StringBuilder();
        assertThat(result.getTextWrittenToStandardError(), containsString("Error when attempting to formatting the arrival time & date arguments, 123123123 and 123"));
    }
}