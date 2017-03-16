package com.ysu.zyw.tc.model.menum;

public enum TmPlatform {

    PC_PLATFORM("网页端"),

    MOBILE_PLATFORM("无线端");

    private String name;

    TmPlatform(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
