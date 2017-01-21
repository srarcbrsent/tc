package com.ysu.zyw.tc.api.svc.items;

import com.ysu.zyw.tc.api.dao.mappers.TcItemMapper;
import com.ysu.zyw.tc.api.dao.po.TcItem;
import com.ysu.zyw.tc.base.tools.TcIdGen;
import org.easymock.EasyMock;
import org.easymock.IMocksControl;
import org.junit.Assert;
import org.junit.Test;

public class TcItemServiceTest {

    private static final IMocksControl I_MOCKS_CONTROL = EasyMock.createControl();

    @Test
    public void findShop() throws Exception {
        String id = TcIdGen.upperCaseUuid();
        TcItemMapper tcItemMapper = I_MOCKS_CONTROL.createMock(TcItemMapper.class);
        EasyMock.expect(tcItemMapper.selectByPrimaryKey(EasyMock.anyObject(String.class)))
                .andReturn(new TcItem().setId(id)).times(1);
        I_MOCKS_CONTROL.replay();

        TcItem tcItem = tcItemMapper.selectByPrimaryKey(id);
        Assert.assertNotNull(tcItem);
        Assert.assertEquals(id, tcItem.getId());
    }

}
