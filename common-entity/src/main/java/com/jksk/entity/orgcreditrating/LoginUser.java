package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录用户
 *
 * @Author
 * @create 2019-07-02
 **/
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = -6059399343395204392L;

    // 用户ID
    private Long id;
    /** 登录账号 */
    private String userName;
    /** 登录密码 */
    private String userPwd;
    /** 真实姓名 */
    private String realName;

    /** 用户角色（有可能有多个角色--目前只展示一个角色） */
    private String roleName;

}
