package com.modofo.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringUtils {
	private static ApplicationContext ctx;
	public static void initFromClasspath(String[] path){
		ctx = new ClassPathXmlApplicationContext(path);
	}
	public static Object getBean(String name){
		return ctx.getBean(name);
	}

}
