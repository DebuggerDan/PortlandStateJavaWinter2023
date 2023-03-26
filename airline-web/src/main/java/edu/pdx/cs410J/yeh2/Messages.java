package edu.pdx.cs410J.yeh2;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.*;

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

    public static Map.Entry<String, String> parseAftFlightEntry(String content) {
        Pattern pattern = Pattern.compile("\\s*(.*) : (.*)");
        Matcher matcher = pattern.matcher(content);

        if (!matcher.find()) {
            return null;
        }

        return new Map.Entry<>() {
            @Override
            public String getKey() {
                return matcher.group(1);
            }

            @Override
            public String getValue() {
                String value = matcher.group(2);
                if ("null".equals(value)) {
                    value = null;
                }
                return value;
            }

            @Override
            public String setValue(String value) {
                return null;
            }
        };
    }

    public static Map<String, String> parseAftFlightEntries(String content) {
        Map<String, String> map = new HashMap<>();
        String[] lines = content.split("\n");
        for (String line : content.split("\n"))
        {
            Map.Entry<String, String> entry = parseAftFlightEntry(line);
            if (entry != null) {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return map;
    }

}
