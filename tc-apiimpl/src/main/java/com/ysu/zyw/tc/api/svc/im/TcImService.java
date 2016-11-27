package com.ysu.zyw.tc.api.svc.im;

import com.google.common.collect.Lists;
import com.ysu.zyw.tc.api.dao.mappers.TcMessageMapper;
import com.ysu.zyw.tc.api.dao.po.TcMessage;
import com.ysu.zyw.tc.api.dao.po.TcMessageExample;
import com.ysu.zyw.tc.api.dao.po.TcMessageWithBLOBs;
import com.ysu.zyw.tc.base.utils.TcPaginationUtils;
import com.ysu.zyw.tc.model.api.i.im.ToImIdentifier;
import com.ysu.zyw.tc.model.api.o.im.ToMessage;
import com.ysu.zyw.tc.model.menum.TmImMessageType;
import com.ysu.zyw.tc.model.menum.TmPlatform;
import com.ysu.zyw.tc.model.msg.TcBaseMessage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.google.common.base.Preconditions.checkNotNull;

@Slf4j
@Service
public class TcImService {

    @Resource
    private TcMessageMapper tcMessageMapper;

    private Map<TmImMessageType, List<TcAbstractImProcessor>> processorMapping;

    public void consume(@Nonnull TcBaseMessage tcMessage) {
        checkNotNull(tcMessage);

        // TODO: 2016/11/27  
        List<TcAbstractImProcessor> processors = processorMapping.get(tcMessage.getClass());
        for (TcAbstractImProcessor processor : processors) {
            try {
                processor.process(tcMessage);
            } catch (Exception e) {
                log.error("processor [{}] process msg [{}] failed", processor, tcMessage, e);
            }
        }
    }

    public void deleteOne(@Nonnull List<String> ids) {
        checkNotNull(ids);

        if (CollectionUtils.isNotEmpty(ids)) {
            TcMessageExample tcMessageExample = new TcMessageExample();
            tcMessageExample.createCriteria().andIdIn(ids);
            tcMessageMapper.deleteByExample(tcMessageExample);
        }
    }

    public void readOne(@Nonnull List<String> ids) {
        checkNotNull(ids);

        if (CollectionUtils.isNotEmpty(ids)) {
            TcMessageExample tcMessageExample = new TcMessageExample();
            tcMessageExample.createCriteria().andIdIn(ids);
            TcMessage tcMessage = new TcMessage().setRead(true);
            tcMessageMapper.updateByExample(tcMessage, tcMessageExample);
        }
    }

    public long count(@Nonnull ToImIdentifier toImIdentifier,
                      @Nonnull TmPlatform tmPlatform,
                      @Nullable TmImMessageType type,
                      @Nullable String bizKey) {
        checkNotNull(toImIdentifier);
        checkNotNull(tmPlatform);

        TcMessageExample tcMessageExample = buildFindMessageCondition(toImIdentifier, tmPlatform, type, bizKey);
        return tcMessageMapper.countByExample(tcMessageExample);
    }

    public List<ToMessage> findOne(@Nonnull List<String> ids) {
        checkNotNull(ids);

        if (CollectionUtils.isNotEmpty(ids)) {
            TcMessageExample tcMessageExample = new TcMessageExample();
            tcMessageExample.createCriteria().andIdIn(ids);
            List<TcMessageWithBLOBs> tcMessages = tcMessageMapper.selectByExampleWithBLOBs(tcMessageExample);
            return tcMessages.stream().map(this::convert2ToMessage).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    public List<ToMessage> find(@Nonnull ToImIdentifier toImIdentifier,
                                @Nonnull TmPlatform tmPlatform,
                                @Nullable TmImMessageType type,
                                @Nullable String bizKey,
                                int currentPage,
                                int pageSize) {
        checkNotNull(toImIdentifier);
        checkNotNull(tmPlatform);

        TcMessageExample tcMessageExample = buildFindMessageCondition(toImIdentifier, tmPlatform, type, bizKey);
        TcPaginationUtils.Pagination pagination = TcPaginationUtils.paging(currentPage, pageSize);
        tcMessageExample.setStartLine(pagination.getStartLine());
        tcMessageExample.setPageSize(pagination.getPageSize());
        tcMessageExample.setOrderByClause("created_timestamp DESC");
        List<TcMessageWithBLOBs> tcMessages = tcMessageMapper.selectByExampleWithBLOBs(tcMessageExample);
        return tcMessages.stream().map(this::convert2ToMessage).collect(Collectors.toList());
    }

    private TcMessageExample buildFindMessageCondition(@Nonnull ToImIdentifier toImIdentifier,
                                                       @Nonnull TmPlatform tmPlatform,
                                                       @Nullable TmImMessageType type,
                                                       @Nullable String bizKey) {
        checkNotNull(toImIdentifier);
        checkNotNull(tmPlatform);

        TcMessageExample tcMessageExample = new TcMessageExample();
        // c1
        TcMessageExample.Criteria criteria1 = tcMessageExample.or();
        // c2
        TcMessageExample.Criteria criteria2 = tcMessageExample.or();
        if (Objects.nonNull(toImIdentifier.getReceiverAccountId())) {
            criteria1.andReceiverAccountIdEqualTo(toImIdentifier.getReceiverAccountId());
        }
        if (Objects.nonNull(toImIdentifier.getReceiverRegionId())) {
            criteria2.andReceiverRegionIdEqualTo(toImIdentifier.getReceiverRegionId());
        }
        criteria1.andPlatformEqualTo(tmPlatform);
        criteria2.andPlatformEqualTo(tmPlatform);
        if (Objects.nonNull(type)) {
            criteria1.andTypeEqualTo(type);
            criteria2.andTypeEqualTo(type);
        }
        if (Objects.nonNull(bizKey)) {
            criteria1.andBizKeyEqualTo(bizKey);
            criteria2.andBizKeyEqualTo(bizKey);
        }
        return tcMessageExample;
    }

    private ToMessage convert2ToMessage(@Nonnull TcMessage tcMessage) {
        checkNotNull(tcMessage);
        ToMessage toMessage = new ToMessage();
        BeanUtils.copyProperties(tcMessage, toMessage);
        return toMessage;
    }

}
