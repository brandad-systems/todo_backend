package de.bas.todo_backend;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

public interface TodoJPARepository extends CrudRepository<TodoModel, UUID> {

    List<TodoModel> findByTitle(String title);

}
