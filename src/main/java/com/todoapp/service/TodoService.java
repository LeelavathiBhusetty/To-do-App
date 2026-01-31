package com.todoapp.service;

import com.todoapp.exception.ResourceNotFoundException;
import com.todoapp.model.Todo;
import com.todoapp.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }

    public Todo getTodoById(String id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Todo not found with id: " + id));
    }

    public List<Todo> getTodosByCompleted(boolean completed) {
        return todoRepository.findByCompleted(completed);
    }

    public List<Todo> searchTodosByTitle(String title) {
        return todoRepository.findByTitleContainingIgnoreCase(title);
    }

    public Todo createTodo(Todo todo) {
        todo.setCreatedAt(LocalDateTime.now());
        todo.setUpdatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    public Todo updateTodo(String id, Todo todoDetails) {
        Todo todo = getTodoById(id);

        todo.setTitle(todoDetails.getTitle());
        todo.setDescription(todoDetails.getDescription());
        todo.setCompleted(todoDetails.isCompleted());
        todo.setUpdatedAt(LocalDateTime.now());

        return todoRepository.save(todo);
    }

    public Todo toggleTodoStatus(String id) {
        Todo todo = getTodoById(id);
        todo.setCompleted(!todo.isCompleted());
        todo.setUpdatedAt(LocalDateTime.now());
        return todoRepository.save(todo);
    }

    public void deleteTodo(String id) {
        Todo todo = getTodoById(id);
        todoRepository.delete(todo);
    }

    public void deleteAllTodos() {
        todoRepository.deleteAll();
    }
}
