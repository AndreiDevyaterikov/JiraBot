package jira.jirabot.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class JiraClientConfig {

    @Value("${jira.login}")
    private String login;

    @Value("${jira.password}")
    private String password;

    @Value("${jira.jiraUrl}")
    private String jiraUrl;
}
