package dziubenko;

/**
 * Created by Nataliia Dziubenko on 13.07.2017
 */
class FakeSenderImpl implements EmailSenderService {
    @Override
    public void sendEmail(String email, String name, String lastName) {
        try {
            Thread.sleep(500);
            System.out.println(String.format("successfully sent message to %s %s", name, lastName));
        } catch (InterruptedException e) {
            System.err.println(String.format("failed sending message to %s %s", name, lastName));
            e.printStackTrace();
        }
    }
}
