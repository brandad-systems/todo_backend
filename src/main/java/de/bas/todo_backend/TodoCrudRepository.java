package de.bas.todo_backend;

import org.springframework.data.repository.CrudRepository;

import java.util.*;

public interface TodoCrudRepository extends CrudRepository<TodoModel, UUID> {

    List<TodoModel> findAll();

}
