package com.anthem.jiraTool;

//import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;

import com.atlassian.jira.rest.client.IssueRestClient;
import com.atlassian.jira.rest.client.JiraRestClient;
import com.atlassian.jira.rest.client.JiraRestClientFactory;
import com.atlassian.jira.rest.client.ProjectRestClient;
import com.atlassian.jira.rest.client.domain.Attachment;
import com.atlassian.jira.rest.client.domain.Issue;
import com.atlassian.jira.rest.client.domain.Project;
import com.atlassian.jira.rest.client.domain.User;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import com.atlassian.util.concurrent.Promise;

public class JiraConnector {

	private static String JIRA_URI = "";
	private static String USERNAME = "";
	private static String PASSWORD = "";
	private JiraRestClient client = null;

	public JiraConnector(String javaUri, String username, String password) {
		JIRA_URI = javaUri;
		USERNAME = username;
		PASSWORD = password;
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
		URI uri = null;
		try {
			uri = new URI(JIRA_URI);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		client = factory.createWithBasicHttpAuthentication(uri, USERNAME, PASSWORD);
	}

	public void connect(String issueName) throws Exception {
		// Invoke the JRJC Client
		Promise<User> promise = client.getUserClient().getUser(USERNAME);
		User user = promise.claim();
		// Print the result
		System.out.println(String.format("Your admin user's email address is: %s\r\n", user.getEmailAddress()));

		ProjectRestClient project = client.getProjectClient();
		Promise<Project> projectDetails = project.getProject("JiraTest");
		System.out.println(projectDetails.isDone());

		IssueRestClient issue = client.getIssueClient();
		Promise<Issue> issueDetails = issue.getIssue(issueName);

		System.out.println(issueDetails.get().getStatus().getName());

		Iterator<Attachment> attachmentIteraor = issueDetails.get().getAttachments().iterator();
		while (attachmentIteraor.hasNext()) {
			Attachment attachment = attachmentIteraor.next();
			System.out.println(attachment.getContentUri().toString());
			URI attachmentURI=attachment.getContentUri();
			System.out.println(attachmentURI.getPath());
			// Runtime.getRuntime().exec(new String[] { "cmd", "/c", "start
			// chrome " + attachment.getContentUri() });
			// Runtime.getRuntime().exec("C:/Program Files
			// (x86)/Google/Chrome/Application/chrome.exe", new
			// String[]{attachment.getContentUri()+""});
			 //Desktop.getDesktop().browse(attachment.getContentURI());

			/*
			 * URL url=new URL(attachment.getContentUri().toString());
			 * ReadableByteChannel rbc=Channels.newChannel(url.openStream());
			 * fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			 */

	
			// Make sure that this directory exists
/*	        String dirName = "C:\\Users\\ahunjan\\Desktop";
	        try {
	            saveFileFromUrlWithCommonsIO(
	                dirName + "\\java_tutorial_12345678.png", "http://localhost:8080/secure/attachment/10005/java_tutorial.png");
	            System.out.println("finished");
	        } catch (MalformedURLException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
*/
		}

	}

	public void saveFileFromUrlWithJavaIO(String fileName, String fileUrl) throws MalformedURLException, IOException {
		BufferedInputStream in = null;
		FileOutputStream fout = null;
		try {
			in = new BufferedInputStream(new URL(fileUrl).openStream());
			fout = new FileOutputStream(fileName);
			byte data[] = new byte[1024];
			int count;
			while ((count = in.read(data, 0, 1024)) != -1) {
				fout.write(data, 0, count);
			}
		} finally {
			if (in != null)
				in.close();
			if (fout != null)
				fout.close();
		}
	}
	
	public static void saveFileFromUrlWithCommonsIO(String fileName,
	        String fileUrl) throws MalformedURLException, IOException {
	        FileUtils.copyURLToFile(new URL(fileUrl), new File(fileName));
	    }
}
