package jirabot.services;

import com.atlassian.jira.rest.client.api.domain.Issue;

public interface JiraService {

    /**
     * Получение всех своих задач
     *
     * @return {@link Issue} Список задач
     */
    Iterable<Issue> getAllIssues();

    /**
     * Поиск задачи по номеру
     *
     * @param issueKey Номер задачи
     * @return {@link Issue} Найденная задача
     */
    Issue getIssueByKey(String issueKey);
}
