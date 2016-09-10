package com.demo.wms.util;

/**
 * Created by anton_kramarev on 8/30/2016.
 */
public enum Caption {

    LOGIN("common.login"),
    PASSWORD("common.password"),
    ROLE("common.role"),
    SAVE("common.save"),
    CANCEL("common.cancel"),
    TITLE_ADD("common.add.title"),
    TITLE_EDIT("common.edit.title");

    private final String code;

    Caption(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
