package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.dtos.CandidateCreateRequest;
import org.example.models.Candidate;
import org.example.repository.CandidateRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Rollback
public class CandidateControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CandidateRepository candidateRepository;


    @Test
    public void testGetCandidateById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/candidates/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vitalii"));
    }

    @Test
    public void testGetCandidates() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


    @Test
    public void testCreateCandidateInvalidData() throws Exception {
        var request = new CandidateCreateRequest(null, 1L);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/candidates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testDeleteCandidate() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/candidates/2"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/candidates/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}
