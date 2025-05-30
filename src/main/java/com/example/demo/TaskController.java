package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepo;

    @GetMapping("/tasks")
    public String viewTasks(Model model) {
        model.addAttribute("tasks", taskRepo.findAll());
        return "tasks";
    }

    @PostMapping("/tasks")
    public String addTask(@RequestParam String description) {
        taskRepo.save(new Task(description)); // Task constructor sets today's date
        return "redirect:/tasks";
    }

    @GetMapping("/complete/{id}")
    public String markComplete(@PathVariable Long id) {
        Task task = taskRepo.findById(id).orElse(null);
        if (task != null && !task.isCompleted()) {
            task.setCompleted(true);
            taskRepo.save(task);
        }
        return "redirect:/tasks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTask(@PathVariable Long id) {
        taskRepo.deleteById(id);
        return "redirect:/tasks";
    }
}
