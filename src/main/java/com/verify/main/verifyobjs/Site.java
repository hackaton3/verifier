package com.verify.main.verifyobjs;

import java.util.ArrayList;
import java.util.List;

public class Site extends AbsBasicVerifyObject {
    private String name;
    private String externalId;
    private List<String> fileLookupKeys = new ArrayList<String>();
    private String metadataFormat;
    private int distributionOption;
    private String distributionTemplate;
    private int alertDelayPeriod;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getExternalId() {
        return externalId;
    }
    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }
    public List<String> getFileLookupKeys() {
        return fileLookupKeys;
    }
    public void setFileLookupKeys(List<String> fileLookupKeys) {
        this.fileLookupKeys = fileLookupKeys;
    }
    public String getMetadataFormat() {
        return metadataFormat;
    }
    public void setMetadataFormat(String metadataFormat) {
        this.metadataFormat = metadataFormat;
    }
    public int getDistributionOption() {
        return distributionOption;
    }
    public void setDistributionOption(int distributionOption) {
        this.distributionOption = distributionOption;
    }
    public String getDistributionTemplate() {
        return distributionTemplate;
    }
    public void setDistributionTemplate(String distributionTemplate) {
        this.distributionTemplate = distributionTemplate;
    }
    public int getAlertDelayPeriod() {
        return alertDelayPeriod;
    }
    public void setAlertDelayPeriod(int alertDelayPeriod) {
        this.alertDelayPeriod = alertDelayPeriod;
    }
}
