package com.workingbit.taskmanager.service;

import com.workingbit.taskmanager.dao.TaskManagerRepository;
import com.workingbit.taskmanager.domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by Aleksey Popryaduhin on 19:57 15/07/2017.
 */
@Service
public class TaskManagerService {

    @Autowired
    private TaskManagerRepository taskManagerRepository;

    @Transactional
    public void save(Task task) {
        taskManagerRepository.save(task);
    }

    @Transactional
    public List<Task> find() {
        return taskManagerRepository.findAll();
    }
}
