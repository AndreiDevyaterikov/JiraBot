package jirabot.configs;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.internal.async.AsynchronousJiraRestClientFactory;
import jirabot.configs.properties.JiraConfigProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;

@Configuration
@EnableConfigurationProperties({JiraConfigProperties.class})
public class JiraClientConfig {

    @Bean
    public JiraRestClient getJiraRestClient(JiraConfigProperties jiraConfigProperties) {
        var uri = URI.create(jiraConfigProperties.getJiraUrl());
        return new AsynchronousJiraRestClientFactory()
                .createWithBasicHttpAuthentication(uri, jiraConfigProperties.getLogin(), jiraConfigProperties.getPassword());
    }
}
