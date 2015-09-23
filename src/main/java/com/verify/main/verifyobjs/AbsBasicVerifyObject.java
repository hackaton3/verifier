package com.verify.main.verifyobjs;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbsBasicVerifyObject {
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
