package com.example.employee.model.enums;

/**
 * 审批状态枚举
 */
public enum ApproveStatusEnum {

    WAITING(0, "待审批"),
    APPROVED(1, "已通过"),
    REJECTED(2, "已驳回");

    private int value;

    private String text;

    public static ApproveStatusEnum getEnumByValue(Integer value) {
        if (value == null) {
            return null;
        }
        ApproveStatusEnum[] values = ApproveStatusEnum.values();
        for (ApproveStatusEnum approveStatusEnum : values) {
            if (approveStatusEnum.getValue() == value) {
                return approveStatusEnum;
            }
        }
        return null;
    }

    ApproveStatusEnum(int value, String text) {
        this.value = value;
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public ApproveStatusEnum setValue(int value) {
        this.value = value;
        return this;
    }

    public String getText() {
        return text;
    }

    public ApproveStatusEnum setText(String text) {
        this.text = text;
        return this;
    }
}
