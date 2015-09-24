package com.verify.main.installed;

import java.util.List;

public class ComponentJsonObject {
	String componentName;
	List<ComponentJsonObject> subComponents;
	public String getComponentName() {
		return componentName;
	}
	public void setComponentName(String componentName) {
		this.componentName = componentName;
	}
	public List<ComponentJsonObject> getSubComponents() {
		return subComponents;
	}
	public void setSubComponents(List<ComponentJsonObject> subComponents) {
		this.subComponents = subComponents;
	}
	@Override
	public String toString() {
		return "jsonObj [componentName=" + componentName + ", subComponents=" + subComponents + "]";
	}
	
	
}
