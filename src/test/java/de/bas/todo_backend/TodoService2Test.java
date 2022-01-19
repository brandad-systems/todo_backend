package de.bas.todo_backend;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(TodoService2.class)
@Slf4j
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
        //given
        long numberOfTodos = repository.count();
        TodoCreateModel todoRequest = new TodoCreateModel("Test", "Test Content", false);
        //when
        TodoModel todo = todoService.createTodo(todoRequest);
        //then
        assertEquals(repository.count(), numberOfTodos + 1);
        assertTrue(repository.findById(todo.getId()).isPresent());
    }

    @Test
    void upDateTodo() {
        //given
        TodoModel targetTodo = testTodo;
        String test_to_do_not = "test to do not";
        targetTodo.setTitle(test_to_do_not);

        //when
        TodoModel expected = todoService.updateTodo(targetTodo.getId(), targetTodo);

        //then
        assertTrue(expected.getTitle().equals(test_to_do_not));
    }


    @Test
    @DisplayName("fails when id is not available.")
    void whenTodoNotFound_Throw_Exception_UpdateTodo() {

        //given
        TodoModel targetTodo = testTodo;
        String test_to_do_not = "test to do not";
        targetTodo.setTitle(test_to_do_not);

        //then
        assertThrows(IllegalArgumentException.class, () -> todoService.updateTodo(UUID.randomUUID(), targetTodo));
    }

    @Test
    void patchTodo() throws Exception {

        //given
        TodoModel targetTodo = testTodo;
        String test_to_do_not = "test to do not";
        targetTodo.setTitle(test_to_do_not);

        //when
        TodoModel expected = todoService.patch(targetTodo.getId(), "{\"title\":\"change me\"}");

        //then
        assertEquals(expected, targetTodo);

    }

    @Test
    void invalid_json_patchTodo() throws Exception {

        //given
        TodoModel targetTodo = testTodo;
        String test_to_do_not = "test to do not";
        targetTodo.setTitle(test_to_do_not);

        //when
        // TodoModel expected = todoService.patch(targetTodo.getId(), "{\title\":\"change me\"}");

        //then
        assertThrows(Exception.class, () -> {
            todoService.patch(targetTodo.getId(), "{\title\":\"change me\"}");
        });

    }

    @Test
    void invalid_json_property_patchTodo() throws Exception {

        //given
        TodoModel targetTodo = testTodo;
        String test_to_do_not = "test to do not";
        targetTodo.setTitle(test_to_do_not);

        //when
        // TodoModel expected = todoService.patch(targetTodo.getId(), "{\title\":\"change me\"}");

        //then
        Exception exception = assertThrows(Exception.class, () -> {
            todoService.patch(targetTodo.getId(), "{\"title1\":\"change me\"}");
        });
        assertThat(exception.getClass().getName()).isEqualTo(UnrecognizedPropertyException.class.getName());

    }

    @Test
    void deleteTodo() {
        //when
        todoService.deleteTodo(testTodo.getId());
        //then
        assertTrue(repository.findById(testTodo.getId()).isEmpty());
    }

    @Test
    @DisplayName("This test throws an exception when uuid is not found in database.")
    void whenTodoNotFound_Throw_Exception_deleteTodo() {
        assertThrows(IllegalArgumentException.class, () -> todoService.deleteTodo(UUID.randomUUID()));
    }
}