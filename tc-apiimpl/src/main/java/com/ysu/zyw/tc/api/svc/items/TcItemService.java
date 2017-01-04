package com.ysu.zyw.tc.api.svc.items;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.dao.mappers.*;
import com.ysu.zyw.tc.api.dao.po.TcItem;
import com.ysu.zyw.tc.api.dao.po.TcItemExample;
import com.ysu.zyw.tc.api.dao.po.TcShop;
import com.ysu.zyw.tc.api.dao.po.TcShopExample;
import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

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

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 店铺不存在;
     */
    @Transactional
    public void lockShop(@Nonnull String id) {
        checkNotNull(id);

        if (!existShop(id)) {
            throw new TcUnProcessableEntityException(1, "店铺不存在！");
        }

        TcShop tcShop = new TcShop();
        tcShop.setId(id).setLocked(true);

        int count = tcShopMapper.updateByPrimaryKeySelective(tcShop);
        checkArgument(count == 1);
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 店铺不存在;
     */
    @Transactional(readOnly = true)
    public TcShop findShop(@Nonnull String id) {
        checkNotNull(id);

        TcShopExample tcShopExample = new TcShopExample();
        tcShopExample.setStartLine(0);
        tcShopExample.setPageSize(1);
        tcShopExample.createCriteria()
                .andIdEqualTo(id)
                .andLockedEqualTo(false);
        List<TcShop> tcShops = tcShopMapper.selectByExample(tcShopExample);

        if (CollectionUtils.isEmpty(tcShops)) {
            throw new TcUnProcessableEntityException(1, "店铺不存在！");
        }

        // TODO: 2017/1/4 类型转换
        return tcShops.get(0);
    }

    /**
     * @return 是否存在店铺
     */
    @Transactional(readOnly = true)
    public boolean existShop(@Nonnull String id) {
        checkNotNull(id);

        TcShopExample tcShopExample = new TcShopExample();
        tcShopExample.createCriteria()
                .andIdEqualTo(id)
                .andLockedEqualTo(false);
        long count = tcShopMapper.countByExample(tcShopExample);

        return count == 1;
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 店铺不存在;
     */
    @Transactional(readOnly = true)
    public List<String> findShopOwnedItems(@Nonnull String id) {
        checkNotNull(id);

        if (!existShop(id)) {
            throw new TcUnProcessableEntityException(1, "店铺不存在！");
        }

        TcItemExample tcItemExample = new TcItemExample();
        tcItemExample.createCriteria()
                .andShopIdEqualTo(id)
                .andDelectedEqualTo(false);

        return tcItemMapper.selectPrimaryKeyByExample(tcItemExample);
    }

    /**
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 商品不存在;
     */
    @Transactional
    public void deleteItem(@Nonnull String id) {
        checkNotNull(id);

        if (!existItem(id)) {
            throw new TcUnProcessableEntityException(1, "商品不存在！");
        }

        TcItem tcItem = new TcItem();
        tcItem.setId(id).setDelected(true);

        int count = tcItemMapper.updateByPrimaryKeySelective(tcItem);
        checkArgument(count == 1);
    }

    /**
     * @return 商品列表
     */
    @Transactional(readOnly = true)
    public List<TcItem> findItems(@Nonnull List<String> ids) {
        checkNotNull(ids);

        if (CollectionUtils.isEmpty(ids)) {
            return Lists.newArrayList();
        }

        TcItemExample tcItemExample = new TcItemExample();
        tcItemExample.setStartLine(0);
        tcItemExample.setPageSize(1);
        tcItemExample.createCriteria()
                .andIdIn(ids)
                .andDelectedEqualTo(false);
        List<TcItem> tcItems = tcItemMapper.selectByExample(tcItemExample);

        // TODO: 2017/1/4 类型转换 商品属性 封面 详情获取
        return tcItems;
    }

    /**
     * @return 是否存在商品
     */
    @Transactional(readOnly = true)
    public boolean existItem(@Nonnull String id) {
        checkNotNull(id);

        TcItemExample tcItemExample = new TcItemExample();
        tcItemExample.createCriteria()
                .andIdEqualTo(id)
                .andDelectedEqualTo(false);
        long count = tcItemMapper.countByExample(tcItemExample);

        return count == 1;
    }

}
