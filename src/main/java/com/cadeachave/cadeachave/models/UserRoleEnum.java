package com.cadeachave.cadeachave.models;

public enum UserRoleEnum {
    ADMIN("admin"),
    USER("user"),
    HARDWARE("hardware");

    private String role;

    UserRoleEnum(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
