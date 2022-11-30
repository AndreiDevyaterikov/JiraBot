package jira.jirabot.bot;

import jira.jirabot.configs.BotConfig;
import jira.jirabot.constatns.Constants;
import jira.jirabot.services.BotService;
import jira.jirabot.services.JiraService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@AllArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final JiraService jiraService;
    private final BotService botService;

    @Override
    public String getBotUsername() {
        return botConfig.getBotUsername();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        var message = update.getMessage();
        var chatId = message.getChatId();

        switch (message.getText()) {
            case "/start" -> createHelloMessage(message);
            case "/my_issues" -> createAllMyIssuesMessage(chatId);
            default -> createDefaultMessage(chatId);
        }
    }

    private void createAllMyIssuesMessage(Long chatId) {
        var issues = jiraService.getAllIssues();
        for (var issue : issues) {
            try {
                var issueMessage = botService.createIssueMessage(issue);
                sendMessage(chatId, issueMessage);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void createHelloMessage(Message message) {
        var chatId = message.getChatId();
        var user = message.getFrom();
        var helloMessage = Constants.HELLO + user.getFirstName() + " " + user.getLastName();
        try {
            execute(botConfig.getBotCommands());
            sendMessage(chatId, helloMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    private void createDefaultMessage(Long chatId) {
        try {
            sendMessage(chatId, Constants.DONT_FIND_COMMAND);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendMessage(long chatId, String text) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        execute(message);
    }
}
