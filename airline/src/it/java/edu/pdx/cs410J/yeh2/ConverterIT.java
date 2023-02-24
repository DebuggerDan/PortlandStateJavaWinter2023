//package edu.pdx.cs410J.yeh2;
//
//import edu.pdx.cs410J.InvokeMainTestCase;
//
//import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileReader;
//import java.io.IOException;
//
//import static org.hamcrest.CoreMatchers.containsString;
//import static org.hamcrest.MatcherAssert.assertThat;
//
//public class ConverterIT extends InvokeMainTestCase {
//    /**
//     * An integration test for the {@link Converter} main class.
//     */
//    //class ConverterIT extends InvokeMainTestCase {
//
//
//    /**
//     * A function that reads (returns <code>String</code>s) txt files!
//     * It also deletes the file afterwards so that there are no pesky txt files cluttering the resource folders!
//     * (From Project2)
//     * @param txtfile A text file name-string!
//     * @return result A string from a file that was read by the function!
//     * @throws IOException If the file cannot be read!
//     */
//    private String reader(String txtfile) throws IOException
//    {
//        StringBuilder result = new StringBuilder();
//        //result.append("");
//
//        File read_file = new File(txtfile);
//        FileReader file_read = new FileReader(read_file);
//
//        try (BufferedReader read_buffer = new BufferedReader(file_read))
//        {
//            String currline = read_buffer.readLine();
//
//            while (currline != null)
//            {
//                result.append(currline);
//                currline = read_buffer.readLine();
//
//                if (currline != null)
//                {
//                    result.append("\n");
//                }
//            }
//        }
//        catch (IOException m1)
//        {
//            //System.out.println("Error! README not found!", m1);
//        }
//
//        File alright_time_to = new File(txtfile);
//        alright_time_to.delete();
//
//        return result.toString();
//    }
//
//    private MainMethodResult invokeMain(String... args) {
//        return invokeMain(Converter.class, args);
//    }
//
//    /**
//     * Test #0: Converter should print a command-line interface if it is run without any arguments.
//     */
//    void testNoCommandLineArguments() {
//        InvokeMainTestCase.MainMethodResult result = invokeMain();
//        assertThat(result.getTextWrittenToStandardOut(), containsString("usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile"));
//    }
//
//    /**
//     * Test #1: Converter should print a command-line interface if it is run with one argument.
//     */
//    void testOneCommandLineArgument(){
//        InvokeMainTestCase.MainMethodResult result = invokeMain("test1.txt");
//        assertThat(result.getTextWrittenToStandardOut(), containsString("usage: java edu.pdx.cs410J.<login-id>.Converter textFile xmlFile"));
//    }
//
//
//}
