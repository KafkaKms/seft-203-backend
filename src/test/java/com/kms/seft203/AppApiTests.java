package com.kms.seft203;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class AppApiTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AppVersionRepository appVersionRepo;

    // NOTE: intentionally failed test
    @Test
    void testGetCurrentVersion() throws Exception {
        when(appVersionRepo.findById(1L).orElse(null))
                .thenReturn(new AppVersion(1L, "SEFT Program", "1.0"));

        this.mockMvc.perform(get("/version"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("SEFT Program"));
    }
}
