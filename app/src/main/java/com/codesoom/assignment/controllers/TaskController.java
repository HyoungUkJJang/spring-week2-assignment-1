package com.codesoom.assignment.controllers;

import com.codesoom.assignment.NotFoundException;
import com.codesoom.assignment.models.Task;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    private List<Task> tasks = new ArrayList<>();
    private Long newId = 0L;
    NotFoundException notFoundException = new NotFoundException();

    @GetMapping
    public List<Task> getTaskList() {
        return tasks;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task getTaskById(@PathVariable Long id) {
        if (findTask(id) == null) {
            throw notFoundException;
        }
        return findTask(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Task handleCreate(@RequestBody Task task) {
        task.setId(generateId());
        tasks.add(task);
        return task;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Task handleUpdate(@PathVariable Long id, @RequestBody Task task) {
        if (findTask(id) == null) {
            throw notFoundException;
        }
        findTask(id).setTitle(task.getTitle());
        return task;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void handleDelete(@PathVariable Long id) {
        if (findTask(id) == null) {
            throw notFoundException;
        }
        Task task = findTask(id);
        tasks.remove(task);
    }

    private Long generateId() {
        newId += 1;
        return newId;
    }

    public Task findTask(Long id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

}
