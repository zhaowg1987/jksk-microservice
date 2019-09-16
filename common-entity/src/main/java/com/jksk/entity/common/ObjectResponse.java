package com.jksk.entity.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 响应结果为单个对象
 *
 * @Author
 * @create 2019-07-02
 **/
@Data
@ToString(callSuper = true)
public class ObjectResponse<T> extends BaseResponse implements Serializable  {
    private static final long serialVersionUID = -7881253187984490963L;

    public ObjectResponse() {
        super();
    }

    public ObjectResponse(int status,String msg) {
        super(status,msg);
    }

    public ObjectResponse(int status,String msg,T data) {
        super(status,msg);
        this.data = data;
    }

    private T data;

}
