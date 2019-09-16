package com.jksk.orgcreditratingpro.login.dao;

import com.jksk.entity.orgcreditrating.LoginUser;

/**
 * 用户登录接口
 *
 * @Author
 * @create 2019-07-02
 **/
public interface LoginDao {

    /**
     * 根据用户名获取登录用户
     * @param userName 用户名
     * @return LoginUser 用户信息
     */
    LoginUser selectUserByUserName(String userName);
}
