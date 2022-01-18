package de.bas.todo_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.util.UUID;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/api/v1/todos")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService todoService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TodoModel>> getTodos() {
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public String createTodo(@RequestBody TodoCreateModel createTodo) {
        return todoService.createTodo(createTodo);
    }

    @PutMapping(path="/{id}", produces = "application/json")
    public TodoModel updateTodo(@PathVariable("id") UUID id, @RequestBody TodoModel updateTodo) {
        return todoService.updateTodo(id, updateTodo);
    }

    @DeleteMapping(path="/{id}")
    public void deleteTodo(@PathVariable("id") UUID id) {
        todoService.deleteTodo(id);
    }

    @PatchMapping(path = "/{id}")
    public TodoModel updateTodoPartially(@PathVariable("id") UUID id, @RequestBody String patchTodo) {
        return todoService.patch(id, patchTodo);
    }
}
