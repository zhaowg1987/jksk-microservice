package com.jksk.orgcreditratingpro.groupscore.controller;

import com.github.pagehelper.PageInfo;
import com.jksk.commonutil.FastJsonUtil;
import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.constants.ErrorCode;
import com.jksk.entity.constants.SuccessCode;
import com.jksk.entity.exception.JkskException;
import com.jksk.entity.orgcreditrating.GroupScore;
import com.jksk.entity.orgcreditrating.GroupScoreQ;
import com.jksk.entity.orgcreditrating.GroupScoreSource;
import com.jksk.entity.orgcreditrating.GroupScoreSourceRadios;
import com.jksk.orgcreditratingpro.groupscore.service.GroupScoreService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 机构评分管理
 * @Author
 * @create 2019-07-04
 **/
@Slf4j
@RestController
public class GroupScoreController {

    @Autowired
    private GroupScoreService groupScoreService;

    @RequestMapping(value = "/groupscore/getGroupScoreList", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreList(@RequestBody  GroupScoreQ groupScoreQ) {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            PageInfo<GroupScore> pageInfo = groupScoreService.selectGroupScoreList(groupScoreQ);
            objectResponse.setMsg("操作成功。");
            objectResponse.setData(FastJsonUtil.parseToJSON(pageInfo));
        } catch (JkskException e) {
            log.error("JkskException查询数据出现异常。", e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        } catch (Exception e) {
            log.error("Exception查询数据出现异常。", e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        }
        return objectResponse;
    }

    @RequestMapping(value = "/groupscore/getGroupScoreSourceRadios", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceRadios() {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            GroupScoreSourceRadios groupScoreSourceRadios = groupScoreService.selectGroupScoreRadio();
            objectResponse.setMsg("操作成功。");
            objectResponse.setData(FastJsonUtil.parseToJSON(groupScoreSourceRadios));
        }  catch (Exception e) {
            log.error("Exception查询数据出现异常。", e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        }
        return objectResponse;
    }

    @RequestMapping(value = "/groupscore/saveGroupScore", method = RequestMethod.POST)
    public ObjectResponse saveGroupScore(@RequestBody GroupScoreSource groupScoreSource) {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            if(StringUtils.isEmpty(groupScoreSource.getGroup_name())) {
                objectResponse.setStatus(ErrorCode.INVALID_PARAM);
                objectResponse.setMsg("请录入机构名称。");
                return objectResponse;
            }
            GroupScore groupScore = groupScoreService.saveGroupScore(groupScoreSource);
            objectResponse.setData(FastJsonUtil.parseToJSON(groupScore));
            objectResponse.setMsg("操作成功。");
        }  catch (Exception e) {
            log.error("Exception保存数据出现异常。", e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            if(e.toString().contains("for key 'uidx_crd_cr_group_score_groupname'")) {
                objectResponse.setMsg("机构名称已存在，请核查！");
            } else {
                objectResponse.setMsg(e.toString());
            }
        }
        return objectResponse;
    }

    @RequestMapping(value = "/groupscore/updateGroupScore", method = RequestMethod.POST)
    public ObjectResponse updateGroupScore(@RequestBody GroupScore groupScore) {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            if(null == groupScore.getId()) {
                objectResponse.setStatus(ErrorCode.INVALID_PARAM);
                objectResponse.setMsg("主键ID丢失。");
                return objectResponse;
            }
            groupScoreService.updateGroupScore(groupScore);
            objectResponse.setMsg("操作成功。");
        }  catch (Exception e) {
            log.error("Exception更新数据出现异常。", e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            if(e.toString().contains("for key 'uidx_crd_cr_group_score_groupname'")) {
                objectResponse.setMsg("机构名称已存在，请核查！");
            } else {
                objectResponse.setMsg(e.toString());
            }
        }
        return objectResponse;
    }


    @RequestMapping(value = "/groupscore/getGroupScoreSourceById", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceById(@RequestBody Long groupScoreSourceId) {
        try {
            GroupScoreSource groupScoreSource = groupScoreService.selectGroupScoreSourceById(groupScoreSourceId);
            if(null != groupScoreSource) {
                return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功。",FastJsonUtil.parseToJSON(groupScoreSource));
            } else {
                return new ObjectResponse(ErrorCode.INVALID_PARAM,"操作失败，评分来源不存在！");
            }
        } catch (Exception e) {
            log.error("根据ID获取评分来源出现异常。",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR,e.toString());
        }
    }

    @RequestMapping(value = "/groupscore/getGroupScoreSourceByGroupId", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceByGroupId(@RequestBody Long groupId) {
        try {
            GroupScoreSource groupScoreSource = groupScoreService.selectGroupScoreSourceByGroupId(groupId);
            if(null != groupScoreSource) {
                return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功。",FastJsonUtil.parseToJSON(groupScoreSource));
            } else {
                return new ObjectResponse(ErrorCode.INVALID_PARAM,"操作失败，评分来源不存在！");
            }
        } catch (Exception e) {
            log.error("根据ID获取评分来源出现异常。",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR,e.toString());
        }
    }

    @RequestMapping(value = "/groupscore/getGroupScoreById", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreById(@RequestBody Long groupId) {
        try {
            GroupScore groupScore = groupScoreService.selectGroupScoreById(groupId);
            if(null != groupScore) {
                return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功。",FastJsonUtil.parseToJSON(groupScore));
            } else {
                return new ObjectResponse(ErrorCode.INVALID_PARAM,"操作失败，评分不存在！");
            }
        } catch (Exception e) {
            log.error("根据ID获取评分出现异常。",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR,e.toString());
        }
    }

    @RequestMapping(value = "/groupscore/getGroupScoreByName", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreByName(@RequestBody String groupName) {
        try {
            GroupScore groupScore = groupScoreService.selectGroupScoreByName(groupName);
            if(null != groupScore) {
                return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功。",FastJsonUtil.parseToJSON(groupScore));
            } else {
                return new ObjectResponse(ErrorCode.INVALID_PARAM,"操作失败，评分不存在！");
            }
        } catch (Exception e) {
            log.error("根据ID获取评分出现异常。",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR,e.toString());
        }
    }

}
