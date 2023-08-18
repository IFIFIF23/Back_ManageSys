package com.lantu;

import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.User;
import io.jsonwebtoken.Claims;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class JwtUtilTest {
    @Autowired
    private JwtUtil jwtUtil;

    @Test
    public void testCreateJwt(){
        User user = new User();
        user.setUsername("jhq");
        user.setPhone("18801216165");
        String token = jwtUtil.createToken(user);
        System.out.println(token);
    }

    @Test
    public void testParseJwt(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhMzRkMWMyNS02ZTBlLTRlM2YtOThjYi03YWVhMTRmMzgyMWQiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTg4MDEyMTYxNjVcIixcInVzZXJuYW1lXCI6XCJqaHFcIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTIzMzE0MjcsImV4cCI6MTY5MjMzMzIyN30.nRrRNUi2OE3HYwF9wWQfcTd-txJCc6NJ83A34POSbQ0";
        Claims claims = jwtUtil.parseToken(token);
        System.out.println(claims);
    }

    @Test
    public void testParseJwt2(){
        String token = "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJhMzRkMWMyNS02ZTBlLTRlM2YtOThjYi03YWVhMTRmMzgyMWQiLCJzdWIiOiJ7XCJwaG9uZVwiOlwiMTg4MDEyMTYxNjVcIixcInVzZXJuYW1lXCI6XCJqaHFcIn0iLCJpc3MiOiJzeXN0ZW0iLCJpYXQiOjE2OTIzMzE0MjcsImV4cCI6MTY5MjMzMzIyN30.nRrRNUi2OE3HYwF9wWQfcTd-txJCc6NJ83A34POSbQ0";
        User user = jwtUtil.parseToken(token, User.class);
        System.out.println(user);
    }
}
