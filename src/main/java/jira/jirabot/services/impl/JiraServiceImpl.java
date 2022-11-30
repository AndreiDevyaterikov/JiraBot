package jira.jirabot.services.impl;

import com.atlassian.jira.rest.client.api.JiraRestClient;
import com.atlassian.jira.rest.client.api.domain.Issue;
import jira.jirabot.services.JiraService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class JiraServiceImpl implements JiraService {

    private final JiraRestClient jiraClient;

    @Override
    public Iterable<Issue> getAllIssues() {
        return jiraClient.getSearchClient().searchJql("assignee in (currentuser())").claim().getIssues();
    }
}
