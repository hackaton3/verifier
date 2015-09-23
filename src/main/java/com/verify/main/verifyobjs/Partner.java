package com.verify.main.verifyobjs;

public class Partner extends AbsBasicVerifyObject {
    private String name;
    private String providerId;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getProviderId() {
        return providerId;
    }
    public void setProviderId(String providerId) {
        this.providerId = providerId;
    }
}
