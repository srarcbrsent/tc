package com.ysu.zyw.tc.platform.fk.config;

import lombok.Data;

@Data
public class TcConfig {

    private boolean devMode = false;

    private boolean fixedVerificationCode = false;

    private String tempDir;

}
