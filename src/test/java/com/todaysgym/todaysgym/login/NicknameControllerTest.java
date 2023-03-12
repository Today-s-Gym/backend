package com.todaysgym.todaysgym.login;

import com.todaysgym.todaysgym.exception.BaseResponse;
import com.todaysgym.todaysgym.exception.BaseResponseStatus;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class NicknameControllerTest {

    @Autowired
    NicknameController nicknameController;

    @Test
    public void 중복체크() throws Exception{
        //Given
        String nickname = "pulsop";

        //Then
        Assertions.assertEquals(new BaseResponse<>(BaseResponseStatus.SUCCESS).getCode(), nicknameController.updateNickname(nickname).getCode());

    }
}
