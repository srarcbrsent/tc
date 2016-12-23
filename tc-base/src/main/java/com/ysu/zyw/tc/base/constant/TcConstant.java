package com.ysu.zyw.tc.base.constant;

/**
 * TcConstant defines tc system level constant.
 *
 * @author yaowu.zhang
 */
public class TcConstant {

    public static abstract class Base {

        public static final String STR_32x0 = "00000000000000000000000000000000";

    }

    public static abstract class BeanNames {

        // shiro
        public static final String SHIRO_AUTHENTICATION_CACHE = "authenticationCache";

        public static final String SHIRO_AUTHORIZATION_CACHE = "authorizationCache";

        public static final String SHIRO_ACTIVE_SESSION_CACHE = "activeSessionCache";

        // redis cache
        public static final String SS_REDIS_CACHE = "ssRedisCache";

        public static final String SO_REDIS_CACHE = "soRedisCache";

        public static final String OO_REDIS_CACHE = "ooRedisCache";

        // codis cache
        public static final String SS_CODIS_CACHE = "ssCodisCache";

        public static final String SO_CODIS_CACHE = "soCodisCache";

        public static final String OO_CODIS_CACHE = "ooCodisCache";

        // redis service
        public static final String SS_REDIS_SERVICE = "ssRedisService";

        public static final String SO_REDIS_SERVICE = "soRedisService";

        public static final String OO_REDIS_SERVICE = "ooRedisService";

        // codis service
        public static final String SS_CODIS_SERVICE = "ssCodisService";

        public static final String SO_CODIS_SERVICE = "soCodisService";

        public static final String OO_CODIS_SERVICE = "ooCodisService";

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

    public static abstract class SessionKey {

        public static final String SHIRO_MATCHER_ONCE_TOKEN = "session_shiro_matcher_token";

        public static final String INDEX_VERIFICATION_CODE = "index_verification_code";

        public static final String S_ACCOUNT_ID = "session_account_id";

    }

}
