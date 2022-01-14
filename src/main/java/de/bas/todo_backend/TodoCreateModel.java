package de.bas.todo_backend;

import lombok.Data;

@Data
public class TodoCreateModel {
    private String title;
    private String content;
    private Boolean done;
}
