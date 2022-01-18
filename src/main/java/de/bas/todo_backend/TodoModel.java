package de.bas.todo_backend;

import lombok.AllArgsConstructor;
import lombok.Data;


import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
public class TodoModel {

    @Id
    @GeneratedValue
    private UUID id;

    private String title;

    private String content;

    private Boolean done;

    public TodoModel(String title, String content, Boolean done) {
        this.title = title;
        this.content = content;
        this.done = done;
    }

    public TodoModel() {

    }
}
