package com.verify.main.util;

public class CommonUtils {
    public static final String ROOT_ELEM_NAME_4YML = "document";
    
    public static final String HOST_FILE = "/etc/hosts";
    
    /**
     * Already thought about these options:
     * <ul>
     * <li>org.apache.commons.lang3.ObjectUtils.toString(Object) is "Deprecated".</li>
     * <li>java.util.Objects.toString(Object) belongs to JRE 7+.</li>
     * <li>org.springframework.util.ObjectUtils.nullSafeToString(Object) may return "null".</li>
     * </ul>
     * 
     * @param obj
     * @return
     */
    public static String nullSafeToString(Object obj) {
        return obj == null ? "" : obj.toString();
    }
}
