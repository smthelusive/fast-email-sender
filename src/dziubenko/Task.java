package dziubenko;

/**
 * Created by Nataliia Dziubenko on 13.07.2017
 */
class Task implements Runnable {
    private EmailSenderService service;
    private String email;
    private String name;
    private String lastName;

    Task(String email, String name, String lastName) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        service = new FakeSenderImpl();
    }

    @Override
    public void run() {
        service.sendEmail(email, name, lastName);
    }
}
