package com.jksk.orgcreditratingpro.login.controller;

import com.jksk.commonutil.FastJsonUtil;
import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.constants.ErrorCode;
import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.LoginUser;
import com.jksk.entity.orgcreditrating.LoginUserQ;
import com.jksk.orgcreditratingpro.login.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author
 * @create 2019-07-02
 **/
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @RequestMapping("/org_credit_rating_login")
    public ObjectResponse org_credit_rating_login(@RequestBody LoginUserQ loginUserQ) {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            LoginUser loginUser = loginService.selectUserByUserName(loginUserQ.getUserName());
            objectResponse.setMsg("操作成功。");
            objectResponse.setData(FastJsonUtil.parseToJSON(loginUser));
        } catch (JkskException e) {
            log.error("JkskException查询用户发生异常。",e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        } catch (Exception e) {
            log.error("Exception查询用户发生异常。",e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        }
        return objectResponse;
    }

}
