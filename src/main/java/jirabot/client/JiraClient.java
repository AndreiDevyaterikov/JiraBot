package jirabot.client;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import jirabot.configs.JiraClientConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
public class JiraClient {

    @Bean
    public JiraRestClient getJiraRestClient(@Autowired JiraClientConfig jiraClientConfig) {
        var uri = URI.create(jiraClientConfig.getJiraUrl());
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(uri, jiraClientConfig.getLogin(), jiraClientConfig.getPassword());
    }
}
