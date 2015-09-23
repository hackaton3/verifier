package com.verify.main.verifyobjs;

public class CustomField extends AbsBasicVerifyObject {
    private String name;
    private String assetPath;
    private String dataType;
    private String fieldType;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAssetPath() {
        return assetPath;
    }
    public void setAssetPath(String assetPath) {
        this.assetPath = assetPath;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getFieldType() {
        return fieldType;
    }
    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }
}
