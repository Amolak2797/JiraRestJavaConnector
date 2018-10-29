package com.anthem.jiraTool;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws Exception{
		
		String propertiesFileName=System.getProperty("user.home")+"/config.properties";
		
		System.out.println(propertiesFileName);
		Properties prop=new Properties();
		InputStream input=null;
		input=new FileInputStream(propertiesFileName);
		prop.load(input);
		
		String hostName=prop.getProperty("HOST_NAME");
		String username=prop.getProperty("USERNAME");
		String password=prop.getProperty("PASSWORD");
		String handOffFileName=prop.getProperty("HANDOFF_FILE");
		
		System.out.println(hostName+"\t"+username+"\t"+password+"\t"+handOffFileName);
				
		HandOffExcelReader excelReader=new HandOffExcelReader(handOffFileName);
		System.out.println(excelReader.getJiraIssues());
		
		HashSet<String> jiraIssueSet=new HashSet<>();
		jiraIssueSet.add("COCAP-2980");
		JiraConnector jc=new JiraConnector(hostName,username,password);
		
		Iterator<String> jiraIssueIterator=jiraIssueSet.iterator();
		while(jiraIssueIterator.hasNext())
			jc.connect(jiraIssueIterator.next());
		
		System.out.println("Example complete.Exiting now");
		System.exit(0);
	}

}
