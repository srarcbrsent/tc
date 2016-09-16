package com.ysu.zyw.tc.sys.constant;

import java.text.SimpleDateFormat;

/**
 * TcConstant defines tc system level constant.
 *
 * @author yaowu.zhang
 */
public class TcConstant {

    public static abstract class Sys {

        public static final String TC_ADMIN_ID = "00000000000000000000000000000000";

        public static final String DEFAULT_CACHE = "defaultCache";

        public static final String CODIS_CACHE = "codisCache";

        public static final String SHIRO_AUTHENTICATION_CACHE = "authenticationCache";

        public static final String SHIRO_AUTHORIZATION_CACHE = "authorizationCache";

        public static final String SHIRO_USERNAME_PASSWORD_REALM = "jdbcUsernamePasswordRealm";

        public static final String SHIRO_MOBILE_PASSWORD_REALM = "jdbcMobilePasswordRealm";

        public static final String SHIRO_EMAIL_PASSWORD_REALM = "jdbcEmailPasswordRealm";

    }

    public static abstract class Str {

        public static final String EMPTY = "";

        public static final String HYPHEN = "-";

        public static final String SLASH = "/";

        public static final String COLON = ":";

        public static final String SPACE = " ";

        public static final String SINGLE_QUOTE = "'";

        public static final String DOUBLE_QUOTE = "\"";

        public static final String DOT = ".";

    }

    public static abstract class R {

        public static final int SUCCESS = 200;

        public static final int NO_PERMISSIONS = 300;

        public static final int CLIENT_ERROR = 400;

        public static final int SERVER_ERROR = 500;

    }

    public static abstract class C {

        public static final String FULL_DATE_FORMAT_VALUE = "yyyy-MM-dd HH:mm:ss zzz";

        public static final SimpleDateFormat FULL_DATE_FORMAT = new SimpleDateFormat(FULL_DATE_FORMAT_VALUE);

        public static final String SIMPLE_DATE_FORMAT_VALUE = "yyyy-MM-dd HH:mm:ss";

        public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat(SIMPLE_DATE_FORMAT_VALUE);

    }

    public static abstract class S {

        public static final String SESSION_SHIRO_MATCHER_ONCE_TOKEN = "matcher_token";

    }

}
