package com.jksk.orgcreditratingpro.login.service.impl;

import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.LoginUser;
import com.jksk.orgcreditratingpro.login.service.LoginService;
import com.jksk.orgcreditratingpro.login.dao.LoginDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author
 * @create 2019-07-02
 **/
@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private LoginDao loginDao;

    @Override
    public LoginUser selectUserByUserName(String userName) throws JkskException {
        return loginDao.selectUserByUserName(userName);
    }
}