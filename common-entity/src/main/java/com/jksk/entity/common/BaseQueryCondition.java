package com.jksk.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页查询条件基础类
 *
 * @Author
 * @create 2019-07-01
 **/
@Data
public class BaseQueryCondition implements Serializable {
    private static final long serialVersionUID = 7385974152369118793L;

    private Integer pageNum = 1;

    private Integer pageSize = 20;

}
