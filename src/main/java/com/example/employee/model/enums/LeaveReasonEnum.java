package com.example.employee.model.enums;

/**
 * 请假原因枚举
 */
public enum LeaveReasonEnum {

    SICK(0, "病假"),
    PERSONAL(1, "事假"),
    ANNUAL(2, "年假"),
    OTHER(3, "其他");

    private int value;

    private String text;

    public static LeaveReasonEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        LeaveReasonEnum[] values = LeaveReasonEnum.values();
        for (LeaveReasonEnum leaveReasonEnum : values) {
            if (leaveReasonEnum.getValue() == value) {
                return leaveReasonEnum;
            }
        }
        return null;
    }

    LeaveReasonEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public LeaveReasonEnum setValue(int value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public LeaveReasonEnum setText(String text) {
        this.text = text;
        return this;
    }
}
