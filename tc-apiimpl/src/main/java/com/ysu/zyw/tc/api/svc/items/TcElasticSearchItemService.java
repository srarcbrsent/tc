package com.ysu.zyw.tc.api.svc.items;

import com.ysu.zyw.tc.model.api.i.items.TcItemSearchTerms;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TcElasticSearchItemService {

    @Resource
    private TcItemService tcItemService;

    /**
     * 查询数量
     */
    public void count(TcItemSearchTerms tcItemSearchTerms) {

    }

    /**
     * 查询列表
     */
    public void query(TcItemSearchTerms tcItemSearchTerms) {

    }

    /**
     * 推荐词
     */
    public void suggest() {

    }

    /**
     * 建索引
     */
    public void index(String id) {
    }

    /**
     * 删索引
     */
    public void delete(String id) {
    }



}
