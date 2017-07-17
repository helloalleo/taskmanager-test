package com.workingbit.taskmanager.controller;

import com.workingbit.taskmanager.common.AppConstants;
import com.workingbit.taskmanager.common.EnumResponse;
import com.workingbit.taskmanager.common.StringMap;
import com.workingbit.taskmanager.domain.Task;
import com.workingbit.taskmanager.service.TaskManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Aleksey Popryaduhin on 16:03 17/06/2017.
 */
@RestController
@RequestMapping(AppConstants.API)
public class TaskManagerController {

    @Autowired
    private TaskManagerService taskManagerService;

//    @Value("${spring.datasource.url}")
//    private String datasource;

    @PostMapping(AppConstants.TASKS_PATH)
    public StringMap addTask(@RequestBody Task task) {
        try {
            taskManagerService.save(task);
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), true)
                    .put(EnumResponse.data.name(), task)
                    .build();
        } catch (Exception e) {
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), false)
                    .put(EnumResponse.message.name(), "Ошибка сохранения задачи " + e.getMessage())
                    .build();
        }
    }

    @PutMapping(AppConstants.TASKS_PATH)
    public StringMap saveTask(@RequestBody Task task) {
        try {
            taskManagerService.save(task);
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), true)
                    .put(EnumResponse.data.name(), task)
                    .build();
        } catch (Exception e) {
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), false)
                    .put(EnumResponse.message.name(), "Ошибка сохранения задачи " + e.getMessage())
                    .build();
        }
    }

    @GetMapping(AppConstants.TASKS_PATH)
    public StringMap find() {
//        System.out.println("DATASOURCE: " + datasource);
        try {
            List<Task> tasks = taskManagerService.find();
            System.out.println(tasks);
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), true)
                    .put(EnumResponse.data.name(), tasks)
                    .build();
        } catch (Exception e) {
            return StringMap.StringMapBuilder.getInstance()
                    .put(EnumResponse.ok.name(), false)
                    .put(EnumResponse.message.name(), "Не удалось загрузить задачи " + e.getMessage())
                    .build();
        }
    }
}
