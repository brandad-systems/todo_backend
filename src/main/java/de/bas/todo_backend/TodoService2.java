package de.bas.todo_backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Getter
public class TodoService2 {

    private final TodoRepository2 todoRepo;
    private final ObjectMapper objectMapper;

    public String createTodo(TodoCreateModel createTodo) {
        TodoModel todoModel = new TodoModel();
        todoModel.setContent(createTodo.getContent());
        todoModel.setTitle(createTodo.getTitle());
        todoModel.setDone(createTodo.getDone());

        TodoModel saved = todoRepo.save(todoModel);
        return "{ \"id\" : \"" + saved.getId() + "\"}";
    }

    public TodoModel updateTodo(UUID id, TodoModel updateTodo) throws Exception {
        Optional<TodoModel> todo = todoRepo.findById(id);
        if (todo.isPresent()) {
            todo.get().setTitle(updateTodo.getTitle());
            todo.get().setContent(updateTodo.getContent());
            todo.get().setDone(updateTodo.getDone());
            return todoRepo.save(todo.get());
        } else
            throw new Exception("No class de.bas.todo_backend.TodoModel entity with id="+id);
    }

    public void deleteTodo(UUID id) {
        todoRepo.deleteById(id);
    }


    public TodoModel patch(UUID id, String patchTodo) throws Exception {
        Optional<TodoModel> todoOpt = todoRepo.findById(id);
        ObjectReader todoReader = objectMapper.readerForUpdating(todoOpt.orElse(null));
        TodoModel todo = todoReader.readValue(patchTodo);
        return updateTodo( id, todo );
    }

    public List<TodoModel> getTodos() {
        return  todoRepo.findAll();
    }
}
