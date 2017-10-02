package com.haight.comp2240.farmers.part1;

public enum Island {

    North("N", "North"),
    South("S", "South");

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
