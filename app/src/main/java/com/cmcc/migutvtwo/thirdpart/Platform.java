package com.cmcc.migutvtwo.thirdpart;

/**
 * Created by caixiangyu on 2017/6/30.
 */

public enum Platform {
    WEICHAT(1, "微信"),
    WEICHATCIRLE(2, "朋友圈"),
    QQ(3, "QQ"),
    QQZONE(4, "zone"),
    SINA(5, "新浪");

    private int code;
    private String name;

    Platform(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static Platform getPlatform(String name) {
        for (Platform platform : values()) {
            if (platform.name.equals(name)) {
                return platform;
            }
        }
        return null;
    }
}
