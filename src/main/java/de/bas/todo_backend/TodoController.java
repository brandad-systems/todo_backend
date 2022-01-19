package de.bas.todo_backend;

import com.fasterxml.jackson.core.JsonProcessingException;
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

    private final TodoService2 todoService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TodoModel>> getTodos() {
        return new ResponseEntity<>(todoService.getTodos(), HttpStatus.OK);
    }

    @PostMapping(produces = "application/json")
    public void createTodo(@RequestBody TodoCreateModel createTodo) throws JsonProcessingException {
        todoService.createTodo(createTodo);
    }

    @PutMapping(path="/{id}", produces = "application/json")
    public TodoModel updateTodo(@PathVariable("id") UUID id, @RequestBody TodoModel updateTodo) {
        return todoService.updateTodo(id, updateTodo);
    }

    @DeleteMapping(path="/{id}")
    public void deleteTodo(@PathVariable("id") UUID id) {
        todoService.deleteTodo(id);
    }

    @PatchMapping(path = "/{id}", consumes = "text/plain")
    public TodoModel updateTodoPartially(@PathVariable("id") UUID id, @RequestBody String patchTodo) throws Exception {
        return todoService.patch(id, patchTodo);
    }
}
