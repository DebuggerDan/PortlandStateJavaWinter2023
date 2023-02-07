package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class TextDumperTest {

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

  @Test
  void airlineNameIsDumpedInTextFormat() throws IOException {
    String filename = "dump_test.txt";
    String airlineName = "Lufthansa";
    String flightNumber = "123";
    String src = "PDX";
    String depart = "2/04/2023 6:51:34 AM";
    String dest = "XDP";
    String arrive = "2/04/2023 7:00:00 AM";

    Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
    Airline airline = new Airline(airlineName, testdrive);

    //StringWriter sw = new StringWriter();
    TextDumper dumper = new TextDumper(filename);//sw.toString());
    try {
      dumper.dump(airline);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    String text = reader(filename);//sw.toString();

    assertThat(text, containsString("Lufthansa\n123, PDX, 2/04/2023 6:51:34 AM, XDP, 2/04/2023 7:00:00 AM"));

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
