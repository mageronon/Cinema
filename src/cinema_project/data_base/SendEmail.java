package cinema_project.data_base;

import com.sun.mail.smtp.SMTPTransport;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class SendEmail {

    public static String passwordReset = null;

    public SendEmail(String email) throws MessagingException {
        Properties props = System.getProperties();
        props.put("mail.smtp.host", "smtp.mailtrap.io");
        props.put("mail.smtp.port", 2525);
        props.put("mail.smtp.user", "634a2ecd21b2fa");
        props.put("mail.smtp.pass", "1f3a83448c79a6");
        props.put("mail.transport.protocol", "smtp");
        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("mageron52@gmail.com"));

        RandomString randomString = new RandomString();
        passwordReset = randomString.nextString();
        System.out.println(passwordReset);
        msg.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(email, false));
        msg.setSubject("Heisann " + System.currentTimeMillis());
        msg.setText(passwordReset);

        msg.setHeader("X-Mailer", "Tov Are's program");
        msg.setSentDate(new Date());
        SMTPTransport t =
                (SMTPTransport) session.getTransport("smtp");
        t.connect("smtp.mailtrap.io", "634a2ecd21b2fa", "1f3a83448c79a6");
        t.sendMessage(msg, msg.getAllRecipients());
        System.out.println("Response: " + t.getLastServerResponse());
        t.close();
    }
}