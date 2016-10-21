package com.ysu.zyw.tc.api.svc.classifies;

import com.ysu.zyw.tc.api.dao.mappers.TcItemClassifyMapper;
import com.ysu.zyw.tc.api.dao.po.TcItemClassify;
import com.ysu.zyw.tc.api.dao.po.TcItemClassifyExample;
import com.ysu.zyw.tc.api.fk.ex.TcResourceNotFoundException;
import com.ysu.zyw.tc.model.api.classifies.TmClassify;
import com.ysu.zyw.tc.model.constant.classifies.TmClassifyConst;
import com.ysu.zyw.tc.sys.ex.TcException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.ResourceNotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TcClassifyService {

    @Resource
    private TcItemClassifyMapper tcItemClassifyMapper;

    @Transactional(readOnly = true)
    public TmClassify findClassify(@NotNull String classifyId,
                                   @NotNull Boolean includeParentClassify) {
        TcItemClassify tcItemClassify = tcItemClassifyMapper.selectByPrimaryKey(classifyId);
        if (Objects.isNull(tcItemClassify)) {
            throw new TcResourceNotFoundException("资源不存在");
        }

        TmClassify tmClassify = new TmClassify();
        BeanUtils.copyProperties(tcItemClassify, tmClassify);

        if (includeParentClassify && StringUtils.isNotBlank(tcItemClassify.getParentId())) {
            tmClassify.setParentClassify(this.findClassify(tcItemClassify.getParentId(), true));
        }

        return tmClassify;
    }

    @Transactional(readOnly = true)
    public TmClassify findClassifies() {
        List<TcItemClassify> tcItemClassifies = tcItemClassifyMapper.selectByExample(new TcItemClassifyExample());
        Optional<TcItemClassify> tcRootClassifyOptional = tcItemClassifies.stream()
                .filter(tcItemClassify -> Objects.equals(tcItemClassify.getId(), TmClassifyConst.ROOT_CLASSIFY_ID))
                .findFirst();
        if (!tcRootClassifyOptional.isPresent()) {
            throw new TcException("none root classify present");
        }

        TmClassify tmRootClassify = new TmClassify();
        BeanUtils.copyProperties(tcRootClassifyOptional.get(), tmRootClassify);

        return findSubClassifiesRecursive(tmRootClassify, tcItemClassifies);
    }

    @Transactional(readOnly = true)
    public TmClassify findSubClassifies(@NotNull String classifyId,
                                        @NotNull Boolean recursive) {
        if (recursive) {
            List<TcItemClassify> tcClassifies = tcItemClassifyMapper.selectByExample(new TcItemClassifyExample());
            Optional<TcItemClassify> tcSpecClassifyOptional = tcClassifies.stream()
                    .filter(tcClassify -> Objects.equals(tcClassify.getId(), classifyId))
                    .findFirst();
            if (!tcSpecClassifyOptional.isPresent()) {
                throw new ResourceNotFoundException("资源不存在");
            }

            TmClassify tmSpecClassify = new TmClassify();
            BeanUtils.copyProperties(tcSpecClassifyOptional.get(), tmSpecClassify);
            return findSubClassifiesRecursive(tmSpecClassify, tcClassifies);
        } else {
            TcItemClassify tcItemClassify = tcItemClassifyMapper.selectByPrimaryKey(classifyId);
            TcItemClassifyExample tcItemClassifyExample = new TcItemClassifyExample();
            tcItemClassifyExample.createCriteria()
                    .andParentIdEqualTo(tcItemClassify.getId());
            List<TcItemClassify> tcClassifies = tcItemClassifyMapper.selectByExample(tcItemClassifyExample);
            List<TmClassify> tmSubClassifies = tcClassifies.stream().map(tcClassify -> {
                TmClassify tmClassify = new TmClassify();
                BeanUtils.copyProperties(tcClassify, tmClassify);
                return tmClassify;
            }).sorted((o1, o2) -> {
                return ObjectUtils.compare(o1.getHierarchy(), o2.getHierarchy());
            }).collect(Collectors.toList());
            TmClassify tmSpecClassify = new TmClassify();
            BeanUtils.copyProperties(tcItemClassify, tmSpecClassify);
            tmSpecClassify.setSubClassifies(tmSubClassifies);
            return tmSpecClassify;
        }
    }

    private TmClassify findSubClassifiesRecursive(TmClassify tmClassify,
                                                  List<TcItemClassify> tcItemClassifies) {
        List<TmClassify> tmSubClassifies = tcItemClassifies.stream()
                .filter(tcItemClassify -> Objects.equals(tcItemClassify.getParentId(), tmClassify.getId()))
                .map(tcItemClassify -> {
                    TmClassify tmSubClassify = new TmClassify();
                    BeanUtils.copyProperties(tcItemClassify, tmSubClassify);
                    return tmSubClassify;
                }).sorted((o1, o2) -> {
                    return ObjectUtils.compare(o1.getHierarchy(), o2.getHierarchy());
                }).collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(tmSubClassifies)) {
            tmSubClassifies.forEach(tmSubClassify -> {
                findSubClassifiesRecursive(tmSubClassify, tcItemClassifies);
            });
        }

        tmClassify.setSubClassifies(tmSubClassifies);
        return tmClassify;
    }

}
