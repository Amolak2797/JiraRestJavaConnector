package com.anthem.jiraTool;

public class Main {

	public static void main(String[] args) throws Exception{
		
		JiraConnector jc=new JiraConnector(args[0], args[1],args[2]);
		jc.connect();

	}

}
