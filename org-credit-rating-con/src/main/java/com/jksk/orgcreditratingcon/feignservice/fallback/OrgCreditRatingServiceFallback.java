package com.jksk.orgcreditratingcon.feignservice.fallback;

import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.constants.ErrorCode;
import com.jksk.entity.orgcreditrating.GroupScore;
import com.jksk.entity.orgcreditrating.GroupScoreQ;
import com.jksk.entity.orgcreditrating.GroupScoreSource;
import com.jksk.entity.orgcreditrating.LoginUserQ;
import com.jksk.orgcreditratingcon.feignservice.OrgCreditRatingService;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @Author
 * @create 2019-07-04
 **/
@Component
public class OrgCreditRatingServiceFallback implements OrgCreditRatingService {
    @Override
    public ObjectResponse selectUserByUserName(@RequestBody LoginUserQ loginUserQ) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"LoginService-selectUserByUserName调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreList(GroupScoreQ groupScoreQ) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreList调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreSourceRadios() {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreSourceRadios调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse saveGroupScore(GroupScoreSource groupScoreSource) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-saveGroupScore调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse updateGroupScore(GroupScore groupScore) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-updateGroupScore调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreSourceById(Long groupScoreSourceId) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreSourceById调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreSourceByGroupId(Long groupId) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreSourceByGroupId调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreById(Long groupId) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreById调用服务提供者出现异常。");
    }

    @Override
    public ObjectResponse getGroupScoreByName(String groupName) {
        return new ObjectResponse(ErrorCode.FUSE_ERROR,"GroupScoreService-getGroupScoreByName调用服务提供者出现异常。");
    }
}
