package com.ysu.zyw.tc.api.svc.items;

import com.ysu.zyw.tc.api.dao.mappers.*;
import com.ysu.zyw.tc.api.dao.po.*;
import com.ysu.zyw.tc.api.fk.ex.TcUnProcessableEntityException;
import com.ysu.zyw.tc.model.api.o.items.ToItem;
import com.ysu.zyw.tc.model.api.o.items.ToShop;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Nonnull;
import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

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
    public ToShop findShop(@Nonnull String id) {
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

        return convert2ToShop(tcShops.get(0));
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
     * @throws TcUnProcessableEntityException 不符合业务规则要求时抛出
     * @code code == 1 => 商品不存在;
     */
    @Transactional(readOnly = true)
    public ToItem findItem(@Nonnull String id) {
        checkNotNull(id);

        TcItemExample tcItemExample = new TcItemExample();
        tcItemExample.setStartLine(0);
        tcItemExample.setPageSize(1);
        tcItemExample.createCriteria()
                .andIdEqualTo(id)
                .andDelectedEqualTo(false);
        List<TcItem> tcItems = tcItemMapper.selectByExample(tcItemExample);

        if (CollectionUtils.isEmpty(tcItems)) {
            throw new TcUnProcessableEntityException(1, "商品不存在！");
        }

        TcItem tcItem = tcItems.get(0);


        // find covers
        TcItemCoverExample tcItemCoverExample = new TcItemCoverExample();
        tcItemCoverExample.createCriteria()
                .andItemIdEqualTo(tcItem.getId());
        List<TcItemCover> tcItemCovers = tcItemCoverMapper.selectByExample(tcItemCoverExample);

        // find attrs
        TcItemAttrExample tcItemAttrExample = new TcItemAttrExample();
        tcItemAttrExample.createCriteria()
                .andItemIdEqualTo(tcItem.getId());
        List<TcItemAttr> tcItemAttrs = tcItemAttrMapper.selectByExample(tcItemAttrExample);

        // find details
        TcItemDetailExample tcItemDetailExample = new TcItemDetailExample();
        tcItemDetailExample.createCriteria()
                .andItemIdEqualTo(tcItem.getId());
        List<TcItemDetail> tcItemDetails = tcItemDetailMapper.selectByExample(tcItemDetailExample);

        // convert and inject
        return convert2ToItem(tcItem, tcItemCovers, tcItemAttrs, tcItemDetails);
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

    private ToShop convert2ToShop(@Nonnull TcShop tcShop) {
        checkNotNull(tcShop);
        ToShop toShop = new ToShop();
        BeanUtils.copyProperties(tcShop, toShop);
        return toShop;
    }

    private ToItem convert2ToItem(@Nonnull TcItem tcItem,
                                  @Nonnull List<TcItemCover> tcItemCovers,
                                  @Nonnull List<TcItemAttr> tcItemAttrs,
                                  @Nonnull List<TcItemDetail> tcItemDetails) {
        checkNotNull(tcItem);
        checkNotNull(tcItemCovers);
        checkNotNull(tcItemAttrs);
        checkNotNull(tcItemDetails);

        // base info
        ToItem toItem = new ToItem();
        BeanUtils.copyProperties(tcItem, toItem);

        // cover info
        List<ToItem.ToItemCover> toItemCovers = tcItemCovers.stream().map(tcItemCover -> {
            ToItem.ToItemCover cover = new ToItem.ToItemCover();
            BeanUtils.copyProperties(tcItemCover, cover);
            return cover;
        }).collect(Collectors.toList());
        toItem.setCovers(toItemCovers);

        // attr info
        List<ToItem.ToItemAttr> toItemAttrs = tcItemAttrs.stream().map(tcItemAttr -> {
            ToItem.ToItemAttr attr = new ToItem.ToItemAttr();
            BeanUtils.copyProperties(tcItemAttr, attr);
            return attr;
        }).collect(Collectors.toList());
        toItem.setAttrs(toItemAttrs);

        // detail info
        List<ToItem.ToItemDetail> toItemDetails = tcItemDetails.stream().map(tcItemDetail -> {
            ToItem.ToItemDetail detail = new ToItem.ToItemDetail();
            BeanUtils.copyProperties(tcItemDetail, detail);
            return detail;
        }).collect(Collectors.toList());
        toItem.setDetails(toItemDetails);

        return toItem;
    }

}
