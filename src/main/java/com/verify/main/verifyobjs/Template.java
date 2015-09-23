package com.verify.main.verifyobjs;

public class Template extends AbsBasicVerifyObject {
    private String name;
    private String version;
    private String selectorKey;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getSelectorKey() {
        return selectorKey;
    }
    public void setSelectorKey(String selectorKey) {
        this.selectorKey = selectorKey;
    }
}
