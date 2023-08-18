package com.lantu.sys.service.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lantu.common.utils.JwtUtil;
import com.lantu.sys.entity.User;
import com.lantu.sys.mapper.UserMapper;
import com.lantu.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Yyf
 * @since 2023-05-05
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public Map<String, Object> login(User user) {
        //根据用户名和密码查询(mybatis-plus)
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername,user.getUsername());
        User loginUser = this.baseMapper.selectOne(wrapper);

        //结果不为空，并且密码和传入密码匹配，则生成token给前端（登陆凭证），并将用户信息存入redis
        if(loginUser!=null && passwordEncoder.matches(user.getPassword(), loginUser.getPassword())){
            //暂时用UUID
            //String key = "user:" + UUID.randomUUID();

            //存入redis
            loginUser.setPassword(null);
            //redisTemplate.opsForValue().set(key, loginUser,30, TimeUnit.MINUTES);

            //用jwt替换uuid和redis
            String token = jwtUtil.createToken(loginUser);

            //返回数据
            Map<String,Object> data = new HashMap<>();
            data.put("token", token);
            return data;
        }

        return null;
    }

//密码加密后登录逻辑变化
//    @Override
//    public Map<String, Object> login(User user) {
//        //根据用户名和密码查询(mybatis-plus)
//        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(User::getUsername,user.getUsername());
//        wrapper.eq(User::getPassword,user.getPassword());
//        User loginUser = this.baseMapper.selectOne(wrapper);
//
//        //结果不为空，则生成token给前端（登陆凭证），并将用户信息存入redis
//        if(loginUser!=null){
//            //暂时用UUID
//            String key = "user:" + UUID.randomUUID();
//
//            //存入redis
//            loginUser.setPassword(null);
//            //redisTemplate.opsForValue().set(key, loginUser); //登录有效时长默认永久有效
//            //redisTemplate.opsForValue().set(key, loginUser,10, TimeUnit.SECONDS);
//            redisTemplate.opsForValue().set(key, loginUser,30, TimeUnit.MINUTES);
//
//            //返回数据
//            Map<String,Object> data = new HashMap<>();
//            data.put("token",key);
//            return data;
//        }
//
//        return null;
//    }

    @Override
    public Map<String, Object> getUserInfo(String token) {
        //根据token获取用户信息
        //Object obj = redisTemplate.opsForValue().get(token);
        User loginUser = null;
        try {
            loginUser = jwtUtil.parseToken(token, User.class);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(loginUser != null){
            //User loginUser = JSON.parseObject(JSON.toJSONString(obj), User.class);  //反序列化为一个User对象(redis用)
            Map<String,Object> data = new HashMap<>();
            data.put("name", loginUser.getPassword());
            data.put("avatar", loginUser.getAvatar());   //头像

            //角色
            List<String> roleList = this.baseMapper.getRoleNameByUserId(loginUser.getId());
            data.put("roles", roleList);

            return data;
        }
        return null;
    }

    @Override
    public void logout(String token) {
        //redisTemplate.delete(token);
    }

}
