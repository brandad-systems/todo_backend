package de.bas.todo_backend;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {TodoController.class})
public class TodoControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private TodoService2 todoService;

    private final TodoModel testTodo = new TodoModel(UUID.randomUUID(), "todo1", "test", false);

    @Test
    public void validGetReturns200andCallsServiceOnceAndReturnsTodos() throws Exception {
        //given
        List<TodoModel> todoList = new ArrayList<>(Arrays.asList(testTodo));
        when(todoService.getTodos()).thenReturn(todoList);

        //when
        mockMvc.perform(get("/api/v1/todos"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(todoList)));
        verify(todoService, times(1)).getTodos();
    }

    @Test
    public void validPost_return200AndIdAndCallsServiceOnce() throws Exception{

        //given
        TodoCreateModel todo1 = new TodoCreateModel("todo1", "test", false);
        UUID id = UUID.randomUUID();
        when(todoService.createTodo(todo1)).thenReturn(id.toString());
        // when
        mockMvc.perform(post("/api/v1/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(todo1)))
                        .andExpect(status().isOk())
                        .andExpect(content().string(id.toString()));

        verify(todoService, times(1)).createTodo(todo1);
    }

    @Test
    public void validPut_return200AndUpdatedTodoAndCallsServiceOnce() throws  Exception {
        //given
        when(todoService.updateTodo(testTodo.getId(), testTodo)).thenReturn(testTodo);

        //when
        mockMvc.perform(put("/api/v1/todos/" + testTodo.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(testTodo)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testTodo)));

        verify(todoService, times(1)).updateTodo(testTodo.getId(), testTodo);
    }

    @Test
    public void validDelete_return200AndCallsServiceOnce() throws Exception {

        mockMvc.perform(delete("/api/v1/todos/" + testTodo.getId()))
                .andExpect(status().isOk());

        verify(todoService, times(1)).deleteTodo(testTodo.getId());
    }

    @Test
    public void validPatch_return200AndUpdatedTodoAndCallsServiceOnce() throws Exception {
        //given
        String partialTodo = "{ \"done\": \"true\" }";
        testTodo.setDone(true);
        when(todoService.patch(testTodo.getId(), partialTodo)).thenReturn(testTodo);

        //when
        mockMvc.perform(patch("/api/v1/todos/" + testTodo.getId())
                        .contentType(MediaType.TEXT_PLAIN)
                        .content(partialTodo))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(testTodo)));

        verify(todoService, times(1)).patch(testTodo.getId(), partialTodo);
    }

}
