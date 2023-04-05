package com.todaysgym.todaysgym.login;


import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class NicknameControllerTest {

    @Autowired
    NicknameController nicknameController;

    public void 중복체크() throws Exception{
        //Given
        String nickname = "pulsop";

        //Then
        Assertions.assertEquals("pulsop", nicknameController.updateNickname(nickname));

    }
}
