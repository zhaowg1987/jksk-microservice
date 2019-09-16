package com.jksk.orgcreditratingcon.feignservice;

import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.orgcreditrating.GroupScore;
import com.jksk.entity.orgcreditrating.GroupScoreQ;
import com.jksk.entity.orgcreditrating.GroupScoreSource;
import com.jksk.entity.orgcreditrating.LoginUserQ;
import com.jksk.orgcreditratingcon.feignservice.fallback.OrgCreditRatingServiceFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author
 * @create 2019-07-04
 **/
@FeignClient(name="jksk-org-credit-rating-pro", fallback = OrgCreditRatingServiceFallback.class)
public interface OrgCreditRatingService {

    /**
     * 根据用户名获取登录用户
     * @param loginUserQ 用户名
     * @return LoginUser 用户信息
     */
    @PostMapping("/org_credit_rating_login")
    ObjectResponse selectUserByUserName(@RequestBody LoginUserQ loginUserQ);

    /**
     * 获取评测机构列表
     * @param groupScoreQ
     * @return
     */
    @PostMapping("/groupscore/getGroupScoreList")
    ObjectResponse getGroupScoreList(@RequestBody  GroupScoreQ groupScoreQ);


    /**
     * 获取评测选项
     * @return
     */
    @PostMapping("/groupscore/getGroupScoreSourceRadios")
    ObjectResponse getGroupScoreSourceRadios();

    /**
     *
     * @param groupScoreSource
     * @return
     */
    @PostMapping("/groupscore/saveGroupScore")
    ObjectResponse saveGroupScore(@RequestBody GroupScoreSource groupScoreSource);


    @PostMapping("/groupscore/updateGroupScore")
    ObjectResponse updateGroupScore(@RequestBody GroupScore groupScore);


    @PostMapping("/groupscore/getGroupScoreSourceById")
    ObjectResponse getGroupScoreSourceById(@RequestBody Long groupScoreSourceId);


    @PostMapping("/groupscore/getGroupScoreSourceByGroupId")
    ObjectResponse getGroupScoreSourceByGroupId(@RequestBody Long groupId);

    @PostMapping("/groupscore/getGroupScoreById")
    ObjectResponse getGroupScoreById(@RequestBody Long groupId);


    @PostMapping("/groupscore/getGroupScoreByName")
    ObjectResponse getGroupScoreByName(@RequestBody String groupName);
}
