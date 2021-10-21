package com.kms.seft203;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kms.seft203.auth.JwtService;
import com.kms.seft203.auth.UserJwtRepository;
import com.kms.seft203.auth.UserRepository;
import com.kms.seft203.contact.Contact;
import com.kms.seft203.contact.ContactApi;
import com.kms.seft203.contact.ContactRepository;
import com.kms.seft203.contact.ContactService;
import com.kms.seft203.contact.SaveContactRequest;
import com.kms.seft203.exceptions.DataNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
@WebMvcTest(controllers = ContactApi.class)
class ContactApiTest {

    @MockBean
    private ContactService contactService;

    @MockBean
    private ContactRepository contactRepository;

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

    private final Contact contact = new Contact(0L,
            "Kafka",
            "Wanna Fly",
            "SE",
            "APAC",
            "SLA",
            "avatar",
            0);
    private final List<Contact> contacts = List.of(
            new Contact(0L,
                    "Kafka",
                    "Wanna Fly",
                    "SE",
                    "APAC",
                    "SLA",
                    "avatar",
                    0)
    );
    private final SaveContactRequest saveContactRequest = new SaveContactRequest(
            "Lord",
            "Yutoba",
            "SSE",
            "APAC",
            "HS",
            "ava",
            1);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {

    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJsonList_WhenGetAll() throws Exception {
        when(contactService.getAll())
                .thenReturn(contacts);

        mockMvc.perform(get("/contacts"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJSONString(contacts)));
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenGetById() throws Exception {
        when(contactRepository.findById(0L))
                .thenReturn(Optional.of(contact));

        when(contactService.findById(0L))
                .thenReturn(contact);

        mockMvc.perform(get("/contacts/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJSONString(contact)));
    }

    @Test
    @WithMockUser
    void shouldNotFound_WhenGetByNotExistingId() throws Exception {
        when(contactRepository.findById(1L))
                .thenReturn(Optional.empty());

        when(contactService.findById(1L))
                .thenThrow(new DataNotFoundException());

        mockMvc.perform(get("/contacts/1"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenCreate() throws Exception {
        var createContact = Contact.of(saveContactRequest);

        when(contactRepository.save(createContact))
                .thenReturn(createContact);

        when(contactService.create(saveContactRequest))
                .thenReturn(createContact);

        mockMvc.perform(post("/contacts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSONString(saveContactRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJSONString(createContact)));
    }

    @Test
    @WithMockUser
    void shouldOkAndResponseJson_WhenUpdateById() throws Exception {
        var updatedContact = Contact.of(saveContactRequest);

        when(contactRepository.existsById(updatedContact.getId()))
                .thenReturn(true);

        when(contactService.update(updatedContact.getId(), saveContactRequest))
                .thenReturn(updatedContact);

        mockMvc.perform(put("/contacts/" + updatedContact.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSONString(saveContactRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().json(toJSONString(updatedContact)));
    }

    @Test
    @WithMockUser
    void shouldNotFound_WhenUpdateByNotExistingId() throws Exception {
        var notExistingId = 177013L;

        when(contactRepository.existsById(notExistingId))
                .thenReturn(false);

        when(contactService.update(notExistingId, saveContactRequest))
                .thenThrow(new DataNotFoundException());

        mockMvc.perform(put("/contacts/" + notExistingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSONString(saveContactRequest)))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser
    void shouldResponseNoContent_WhenDeleteContact() throws Exception {
        var deletedContact = Contact.of(saveContactRequest);

        doNothing().when(contactRepository)
                .deleteById(deletedContact.getId());

        doNothing().when(contactService)
                .delete(deletedContact.getId());

        mockMvc.perform(delete("/contacts/" + deletedContact.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJSONString(saveContactRequest)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private String toJSONString(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }
}