package com.lantu;



import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest
class XAdminApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    void testMapper() {
        List<User> users = userMapper.selectList(null);//java.sql.SQLException: ORA-01017: 用户名/口令无效; 登录被拒绝??
        users.forEach(System.out::println);
    }
}
