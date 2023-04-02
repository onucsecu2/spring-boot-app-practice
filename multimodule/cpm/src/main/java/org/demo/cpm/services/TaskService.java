package org.demo.cpm.services;

import java.util.List;

public interface TaskService {
    List<String> getCriticalTasks();

    int getTotalProjectDays();
}
