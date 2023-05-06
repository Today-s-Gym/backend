package com.todaysgym.todaysgym.Email;


import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailService implements EmailServiceImpl {
    private final JavaMailSender emailSender;
    @Override
    public void sendSimpleMessage(String to) throws Exception {
        MimeMessage message = createMessage(to);
        try{
            emailSender.send(message);
        }catch (MailException es){
            es.printStackTrace();
            throw new IllegalStateException();
        }
    }

    private MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage  message = emailSender.createMimeMessage();
        message.addRecipients(Message.RecipientType.TO, to);
        message.setSubject("TodaysGym 경고 메일");

        String msgg="";
        msgg +=  "<div style='margin:20px;'>";
        msgg+= "<h1> 안녕하세요 TodaysGym입니다. </h1>";
        msgg+= "<br>";
        msgg+= "<p> 경고!! <p>";
        msgg+= "<br>";
        msgg+= "<p> 경고!!<p>";
        msgg+= "<br>";
        msgg+= "</div>";
        message.setText(msgg, "utf-8", "html");//내용
        message.setFrom(new InternetAddress("todaysgym12@gmail.com","TodaysGym"));//보내는 사람
        return message;
    }
}
