package com.verify.main.verifyobjs;


public class Alert extends AbsBasicVerifyObject {
    private String name;
    private String pattern;
    private int threshCount;
    private int threshSeconds;
    private int freqCount;
    private int freqSecs;
    private int trap;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPattern() {
        return pattern;
    }
    public void setPattern(String pattern) {
        this.pattern = pattern;
    }
    public int getThreshCount() {
        return threshCount;
    }
    public void setThreshCount(int threshCount) {
        this.threshCount = threshCount;
    }
    public int getThreshSeconds() {
        return threshSeconds;
    }
    public void setThreshSeconds(int threshSeconds) {
        this.threshSeconds = threshSeconds;
    }
    public int getFreqCount() {
        return freqCount;
    }
    public void setFreqCount(int freqCount) {
        this.freqCount = freqCount;
    }
    public int getFreqSecs() {
        return freqSecs;
    }
    public void setFreqSecs(int freqSecs) {
        this.freqSecs = freqSecs;
    }
    public int getTrap() {
        return trap;
    }
    public void setTrap(int trap) {
        this.trap = trap;
    }
}
