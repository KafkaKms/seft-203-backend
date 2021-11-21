package com.kms.seft203.dashboard;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.AppVersionRepository;
import com.kms.seft203.auth.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@WebMvcTest(controllers = DashboardApi.class)
class DashboardApiTest {

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private UserJwtRepository userJwtRepository;

    @MockBean
    private AppVersionRepository appVersionRepository;

    @MockBean
    private DashboardService dashboardService;

    @MockBean
    private DashboardRepository dashboardRepository;

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final User user = new User(0L, "user", "email", "password", null, null, null);

    private List<Dashboard> dashboards;

    private final SaveDashboardRequest saveDashboardRequest = new SaveDashboardRequest("Pie", "Grid", null);

    @BeforeEach
    public void beforeAll() {
        dashboards = List.of(
                new Dashboard(0L, user, "Main Board", "Vertical", null),
                new Dashboard(1L, user, "Chart", "Horizontal", null)
        );
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenGetAll() throws Exception {
        when(dashboardRepository.getAllByUserId(user.getId()))
                .thenReturn(dashboards);

        when(dashboardService.getAllByUserId(user.getId()))
                .thenReturn(dashboards);

        mockMvc.perform(get("/dashboards"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dashboards)));
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenCreate() throws Exception {
        var dashboard = Dashboard.of(saveDashboardRequest, user);

        when(dashboardRepository.save(dashboard))
                .thenReturn(dashboard);

        when(dashboardService.create(saveDashboardRequest, user))
                .thenReturn(dashboard);

        mockMvc.perform(post("/dashboards")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveDashboardRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dashboard)));
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenUpdate() throws Exception{
        var id = 1L;

        when(dashboardRepository.existsById(id))
                .thenReturn(true);

        var dashboard = Dashboard.of(saveDashboardRequest, user);

        when(dashboardRepository.save(dashboard))
                .thenReturn(dashboard);

        when(dashboardService.update(id, saveDashboardRequest, user))
                .thenReturn(dashboard);

        mockMvc.perform(put("/dashboards/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveDashboardRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(dashboard)));
    }
}