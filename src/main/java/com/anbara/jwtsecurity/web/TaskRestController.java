package com.anbara.jwtsecurity.web;

import com.anbara.jwtsecurity.dao.TaskRepository;
import com.anbara.jwtsecurity.entities.Task;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskRestController {

    private final TaskRepository taskRepository;

    public TaskRestController(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    @GetMapping("/tasks")
     List<Task> taskList(){
        return taskRepository.findAll();
    }
    @PostMapping("/tasks" )
    Task saveTask(@RequestBody Task task){
       return taskRepository.save(task);
    }
}
