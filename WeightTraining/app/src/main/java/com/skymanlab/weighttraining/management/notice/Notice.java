package com.skymanlab.weighttraining.management.notice;

public class Notice {

    // constant
    public static final String NUMBER = "number";
    public static final String TYPE = "type";
    public static final String DATE = "date";
    public static final String TITLE = "title";
    public static final String MESSAGE = "message";

    // instance variable
    private long number;        // 번호
    private String type;        // 타입
    private String date;        // 날짜
    private String title;       // 제목
    private String message;     // 메시지

    // getter, setter
    public long getNumber() {
        return number;
    }

    public void setNumber(long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
