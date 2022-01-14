package de.bas.todo_backend;

import com.github.fge.jsonpatch.JsonPatch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository repo;

    public List<TodoModel> getTodos(){
        return repo.getTodos();
    }

    public String createTodo(TodoCreateModel createTodo){
        return repo.createTodo(createTodo);
    }

    public TodoModel updateTodo(UUID id, TodoModel updateTodo){
       return repo.updateTodo(id,updateTodo);
    }

    public void deleteTodo(UUID id){
        repo.deleteTodo(id);
    }


    public TodoModel patch(UUID id, String patchTodo) {
        return repo.patch(id, patchTodo);
    }
}
