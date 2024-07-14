package org.example.book_service.controller;

import org.example.book_service.entity.Ingredient;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class IngredientCatalogControllerIntegrationTest {
    private  String url="http://localhost:8080/api";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
    }

    @Test
    void testCreateIngredient() throws Exception {
        Ingredient ingredient = new Ingredient();
        ingredient.setName("Test Ingredient");

        mockMvc.perform(post((url+"/Ingredient"))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(ingredient)))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("Test Ingredient"));
    }

    @Test
    void testGetAllIngredients() throws Exception {
        mockMvc.perform(get(url+"/Ingredient")
                        .contentType(MediaType.APPLICATION_JSON))

                .andExpect(status().is(200));
                //.andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetIngredientById() throws Exception {

        mockMvc.perform(get(url+"/Ingredient/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("sugar"));
    }
}


