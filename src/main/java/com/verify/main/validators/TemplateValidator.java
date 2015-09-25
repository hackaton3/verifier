package com.verify.main.validators;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.verify.main.verifyobjs.Template;

public class TemplateValidator {
    public static String validate(List<Template> expectTemplates, List<Template> allInstalled) {
        StringBuilder errSummary = new StringBuilder();
        
        for (Template exp : expectTemplates) {
            boolean foundName = false;
            for (Template act : allInstalled) {
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
