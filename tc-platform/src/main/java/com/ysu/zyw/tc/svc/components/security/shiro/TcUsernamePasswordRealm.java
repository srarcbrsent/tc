package com.ysu.zyw.tc.svc.components.security.shiro;

import com.ysu.zyw.tc.dao.mappers.TcAccountMapper;
import com.ysu.zyw.tc.dao.po.TcAccount;
import com.ysu.zyw.tc.dao.po.TcAccountExample;
import com.ysu.zyw.tc.sys.constant.TcConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
public class TcUsernamePasswordRealm extends TcAbstractAuthorizingRealm {

    @Resource
    private TcAccountMapper tcAccountMapper;

    @Override
    protected TcAccount fetchAccount(String username) {
        TcAccountExample tcAccountExample = new TcAccountExample();
        tcAccountExample.createCriteria().andNameEqualTo(username);
        List<TcAccount> tcAccountList = tcAccountMapper.selectByExample(tcAccountExample);

        if (CollectionUtils.isEmpty(tcAccountList)) {
            return null;
        } else {
            return tcAccountList.get(0);
        }
    }

    @Override
    protected String fetchRealmName() {
        return TcConstant.Sys.SHIRO_USERNAME_PASSWORD_REALM;
    }
}