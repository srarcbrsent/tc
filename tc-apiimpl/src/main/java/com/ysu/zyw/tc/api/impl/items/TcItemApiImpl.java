package com.ysu.zyw.tc.api.impl.items;

import com.ysu.zyw.tc.api.api.items.TcItemApi;
import com.ysu.zyw.tc.api.svc.items.TcItemService;
import com.ysu.zyw.tc.model.api.o.items.ToItem;
import com.ysu.zyw.tc.model.api.o.items.ToShop;
import com.ysu.zyw.tc.model.mw.TcR;
import lombok.SneakyThrows;

import javax.annotation.Resource;

public class TcItemApiImpl implements TcItemApi {

    @Resource
    private TcItemService tcItemService;

    @SneakyThrows
    @Override
    public TcR<ToShop> findShop(String id) {

        ToShop toShop = tcItemService.findShop(id);

        return TcR.ok(toShop);
    }

    @SneakyThrows
    @Override
    public TcR<ToItem> findItem(String id) {

        ToItem toItem = tcItemService.findItem(id);

        return TcR.ok(toItem);
    }

}
