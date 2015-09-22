package com.verify.main.interfaze;

public interface Validator {
	
	public enum Result{
		VALID, INVALID, UNKNOW
	}
	
	public Result verify() throws Exception;
}
