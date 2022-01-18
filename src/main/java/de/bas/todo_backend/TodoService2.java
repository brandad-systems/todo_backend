package de.bas.todo_backend;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TodoService2 {

    private final TodoCrudRepository repo;

    public Iterable<TodoModel> getTodos(){
        return repo.findAll();
    }

    public String createTodo(TodoCreateModel createTodo) {
        return repo.save(new TodoModel( createTodo.getTitle(), createTodo.getContent(), createTodo.getDone())).getId().toString();
    }

//    public TodoModel updateTodo(UUID id, TodoModel updateTodo){
//       return repo.updateTodo(id,updateTodo);
//    }
//
//    public void deleteTodo(UUID id){
//        repo.deleteTodo(id);
//    }
//
//
//    public TodoModel patch(UUID id, String patchTodo) {
//        return repo.patch(id, patchTodo);
//    }
}
