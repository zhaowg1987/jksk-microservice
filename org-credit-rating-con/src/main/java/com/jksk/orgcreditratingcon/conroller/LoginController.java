package com.jksk.orgcreditratingcon.conroller;

import com.jksk.commonutil.FastJsonUtil;
import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.constants.ErrorCode;
import com.jksk.entity.constants.SuccessCode;
import com.jksk.entity.orgcreditrating.LoginUser;
import com.jksk.entity.orgcreditrating.LoginUserQ;
import com.jksk.orgcreditratingcon.feignservice.OrgCreditRatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 登录接口
 *
 * @Author
 * @create 2019-07-03
 **/
@Slf4j
@Api(value = "用户登录")
@RestController
public class LoginController {

    @Autowired
    private OrgCreditRatingService orgCreditRatingService;

    @ApiOperation(value="用户输入用户名密码登录", notes="根据输入用户名和密码校验用户是否可以登录。")
    @RequestMapping(value = "/org_credit_rating_login", method = RequestMethod.POST)
    public ObjectResponse org_credit_rating_login(
            @RequestBody @ApiParam(name = "LoginUserQ", value = "登录用户实体LoginUserQ", required = true) LoginUserQ loginUserQ) {
        log.info("用户登录：{}",loginUserQ.toString());
        // 校验用户名密码是否为空
        if(StringUtils.isEmpty(loginUserQ.getUserName())
                || StringUtils.isEmpty(loginUserQ.getUserPwd())) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"用户名或密码为空，请重新录入！");
        }
        // 调用接口
        ObjectResponse objRes = orgCreditRatingService.selectUserByUserName(loginUserQ);
        // 出现异常。。。
        if(SuccessCode.SUCCESS_CODE != objRes.getStatus()) {
            return objRes;
        }
        // 获取数据库中查询出来的对象
        LoginUser loginUser = FastJsonUtil.parseToClass(String.valueOf(objRes.getData()),LoginUser.class) ;
        if(null == loginUser) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"用户名或密码错误，请重新录入！");
        }
        // 对输入密码进行处理
        String inputPwd = new SimpleHash("SHA-1", loginUserQ.getUserPwd(), null,1).toHex();
        // 比对密码
        if(!inputPwd.equals(loginUser.getUserPwd())) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"用户名或密码错误，请重新录入！");
        }
        if(!loginUserQ.getUserName().equals(loginUser.getUserName())) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"用户名或密码错误，请重新录入！");
        }
        return objRes;
    }

}
