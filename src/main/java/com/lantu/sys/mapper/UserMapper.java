package com.lantu.sys.mapper;

import com.lantu.sys.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Yyf
 * @since 2023-05-10
 */
public interface UserMapper extends BaseMapper<User> {
    //@Select({"SELECT ROLE_NAME FROM X_USER_ROLE a, X_ROLE b WHERE a.USER_ID = #{userId} AND a.ROLE_ID = b.ROLE_ID"})//用注解写太长了，直接写在xml中更直观
    public List<String> getRoleNameByUserId(Integer userId);
}
