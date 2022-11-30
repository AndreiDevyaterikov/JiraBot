package jira.jirabot.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class BotConfig {

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.name}")
    private String botUsername;
}
