package com.ysu.zyw.tc.sys.constant;

/**
 * TcConstant defines tc system level constant.
 *
 * @author yaowu.zhang
 */
public class TcConstant {

    public abstract class Str {

        public static final String EMPTY = "";

        public static final String HYPHEN = "-";

        public static final String SLASH = "/";

        public static final String COLON = ":";

        public static final String SPACE = " ";

        public static final String SINGLE_QUOTE = "'";

        public static final String DOUBLE_QUOTE = "\"";

        public static final String DOT = ".";

    }

    public abstract class R {

        int SUCCESS = 200;

        int NO_PERMISSIONS = 300;

        int CLIENT_ERROR = 400;

        int SERVER_ERROR = 500;

    }

}
