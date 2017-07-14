package dziubenko;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * Created by Nataliia Dziubenko on 13.07.2017
 */
public class CSVReader {
    private static boolean done;
    private final static int NUM = 10000;
    final static BlockingQueue<String> queue = new ArrayBlockingQueue<>(50);

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        reader.run();
    }

    static boolean isDone() {
        return done;
    }

    private void run() {
        String path = "";
        boolean gotPath = false;
        Scanner scanner = new Scanner(System.in);
        while (!gotPath) {
            System.out.println("please input csv file path: ");
            path = scanner.next();
            if (new File(path).exists()) {
                gotPath = true;
            } else {
                System.out.println("sorry, your file not found...");
            }
        }
        long before = System.currentTimeMillis();
        ExecutorService executor = Executors.newFixedThreadPool(NUM);
        for (int i = 0; i < NUM; i++) {
            executor.submit(new Task());
        }
        Path file = Paths.get(path);
        try {
            Stream<String> lines = Files.lines(file, StandardCharsets.UTF_8);
            for(String line : (Iterable<String>)lines :: iterator) {
                queue.put(line);
            }
            done = true;
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
            try {
                executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
                System.out.println(System.currentTimeMillis() - before + " ms");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
