package org.demo.dto.web.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class TaskDto {
    @Id
    private String taskId;
    private String taskName;
    private int requiredDay;
    private List<String> dependentTaskId;

    public TaskDto(String taskName, int requiredDay, List<String> dependentTaskId) {
        this.taskName = taskName;
        this.requiredDay = requiredDay;
        this.dependentTaskId = dependentTaskId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public int getRequiredDay() {
        return requiredDay;
    }

    public void setRequiredDay(int requiredDay) {
        this.requiredDay = requiredDay;
    }

    public List<String> getDependentTaskId() {
        return dependentTaskId;
    }

    public void setDependentTaskId(List<String> dependentTaskId) {
        this.dependentTaskId = dependentTaskId;
    }
}
