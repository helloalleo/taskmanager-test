package com.workingbit.taskmanager.dao;

import com.workingbit.taskmanager.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Aleksey Popryaduhin on 09:02 15/07/2017.
 */
public interface TaskManagerRepository extends JpaRepository<Task, Integer> {
}
