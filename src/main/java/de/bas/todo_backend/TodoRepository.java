package de.bas.todo_backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private List<TodoModel> todos = new ArrayList<>(Arrays.asList(temp, temp2));

    public List<TodoModel> getTodos() {
        return todos;
    }

    public String createTodo(TodoCreateModel createTodo) {
        TodoModel todo = new TodoModel(UUID.randomUUID(), createTodo.getTitle(), createTodo.getContent(), createTodo.getDone());
        todos.add(todo);
        return todo.getId().toString();
    }

    public TodoModel updateTodo(UUID id, TodoModel updateTodo) {
        for (int i = 0; i < todos.size(); i++) {
            TodoModel todo = todos.get(i);
            if (todo.getId().equals(id)) {
                todos.set(i, updateTodo);
                return updateTodo;
            }
        }
        return null;
    }

    public void deleteTodo(UUID id) {
        for (int i = 0; i < todos.size(); i++) {
            TodoModel todo = todos.get(i);
            if (todo.getId().equals(id)) {
                todos.remove(i);
            }
        }
    }


    public TodoModel patch(UUID id, String patchTodo) {
        ObjectMapper objectMapper = new ObjectMapper();
        TodoCreateModel todo = new TodoCreateModel();
        try {
            todo = objectMapper.readValue(patchTodo,TodoCreateModel.class);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
        }
        logger.info(todo.toString());
        return null;
    }
}
