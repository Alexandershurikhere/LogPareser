package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogAnalazy {
    private LogParser parser=new LogParser();
    public Map<String, Integer> countLevels(List<String> lines) {
        Map<String, Integer> stats = new HashMap<>();
        for (String line : lines) {
            LogEntry entry =parser.parse(line);
            if (entry == null) {
                continue;
            }
            String level = entry.getLevel();
            stats.merge(level, 1, Integer::sum);
        }
        return stats;
    }
}
