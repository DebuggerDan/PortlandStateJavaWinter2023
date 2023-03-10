package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import edu.pdx.cs410J.UncaughtExceptionInMain;
import edu.pdx.cs410J.web.HttpRequestHelper.RestException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.io.IOException;
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
}