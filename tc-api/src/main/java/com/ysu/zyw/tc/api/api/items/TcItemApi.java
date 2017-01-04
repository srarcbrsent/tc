package com.ysu.zyw.tc.api.api.items;

import com.ysu.zyw.tc.model.api.o.items.ToItem;
import com.ysu.zyw.tc.model.api.o.items.ToShop;
import com.ysu.zyw.tc.model.mw.TcR;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

@Path(value = "/items")
public interface TcItemApi {

    /**
     * @code code == 0 查询成功, body = 店铺;
     * @code code == 1 => 店铺不存在;
     * @code code == 9999 => 系统异常;
     */
    @GET
    @Path(value = "/find_shops/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToShop> findShop(
            @PathParam(value = "id") String id
    );

    /**
     * @code code == 0 查询成功, body = 商品;
     * @code code == 1 => 商品不存在;
     * @code code == 9999 => 系统异常;
     */
    @GET
    @Path(value = "/find_items/{id}")
    @Consumes(value = {MediaType.WILDCARD})
    @Produces(value = {MediaType.APPLICATION_JSON})
    TcR<ToItem> findItem(
            @PathParam(value = "id") String id
    );

}
