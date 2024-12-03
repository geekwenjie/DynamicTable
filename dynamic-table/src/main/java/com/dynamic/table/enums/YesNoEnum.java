package com.dynamic.table.enums;

/**
 * @author dwj
 * @date 2024/11/27
 */
public enum YesNoEnum {

    YES(1, "是"),
    NO(0, "否");

    private final int value;
    private final String description;

    YesNoEnum(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static YesNoEnum fromValue(int value) {
        for (YesNoEnum e : YesNoEnum.values()) {
            if (e.value == value) {
                return e;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }

}
