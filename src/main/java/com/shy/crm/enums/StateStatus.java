package com.shy.crm.enums;

/**
 * 营销机会分配状态枚举类
 * @author suhongyv
 */

public enum StateStatus {
    //已分配
    STATED(1),
    //未分配
    UNSTATE(0);
    private Integer type;

    StateStatus(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }
}
