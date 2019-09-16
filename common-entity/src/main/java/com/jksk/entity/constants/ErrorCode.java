package com.jksk.entity.constants;

import java.io.Serializable;

/**
 * 错误码
 *
 * @Author
 * @create 2019-07-02
 **/
public class ErrorCode implements Serializable  {
    private static final long serialVersionUID = -400996154967467701L;

    /**
     * server error code, use http code 400 403 throw exception to user 500 502
     * 503 change ip and retry
     */
    /**
     *  invalid param
     */
    public static final int INVALID_PARAM = 400;
    /**
     *  no right
     */
    public static final int NO_RIGHT = 403;
    /**
     *  not found
     */
    public static final int NOT_FOUND = 404;

    /**
     *  conflict  冲突
     */
    public static final int CONFLICT = 409;
    /**
     *  server error
     */
    public static final int SERVER_ERROR = 500;
    /**
     *  bad gateway
     */
    public static final int BAD_GATEWAY = 502;
    /**
     *  over threshold
     */
    public static final int OVER_THRESHOLD = 503;

    /**
     * 熔断器错误
     */
    public static final int FUSE_ERROR = 509;

}
