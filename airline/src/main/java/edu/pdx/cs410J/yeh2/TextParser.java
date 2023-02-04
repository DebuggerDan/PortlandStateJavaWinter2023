package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.AirlineParser;
import edu.pdx.cs410J.ParserException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import java.io.File;
import java.io.FileReader;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * A skeletal implementation of the <code>TextParser</code> class for Project 2.
 */
public class TextParser implements AirlineParser<Airline> {
  private final Reader reader;
  private String file_name;

  public TextParser(Reader reader) {
    this.reader = reader;
  }

  /**
   * Constructs a new TextParser object with the given file name, checks if it exists, but if it does not, throws
   * illegal argument exception, but if it exists, then set the name of the file to the constructed TextParser's file name.
   * @param file
   * @throws IllegalArgumentException
   */
  public TextParser(String file) throws IllegalArgumentException
  {
    if (file.isEmpty())
    {
      throw new IllegalArgumentException("Name of the file is invalid!");
    }

    try {
      File test = new File(file);
      testfile = new FileReader(test);
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("Looks like the file does not exist!");
    }
  }

  @Override
  public Airline parse() throws ParserException {
    try (
      BufferedReader br = new BufferedReader(this.reader)
    ) {

      String airlineName = br.readLine();

      if (airlineName == null) {
        throw new ParserException("Missing airline name");
      }

      return new Airline(airlineName);

    } catch (IOException e) {
      throw new ParserException("While parsing airline text", e);
    }
  }
}
