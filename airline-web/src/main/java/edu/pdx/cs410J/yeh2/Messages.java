package edu.pdx.cs410J.yeh2;

/**
 * Class for formatting messages on the server side.  This is mainly to enable
 * test methods that validate that the server returned expected strings.
 */
public class Messages
{
    public static String missingRequiredParameter( String parameterName )
    {
        return String.format("[AftFlight] The required parameter \"%s\" is missing", parameterName);
    }

    public static String definedWordAs(String word, String definition )
    {
        return String.format( "[AftFlight] Defined %s as %s", word, definition );
    }

    public static String allAftFlightEntriesDeleted() {
        return "[AftFlight] All dictionary entries have been deleted";
    }

}
