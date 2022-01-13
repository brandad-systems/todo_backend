package de.bas.todo_backend;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class TodoModel {
    private UUID id;
    private String title;
    private String content;
    private boolean done;
}
