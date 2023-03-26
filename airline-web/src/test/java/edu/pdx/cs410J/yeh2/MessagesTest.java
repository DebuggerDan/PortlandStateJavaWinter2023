package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Map;
import static org.hamcrest.Matchers.*;

class MessagesTest {

    /**
     * Checks that the message for a missing required parameter is correct!
     */
    @Test
    void testMissingRequiredParameter() {
        String message = Messages.missingRequiredParameter("testParameter");
        assertThat(message, containsString("[AftFlight] The required parameter \"testParameter\" is missing"));
    }

    /**
     * Checks definition stuff
     */
    @Test
    void testDefinitiveTVRatedMforMessages() {
        String message = Messages.definedWordAs("word", "definition");
        assertThat(message, containsString("[AftFlight] Defined word as definition"));
    }

    /**
     * Checks that all entries are deleted
     */
    @Test
    void testAllAftFlightPoof() {
        String message = Messages.allAftFlightEntriesDeleted();
        assertThat(message, containsString("[AftFlight] All dictionary entries have been deleted"));
    }

    /**
     * Dictionary word definition, yes
     */
    @Test
    void testParseAftFlightEntry() {
        Map.Entry<String, String> entry = Messages.parseAftFlightEntry("word : definition");
        assertThat(entry, is(not(nullValue())));
        assertThat(entry.getKey(), is("word"));
        assertThat(entry.getValue(), is("definition"));
    }
}
//public class Messages {
//}
