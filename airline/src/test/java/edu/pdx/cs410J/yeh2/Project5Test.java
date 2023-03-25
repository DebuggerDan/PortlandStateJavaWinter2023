package edu.pdx.cs410J.yeh2;

import edu.pdx.cs410J.InvokeMainTestCase;
import org.junit.jupiter.api.Test;

public class Project5Test {
    /**
     * Test #1: "Maximum Overdrive" Debug Smashing Test [Quashing the Query Bug, circa. 3/25/23] {@literal <Non-Integration Version>}
     * If all checks-out, then indeed, output the flight information to the user!
     * First, we will add an {@code airline} "Lufthansa" to the server, then add two valid {@code flight}s (with the same src & depart airport codes) to that airline.
     * Second, we will search for that {@code flight}, then search for it using {@code HTTP}, where we search with the same src & depart airport codes.
     * Third, if it is found, which it should find it since it should be valid, then the machine-readable {@code XML} format should be displayed.
     *
     * Check for the following:
     * 1. Check that the {@code XML} was returned via the {@code HTTP GET} request.
     */
//    @Test
//    void testQueso()
//    {
//        //InvokeMainTestCase.MainMethodResult resultQue1 = invokeMain(Project5.class, "-host", "localhost", "-port", "8080", "-search", "Lufthansa");
//    }
}
