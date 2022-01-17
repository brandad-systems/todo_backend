package de.bas.todo_backend;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;



@DataJpaTest
@Import({TodoService2.class})

@Slf4j
public class TodoService2Test {


    @Autowired
    private TodoRepository2 todoRepo;

    @Autowired
    private TodoService2 todoService2 ;
    TodoModel todo;
    TodoModel todo2;
    private TodoModel savedTodo2;
    private TodoModel savedTodo;
    private String mycontent3;
    private String myTitle3;



    @BeforeEach
    public void setup() {
        todo = new TodoModel();
        todo.setTitle("test");
        todo.setContent("mycontent");
        todo.setDone(false);
        savedTodo = todoRepo.save(todo);
        todo2 = new TodoModel();
        todo2.setTitle("test2");
        todo2.setContent("mycontent");
        savedTodo2 = todoRepo.save(todo2);
         mycontent3 = "mycontent3";
         myTitle3= "title3";
    }

    @Test
    public void testJPARepoFindByLastName() {
        long count = todoRepo.count();
        assertThat(count).isEqualTo(2L);

        List<TodoModel> findByLastName = todoRepo.findByTitle(todo.getTitle());
        log.info("element title: "+findByLastName.get(0).getTitle());
        findByLastName.forEach(e -> log.info("element title: "+e.getTitle()));
        assertThat(findByLastName).extracting(TodoModel::getTitle).containsOnly(todo.getTitle());
    }

    @Test
    public void createTodo(){
        assertThat(todoService2).isNotNull();
        assertThat(todoService2.getTodoRepo()).isNotNull();
       
        TodoCreateModel createModel = new TodoCreateModel(myTitle3,mycontent3,false);
        String resultUUID = todoService2.createTodo(createModel);

        Iterable<TodoModel> todos = todoRepo.findByTitle("test3");
        todos.forEach(savedTodo -> log.info("savedTodo:"+savedTodo.toString()));
        todos.forEach(savedTodo -> assertThat(savedTodo.getContent()).isEqualTo((mycontent3)));
    }
    @Test
    public void update(){
        //arrange
        Iterable<TodoModel> todos = todoRepo.findByTitle(todo.getTitle());
        TodoModel mytodo = todos.iterator().next();
        mytodo.setTitle(myTitle3);
        mytodo.setContent(mycontent3);
        //act
        todoService2.updateTodo(mytodo.getId(),mytodo);
        //assert
        TodoModel checkTodo = todoRepo.findByTitle(todo.getTitle()).iterator().next();
        assertThat( checkTodo.getTitle()).isEqualTo(myTitle3);
        assertThat( checkTodo.getContent()).isEqualTo(mycontent3);

    }
    @Test
    public void delete(){
        Iterable<TodoModel> todos = todoRepo.findByTitle(todo.getTitle());
        TodoModel mytodo = todos.iterator().next();
        //act
        todoService2.deleteTodo(mytodo.getId());
        //assert
        assertThat( todoRepo.findByTitle(todo.getTitle()).iterator().hasNext()).isFalse();

    }
    @Test
    public void patch() throws JsonProcessingException {

        //arrange
        Iterable<TodoModel> todos = todoRepo.findByTitle(todo.getTitle());
        TodoModel mytodo = todos.iterator().next();
        String patch ="{ \"title\":\"othertitle\"}";
        //act
        todoService2.patch(mytodo.getId(),patch);
        //assert
        TodoModel checkTodo = todoRepo.findByTitle(todo.getTitle()).iterator().next();
        assertThat( checkTodo.getTitle()).isEqualTo("othertitle");
        assertThat( checkTodo.getContent()).isEqualTo(todo.getContent());

    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public ObjectMapper getObjectMapper() {
            return new ObjectMapper();
        }
    }
}

