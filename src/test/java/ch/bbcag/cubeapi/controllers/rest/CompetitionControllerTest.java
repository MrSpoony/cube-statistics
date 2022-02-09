package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Competition;
import ch.bbcag.cubeapi.services.CompetitionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.TimeZone;

import static ch.bbcag.cubeapi.utils.TestData.getCompetitionTestData;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = CompetitionController.class)
@AutoConfigureMockMvc(addFilters = false)
class CompetitionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CompetitionService competitionService;

    @Autowired
    private ObjectMapper objectMapper;


    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNotExistingName_thenIsOkAndNoCuberIsReturned() throws Exception {
        mockMvc.perform(get("/competitions")
                        .contentType("application/json")
                        .queryParam("name", "ThisIsANameWhichDefinitelyDoesNotExist"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void checkGet_whenNoParameter_thenIsOkAndCompetitionsAreReturned() throws Exception {
        List<Competition> competitions = getCompetitionTestData();
        String competitionsString = objectMapper.writeValueAsString(competitions);

        doReturn(competitions).when(competitionService).findCompetitions(null,null,null);
        mockMvc.perform(get("/competitions")
                        .contentType("application/json"))
                .andExpect(status().isOk()).andExpect(content().string(competitionsString));
    }

    @Test
    public void checkGet_whenExistingName_thenIsOk() throws Exception {
        mockMvc.perform(get("/competitions")
                        .contentType("application/json")
                        .queryParam("name", "Sunday"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenValidId_thenCuberIsReturned() throws Exception {
        mockMvc.perform(get("/competitions/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(competitionService).findById(99999);
        mockMvc.perform(get("/competitions/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidCompetition_thenIsCreated() throws Exception {
        mockMvc.perform(post("/competitions")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 999,
                                  "name": "LocalComp1",
                                  "date": "2022-02-09T09:03:09.222Z",
                                  "location": {
                                    "id": 1
                                  }
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidCompetition_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(competitionService).insert(new Competition());
        mockMvc.perform(post("/competitions")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "competition1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPut_whenValidCompetition_thenIsOk() throws Exception {
        mockMvc.perform(put("/competitions")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 1,
                                  "name": "LocalComp1",
                                  "date": "2022-02-09T09:03:09.222Z",
                                  "location": {
                                    "id": 1
                                  }
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidCompetition_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(competitionService).update(new Competition());
        mockMvc.perform(put("/competitions")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "competition1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/competitions/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete_whenValidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(competitionService).deleteById(99999);
        mockMvc.perform(delete("/competitions/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}