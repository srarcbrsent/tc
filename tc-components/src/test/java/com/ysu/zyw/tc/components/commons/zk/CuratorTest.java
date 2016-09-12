package com.ysu.zyw.tc.components.commons.zk;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.SerializationUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
public class CuratorTest {

    private CuratorFramework curatorFramework;

    @Before
    public void setUp() throws Exception {
        curatorFramework = CuratorFrameworkFactory
                .builder()
                .connectString("db.tc.com:2181")
                .sessionTimeoutMs(30000)
                .connectionTimeoutMs(30000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .namespace("mtc")
                .defaultData(null)
                .build();
        curatorFramework.start();
        curatorFramework.getChildren().forPath("/").parallelStream().forEach(path -> {
            try {
                curatorFramework.delete().deletingChildrenIfNeeded().forPath("/" + path);
                log.info("set up delete node [{}]", "/mtc/" + path);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @After
    public void tearDown() throws Exception {
        curatorFramework.close();
    }

    @Test
    public void test1() throws Exception {
        curatorFramework.create().forPath("/c", SerializationUtils.serialize("helo"));
        Assert.assertEquals("helo", SerializationUtils.deserialize(curatorFramework.getData().forPath("/c")));
        Assert.assertNotNull(curatorFramework.checkExists().forPath("/c"));
        curatorFramework.setData().forPath("/c", SerializationUtils.serialize("hello"));
        Assert.assertEquals("hello", SerializationUtils.deserialize(curatorFramework.getData().forPath("/c")));
        curatorFramework.delete().forPath("/c");
        Assert.assertNull(curatorFramework.checkExists().forPath("/c"));
    }

    @Test
    public void test2() throws Exception {
        // PathChildrenCache -> watch current node data update and children nodes create / delete
        // NodeCache -> watch current node create update delete
        // TreeCache -> watch current node and children nodes create update delete
        try (PathChildrenCache pathChildrenCache = new PathChildrenCache(curatorFramework, "/c", true)) {
            pathChildrenCache.getListenable().addListener((CuratorFramework client, PathChildrenCacheEvent event) -> {
                ChildData data = event.getData();
                if (Objects.isNull(data)) {
                    log.info("no data ...");
                } else {
                    log.info("event type [{}], path [{}], data [{}], stat [{}]",
                            event.getType(), data.getPath(), new String(data.getData()), data.getStat());
                }
            });
            pathChildrenCache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            log.info("watch node /c ...");
            TimeUnit.SECONDS.sleep(60);
        }
    }

    @Test
    public void test3() {
        // InterProcessMutex -> distribution lock
        // LeaderSelector -> leader election
    }

}
