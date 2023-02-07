package edu.pdx.cs410J.yeh2;

import com.google.common.annotations.VisibleForTesting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import java.util.*;
//import java.util.List;
//import java.util.LinkedList;
//import java.util.Collection;
import edu.pdx.cs410J.ParserException;

/**
 * The main class for the CS410J TextFile Project (#2)
 * usage: java -jar target/airline-2023.0.0.jar [options] <args>
 *      args are (in this order):
 *          airline      The name of the airline
 *          flightNumber     The flight number
 *          src      Three-letter code of departure airport
 *          depart      Departure date and time (24-hour time)
 *          dest     Three-letter code of arrival airport
 *          arrive      Arrival date and time (24-hour time)
 *      options are (options may appear in any order))
 *          -textFile      file Where to read/write the airline info
 *          -print      Prints a description of the new flight
 *          -README     Prints a README for this project and exits
 *      Date and time should be in the format: mm/dd/yyyy hh:mm
 *
 */
public class Project2 {

    public static void displayer(String txtfile, Integer Option)
    {

        if (Option == 1)
            /* If <code>Option</code> is 1, then display as a README file/parameter (where three lines are cut to only display
             * the command-line interface*/
        {
            try (InputStream displayme = Project2.class.getResourceAsStream(txtfile)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();
                    int idx = 0;
                    while (display_line != null && idx != 3)
                    {
                        display_line = display_buffer.readLine();
                        idx--;
                    }

                    while (display_line != null) {
                        System.out.println(display_line);
                        display_line = display_buffer.readLine();
                    }
                }
            } catch (IOException m1) {
                //System.out.println("Error! README not found!", m1);
            }
        }
        //if (Option == 0)
        else
            /* If Option != (n), where n is an valid, specific <code>Option</code> number corresponding to an If-Statement, then display whole file normally. */
        {
            try (InputStream displayme = Project2.class.getResourceAsStream(txtfile)) {
                InputStreamReader display_me = new InputStreamReader(displayme);

                try (BufferedReader display_buffer = new BufferedReader(display_me)) {
                    String display_line = display_buffer.readLine();

                    while (display_line != null) {
                        System.out.println(display_line);
                        display_line = display_buffer.readLine();
                    }
                }
            } catch (IOException m2) {
                //System.out.println("Error! README not found!", m1);
            }
        }
    }

    /**
     * <p>
     * A whimsical, simple but a bit silly, 'unwraveler' function for <code>AbstractList</code>s,
     * that "unthneeds" them into simple {@code String[]} arrays.
     * </p>
     * @param thneed {@code AbstractList<String>} In goes the list!
     * @return tuft Out goes a {@code String[]} array, unraveled from the {@code AbstractList<String>} {@param thneed}!
     */
    public static String[] unthneed(List<String> thneed) // Dr. Seuss reference heh
    {
        String[] tuft = new String[thneed.size()];
        Iterator<String> lorax = thneed.iterator();
//        int idx = 0;
//        for (Object truffula : thneed)
//        {
//            tuft[idx] = truffula;
//            idx++;
//        }
        for (int truffula = 0; truffula != thneed.size(); truffula++)
        {
            if (lorax.hasNext())
            {
                tuft[truffula] = lorax.next();
            }
            else
            {
                System.err.println("An arwy truffula tree tuft! The Lorax says, check the that loop, For-where this came!");
            }
        }

        return tuft;
    }

    @VisibleForTesting
    static boolean isValidDateAndTime(String dateAndTime) {
        return true;
    }

    public static void main(String[] args) {
        //Flight flight = new Flight();  // Refer to one of Dave's classes so that we can be sure it is on the classpath
        Flight runway = null;
        Airline lufthansa = null;
        String file_name = null;

        // This boolean will be used to indicate the creation of an new, but blank Airline (e.g. Blank Airline)
        Boolean fresh = false;
        Integer freshnum = 0;

        StringBuilder yarn = null;
        // For temporary string concatenation stuffs

        //String[] filelist = null;
        //Collection<>

        LinkedList<String> filelist = new LinkedList<String>();
        List<String> arglist = new LinkedList<String>();

        int print_option_num = 0;
        int readme_option_num = 0;
        int argnum = 0;

        final String readme_file = "README2.txt";

        // if (args == null) // args will never equal null, but it can have 0 arguments, i think
        if (args.length == 0)
        {
            // lufthansa = new Airline("N/A");
            displayer(readme_file, 1);
            /* Assuming that this function, that uses the Resources API
             * is permitted as it is only displaying a static text file,
             * specific for the README/no-args-default-command-line-interface displaying of text.
             */
        }

        for (int idx = 0; idx != args.length; idx++)
        {
            String current_arg = args[idx];

            if (current_arg.startsWith("-"))
            {
                String action = current_arg.substring(1);

                switch (action.toLowerCase())
                {
                    case "textFile":
                        file_name = args[(args.length <= idx++) ? (idx-1) : idx];
                        if (file_name.isEmpty())
                        {
                            file_name = "Empty Airline";
                        }
                        if (file_name.startsWith("-"))
                        {
                            //file_name = "-";
                            System.err.println("Hmm, a invalid file name was detected for the textFile (valid names include, e.g. lufthansa.txt)!\nTreating as empty name, creating default Empty Airline!");
                            file_name = "Empty Airline";

                            freshnum++;

                            if (fresh) // If 2nd+ 'Fresh' Airline to be Created
                            {
                                yarn = new StringBuilder();
                                yarn.append("Empty Airline #");
                                yarn.append(freshnum);
                                file_name = yarn.toString();
                            }
                            fresh = true;

                            //System.exit(1);
                        }
                        filelist.add(file_name);
//                        try {
//                            TextParser parsley = new TextParser(file_name);
//
//                            lufthansa = parsley.parse();
//
//                            if (lufthansa == null)
//                            {
//                                lufthansa = new Airline(args[0+idx], args[10+idx], args[20+idx], args[30+idx], args[40+idx], args[50+idx]);
//                            }
//
//                        }
//                        catch (ParserException m3)
//                        {
//                            lufthansa = new Airline(args[0]);
//                        }

                        break;

                    case "readme":
                        //displayer(readme_file, 0);
                        /* Assuming that this function, that uses the Resources API
                         * is permitted as it is only displaying a static text file,
                         * specific for the README/no-args-default-command-line-interface displaying of text.
                         */
//                        try (InputStream readme = Project2.class.getResourceAsStream("README2.txt")) {
//                            InputStreamReader reader_me = new InputStreamReader(readme);
//
//                            try (BufferedReader readme_buffer = new BufferedReader(reader_me)) {
//                                String readme_line = readme_buffer.readLine();
//
//                                while (readme_line != null) {
//                                    System.out.println(readme_line);
//                                    readme_line = readme_buffer.readLine();
//                                }
//                            }
//                        } catch (IOException m1) {
//                            //System.out.println("Error! README not found!", m1);
//                        }

                        //System.exit(1);

                        readme_option_num++;
                        // While I realize allowing the user to print the README multiple times may be kind of redundant
                        // and/or something that may become so, playing around with iterative option-handling now,
                        // just in case future projects may require repetitive options-handling stuffs.
                        break;

                    case "print":
//                        if (lufthansa == null)
//                        {
//                            lufthansa = new Airline(args[0]);
//                        }
//                        lufthansa.printAll();
                        print_option_num++;

                        break;

                    default:
                        System.err.println("Uh oh, looks like there was a invalid option used: " + action);
                }
            }
            else
            {
                argnum++;

                arglist.add(current_arg);
            }
        }

        if (argnum != 8)
        {
            System.err.println("Error, looks like we are missing some command line arguments. Creating blank empty airline.");
            fresh = true;
        }

        /**
         * <p>
         *     Self-Note #1: Argument-array Key:
         *
         *          {@code String airline = landing[0];}
         *
         *          {@code String flightNumber = landing[1];}
         *          {@code String src = landing[2]}
         *          {@code String depart = landing[3] + " " + landing[4];} Since Time-and-Date Stamps are two-ply args!
         *              E.g.{@code landing[3] + " " + landing[4];} = {@code "10/20/3040" + " " + "10:20";}
         *          {@code String dest = landing[5];
         *          {@code String arrive = landing[6] + " " + landing[5];}
         * </p>
         */

        try
        {
            String[] landing = unthneed(arglist);
            runway = new Flight(landing);
        }

        int total_actions_num = (print_option_num + readme_option_num + filelist.size());//argnum);
//        int readmes = print_option_num;
//        int prints = readme_option_num;
        int files = filelist.size();



        for (int idx = 0; idx != total_actions_num; idx++)
        {
            // 1.) Handling [multiple possibly] -README option(s)
            if (readme_option_num != 0)
            {
                /* Assuming that this function, that uses the Resources API
                 * is permitted as it is only displaying a static text file,
                 * specific for the README/no-args-default-command-line-interface displaying of text.
                 */
                displayer(readme_file, 0);
                readme_option_num--;
            }
            // 2.) Handling [multiple possibly] -textFile parameters
            if (files != 0)
            {

            }
        }
    }

    //args[0], args[1], args[2], args[3], args[4], args[5]);
    //AirlineParser read = new AirlineParser();
    //TextParser parser = new TextParser(lufthansa);
    //TextDumper dumper = new TextDumper();



//        if (args == null) {
//            System.err.println("Missing command line arguments");
//        }

//        try {
//            for (String arg : args) {
//                System.out.println(arg);
//
//                if (arg.startsWith("-")) {
//                    String parameter = arg.substring(1);
//                    if (parameter.equals("print")) {
//                        lufthansa.printAll();
//                    }
//                    if (parameter.equals("README")) {
//                        //System.out.println("Welcome to Project 1, created by Dan Jang for CS410P: Advanced Java Programming!");
//                        //System.out.println("This project focuses on extended classes & a bit more complex commandline parsing stuffs.");
//                        try (InputStream readme = Project1.class.getResourceAsStream("README2.txt")) {
//                            InputStreamReader readmereader = new InputStreamReader(readme);
//
//                            try (BufferedReader readmebuffer = new BufferedReader(readmereader)) {
//                                String readmeline = readmebuffer.readLine();
//
//                                while (readmeline != null) {
//                                    System.out.println(readmeline);
//                                    readmeline = readmebuffer.readLine();
//                                }
//                            }
//                        } catch (IOException m1) {
//                            //System.out.println("Error! README not found!", m1);
//                        }
//                        System.exit(1);
//                    } else {
//                        System.err.println("Invalid airline program parameter, o noes! (Parameter Specified: " + parameter + ")");
//
//                    }
//                } else {
//                    argnum++;
//                }
//            }
//        }
//        catch (NullPointerException m2)
//        {
//            System.exit(1);
//        }
        if (argnum != 6)
    {
        System.err.println("Missing command line arguments");
    }
//
}
}