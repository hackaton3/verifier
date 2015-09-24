package com.verify.main.verifyobjs;

public class Host extends AbsBasicVerifyObject {
    private static final long serialVersionUID = 7907149344774500345L;
    private String name;
    
    public Host() {
    }
    
    public Host(String name) {
		super();
		this.name = name;
	}

	public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
