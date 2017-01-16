package com.ysu.zyw.tc.openapi.fk.config;

import lombok.Data;

@Data
public class TcConfig {

    private boolean devMode = false;

    private boolean fixedVerificationCode = false;

    private String tempDir;

}
