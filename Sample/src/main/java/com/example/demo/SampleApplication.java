package com.example.demo;

import java.util.Collection;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
public class SampleApplication {

	/*
	 * 
	 * [WARNING] The requested profile "pom.xml" could not be activated because it does not exist.
	 * https://xzio.tistory.com/1410 
	 * 
	 * Unable to make protected final java.lang.Class java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int)
	 * https://simple-ing.tistory.com/m/54
	 * 
	 */
	
	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run( SampleApplication.class, args) ;
	}

}
