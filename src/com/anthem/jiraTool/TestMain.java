package com.anthem.jiraTool;

import java.io.File;

public class TestMain {

	public static void main(String[] args) {
		String link="http://localhost:8080/secure/attachment/10005/java_tutorial.png";
		File file=new File("C:/Users/ahunjan/Desktop/output_jira.png");
		new Thread(new Test(link, file)).start();

	}

}
