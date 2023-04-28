package com.todaysgym.todaysgym.Email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@PropertySource("classpath:email.properties")
public class EmailConfig {

    @Value("${mail.smtp.port}")
    private int port;
    @Value("${mail.smtp.socketFactory.port}")
    private int socketPort;
    @Value("${mail.smtp.auth}")
    private boolean auth;
    @Value("${mail.smtp.starttls.enable}")
    private boolean starttls;
    @Value("${mail.smtp.starttls.required}")
    private boolean startlls_required;
    @Value("${mail.smtp.socketFactory.fallback}")
    private boolean fallback;
    @Value("${AdminMail.id}")
    private String id;
    @Value("${AdminMail.password}")
    private String password;

    @Bean //의존 관계 주입
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
        javaMailSender.setHost("smtp.gmail.com"); // 메인 도메인 서버 주소, smtp 서버 주소
        javaMailSender.setUsername(id); // 구글 아이디
        javaMailSender.setPassword(password); // 구글 비밀번호
        javaMailSender.setPort(port); // 메일 인증서버 포트 (gmail = 465, naver = 587)
        javaMailSender.setJavaMailProperties(getMailProperties()); //메일 인증서버 정보 가져오기
        javaMailSender.setDefaultEncoding("UTF-8");
        return javaMailSender;
    }

    //smtp 서버 정보 설정
    private Properties getMailProperties()
    {
        Properties pt = new Properties();
        pt.put("mail.smtp.socketFactory.port", socketPort); //포트 번호
        pt.put("mail.smtp.auth", auth); // smtp 인증
        pt.put("mail.smtp.starttls.enable", starttls); // smtp strattles 사용
        pt.put("mail.smtp.starttls.required", startlls_required);
        pt.put("mail.smtp.socketFactory.fallback",fallback);
        pt.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory"); //구글일 경우 이것을 설정해야 함
        return pt;
    }

}
