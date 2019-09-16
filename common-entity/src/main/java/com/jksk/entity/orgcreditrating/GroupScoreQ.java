package com.jksk.entity.orgcreditrating;

import com.jksk.entity.common.BaseQueryCondition;
import lombok.Data;

import java.io.Serializable;

/**
 *
 * 机构评分表 查询条件表
 * @Author
 * @create 2019-07-04
 **/
@Data
public class GroupScoreQ extends BaseQueryCondition implements Serializable {
    private static final long serialVersionUID = 987722602958105425L;

    /*机构名称*/
    private String groupName;
    /*创建日期 开始*/
    private String createTimeStart;
    /*创建日期 结束*/
    private String createTimeEnd;

}
