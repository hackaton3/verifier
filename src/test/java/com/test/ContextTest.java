package com.test;

import java.util.List;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContextTest {
	public static void main(String[] args){
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("bean-context.xml");
		
		List list1 = (List)ctx.getBean("listUtil");
	}
}
