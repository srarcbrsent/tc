package com.ysu.zyw.tc.model.mw;

public interface Rc {

    int SUCCESS = 0;

    String SUCCESS_DESCRIPTION = "请求成功！";

    int UNAUTHORIZED = 7777;

    String UNAUTHORIZED_DESCRIPTION = "未授权！";

    String XSRF_TOKEN_NOT_MATCH = "XSRF-TOKEN不匹配！";

    int BAD_REQUEST = 8888;

    String BAD_REQUEST_DESCRIPTION = "信息有误！";

    int SERVER_ERROR = 9999;

    String SERVER_ERROR_DESCRIPTION = "系统异常！";

}
