package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

  @Test
  void airlineNameIsDumpedInTextFormat() {
    String airlineName = "Test Airline";
    Airline airline = new Airline(airlineName);

    StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(sw);
    try {
      dumper.dump(airline);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String text = sw.toString();
    assertThat(text, containsString(airlineName));
  }

  @Test
  void canParseTextWrittenByTextDumper(@TempDir File tempDir) throws IOException, ParserException {
    String airlineName = "Lufthansa";
    Airline airline = new Airline(airlineName);

    //File test_file = new File("test.txt");
    TextDumper dumper = new TextDumper("test.txt");
    dumper.dump(airline);

    TextParser parser = new TextParser("test.txt");
    Airline read = parser.parse();
    assertThat(read.getName(), equalTo(airlineName));
    //test_file.deleteOnExit();
  }
}
