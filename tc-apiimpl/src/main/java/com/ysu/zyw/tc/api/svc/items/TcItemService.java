package com.ysu.zyw.tc.api.svc.items;

import com.ysu.zyw.tc.api.dao.mappers.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TcItemService {

    @Resource
    private TcItemMapper tcItemMapper;

    @Resource
    private TcItemCoverMapper tcItemCoverMapper;

    @Resource
    private TcItemAttrMapper tcItemAttrMapper;

    @Resource
    private TcItemDetailMapper tcItemDetailMapper;

    @Resource
    private TcShopMapper tcShopMapper;

}
