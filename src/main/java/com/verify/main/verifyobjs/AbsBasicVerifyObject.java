package com.verify.main.verifyobjs;

import java.io.Serializable;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbsBasicVerifyObject implements Serializable {
    private static final long serialVersionUID = -7674647537561888177L;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
