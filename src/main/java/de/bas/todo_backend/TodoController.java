package de.bas.todo_backend;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<TodoModel>> getTodos() {
        TodoModel temp = new TodoModel(UUID.randomUUID(), "Todo", "Todo erledigen", false);
        TodoModel temp2 = new TodoModel(UUID.randomUUID(), "Todo2", "Todo2 erledigen", false);

        return new ResponseEntity(Arrays.asList(temp, temp2), HttpStatus.ACCEPTED);
    }



}
