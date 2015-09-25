package com.verify.main.validators;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.verify.main.verifyobjs.Alert;

public class AlertValidator {
    public static String validate(List<Alert> expectAlerts, List<Alert> allInstalled) {
        if (expectAlerts == null) {
            return "";
        }
        if (allInstalled == null) {
            allInstalled = new ArrayList<Alert>();
        }
        StringBuilder errSummary = new StringBuilder();
        
        for (Alert exp : expectAlerts) {
            boolean foundName = false;
            for (Alert act : allInstalled) {
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
