package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.ParserException;

import java.io.IOException;

/**
 * This class converts a text file to an XML file.
 * @see Project4
 */
public class Converter {
    public static void main(String[] args) {
        if (args.length != 2)
        {
            System.out.println("usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile");
        }
        else
        {
            String textFile = args[0];
            String xmlFile = args[1];

            TextParser parser = new TextParser(textFile);//new InputStreamReader(resource));
            try
            {
                Airline lufthansa = parser.parse();
                XmlDumper xmldumpr = new XmlDumper(xmlFile);
                xmldumpr.dump(lufthansa);
            }
            catch (ParserException e1)
            {
                System.err.println("[Converter Error A] Unable to parse the text file: " + e1.getMessage());
                //return;
            }
            catch (IOException e2)
            {
                System.err.println("[Converter Error B] Unable to write to the XML file: " + e2.getMessage());
                //return;
            }
        }
    }
}
