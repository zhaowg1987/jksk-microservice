package com.jksk.orgcreditratingcon.conroller;

import com.github.pagehelper.PageInfo;
import com.jksk.commonutil.DateUtil;
import com.jksk.commonutil.FastJsonUtil;
import com.jksk.entity.common.DataTablePage;
import com.jksk.entity.common.ObjectResponse;
import com.jksk.entity.constants.ErrorCode;
import com.jksk.entity.constants.SuccessCode;
import com.jksk.entity.orgcreditrating.GroupScore;
import com.jksk.entity.orgcreditrating.GroupScoreQ;
import com.jksk.entity.orgcreditrating.GroupScoreSource;
import com.jksk.orgcreditratingcon.feignservice.OrgCreditRatingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpHost;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequest;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author
 * @create 2019-07-04
 **/
@Slf4j
@Api("机构评分管理")
@RefreshScope
@RestController
public class GroupScoreController {

    @Autowired
    private OrgCreditRatingService orgCreditRatingService;

    @Value("${spring.elasticsearch.hostname}")
    private String esHostname;

    @Value("${spring.elasticsearch.port}")
    private int esPort;


    @ApiOperation(value="获取机构列表", notes="根据查询条件查询机构列表。")
    @RequestMapping(value = "/groupscore/getGroupScoreList", method = RequestMethod.POST)
    public DataTablePage<GroupScore> getGroupScoreList(@ApiParam(name = "GroupScoreQ", value = "查询条件GroupScoreQ")  GroupScoreQ groupScoreQ,
            HttpServletRequest request) {
        log.info("获取机构评分列表:{}",groupScoreQ.toString());
        DataTablePage<GroupScore> dataTable = new DataTablePage<>(request);
        groupScoreQ.setPageNum(dataTable.getPageNum());
        groupScoreQ.setPageSize(dataTable.getiDisplayLength());
        // 调用接口
        ObjectResponse objRes = orgCreditRatingService.getGroupScoreList(groupScoreQ);
        // 请求出现异常
        if(SuccessCode.SUCCESS_CODE != objRes.getStatus()) {
            return new DataTablePage<>(request,objRes.getMsg());
        }
        //
        PageInfo<GroupScore> pageInfo = FastJsonUtil.parseToClass(String.valueOf(objRes.getData()),PageInfo.class);
        return DataTablePage.responseData(dataTable,pageInfo.getList(),pageInfo.getTotal());
    }

    @ApiOperation(value="获取机构得分评测选项", notes="获取机构得分评测选项。")
    @RequestMapping(value = "/groupscore/getGroupScoreSourceRadios", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceRadios() {
        return orgCreditRatingService.getGroupScoreSourceRadios();
    }

    @ApiOperation(value="保存机构系统评分", notes="保存机构系统评分。")
    @RequestMapping(value = "/groupscore/saveGroupScore", method = RequestMethod.POST)
    public ObjectResponse saveGroupScore(GroupScoreSource groupScoreSource) {
        return orgCreditRatingService.saveGroupScore(groupScoreSource);
    }

    @ApiOperation(value="计算系统综合评分", notes="计算系统综合评分。")
    @RequestMapping(value = "/groupscore/calcGroupScore", method = RequestMethod.POST)
    public ObjectResponse calcGroupScore(GroupScore groupScore) {
        try {
            if(null == groupScore.getSysScore()) {
                return new ObjectResponse(ErrorCode.INVALID_PARAM,"系统评分为空！");
            }
            if(null == groupScore.getWeightScore()) {
                groupScore.setWeightScore(0);
            }
            int syntheticalScore = groupScore.getWeightScore() + groupScore.getSysScore();
            String level = getLevelByScore(syntheticalScore);
            groupScore.setSyntheticalScore(syntheticalScore);
            groupScore.setGroupLevel(level);
            return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功。",FastJsonUtil.parseToJSON(groupScore));
        } catch (Exception e) {
            log.error("计算系统综合评分出现异常.",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR,e.toString());
        }
     }


    /*
     *
     *   分值	    等级	    备注
     *    60分（含）以下	D	D级为不合格机构
     *    61分-63分（含）	C+	C至C+++，每级为3分进制
     *    64分-66分（含）	C++
     *    67分-69分（含）	C+++
     *    70分-73分（含）	B+	B至B+++，每级为4分进制
     *    74分-77分（含）	B++
     *    78分-81分（含）	B+++
     *    82分-86分（含）	A+	A至A+++，每级为5分进制
     *    87分-91分（含）	A++
     *    92分-96分（含）	A+++
     *    97分（含）以上	S	S级为优秀级机构
     *
     * */
    private String getLevelByScore(int score) {
        if(score <= 60 ) {
            return "D";
        } else if(score > 60 && score <= 63) {
            return "C+";
        } else if(score > 63 && score <= 66) {
            return "C++";
        } else if(score > 66 && score <= 69) {
            return "C+++";
        } else if(score > 69 && score <= 73) {
            return "B+";
        } else if(score > 73 && score <= 77) {
            return "B++";
        } else if(score > 77 && score <= 81) {
            return "B+++";
        } else if(score > 81 && score <= 86) {
            return "A+";
        } else if(score > 86 && score <= 91) {
            return "A++";
        } else if(score > 91 && score <= 96) {
            return "A+++";
        } else if(score > 97) {
            return "S";
        }
        return null;
    }

    @ApiOperation(value="更新系统综合评分", notes="更新系统综合评分。")
    @RequestMapping(value = "/groupscore/updateGroupScore", method = RequestMethod.POST)
    public ObjectResponse updateGroupScore(GroupScore groupScore) {
        ObjectResponse objectResponse = new ObjectResponse();
        try {
            if(null == groupScore.getId()
                    || null == groupScore.getSyntheticalScore()
                    || null == groupScore.getGroupLevel()) {
                objectResponse.setStatus(ErrorCode.INVALID_PARAM);
                objectResponse.setMsg("系统综合评分为空，请核查是否进行系统综合评分计算！");
                return objectResponse;
            }
            groupScore.setUpdateTime(DateUtil.getNowDateTime());
            orgCreditRatingService.updateGroupScore(groupScore);
            objectResponse.setStatus(SuccessCode.SUCCESS_CODE);
            objectResponse.setMsg("操作成功。");
        } catch (Exception e) {
            log.error("更新系统综合评分出现异常。",e);
            objectResponse.setStatus(ErrorCode.SERVER_ERROR);
            objectResponse.setMsg(e.toString());
        }
        return objectResponse;
    }

    @ApiOperation(value="获取机构评分来源", notes="根据主键ID获取机构评分来源。")
    @RequestMapping(value = "/groupscore/getGroupScoreSourceById", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceById(Long groupScoreSourceId) {
        if(null == groupScoreSourceId) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"请填写机构评分来源。");
        }
        return orgCreditRatingService.getGroupScoreSourceById(groupScoreSourceId);
    }


    @ApiOperation(value="获取机构评分来源", notes="根据机构ID获取机构评分来源。")
    @RequestMapping(value = "/groupscore/getGroupScoreSourceByGroupId", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreSourceByGroupId(Long groupId) {
        if(null == groupId) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"机构不存在，请返回重新查看。");
        }
        return orgCreditRatingService.getGroupScoreSourceByGroupId(groupId);
    }


    @ApiOperation(value="从搜索服务器上获取机构评分", notes="根据机构名称从搜索服务器上获取机构评分。")
    @RequestMapping(value = "/groupscore/getGroupScoresByLikeNameFromEs", method = RequestMethod.POST)
    public ObjectResponse getGroupScoresByLikeNameFromEs(String likeName) {
        List<GroupScore> groupScores = new ArrayList<>();
        try {
            RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost(esHostname, esPort)));
            SearchTemplateRequest request = new SearchTemplateRequest();
            request.setRequest(new SearchRequest("group_score"));
            request.setScriptType(ScriptType.INLINE);
            Map<String, Object> scriptParams = new HashMap<>();
            if(StringUtils.isNotEmpty(likeName)) {
                request.setScript(
                    "{" +
                    "  \"query\": { \"match\" : { \"{{field}}\" : \"{{value}}\" } }," +
                    "  \"size\" : \"{{size}}\"" +
                    "}");
                scriptParams.put("field", "group_name");
                scriptParams.put("value", likeName);
            } else {
                request.setScript(
                    "{" +
                    "\"query\": { \"match_all\" : {} }," +
                    "\"size\" : \"{{size}}\"" +
                    "}");
            }
            scriptParams.put("size", 100);
            request.setScriptParams(scriptParams);
            SearchTemplateResponse response = client.searchTemplate(request, RequestOptions.DEFAULT);
            SearchResponse searchResponse = response.getResponse();
            if(null != response && null != searchResponse && null != searchResponse.getHits()
                    && searchResponse.getHits().getTotalHits().value > 0) {
                log.info("获取条数：{}", searchResponse.getHits().getTotalHits());
                SearchHit[] searchHists = searchResponse.getHits().getHits();
                // 递归遍历
                for (SearchHit sh : searchHists) {
                    log.info("信息：{}", sh.getSourceAsString());
                    GroupScore groupScore = FastJsonUtil.parseToClass(sh.getSourceAsString(),GroupScore.class);
                    groupScores.add(groupScore);
                }
            }
        } catch (Exception e) {
            log.error("搜索服务器上获取机构评分",e);
            return new ObjectResponse(ErrorCode.SERVER_ERROR, e.toString());
        }
        return new ObjectResponse(SuccessCode.SUCCESS_CODE,"操作成功",FastJsonUtil.parseToJSON(groupScores));
    }


    @ApiOperation(value="获取机构评分", notes="根据机构ID获取机构评分。")
    @RequestMapping(value = "/groupscore/getGroupScoreById", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreById(Long groupId) {
        if(null == groupId) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"机构ID为空，请重新操作。");
        }
        return orgCreditRatingService.getGroupScoreById(groupId);
    }

    @ApiOperation(value="获取机构评分", notes="根据机构Name获取机构评分。")
    @RequestMapping(value = "/groupscore/getGroupScoreByName", method = RequestMethod.POST)
    public ObjectResponse getGroupScoreByName(String groupName) {
        if(null == groupName) {
            return new ObjectResponse(ErrorCode.INVALID_PARAM,"机构名称为空，请重新操作。");
        }
        return orgCreditRatingService.getGroupScoreByName(groupName);
    }
}
