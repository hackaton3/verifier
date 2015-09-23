package com.verify.main.verifyobjs;

import java.util.List;

public class Component extends AbsBasicVerifyObject {
    private static final long serialVersionUID = -6022780112417539677L;
    private String name;
    private String path;
    private List<Host> hosts;
    private List<Template> templates;
    private List<Resourse> resources;
    private List<RuleSet> ruleSets;
    private List<CustomField> customFields;
    private List<Alert> alerts;
    private List<Report> reports;
    private List<Partner> partners;
    private List<Site> sites;
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    public List<Host> getHosts() {
        return hosts;
    }
    public void setHosts(List<Host> hosts) {
        this.hosts = hosts;
    }
    public List<Template> getTemplates() {
        return templates;
    }
    public void setTemplates(List<Template> templates) {
        this.templates = templates;
    }
    public List<Resourse> getResources() {
        return resources;
    }
    public void setResources(List<Resourse> resources) {
        this.resources = resources;
    }
    public List<RuleSet> getRuleSets() {
        return ruleSets;
    }
    public void setRuleSets(List<RuleSet> ruleSets) {
        this.ruleSets = ruleSets;
    }
    public List<CustomField> getCustomFields() {
        return customFields;
    }
    public void setCustomFields(List<CustomField> customFields) {
        this.customFields = customFields;
    }
    public List<Alert> getAlerts() {
        return alerts;
    }
    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }
    public List<Report> getReports() {
        return reports;
    }
    public void setReports(List<Report> reports) {
        this.reports = reports;
    }
    public List<Partner> getPartners() {
        return partners;
    }
    public void setPartners(List<Partner> partners) {
        this.partners = partners;
    }
    public List<Site> getSites() {
        return sites;
    }
    public void setSites(List<Site> sites) {
        this.sites = sites;
    }
}
