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

class XmlParserTest {

    /**
     * A function that reads (returns <code>String</code>s) txt files!
     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
     * @see TextDumperTest
     * @param txtfile The text file name-string!
     * @return result A string from a file that was read by the function!
     * @throws IOException If the file cannot be read!
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

    /**
     * A function that tests if valid XML files can be parsed!
     * @throws ParserException If there is a <code>XML</code>-parsing specific error!
     */
    @Test
    void canParseValidXml() throws SAXException, IOException, ParserConfigurationException, ParseException, ParserException {
//        String filename = "valid-airline.xml";
//        XmlParser xmlParserTest = new XmlParser(filename);
//        Airline lufthansa = null;
//        lufthansa = xmlParserTest.parse();
//        assertThat(lufthansa.getName(), equalTo("Valid Airlines"));
//        //assertThat(lufthansa.getFlights().size(), equalTo(2));
        String filename = "dump_test.xml";
        String airlineName = "Valid Airlines";
        String flightNumber = "1437";
        String src = "BJX";
        String depart = "09/25/2020 5:00 pm";
        String dest = "CMN";
        String arrive = "09/26/2020 3:56 am";

        Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
        Airline airline = new Airline(airlineName, testdrive);

        File file = new File("dump_test.xml");
        try (PrintWriter testwrite = new PrintWriter(file))
        {
            testwrite.println("<?xml version='1.0' encoding='us-ascii'?>\n" +
                    "\n" +
                    "<!DOCTYPE airline\n" +
                    "          SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">\n" +
                    "\n" +
                    "<airline>\n" +
                    "  <name>Valid Airlines</name>\n" +
                    "  <flight>\n" +
                    "    <number>1437</number>\n" +
                    "    <src>BJX</src>\n" +
                    "    <depart>\n" +
                    "      <date day=\"25\" month=\"9\" year=\"2020\"/>\n" +
                    "      <time hour=\"17\" minute=\"0\"/>\n" +
                    "    </depart>\n" +
                    "    <dest>CMN</dest> <!-- They're taking me to Marrakesh! -->\n" +
                    "    <arrive>\n" +
                    "      <date day=\"26\" month=\"9\" year=\"2020\"/>\n" +
                    "      <time hour=\"3\" minute=\"56\"/>\n" +
                    "    </arrive>\n" +
                    "  </flight>\n" +
                    "  <flight>\n" +
                    "    <number>7865</number>\n" +
                    "    <src>JNB</src>\n" +
                    "    <depart>\n" +
                    "      <date day=\"15\" month=\"5\" year=\"2020\"/>\n" +
                    "      <time hour=\"7\" minute=\"24\"/>\n" +
                    "    </depart>\n" +
                    "    <dest>XIY</dest>\n" +
                    "    <arrive>\n" +
                    "      <date day=\"16\" month=\"5\" year=\"2020\"/>\n" +
                    "      <time hour=\"9\" minute=\"7\"/>\n" +
                    "    </arrive>\n" +
                    "  </flight>\n" +
                    "</airline>\n" +
                    "\n");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Valid Test XML File was unable to be created: ", t3);
        }

        StringWriter sw = new StringWriter();
        XmlDumper dumper = new XmlDumper(filename);//sw.toString());
        try {
            dumper.dump(airline);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // "\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));
        AirlineXmlHelper dtdTest = new AirlineXmlHelper();
        DocumentBuilderFactory dtdTestFactory = DocumentBuilderFactory.newInstance();
        dtdTestFactory.setValidating(true);

        DocumentBuilder dtdTestBuilder = dtdTestFactory.newDocumentBuilder();
        dtdTestBuilder.setErrorHandler(dtdTest);
        dtdTestBuilder.setEntityResolver(dtdTest);
        //dtdTestBuilder.parse(file);

        //    System.err.println("[XmlParserTest (but technically XmlDumper) #1 Error, IOException]" + m1.getMessage());

        XmlParser xmlParserTest = new XmlParser(file);
        Airline lufthansa = xmlParserTest.parse();
        assertThat(airline.getName(), equalTo("Valid Airlines"));
        String text = reader(filename);//sw.toString();
        assertThat(text, containsString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>"));// +
        file.deleteOnExit();
        //assertThat(lufthansa.getDepartureString(), equalTo("02/04/2023 6:51 am"));
    }

    /**
     * Invalid XML file test.
     * @throws ParserException If the file cannot be created.
     */
    @Test
    void invalidXMLFileReturnsNull() throws ParserException {
        //InputStream resource = getClass().getResourceAsStream("empty-airline.txt");
        //assertThat(testFilesPresent(this.testfile2), equalTo(true));

        XmlParser parser = new XmlParser("invalid-airline.xml");//new InputStreamReader(resource));
        //assertThrows(ParserException.class, parser::parse);
        //assertThat(parser.parse(), equalTo(null));
    }

    /**
     * invalid XML file test, v2.
     * @throws IllegalArgumentException If there was an invalid argument.
     */

    @Test
    void invalidXMLFileCannotBeParsedv2() throws IllegalArgumentException {
        //InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
        //assertThat(testFilesPresent(this.testfile1), equalTo(true));
        File test_file = new File("test.xml");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Lufthansa, 123, 24M");
            testwrite.println("            !!!!!!!!!!!!!!!!!!!!!!!!!!!!!9d0f2j30f0923j 023,f23,,,,,,,,,,,,,,,,,,,");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Invalid Test XML File was unable to be created: ", t3);
        }
        XmlParser parser = new XmlParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        test_file.deleteOnExit();
        //assertThrows(IllegalArgumentException.class, parser::parse);
        assertThrows(ParserException.class, parser::parse);
    }

    /**
     * Invalid XML file test, v3.
     * @throws IllegalArgumentException If there was an invalid argument.
     */
    @Test
    void invalidXMLFileCannotBeParsedv3() throws IllegalArgumentException {
        //InputStream resource = getClass().getResourceAsStream("valid-airline.txt");
        //assertThat(testFilesPresent(this.testfile1), equalTo(true));
        File test_file = new File("test.xml");
        try (PrintWriter testwrite = new PrintWriter(test_file))
        {
            testwrite.println("Lufthansa, 123, PDX, 2/04/2023 6:51:34 AM, SEA, 2414, 34525322/0325234/3202523, 7:25235235:00322 AM, 351253, 4543");
            testwrite.println("g4g43g43gg,43g,4g334g34,g,34g,34 g, 34 ,g3,4 g3, 34,g ,3g4, 3g, 253, 4543");
        }
        catch (FileNotFoundException t3)
        {
            throw new RuntimeException("Invalid Test XML File was unable to be created: ", t3);
        }
        XmlParser parser = new XmlParser(test_file);//new InputStreamReader(resource));
        //Airline airline = parser.parse();
        test_file.deleteOnExit();
        //assertThrows(IllegalArgumentException.class, parser::parse);
        assertThrows(ParserException.class, parser::parse);
    }
}
