//package bg.sofia.uni.fmi.web.project.service;
//
//import bg.sofia.uni.fmi.web.project.model.Event;
//import lombok.AllArgsConstructor;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Service;
//
//import javax.mail.Address;
//import javax.mail.Authenticator;
//import javax.mail.Message;
//import javax.mail.MessagingException;
//import javax.mail.PasswordAuthentication;
//import javax.mail.Session;
//import javax.mail.Transport;
//import javax.mail.internet.InternetAddress;
//import javax.mail.internet.MimeBodyPart;
//import javax.mail.internet.MimeMessage;
//import javax.mail.internet.MimeMultipart;
//import java.io.IOException;
//import java.util.Properties;
//
//@Service
//@AllArgsConstructor
//public class MailClientService {
//    @Value("${spring.mail.sender}")
//    private final String senderEmailAddress;
//
//    @Value("${spring.mail.password}")
//    private final String password;
//
//    public String sendEmail(String receiverEmailAddress, Event event) {
//        Properties prop = configureProperties();
//        Session session = createSession(prop);
//
//        try {
//            Message message = new MimeMessage(session);
//            addDataToMessage(receiverEmailAddress, message, event);
//
//            Transport.send(message);
//
//            return "Successfully sent message!";
//        } catch (MessagingException | IOException e) {
//
//            return "There was error while sending the message! Please try again by inputting correct sender email, " +
//                "sender password and receiver email!";
//        }
//
//    }
//
//    private Properties configureProperties() {
//        Properties prop = new Properties();
//        prop.put("mail.smtp.auth", true);
//        prop.put("mail.smtp.starttls.enable", "true");
//        prop.put("mail.smtp.host", "smtp.office365.com");
//        prop.put("mail.smtp.port", "587");
//        prop.put("mail.smtp.ssl.trust", "smtp.office365.com");
//
//        return prop;
//    }
//
//    private Session createSession(Properties prop) {
//        return Session.getInstance(prop, new Authenticator() {
//            @Override
//            protected PasswordAuthentication getPasswordAuthentication() {
//                return new PasswordAuthentication(senderEmailAddress, password);
//            }
//        });
//    }
//
//    private void addDataToMessage(String receiverEmailAddress, Message message, Event event)
//        throws MessagingException, IOException {
//        message.setFrom(new InternetAddress(senderEmailAddress));
//        Address addressTo = new InternetAddress(receiverEmailAddress);
//        message.setRecipient(Message.RecipientType.TO, addressTo);
//
//        message.setSubject("Invitation for event");
//
//        MimeMultipart multipart = new MimeMultipart();
//        MimeBodyPart attachment = new MimeBodyPart();
////        attachment.attachFile(new File(FILE_PATH));
//
//        MimeBodyPart text = new MimeBodyPart();
//        text.setText("We are pleased to invite you to the " + event.getName() + " event!");
////        if (perfectDayToLaunch == NO_APPROPRIATE_DAY_TO_LAUNCH) {
////
////        } else {
////            text.setText("Based on the given data, the perfect day to launch the space shuttle is day No: "
////                + perfectDayToLaunch);
////        }
//
//        multipart.addBodyPart(attachment);
//        multipart.addBodyPart(text);
//
//        message.setContent(multipart);
//    }
//}
