package com.kms.seft203;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AppApi.class)
class AppApiTests {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AppVersionRepository appVersionRepo;

	// NOTE: intentionally failed test
	@Test
	void testGetCurrentVersion() throws Exception {
		this.mockMvc.perform(get("/app/version"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.name").value("SEFT Program"));
	}
}
