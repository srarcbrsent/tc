package com.ysu.zyw.tc.openapi.fk.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class TcConfig {

    private boolean devMode = false;

    private boolean fixedVerificationCode = false;

    private String tempDir;

}
