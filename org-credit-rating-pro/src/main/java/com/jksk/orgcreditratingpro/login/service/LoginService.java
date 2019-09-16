package com.jksk.orgcreditratingpro.login.service;

import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.LoginUser;

/**
 * 登录服务
 *
 * @Author
 * @create 2019-07-02
 **/
public interface LoginService {

    /**
     * 根据用户名获取登录用户
     * @param userName 用户名
     * @return LoginUser 用户信息
     */
    LoginUser selectUserByUserName(String userName) throws JkskException;

}
