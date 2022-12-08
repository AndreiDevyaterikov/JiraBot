package jirabot.services;

import com.atlassian.jira.rest.client.api.domain.Issue;

public interface BotService {

    /**
     * Метод создания сообщения по задаче
     *
     * @param issue Задача, на основе которой строится сообщение
     * @return Сообщение по с задачей
     * @see Issue
     */
    String createIssueMessage(Issue issue);
}
