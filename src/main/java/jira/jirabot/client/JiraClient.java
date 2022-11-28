package jira.jirabot.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import lombok.Getter;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
public class JiraClient {
    private String login;
    private String password;
    private String jiraUrl;
    private JiraRestClient restClient;

    public JiraClient(String login, String password, String jiraUrl) {
        this.login = login;
        this.password = password;
        this.jiraUrl = jiraUrl;
        this.restClient = getJiraRestClient();
    }

    private JiraRestClient getJiraRestClient() {
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(getJiraUri(), this.login, this.password);
    }

    private URI getJiraUri() {
        return URI.create(this.jiraUrl);
    }
}
