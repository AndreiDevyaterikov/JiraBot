package jirabot.bot;

import jirabot.configs.BotConfig;
import jirabot.constatns.Constants;
import jirabot.services.BotService;
import jirabot.services.JiraService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;
    private final JiraService jiraService;
    private final BotService botService;

    boolean findIssueByKey;

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
        if (message.isCommand()) {
            switch (message.getText()) {
                case "/start" -> createHelloMessage(message);
                case "/my_issues" -> createAllMyIssuesMessage(chatId);
                case "/find_issue_by_key" -> {
                    findIssueByKey = true;
                    sendMessage(chatId, "Введите номер задачи");
                }
                default -> createDefaultMessage(chatId);
            }
        } else {
            if (findIssueByKey) {
                var issueKey = update.getMessage().getText();
                var issue = jiraService.getIssueByKey(issueKey);
                var issueMessage = botService.createIssueMessage(issue);
                findIssueByKey = false;
                sendMessage(chatId, issueMessage);
            }
        }
    }

    private void createAllMyIssuesMessage(Long chatId) {
        var issues = jiraService.getAllIssues();
        for (var issue : issues) {
            var issueMessage = botService.createIssueMessage(issue);
            sendMessage(chatId, issueMessage);
        }
    }

    private void createHelloMessage(Message message) {
        var chatId = message.getChatId();
        var user = message.getFrom();
        var helloMessage = Constants.HELLO + user.getFirstName() + " " + user.getLastName();
        try {
            execute(botConfig.getBotCommands());
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
        sendMessage(chatId, helloMessage);
    }

    private void createDefaultMessage(Long chatId) {
        sendMessage(chatId, Constants.DONT_FIND_COMMAND);
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
