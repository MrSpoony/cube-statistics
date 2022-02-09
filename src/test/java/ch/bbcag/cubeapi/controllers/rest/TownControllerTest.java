package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Town;
import ch.bbcag.cubeapi.services.TownService;
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
import java.util.TimeZone;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(controllers = TownController.class)
@AutoConfigureMockMvc(addFilters = false)
class TownControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TownService townService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGetById_whenValidId_thenCuberIsReturned() throws Exception {
        mockMvc.perform(get("/towns/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(townService).findById(99999);
        mockMvc.perform(get("/towns/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidTown_thenIsCreated() throws Exception {
        mockMvc.perform(post("/towns")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 999,
                                  "name": "bern",
                                  "postcode": 3000,
                                  "country": {
                                    "id": 1
                                  }
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidTown_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(townService).insert(new Town());
        mockMvc.perform(post("/towns")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "town1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPut_whenValidTown_thenIsOk() throws Exception {
        mockMvc.perform(put("/towns")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 999,
                                  "name": "bern",
                                  "postcode": 3000,
                                  "country": {
                                    "id": 1
                                  }
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidTown_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(townService).update(new Town());
        mockMvc.perform(put("/towns")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "town1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/towns/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete_whenValidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(townService).deleteById(99999);
        mockMvc.perform(delete("/towns/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}