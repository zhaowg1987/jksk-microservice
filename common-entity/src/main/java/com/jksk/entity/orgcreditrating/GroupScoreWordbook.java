package com.jksk.entity.orgcreditrating;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 字典表
 * @Author
 * @create 2019-07-05
 **/
@Data
public class GroupScoreWordbook implements Serializable {
    private static final long serialVersionUID = -7576757645614573454L;

    // 主键ID
    private Long id;
    // 码值--对应数据库字段值
    private String code;
    // 描述
    private String description;
    // 分值
    private String score;
    // 父ID
    private Long parent_id;
    // 排序
    private int order_no;

    private List<GroupScoreWordbook> children;

}
