package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Country;
import ch.bbcag.cubeapi.services.CountryService;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CountryController.class)
@AutoConfigureMockMvc(addFilters = false)
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGetById_whenValidId_thenCuberIsReturned() throws Exception {
        mockMvc.perform(get("/countries/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(countryService).findById(99999);
        mockMvc.perform(get("/countries/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidCountry_thenIsCreated() throws Exception {
        mockMvc.perform(post("/countries")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 999,
                                  "name": "germany"
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidCountry_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(countryService).insert(new Country());
        mockMvc.perform(post("/countries")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "country1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPut_whenValidCountry_thenIsOk() throws Exception {
        mockMvc.perform(put("/countries")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 1,
                                  "name": "germany"
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidCountry_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(countryService).update(new Country());
        mockMvc.perform(put("/countries")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "country1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/countries/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkDelete_whenValidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(countryService).deleteById(99999);
        mockMvc.perform(delete("/countries/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}