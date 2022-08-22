package com.github.longdt.netz.http;

public class Range {
    private int start;
    private int end; // exclusive

    Range() {
        reset();
    }

    Range(int start, int end) {
        this.start = start;
        this.end = end;
    }

    int length() {
        return end - start;
    }

    boolean isEmpty() {
        return end == start;
    }

    public Range reset() {
        start = 0;
        end = 0;
        return this;
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
