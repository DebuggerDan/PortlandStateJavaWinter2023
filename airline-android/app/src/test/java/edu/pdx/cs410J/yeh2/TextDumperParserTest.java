//package edu.pdx.cs410J.yeh2;
//
//import edu.pdx.cs410J.ParserException;
//import org.junit.jupiter.api.Test;
//
//import java.io.File;
//import java.io.PrintWriter;
//import java.io.StringReader;
//import java.io.StringWriter;
//import java.util.Collections;
//import java.util.Map;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.equalTo;
//
//public class TextDumperParserTest {
//  static String airline = "Lufthansa";
//  static String flightNumber = "69";
//  static String src = "PDX";
//  static String depart = "3/09/2023 3:35 am";
//  static String dest = "SAN";
//  static String arrive = "3/10/2023 4:47 am";
//  static Airline lufthansa = null;
//  static Flight runway = null;
//
//  @Test
//  void emptyMapCanBeDumpedAndParsed() throws ParserException, Exception {
//    Map<String, String> map = Collections.emptyMap();
//    Map<String, String> read = dumpAndParse(map);
//    assertThat(read, equalTo(map));
//  }
//
//  private Map<String, String> dumpAndParse(Map<String, String> map) throws ParserException, Exception  {
//    StringWriter sw = new StringWriter();
//    TextDumper dumper = new TextDumper(sw);
//    dumper.dump(map);
//
//    String text = sw.toString();
//
//    TextParser parser = new TextParser(new StringReader(text));
//    return parser.parse();
//
////    File parchment = new File("parchment.xml");
////
////    parchment.deleteOnExit();
////
////    PrintWriter quill = new PrintWriter(parchment);
////
////    quill.print(message);
////    quill.close();
////
////    XmlParser scroll = new XmlParser(parchment);
////    lufthansa = scroll.parse();
////
////    PrettyPrinter scribe = new PrettyPrinter(null, false);
////    scribe.dump(lufthansa);
//  }
//
//  @Test
//  void dumpedTextCanBeParsed() throws ParserException, Exception  {
//    Map<String, String> map = Map.of("one", "1", "two", "2");
//    //runway = new Flight(flightNumber, src, depart, dest, arrive);
//    //lufthansa = new Airline(airline, runway);
//    //Map<String, String> map = Map.of(airline, lufthansa);
//    Map<String, String> read = dumpAndParse(map);
//    assertThat(read, equalTo(map));
//  }
//}
