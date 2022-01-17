package de.bas.todo_backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Repository
public class TodoRepository {

    Logger logger = LoggerFactory.getLogger(TodoRepository.class);

    TodoModel temp = new TodoModel(UUID.randomUUID(), "Todo", "Todo erledigen", false);
    TodoModel temp2 = new TodoModel(UUID.fromString("c0b18a3a-c923-4ea0-9782-87e6062ad4c8"), "Todo2", "Todo2 erledigen", false);
    private final List<TodoModel> todos = new ArrayList<>(Arrays.asList(temp, temp2));

    public List<TodoModel> getTodos() {
        return todos;
    }

    public String createTodo(TodoCreateModel createTodo) {
        TodoModel todo = new TodoModel(UUID.randomUUID(), createTodo.getTitle(), createTodo.getContent(), createTodo.getDone());
        todos.add(todo);
        return todo.getId().toString();
    }

    public TodoModel updateTodo(UUID id, TodoModel updateTodo) {

        todos.forEach((todo) -> {
            if (todo.getId().equals(id)) {
                todo.setTitle(updateTodo.getTitle());
                todo.setContent(updateTodo.getContent());
                todo.setDone(updateTodo.getDone());
            }
        });
        return null;
    }

    public void deleteTodo(UUID id) {

            todos.removeIf( todo -> todo.getId().equals(id));
    }


    public TodoModel patch(UUID id, String patchTodo) {
        ObjectMapper objectMapper = new ObjectMapper();

        TodoModel todo = todos.stream()
                .filter(todoObj -> todoObj.getId().equals(id))
                .peek(v -> logger.info("peek:",v))
                .findFirst().get();

        try {
            ObjectReader todoReader = objectMapper.readerForUpdating(todo);
            todo = todoReader.readValue(patchTodo);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        logger.info(todo.toString());
        return null;
    }
}
