package de.bas.todo_backend;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TodoService2.class)
class TodoService2Test {
    @Autowired
    TodoService2 todoService;

    @Autowired
    TodoCrudRepository repository;

    private TodoModel testTodo = new TodoModel("todo1", "test", false);

    @BeforeEach
    void setupTestEnvironment() {
        testTodo = repository.save(testTodo);
    }

    @Test
    void getTodos() {
        //given

        //when
        Iterable<TodoModel> todos = todoService.getTodos();
        //then
        TodoModel todo = todos.iterator().next();
        assertEquals(todo, testTodo);
    }

    @Test
    void createTodo() {
        fail();
    }
}