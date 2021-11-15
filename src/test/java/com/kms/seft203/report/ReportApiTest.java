package com.kms.seft203.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.AppVersionRepository;
import com.kms.seft203.auth.JwtService;
import com.kms.seft203.auth.User;
import com.kms.seft203.auth.UserJwtRepository;
import com.kms.seft203.auth.UserRepository;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactRepository;
import com.kms.seft203.contact.ContactService;
import com.kms.seft203.report.ReportApi;
import com.kms.seft203.report.ReportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("unused")
@WebMvcTest(controllers = ReportApi.class)
class ReportApiTest {
    @MockBean
    private ContactService contactService;

    @MockBean
    private ContactRepository contactRepository;

    @MockBean
    private ReportService reportService;

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

    private final User user = new User(0L, "user", "email", "password", null, null, null);

    private final List<Contact> contacts = List.of(
            new Contact(0L, "Peter", "Parker", "SE", "Avenger", "No Way Home", null, null),
            new Contact(1L, "Shang", "Chi", "SE", "Avenger", "Ten Rings", null, null),
            new Contact(2L, "Nick", "Fury", "SSE", "SHIELD", "Marvel", null, null)
    );


    @WithMockUser
    @Test
    void shouldOkAndResponseJson_WhenCountByFirstNameInContact() throws Exception {
        when(contactRepository.findAll())
                .thenReturn(contacts);

        var collectionName = "contact";
        var fieldName = "firstname";

        var expectedResult = Map.of(
                "Peter", 1,
                "Shang", 1,
                "Nick", 1
        );

        when(reportService.countFieldsOfCollection(collectionName, fieldName, user))
                .thenReturn(expectedResult);

        mockMvc.perform(get("/reports/_countBy/" + collectionName + "/" + fieldName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser
    @Test
    void shouldOkAndResponseJson_WhenCountByLastNameInContact() throws Exception {
        when(contactRepository.findAll())
                .thenReturn(contacts);

        var collectionName = "contact";
        var fieldName = "lastname";

        var expectedResult = Map.of(
                "Parker", 1,
                "Chi", 1,
                "Fury", 1
        );

        when(reportService.countFieldsOfCollection(collectionName, fieldName, user))
                .thenReturn(expectedResult);

        mockMvc.perform(get("/reports/_countBy/" + collectionName + "/" + fieldName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

    @WithMockUser
    @Test
    void shouldOkAndResponseJson_WhenCountByTitleInContact() throws Exception {
        when(contactRepository.findAll())
                .thenReturn(contacts);

        var collectionName = "contact";
        var fieldName = "title";

        var expectedResult = Map.of(
                "SE", 2,
                "SSE", 1
        );

        when(reportService.countFieldsOfCollection(collectionName, fieldName, user))
                .thenReturn(expectedResult);

        mockMvc.perform(get("/reports/_countBy/" + collectionName + "/" + fieldName))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
    }

}
