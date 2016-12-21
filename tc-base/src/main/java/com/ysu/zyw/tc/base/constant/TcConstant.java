package com.ysu.zyw.tc.base.constant;

/**
 * TcConstant defines tc system level constant.
 *
 * @author yaowu.zhang
 */
public class TcConstant {

    public static abstract class Sys {

        public static final String STR_32x0 = "00000000000000000000000000000000";

        public static final String DEFAULT_CACHE = "defaultCache";

        public static final String CODIS_CACHE = "codisCache";

        public static final String REDIS_CACHE = "redisCache";

        public static final String SS_REDIS_SERVICE = "ssRedisService";

        public static final String SHIRO_AUTHENTICATION_CACHE = "authenticationCache";

        public static final String SHIRO_AUTHORIZATION_CACHE = "authorizationCache";

        public static final String SHIRO_ACTIVE_SESSION_CACHE = "activeSessionCache";

    }

    public static abstract class Str {

        public static final String EMPTY = "";

        public static final String HYPHEN = "-";

        public static final String SLASH = "/";

        public static final String COLON = ":";

        public static final String BLANK = " ";

        public static final String SINGLE_QUOTE = "'";

        public static final String DOUBLE_QUOTE = "\"";

        public static final String DOT = ".";

    }

    public static abstract class Session {

        public static final String SESSION_SHIRO_MATCHER_ONCE_TOKEN = "session_matcher_token";

        public static final String SESSION_VERIFICATION_CODE = "session_verification_code";

        public static final String SESSION_ACCOUNT_ID = "session_account_id";

    }

}
