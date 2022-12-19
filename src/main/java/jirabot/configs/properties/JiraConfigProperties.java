package jirabot.configs.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "jira")
public class JiraConfigProperties {
    private String login;
    private String password;
    private String jiraUrl;
}
