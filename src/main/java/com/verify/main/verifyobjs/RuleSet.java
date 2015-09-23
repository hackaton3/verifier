package com.verify.main.verifyobjs;

public class RuleSet extends AbsBasicVerifyObject {
    private static final long serialVersionUID = 6557791110471261587L;
    private String name;
    private String type;
    private String site;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getSite() {
        return site;
    }
    public void setSite(String site) {
        this.site = site;
    }
}
