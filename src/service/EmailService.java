package service;

public class EmailService {

    public void sendEmail(String to, String subject, String body) {
        System.out.println("Надсилаємо email на " + to);
        System.out.println("Тема: " + subject);
        System.out.println("Текст: " + body);
    }
}
