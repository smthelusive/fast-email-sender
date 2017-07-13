package dziubenko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by Nataliia Dziubenko on 13.07.2017
 */
public class CSVReader {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        reader.run();
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
        BufferedReader br = null;
        String line;
        String delimiter = ";";
        ExecutorService executor = Executors.newFixedThreadPool(10000);
        try {
            br = new BufferedReader(new FileReader(path));
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    line = line.replace("\"", "");
                    String[] data = line.split(delimiter);
                    if (data.length == 3 && data[0] != null && data[1] != null && data[2] != null) {
                        Task task = new Task(data[0], data[1], data[2]);

                        executor.execute(task);
                    } else {
                        System.out.println("missed invalid row...");
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
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