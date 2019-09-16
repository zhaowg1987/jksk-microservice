package com.jksk.entity.exception;

/**
 * 异常类
 *
 * @Author
 * @create 2019-07-02
 **/
public class JkskException extends Exception {

    private static final long serialVersionUID = 1106979001147823441L;

    private int errorCode;

    private String errorMsg;

    public JkskException() {
        super();
    }

    public JkskException(int errorCode) {
        super();
        this.errorCode = errorCode;
    }

    public JkskException(int errorCode, String errorMsg) {
        super(errorMsg);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public JkskException(int errorCode, String msg, Throwable cause) {
        super(msg, cause);
        this.errorCode = errorCode;
    }

    public JkskException(int errorCode, Throwable cause) {
        super(cause);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

}
