package com.ysu.zyw.tc.base.constant;

/**
 * TcTopics defins tc system level message queue topics.
 *
 * @author yaowu.zhang
 */
public abstract class TcTopics {

    public abstract static class Topic {

        public static final String TOPIC_ACCOUNT = "topic_account";

    }

    public abstract static class TopicAttr {

        public static final String CREATE = "CREATE";

        public static final String DELETE = "DELETE";

        public static final String UPDATE = "UPDATE";

    }

}
