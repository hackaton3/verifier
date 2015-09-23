package com.verify.main.verifyobjs;

/**
 * "Resource" object.
 */
public class Resourse extends AbsBasicVerifyObject {
    private static final long serialVersionUID = 2586080192338193155L;
    private String name;
    private String resourceType;
    private String resourceGroup;
    private String connectionString;
    private String heartbeatString;
    private int maxConcurrency;
    private int heartbeatFrequency;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getResourceType() {
        return resourceType;
    }
    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }
    public String getResourceGroup() {
        return resourceGroup;
    }
    public void setResourceGroup(String resourceGroup) {
        this.resourceGroup = resourceGroup;
    }
    public String getConnectionString() {
        return connectionString;
    }
    public void setConnectionString(String connectionString) {
        this.connectionString = connectionString;
    }
    public String getHeartbeatString() {
        return heartbeatString;
    }
    public void setHeartbeatString(String heartbeatString) {
        this.heartbeatString = heartbeatString;
    }
    public int getMaxConcurrency() {
        return maxConcurrency;
    }
    public void setMaxConcurrency(int maxConcurrency) {
        this.maxConcurrency = maxConcurrency;
    }
    public int getHeartbeatFrequency() {
        return heartbeatFrequency;
    }
    public void setHeartbeatFrequency(int heartbeatFrequency) {
        this.heartbeatFrequency = heartbeatFrequency;
    }
}
