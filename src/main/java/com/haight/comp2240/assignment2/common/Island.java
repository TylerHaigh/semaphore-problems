package com.haight.comp2240.assignment2.common;

public enum Island {

    North("N", "North"),
    South("S", "South"),
    Undefined("U", "Undefined");

    private String code;
    public String code() { return code; }

    private String text;
    public String text() { return text; }

    private Island(String code, String text) {
        this.code = code;
        this.text = text;
    }

    @Override
    public String toString() {
        return text + " Island (" + code + ")";
    }


}
