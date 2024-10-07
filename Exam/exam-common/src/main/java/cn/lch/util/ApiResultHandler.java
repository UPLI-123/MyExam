package cn.lch.util;
public class ApiResultHandler {

    public static ApiResult success(Object object) {
        ApiResult apiResult = new ApiResult();
        apiResult.setData(object);
        apiResult.setCode(200);
        apiResult.setMessage("请求成功");
        return apiResult;
    }

    public static ApiResult success() {
        return success(null);
    }

    public static ApiResult fail(){
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(500);
        apiResult.setMessage("查询失败");
        apiResult.setData(null);
        return apiResult;
    }

    public static <T> ApiResult buildApiResult(Integer code, String message, T data) {
        ApiResult apiResult = new ApiResult();
        apiResult.setCode(code);
        apiResult.setMessage(message);
        apiResult.setData(data);
        return apiResult;
    }

}
