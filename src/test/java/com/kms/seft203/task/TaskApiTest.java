package com.kms.seft203.task;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.AppVersionRepository;
import com.kms.seft203.auth.JwtService;
import com.kms.seft203.auth.User;
import com.kms.seft203.auth.UserJwtRepository;
import com.kms.seft203.auth.UserRepository;
import com.kms.seft203.exceptions.DataNotFoundException;
import com.kms.seft203.task.SaveTaskRequest;
import com.kms.seft203.task.Task;
import com.kms.seft203.task.TaskApi;
import com.kms.seft203.task.TaskRepository;
import com.kms.seft203.task.TaskService;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings({"unused", "SpellCheckingInspection"})
@WebMvcTest(controllers = TaskApi.class)
class TaskApiTest {

    @MockBean
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJwtRepository userJwtRepository;

    @MockBean
    private AppVersionRepository appVersionRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final List<Task> tasks = List.of(
            new Task(1L, "Fly to the moon", false, null)
    );

    private final Task task = new Task(32L, "Become hokage", true, null);

    private final SaveTaskRequest saveTaskRequest = new SaveTaskRequest("End the Ninja war", 1L);

    @MockBean
    private SecurityContext securityContext;

    @MockBean
    private Authentication authentication;

    private final User user = new User(
            86L,
            "admin",
            "admin@kms.com",
            "admin",
            StringUtils.EMPTY,
            List.of(),
            List.of()
    );

    @BeforeEach
    void setUp() {

    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJsonList_WhenGetAll() throws Exception {
        when(taskRepository.findAll())
                .thenReturn(tasks);

        when(taskService.getAll())
                .thenReturn(tasks);

        mockMvc.perform(get("/tasks"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(tasks)));
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenGetById() throws Exception {
        when(taskService.getById(task.getId()))
                .thenReturn(task);

        when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.of(task));

        mockMvc.perform(get("/tasks/" + task.getId()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(task)));
    }

    @Test
    @WithMockUser
    void shouldNotFound_WhenGetByNotExistingId() throws Exception {
        var notExistingId = 177013L;

        when(taskService.getById(notExistingId))
                .thenThrow(new DataNotFoundException());

        when(taskRepository.findById(task.getId()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/tasks/" + notExistingId))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldOkAndResponseJson_WhenCreate() throws Exception {
        var createdTask = Task.of(saveTaskRequest, user);

        when(taskService.create(saveTaskRequest, user))
                .thenReturn(createdTask);

        when(taskRepository.save(createdTask))
                .thenReturn(createdTask);

        mockMvc.perform(post("/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveTaskRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(createdTask)));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldOkAndResponseJson_WhenUpdateById() throws Exception {
        var updatedTask = Task.of(saveTaskRequest, user);

        when(taskService.update(updatedTask.getId(), saveTaskRequest, user))
                .thenReturn(updatedTask);

        when(taskRepository.existsById(updatedTask.getId()))
                .thenReturn(true);

        when(taskRepository.save(updatedTask))
                .thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/" + updatedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveTaskRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(updatedTask)));
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldNotFound_WhenUpdateByNotExistingId() throws Exception {
        var updatedTask = Task.of(saveTaskRequest, user);

        when(taskService.update(updatedTask.getId(), saveTaskRequest, user))
                .thenThrow(new DataNotFoundException());

        when(taskRepository.existsById(updatedTask.getId()))
                .thenReturn(false);

        when(taskRepository.save(updatedTask))
                .thenReturn(updatedTask);

        mockMvc.perform(put("/tasks/" + updatedTask.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveTaskRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(username = "admin", password = "admin")
    void shouldResponseNoContent_WhenDeleteById() throws Exception{
        doNothing().when(taskService)
                .delete(task.getId());

        doNothing().when(taskRepository)
                .deleteById(task.getId());

        mockMvc.perform(delete("/tasks/" + task.getId()))
                .andExpect(status().isNoContent());
    }
}