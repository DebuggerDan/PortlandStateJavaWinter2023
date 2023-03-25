package edu.pdx.cs410J.yeh2;

import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

class XmlDumperTest {

        /**
         * A function that reads (returns <code>String</code>s) txt files!
         * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
         * (from TextDumperTest)
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
         * <code>XML</code>Dumper Test #1: Valid <code>XML</code> file checks!
         * A test that tests valid <code>XML</code>-dumped files!.
         * @throws IOException If there are file related errors!
         * @throws ParseException If there are parsing related errors!
         * @throws ParserConfigurationException If there are <code>XML</code>-specific parsing errors!
         * @throws SAXException If there are <code>SAX</code>-<code>XML</code> API specific errors!
         */
        @Test
    void canDumpValidAirline2XML() throws IOException, ParseException, ParserConfigurationException, SAXException
    {
        String filename = "dump_test.xml";
        String airlineName = "Lufthansa";
        String flightNumber = "123";
        String src = "PDX";
        String depart = "02/04/2023 6:51 am";
        String dest = "SEA";
        String arrive = "02/04/2023 7:00 am";

        Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
        Airline airline = new Airline(airlineName, testdrive);

        File file = new File(filename);

        //StringWriter sw = new StringWriter();
        XmlDumper dumper = new XmlDumper(file);//sw.toString());
        try {
            dumper.dump(airline);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String text = reader(filename);//sw.toString();

        assertThat(text, containsString("<?"));// +
        // "\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));
        AirlineXmlHelper dtdTest = new AirlineXmlHelper();
        DocumentBuilderFactory dtdTestFactory = DocumentBuilderFactory.newInstance();
        dtdTestFactory.setValidating(true);

        DocumentBuilder dtdTestBuilder = dtdTestFactory.newDocumentBuilder();
        dtdTestBuilder.setErrorHandler(dtdTest);
        dtdTestBuilder.setEntityResolver(dtdTest);
        try (FileReader dtdTestReader = new FileReader(file))
        {
            dtdTestBuilder.parse(filename);
        }
        catch (IOException m1)
        {
           System.err.println("[XmlDumperTest #1 Error, IOException]" + m1.getMessage());
        }

    }

    /**
     * <code>XML</code>Dumper Test #2: Check for airport-code specifically sorted <code>XML</code> file checks!
     * A test that sort-dumps!
     * @throws IOException If there are file related errors!
     * @throws ParseException If there are parsing related errors!
     * @throws ParserConfigurationException If there are <code>XML</code>-specific parsing errors!
     * @throws SAXException If there are <code>SAX</code>-<code>XML</code> API specific errors!
     */
    @Test
    void canDumpVSortedXML() throws IOException, ParseException, ParserConfigurationException, SAXException
    {
        String filename = "dump_test.xml";
        String airlineName = "Lufthansa";
        String flightNumber = "123";
        String src = "PDX";
        String depart = "02/04/2023 6:51 am";
        String dest = "SEA";
        String arrive = "02/04/2023 7:00 am";

        Flight testdrive = new Flight(flightNumber, src, depart, dest, arrive);
        Airline airline = new Airline(airlineName, testdrive);

        File file = new File(filename);

        //StringWriter sw = new StringWriter();
        FileWriter fw = new FileWriter(file);
        PrintWriter pw = new PrintWriter(fw);

        XmlDumper dumper = new XmlDumper(pw, src, dest);//sw.toString());
        try {
            dumper.dump(airline);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pw.close();
        fw.close();
        //XmlParser parser = new XmlParser(sw);
        //String test = sw.toString();
        //pw.close();
        filename = file.getAbsolutePath();
        String parsedfile = reader(filename);

        assertThat(parsedfile, containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));

        //String text = reader(filename);//sw.toString();

        //assertThat(text, containsString("<!DOCTYPE airline SYSTEM \"http://www.cs.pdx.edu/~whitlock/dtds/airline.dtd\">"));// +
        // "\n123, PDX, 02/04/2023 6:51 am, SEA, 02/04/2023 7:00 am"));
//        AirlineXmlHelper dtdTest = new AirlineXmlHelper();
//        DocumentBuilderFactory dtdTestFactory = DocumentBuilderFactory.newInstance();
//        dtdTestFactory.setValidating(true);
//
//        DocumentBuilder dtdTestBuilder = dtdTestFactory.newDocumentBuilder();
//        dtdTestBuilder.setErrorHandler(dtdTest);
//        dtdTestBuilder.setEntityResolver(dtdTest);
//        try (FileReader dtdTestReader = new FileReader(file))
//        {
//            dtdTestBuilder.parse(filename);
//        }
//        catch (IOException m1)
//        {
//            System.err.println("[XmlDumperTest #1 Error, IOException]" + m1.getMessage());
//        }

    }
}
