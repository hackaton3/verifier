package com.verify.main.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.verify.main.verifyobjs.Host;

public class HostValidator {
    public static String validate(List<Host> expectHosts, List<Host> allInstalled) {
        if (expectHosts == null) {
            return "";
        }
        if (allInstalled == null) {
            allInstalled = new ArrayList<Host>();
        }
        StringBuilder errSummary = new StringBuilder();
        
        for (Host exp : expectHosts) {
            boolean found = false;
            for (Host act : allInstalled) {
                if (StringUtils.equals(exp.getName(), act.getName())) {
                    found = true;
                    break;
                }
            }
            
            if (!found) {
                errSummary.append(exp);
                errSummary.append(System.lineSeparator());
            }
        }
        
        return errSummary.toString();
    }
}
