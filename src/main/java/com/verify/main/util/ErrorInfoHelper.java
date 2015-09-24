package com.verify.main.util;

public class ErrorInfoHelper {
    /**
     * Results to:
     * <pre>
     * header: [
     * original string...
     * ]
     * </pre>
     * 
     * @param strBuilder
     * @param header
     * @return
     */
    public static StringBuilder addHeader2ErrorInfo(StringBuilder strBuilder, String header) {
        strBuilder.insert(0, System.lineSeparator());
        strBuilder.insert(0, ": [");
        strBuilder.insert(0, header);
        
        strBuilder.append("]");
        
        return strBuilder;
    }
}
