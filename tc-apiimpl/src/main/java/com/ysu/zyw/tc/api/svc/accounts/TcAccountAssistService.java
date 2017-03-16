package com.ysu.zyw.tc.api.svc.accounts;

import com.ysu.zyw.tc.api.dao.mappers.TcAccountAssistMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class TcAccountAssistService {

    @Resource
    public TcAccountAssistMapper tcAccountAssistMapper;


}
