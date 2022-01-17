package de.bas.todo_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TodoController.class})
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService todoService;

    @Test
    public void validGetReturns200andCallsServiceOnceAndReturnsTodos() throws Exception {
        //given
        TodoModel todo1 = new TodoModel(UUID.randomUUID(), "todo1", "test", false);
        List<TodoModel> todoList = new ArrayList<>(Arrays.asList(todo1));
        when(todoService.getTodos()).thenReturn(todoList);

        //when
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoList)));
        verify(todoService, times(1)).getTodos();
    }

    @Test
    public void validPost_return200() throws Exception{

        //given
        TodoCreateModel todo1 = new TodoCreateModel("todo1", "test", false);
        // when
        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo1)))
                        .andExpect(status().isOk());


    }


}
