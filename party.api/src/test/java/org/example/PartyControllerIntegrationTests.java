package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.dtos.PartyCreateRequest;
import org.example.dtos.PartyUpdateRequest;
import org.example.models.Party;
import org.example.repositories.PartyRepository;
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
public class PartyControllerIntegrationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PartyRepository partyRepository;

    @Test
    public void testGetElectionsById() throws Exception {
        var elections = new Party(null, "Party");
        var saved = partyRepository.save(elections);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Party"));
    }

    @Test
    public void testGetAllElections() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testCreateElections() throws Exception {
        var request = new PartyCreateRequest("Party");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Party"));
    }

    @Test
    public void testCreateElectionsInvalidData() throws Exception {
        var request = new PartyCreateRequest(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/parties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void testUpdateCandidate() throws Exception {
        var elections = new Party(null, "Party");
        var saved = partyRepository.save(elections);

        var request = new PartyUpdateRequest("Party updated");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/parties/" + saved.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(saved.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Party updated"));
    }


    @Test
    public void testDeleteElections() throws Exception {
        var elections = new Party(null, "Some Party");
        var saved = partyRepository.save(elections);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/parties/" + saved.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/parties/999"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

}
