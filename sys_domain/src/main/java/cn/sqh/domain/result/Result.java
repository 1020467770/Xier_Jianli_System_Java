package cn.sqh.domain.result;

import lombok.Data;

@Data
public class Result {

    //访问成功
    public static final String RESULTTYPE_SUCCESS = "ok";
    //访问失败
    public static final String RESULTTYPE_FAILED = "fail";
    //拒绝访问
    public static final String RESULTTYPE_DINIED = "diny";
    //出现异常
    public static final String RESULTTYPE_EXCEPTION = "exception";

    private String status;
    private Object data;

    static public Result build(String status, Object data) {
        return new Result(status, data);
    }


    public Result(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public Result() {
    }


}
