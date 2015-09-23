package com.verify.main.verifyobjs;

public class Report extends AbsBasicVerifyObject {
    private static final long serialVersionUID = 6042488360625856316L;
    private String name;
    private String category;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
}
