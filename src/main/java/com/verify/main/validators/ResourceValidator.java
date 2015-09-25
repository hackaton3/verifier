package com.verify.main.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.verify.main.verifyobjs.Resourse;

public class ResourceValidator {
    public static String validate(List<Resourse> expectResources, List<Resourse> allInstalled) {
        if (expectResources == null) {
            return "";
        }
        if (allInstalled == null) {
            allInstalled = new ArrayList<Resourse>();
        }
        StringBuilder errSummary = new StringBuilder();
        
        for (Resourse exp : expectResources) {
            boolean foundName = false;
            for (Resourse act : allInstalled) {
                if (StringUtils.equals(exp.getName(), act.getName())) {
                    foundName = true;
                    
                    if (!exp.equals(act)) {
                        errSummary.append(exp);
                        errSummary.append(System.lineSeparator());
                    }
                    break;
                }
            }
            
            if (!foundName) {
                errSummary.append(exp);
                errSummary.append(System.lineSeparator());
            }
        }
        
        return errSummary.toString();
    }
}
