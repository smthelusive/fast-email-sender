package dziubenko;

/**
 * Created by Nataliia Dziubenko on 13.07.2017
 */
class Task implements Runnable {

    @Override
    public void run() {
        EmailSenderService service = new FakeSenderImpl();
        String delimiter = ";";
        while (!CSVReader.isDone() || !CSVReader.queue.isEmpty()) {
            try {
                String line = CSVReader.queue.take();
                if (!line.equals("")) {
                    line = line.replace("\"", "");
                    String[] data = line.split(delimiter);
                    if (data.length == 3 && data[0] != null && data[1] != null && data[2] != null) {
                        service.sendEmail(data[0], data[1], data[2]);
                    } else {
                        System.out.println("missed invalid row...");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
