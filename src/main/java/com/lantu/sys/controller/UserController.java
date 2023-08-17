package com.lantu.sys.controller;
// 新导入的包
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lantu.common.vo.Result;
import com.lantu.sys.entity.User;
import com.lantu.sys.service.IUserService;

// 这个地方导错包了
//import kotlin.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Yyf
 * @since 2023-05-05
 */
@RestController
@RequestMapping("/user")
//解决跨域问题  @CrossOrigin  已经单独写了配置文件
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping("/all")
    public Result<List<User>> getAllUser(){
        List<User> list = userService.list();
        return Result.success(list, "查询成功");
    }

    @PostMapping ("/login")
    public Result<Map<String,Object>> login(@RequestBody User user){
        Map<String,Object> data = userService.login(user);
        if (data!=null){
            return Result.success(data);
        }
        return Result.fail(20002,"用户名或密码错误");
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> getUserInfo(@RequestParam("token") String token){
        //根据token从redis获取用户信息
        Map<String, Object> data = userService.getUserInfo(token);
        if (data!=null){
            return Result.success(data);
        }
        return Result.fail(20003,"登录信息无效,请重新登录");
    }

    @PostMapping("/logout")
    public Result<?> logout(@RequestHeader("X-Token") String token){
        userService.logout(token);
        return Result.success();
    }

    @GetMapping("/list")
    public Result<Map<String, Object>> getUserList(@RequestParam(value = "username",required = false) String username,
                                               @RequestParam(value = "phone",required = false) String phone,
                                               @RequestParam("pageNo") Long pageNo,
                                               @RequestParam("pageSize") Long pageSize){

        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();//条件构造器
        wrapper.eq(StringUtils.hasLength(username), User::getUsername, username);//先判断再比较
        wrapper.eq(StringUtils.hasLength(phone), User::getPhone, phone);
        wrapper.orderByDesc(User::getId);

        Page<User> page = new Page<>(pageNo,pageSize);
        userService.page(page,wrapper);

        Map<String,Object> data = new HashMap<>();
        data.put("total",page.getTotal());
        data.put("rows",page.getRecords());

        return Result.success(data);
    }

    @PostMapping("")
    public Result<?> addUSer(@RequestBody User user){
        userService.save(user);
        return Result.success("新增用户成功");
    }

    @PutMapping("")
    public Result<?> updateUSer(@RequestBody User user){
        user.setPassword(null); //传入值为空默认不做更改
        userService.updateById(user);
        return Result.success("修改用户成功");
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<User> deleteUserById(@PathVariable("id") Integer id){
        userService.removeById(id);//逻辑删除，在yml文件中配置
        return Result.success("删除用户成功");
    }

}
