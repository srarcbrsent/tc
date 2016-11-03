package com.ysu.zyw.tc.components.mq.metaq;

import com.taobao.metamorphosis.network.RemotingUtils;
import org.junit.Test;

public class HostTest {

    @Test
    public void testLocalhost() throws Exception {
        String localHost = RemotingUtils.getLocalHost();
        System.out.println(localHost);
    }

}
