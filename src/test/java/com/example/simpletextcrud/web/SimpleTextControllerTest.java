package com.example.simpletextcrud.web;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.example.simpletextcrud.domain.dto.GetSimpleTextDTO;
import com.example.simpletextcrud.domain.dto.SaveSimpleTextDTO;
import com.example.simpletextcrud.service.SimpleTextService;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(SimpleTextController.class)
@ContextConfiguration(classes = {SimpleTextController.class, DefaultExceptionHandler.class, ErrorAttributeOptions.class})
@DirtiesContext
class SimpleTextControllerTest {
  @Autowired
  private MockMvc mockMvc;
  @MockBean
  private SimpleTextService service;

  private final ObjectMapper objectMapper = new ObjectMapper();

  @Test
  void shouldReturn201WhenSaveSimpleText() throws Exception {
    when(service.save(any(SaveSimpleTextDTO.class))).thenReturn(new GetSimpleTextDTO("VGYEyAIOsIzTAjvdYfzvEA==", "Magic " +
        "text in api test."));

    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO("Magic text in api test."))))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(jsonPath("$.id", notNullValue()))
        .andExpect(jsonPath("$.magicText", is("Magic text in api test.")));
  }

  @Test
  void shouldReturn400WhenSaveInvalidSimpleText() throws Exception {
    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO(null))))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO(""))))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());

    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO("In today's dynamic world, technological advancements are " +
                "reshaping industries and societies. The fusion of AI, biotech, and clean energy propels us into uncharted territories. " +
                "It's vital to stay curious, learn continuously, and collaborate for a future that's not just innovative, but also harmonious " +
                "and equitable."))))
        .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  void shouldReturn405WhenSaveExistentSimpleText() throws Exception {
    when(service.save(any(SaveSimpleTextDTO.class))).thenThrow(new DataIntegrityViolationException("Entity already exists with such " +
        "magic text."));
    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO("Magic text in api test."))))
        .andExpect(MockMvcResultMatchers.status().isMethodNotAllowed());
  }

  @Test
  void shouldReturn500WhenSaveSimpleText() throws Exception {
    when(service.save(any(SaveSimpleTextDTO.class))).thenThrow(HibernateException.class);
    mockMvc.perform(post("/api/v1/texts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(new SaveSimpleTextDTO("Magic text in api test."))))
        .andExpect(MockMvcResultMatchers.status().isInternalServerError());
  }
}
