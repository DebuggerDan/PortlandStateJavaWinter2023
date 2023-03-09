package edu.pdx.cs410J.yeh2;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Map;

public class TextDumper {
  private final Writer writer;

  public TextDumper(Writer writer) {
    this.writer = writer;
  }

  public void dump(Map<String, Airline> dictionary) {
    try (
      PrintWriter pw = new PrintWriter(this.writer)
    ){
      for (Map.Entry<String, Airline> entry : dictionary.entrySet()) {
        pw.println(entry.getKey() + " : " + entry.getValue());
      }

      pw.flush();
    }
  }
}
