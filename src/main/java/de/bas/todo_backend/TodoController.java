package de.bas.todo_backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/v1/todos")
public class TodoController {

    @GetMapping
    public List<String> getTodos()
    {
        return Arrays.asList("xxx","yyy");
    }

}
