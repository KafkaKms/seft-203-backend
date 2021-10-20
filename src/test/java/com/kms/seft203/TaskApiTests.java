package com.kms.seft203;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.task.SaveTaskRequest;
import com.kms.seft203.task.Task;
import com.kms.seft203.task.TaskRepository;
import com.kms.seft203.task.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class TaskApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private TaskService taskService;

    @Test
    void shouldOkAndResponseJsonList_WhenGetAll() throws Exception {
        var tasks = List.of(new Task(1L, "Done seft", true, null));

        when(taskRepository.findAll())
                .thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJSONString(tasks)));
    }

    @Test
    void shouldOkAndResponseJson_WhenGetById() throws Exception {
        var task = new Task(1L, "Done seft", true, null);

        when(taskRepository.findById(1L).orElse(null))
                .thenReturn(task);

        mockMvc.perform(get("/tasks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJSONString(task)));
    }

    @Test
    void shouldOkAndResponseJson_WhenCreateTask() throws Exception {
        var saveTaskRequest = new SaveTaskRequest("Fly me to the moon", 1L);
        var task = Task.of(saveTaskRequest, null);

        when(taskRepository.saveAndFlush(task))
                .thenReturn(task);

        mockMvc.perform(post("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJSONString(task)));
    }

    @Test
    void shouldOkAndResponseJson_WhenUpdateTask() throws Exception {
        var saveTaskRequest = new SaveTaskRequest("Hey Jude, don't make it bad", 1L);
        var task = Task.of(saveTaskRequest, null);

        when(taskService.update(1L, saveTaskRequest, null))
                .thenReturn(task);

        mockMvc.perform(put("/tasks/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(toJSONString(task)));
    }

    @Test
    void shouldResponseNoContent_WhenDeleteTask() throws Exception {
        Long id = 1L;

        var spy = Mockito.spy(taskRepository);
        doNothing().when(spy)
                .deleteById(id);

        mockMvc.perform(delete("/tasks/1"))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldResponseNotFound_WhenFindById() throws Exception {
        var task = new Task(1L, "Done seft", true, null);

        when(taskRepository.findById(1L).orElse(null))
                .thenReturn(task);

        mockMvc.perform(get("/tasks/177013"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldResponseNotFound_WhenUpdateTask() throws Exception {
        var saveTaskRequest = new SaveTaskRequest("Hey Jude, don't make it bad", 1L);
        var task = Task.of(saveTaskRequest, null);

        when(taskService.update(1L, saveTaskRequest, null))
                .thenReturn(task);

        mockMvc.perform(put("/tasks/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }


    private String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        return om.writeValueAsString(obj);
    }
}
