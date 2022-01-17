package de.bas.todo_backend;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoCreateModel {
    private String title;
    private String content;
    private Boolean done;
}
