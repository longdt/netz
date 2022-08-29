package com.github.longdt.netz.http;

public class Range {
    int start;
    int end; // exclusive

    int length() {
        return end - start;
    }

    boolean isEmpty() {
        return end == start;
    }

    public int getStart() {
        return start;
    }

    public Range setStart(int start) {
        this.start = start;
        return this;
    }

    public int getEnd() {
        return end;
    }

    public Range setEnd(int end) {
        this.end = end;
        return this;
    }

    @Override
    public String toString() {
        return "Range[" + start + "," + end + ")";
    }
}
