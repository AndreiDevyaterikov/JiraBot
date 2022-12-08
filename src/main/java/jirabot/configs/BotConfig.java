package jirabot.configs;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.List;

@Getter
@Component
public class BotConfig {

    @Value("${telegram.token}")
    private String botToken;

    @Value("${telegram.name}")
    private String botUsername;

    public SetMyCommands getBotCommands(){
        SetMyCommands setMyCommands = new SetMyCommands();
        setMyCommands.setCommands(List.of(
                new BotCommand("/my_issues", "Список моих задач"),
                new BotCommand("/find_issue_by_key", "Найти задачу по номеру")
        ));
        return setMyCommands;
    }
}
