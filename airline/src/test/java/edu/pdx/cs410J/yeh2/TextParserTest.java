package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;
import org.junit.jupiter.api.Test;

import java.io.*;
//import java.io.InputStreamReader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TextParserTest {

  private final String testfile1 = "valid-airline.txt";
  private final String testfile2 = "empty-airline.txt";
  //protected File file1 = null;
  //protected File file2 = null;

  @Test
 boolean testFilesPresent(String testname)
  {
//    try
//    {
    //this.file1 = new File(testfile1);
    //this.file2 = new File(testfile2);
    File localtestfile = new File(testname);
    try (FileReader testreader = new FileReader(localtestfile))// (File localtestfile = new File(testname))
    {
      if (localtestfile.exists())
      {
        return true;
      }
      else
      {
        return false;
      }
      // testreader = new FileReader(localtestfile);

    }
    catch (FileNotFoundException t1)
    {
      throw new RuntimeException("Test files were not found: ", t1);
      //System.err.println("Test files were not found: ");//, t1);
    }
    catch (IOException t2)
    {
      throw new RuntimeException("Input / Output error found: ", t2);
    }



//    }
//    catch (FileNotFoundException t1)
//    {
//      throw new FileNotFoundException("Test file #1 not found: ", t1);
//    }
  }

  @Test
  void validTextFileCanBeParsed() throws ParserException {
    //InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
    assertThat(testFilesPresent(this.testfile1), equalTo(true));

    TextParser parser = new TextParser(testfile1);//new InputStreamReader(resource));
    Airline airline = parser.parse();
    assertThat(airline.getName(), equalTo("Test Airline"));
  }

  @Test
  void invalidTextFileThrowsParserException() {
    //InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
    assertThat(testFilesPresent(this.testfile1), equalTo(true));

    TextParser parser = new TextParser(testfile2);//new InputStreamReader(resource));
    assertThrows(ParserException.class, parser::parse);
  }
}
