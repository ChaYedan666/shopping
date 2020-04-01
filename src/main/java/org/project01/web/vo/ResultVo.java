package org.project01.web.vo;

public class ResultVo {
    public static final int CODE_NOLOGIN=2;//未登录
    public static final int CODE_SERVER_ERROR = 5;//服务器崩溃
    public static final int CODE_AUTH_FAIL=403;
    public static final int CODE_SUCCESS=1;
    public static final int CODE_FAILED=0;

    private int code;
    private Object data;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ResultVo() {
    }

    public ResultVo(int code, Object data, String message) {
        this.code = code;
        this.data = data;
        this.message = message;
    }

    public ResultVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultVo(Object data) {
        this.code = CODE_SUCCESS;
        this.data = data;
        this.message = "";
    }
}
