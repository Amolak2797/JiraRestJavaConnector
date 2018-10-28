package com.anthem.jiraTool;

import java.util.HashSet;
import java.util.Iterator;

public class Main {

	public static void main(String[] args) throws Exception{
		
		String hostName=args[0];
		String username=args[1];
		String password=args[2];
		String handOffFileName=args[3];
		
		HandOffExcelReader excelReader=new HandOffExcelReader(handOffFileName);
		System.out.println(excelReader.getJiraIssues());
		
		HashSet<String> jiraIssueSet=new HashSet<>();
		jiraIssueSet.add("JIRTEST-1");
		jiraIssueSet.add("JIR-1");
		JiraConnector jc=new JiraConnector(hostName,username,password);
		
		Iterator<String> jiraIssueIterator=jiraIssueSet.iterator();
		while(jiraIssueIterator.hasNext())
			jc.connect(jiraIssueIterator.next());
		
		System.out.println("Example complete.Exiting now");
		System.exit(0);
	}

}
