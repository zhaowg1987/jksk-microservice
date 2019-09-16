package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录接口参数
 *
 * @Author
 * @create 2019-07-02
 **/
@Data
public class LoginUserQ implements Serializable {
    private static final long serialVersionUID = 5121539465374429036L;

    /*登录用户名*/
    private String userName;
    /*登录用户密码*/
    private String userPwd;
}
