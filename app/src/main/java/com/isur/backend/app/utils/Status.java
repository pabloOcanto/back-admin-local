package com.isur.backend.app.utils;

import java.util.HashMap;
import java.util.Map;

public enum Status{

    ACTIVE(1, "Active"),
    INACTIVE(2, "In Active"),

    ADMIN(1, "Admin"),
    EMPLOYEE(1, "Employee");

    private final Integer value;
    private final String text;

    /**
     * A mapping between the integer code and its corresponding text to facilitate lookup by code.
     */
    private static Map<Integer, Status> valueToTextMapping;

    private Status(Integer value, String text){
        this.value = value;
        this.text = text;
    }

    public static Status getStatus(Integer i){
        if(valueToTextMapping == null){
            initMapping();
        }
        return valueToTextMapping.get(i);
    }

    private static void initMapping(){
        valueToTextMapping = new HashMap<>();
        for(Status s : values()){
            valueToTextMapping.put(s.value, s);
        }
    }

    public Integer getValue(){
        return value;
    }

    public String getText(){
        return text;
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();
        sb.append("Status");
        sb.append("{value=").append(value);
        sb.append(", text='").append(text).append('\'');
        sb.append('}');
        return sb.toString();
    }
}