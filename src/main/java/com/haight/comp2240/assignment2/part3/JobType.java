package com.haight.comp2240.assignment2.part3;

public enum JobType {
    Empty("E"),
    Monochrome("M"),
    Colour("C");

    private String code;
    public String code() { return code; }

    private JobType(String code) { this.code = code; }
}
