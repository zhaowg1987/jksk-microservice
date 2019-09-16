package com.jksk.entity.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 响应结果公共基类
 *
 * @Author
 * @create 2019-07-02
 **/
@Data
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 7993161424312691115L;

    public BaseResponse() {

    }

    public BaseResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    // 响应结果状态码 （默认200 代表成功）
    private int status = 200;
    // 响应状态码对应描述
    private String msg;

}
