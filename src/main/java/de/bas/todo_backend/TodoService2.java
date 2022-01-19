package de.bas.todo_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class TodoService2 {

    private final TodoCrudRepository repo;

    public List<TodoModel> getTodos() {
        return repo.findAll();
    }

    public void createTodo(TodoCreateModel createTodo) {
        TodoModel todoModel = new TodoModel(createTodo.getTitle(), createTodo.getContent(), createTodo.getDone());
       repo.save(todoModel);
    }

    public TodoModel updateTodo(UUID id, TodoModel updateTodo) {
        TodoModel todoModel;
        Optional<TodoModel> todoModelById = repo.findById(id);
       if( todoModelById.isPresent()){
            todoModel = todoModelById.get();
            todoModel.setContent(updateTodo.getContent());
            todoModel.setDone(updateTodo.getDone());
            todoModel.setTitle(updateTodo.getTitle());
            repo.save(todoModel);
        }else
        {
            throw new IllegalArgumentException();
        };


        return todoModel;
    }

    public void deleteTodo(UUID id) {
        Optional<TodoModel> todoById = repo.findById(id);
        todoById.ifPresentOrElse((value) ->     {
            repo.deleteById(value.getId());
        }, () -> {
            throw new IllegalArgumentException();
        });
    }


    public TodoModel patch(UUID id, String patchTodo) throws Exception {

        TodoModel tobePatchedTodo = repo.findById(id).get();
        ObjectMapper objectMapper = new ObjectMapper();

            ObjectReader todoReader = objectMapper.readerForUpdating(tobePatchedTodo);
            tobePatchedTodo = todoReader.readValue(patchTodo);
            repo.save(tobePatchedTodo);
            return tobePatchedTodo;

    //    return new TodoModel();
    }


}
