package com.ysu.zyw.tc.api.svc.items;

import com.ysu.zyw.tc.api.dao.mappers.TcItemMapper;
import com.ysu.zyw.tc.api.dao.po.TcItem;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import com.ysu.zyw.tc.model.api.o.items.ToItem;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.UUID;

public class TcItemServiceTest {

    private static final IMocksControl I_MOCKS_CONTROL = EasyMock.createControl();

    @Before
    public void setUp() {
        // do nothing
    }

    @After
    public void tearDown() {
        I_MOCKS_CONTROL.reset();
    }

    @Test
    public void findShop() throws Exception {
        String id = TcIdGen.upperCaseUuid();
        TcItemMapper tcItemMapper = I_MOCKS_CONTROL.createMock(TcItemMapper.class);
        TcItemService tcItemService = I_MOCKS_CONTROL.createMock(TcItemService.class);
        ReflectionTestUtils.setField(tcItemService, "tcItemMapper", tcItemMapper);
        EasyMock.expect(tcItemMapper.selectByPrimaryKey(EasyMock.anyObject(String.class)))
                .andReturn(new TcItem().setId(id)).times(1);
        I_MOCKS_CONTROL.replay();

        TcItem tcItem = tcItemMapper.selectByPrimaryKey(id);
        Assert.assertNotNull(tcItem);
        Assert.assertEquals(id, tcItem.getId());

        ToItem toItem = tcItemService.findItem(UUID.randomUUID().toString());
    }

}
