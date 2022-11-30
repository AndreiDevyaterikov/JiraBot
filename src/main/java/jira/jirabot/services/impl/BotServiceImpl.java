package jira.jirabot.services.impl;

import com.atlassian.jira.rest.client.api.domain.Issue;
import jira.jirabot.constatns.Constants;
import jira.jirabot.services.BotService;
import org.springframework.stereotype.Service;

@Service
public class BotServiceImpl implements BotService {
    @Override
    public String createIssueMessage(Issue issue) {
        StringBuilder issueMessage = new StringBuilder();

        var description = issue.getDescription() != null ? issue.getDescription() : Constants.WITHOUT_DESCRIPTION;

        var priority = issue.getPriority();
        var priorityName = priority != null ? priority.getName() : Constants.WITHOUT_PRIORITY;

        var assignee = issue.getAssignee();
        var assigneeName = assignee != null ? assignee.getDisplayName() : Constants.WITHOUT_ASSIGNEE;

        var reporter = issue.getReporter();
        var reporterName = reporter != null ? reporter.getDisplayName(): Constants.WITHOUT_REPORTER;

        issueMessage.append(Constants.PROJECT_NAME).append(issue.getProject().getName())
                .append("\n")
                .append(Constants.ISSUE_NUMBER).append(issue.getKey())
                .append("\n")
                .append(Constants.ISSUE_STATUS).append(issue.getStatus().getName())
                .append("\n")
                .append(Constants.ISSUE_PRIORITY).append(priorityName)
                .append("\n")
                .append(Constants.ISSUE_TYPE).append(issue.getIssueType().getName())
                .append("\n")
                .append(Constants.ISSUE_SUMMARY).append(issue.getSummary())
                .append("\n")
                .append(Constants.ISSUE_DESCRIPTION).append(description)
                .append("\n")
                .append(Constants.ISSUE_REPORTER).append(reporterName)
                .append("\n")
                .append(Constants.ISSUE_ASSIGNEE).append(assigneeName);
        return issueMessage.toString();
    }
}
