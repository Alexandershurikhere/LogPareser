package org.example;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class LogTask implements Callable<Map<String, Integer>>{
    private List<String> list;
    private LogParser logParser;

    public LogTask(List<String> list, LogParser logParser) {
        this.list = list;
        this.logParser = logParser;
    }

    @Override
    public Map<String,Integer> call() throws Exception {
        Map<String, Integer> stats = new HashMap<>();
        for(String line:list){
            LogEntry logEntry=logParser.parse(line);
            if(logEntry==null){
                continue;
            }
            String level=logEntry.getLevel();
            stats.merge(level,1,Integer::sum);

        }

        return stats;
    }
}
