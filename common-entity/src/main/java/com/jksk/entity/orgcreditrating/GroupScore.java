package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;

/**
 *
 * 机构评分表
 * @Author
 * @create 2019-07-04
 **/
@Data
public class GroupScore implements Serializable {
    private static final long serialVersionUID = 987722602958105425L;
    /*主键*/
    private Long id;
    /*机构名称*/
    private String groupName;
    /*系统得分*/
    private Integer sysScore;
    /*权重得分*/
    private Integer weightScore;
    /*系统综合得分*/
    private Integer syntheticalScore;
    /*机构等级*/
    private String groupLevel;
    /*机构风险分析*/
    private String riskAnalysis;

    /*更新时间 @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")*/
    private String updateTime;
    /*创建时间  @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")*/
    private String createTime;

    /*是否包含D选项，0：不包含 1：包含*/
    private String containD;
    /*机构评分来源ID*/
    private Long groupScoreSourceId;

}
