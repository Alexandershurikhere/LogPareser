package org.example;

import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Main {
    public static void main(String[] args) throws IOException, ExecutionException, InterruptedException {
        ExecutorService executorService=Executors.newFixedThreadPool(4);
        Path path=Path.of("src/main/resources/app.log");
        List<String> allLines= Files.readAllLines(path);
        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        int chunkSize=500/4;
        for(int i=0;i<4;i++) {
            int from = i * chunkSize;
            int to = (i == 4 - 1) ? allLines.size() : (i + 1) * chunkSize;
           List<String> chunk= allLines.subList(from,to);
            LogTask logTask=new LogTask(chunk,new LogParser());
            futures.add(executorService.submit(logTask));

        }
        Map<String, Integer> totalStats = new HashMap<>();
        for (Future<Map<String, Integer>> future : futures){
            Map<String, Integer> taskStats = future.get();
           taskStats.forEach((level, count) -> totalStats.merge(level, count, Integer::sum));
        }
        executorService.shutdown();
        System.out.println(totalStats);



    }
}
