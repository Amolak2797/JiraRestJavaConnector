package com.anthem.jiraTool;

import java.net.URI;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.ProjectRestClient;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.Project;
import com.atlassian.jira.rest.client.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;
 


public class JiraConnector {

	private static String JIRA_URI = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";

	public JiraConnector(String javaUri, String username, String password) {
		JIRA_URI = javaUri;
		USERNAME = username;
		PASSWORD = password;
	}

	public void connect() throws Exception {
		// Print usage instructions
		StringBuilder intro = new StringBuilder();
		intro.append(
				"**********************************************************************************************\r\n");
		intro.append(
				"* JIRA Java REST Client ('JRJC') example.                                                    *\r\n");
		intro.append(
				"* NOTE: Start JIRA using the Atlassian Plugin SDK before running this example.               *\r\n");
		intro.append(
				"* (for example, use 'atlas-run-standalone --product jira --version 6.0 --data-version 6.0'.) *\r\n");
		intro.append(
				"**********************************************************************************************\r\n");
		System.out.println(intro.toString());

		// Construct the JRJC client
		System.out.println(
				String.format("Logging in to %s with username '%s' and password '%s'", JIRA_URI, USERNAME, PASSWORD));
		JiraRestClientFactory factory = new AsynchronousJiraRestClientFactory();
		URI uri = new URI(JIRA_URI);
		JiraRestClient client = factory.createWithBasicHttpAuthentication(uri, USERNAME, PASSWORD);

		// Invoke the JRJC Client
		Promise<User> promise = client.getUserClient().getUser(USERNAME);
		User user = promise.claim();
		// Print the result
		System.out.println(String.format("Your admin user's email address is: %s\r\n", user.getEmailAddress()));

		ProjectRestClient project = client.getProjectClient();
		Promise<Project> projectDetails=project.getProject("JiraTest");
		System.out.println(projectDetails.isDone());
		
		IssueRestClient issue=client.getIssueClient();
		Promise<Issue> issueDetails=issue.getIssue("JIRTEST-1");
		
		
		System.out.println(issueDetails.get().getStatus().getName());

		System.out.println("Example complete.Exiting now");
		System.exit(0);

	}
}
