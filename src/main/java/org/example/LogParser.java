package org.example;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogParser {
    private final static Pattern PATTERN=Pattern.compile("\\[(\\d+-\\d+-\\d+\\s\\d+:\\d+:\\d+)\\]\\s([A-Z]+)\\s(.*)");
    public LogEntry parse(String line){
        Matcher matcher= PATTERN.matcher(line);
        if(!matcher.matches()){
            return null;
        }
        String timestamp = matcher.group(1);
        String level = matcher.group(2);
        String message = matcher.group(3);


        return new LogEntry(timestamp, level, message);

    }


}
