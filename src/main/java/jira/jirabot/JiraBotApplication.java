package jira.jirabot;

import jira.jirabot.client.JiraClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JiraBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(JiraBotApplication.class, args);
        test();
    }

    public static void test() {
        JiraClient jiraClient = new JiraClient(
                "",
                "",
                "");
        var client = jiraClient.getRestClient();
        var x = client.getProjectClient();
        var issues = client.getSearchClient().searchJql("assignee in (currentuser())").claim().getIssues();

    }
}
