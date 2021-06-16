package com.littlestore.littlestore.domain.enums;

public enum Role {

    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    public static final String INVALID_CODE = "Invalid code:";
    private int code;
    private String description;

    Role(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Role toEum(int code) {
        for (Role role : Role.values()) {
            if (code == role.code) {
                return role;
            }
        }
        throw new IllegalArgumentException(INVALID_CODE + code);
    }
}
