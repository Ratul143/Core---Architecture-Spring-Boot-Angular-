package jwt.backend.service;

import com.sun.mail.smtp.SMTPTransport;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

import static javax.mail.Message.RecipientType.CC;
import static javax.mail.Message.RecipientType.TO;
import static jwt.backend.constant.AppConstant.*;

/**
 * @author Jaber
 * @Date 8/24/2022
 * @Project backend
 */
@Service
@EnableAsync
@RequiredArgsConstructor
public class EmailService {
    private final CommonService commonService;


    @Async
    public void sendNewPasswordEmail(String username, String firstName, String password, String email, String role) throws MessagingException {
        Message message = createEmail(username, firstName, password, email, role);
        SMTPTransport smtpTransport = (SMTPTransport) getEmailSession().getTransport(TRANSFER_PROTOCOL);
        smtpTransport.connect(SMTP_SERVER, USERNAME, PASSWORD);
        smtpTransport.sendMessage(message, message.getAllRecipients());
        smtpTransport.close();
    }

    private Message createEmail(String username, String firstName, String password, String email, String role) throws MessagingException {
        Message message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(FROM_EMAIL));
        message.setRecipients(TO, InternetAddress.parse(email, false));
        message.setRecipients(CC, InternetAddress.parse(CC_EMAIL, false));
        message.setSubject(EMAIL_SUBJECT);
        message.setText("Hello " + firstName +
                ", \n \n Url: " + APPLICATION_URL +
                " \n \n Username: " + username +
                " \n Password: " + password +
                " \n \n Role: " + role +
                "\n \n For any queries, Please contact to the administrator");
        message.setSentDate(commonService.getCurrentDateTime());
        message.saveChanges();
        return message;
    }

    private Session getEmailSession() {
        Properties properties = System.getProperties();
        properties.put(SMTP_HOST, SMTP_SERVER);
        properties.put(SMTP_AUTH, true);
        properties.put(SMTP_PORT, DEFAULT_PORT);
        properties.put(SMTP_STARTTLS_ENABLE, true);
        properties.put(SMTP_STARTTLS_REQUIRED, true);
        return Session.getInstance(properties, null);
    }
}
