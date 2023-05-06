package com.todaysgym.todaysgym.Email;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class EmailController {
    private final EmailService emailService;

    @PostMapping("/emailSend")
    public String emailSend(@RequestParam String email) throws Exception{
        emailService.sendSimpleMessage(email);
        return "이메일 전송을 완료했습니다!!";
    }
}
