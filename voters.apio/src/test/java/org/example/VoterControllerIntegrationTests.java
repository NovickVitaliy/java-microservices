package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.dtos.VoterCreateRequest;
import org.example.dtos.VoterUpdateRequest;
import org.example.models.Voter;
import org.example.repositories.VoterRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
@Rollback
public class VoterControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private VoterRepository voterRepository;

    @Test
    public void testGetVoterById() throws Exception {
        var voter = new Voter(null, "vitalii", "novickvitaliy@gmail.com");
        var saved = voterRepository.save(voter);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/voters/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("vitalii"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("novickvitaliy@gmail.com"));
    }

    @Test
    public void testGetAllVoters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/voters")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateVoter() throws Exception {
        var request = new VoterCreateRequest("vitalii", "novickvitaliy@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("vitalii"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("novickvitaliy@gmail.com"));
    }

    @Test
    public void testCreateVoterInvalidData() throws Exception {
        var request = new VoterCreateRequest("vitalii", "novickvitaliygmail.com");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/voters")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateVoter() throws Exception {
        var voters = new Voter(null, "vitalii", "novickvitaliy@gmail.com");
        var saved = voterRepository.save(voters);

        var request = new VoterUpdateRequest("Vitalii updated", "novickvitaliy@gmail.com");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/voters/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Vitalii updated"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("novickvitaliy@gmail.com"));
    }
}
