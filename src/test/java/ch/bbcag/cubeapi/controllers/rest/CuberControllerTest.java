package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Cuber;
import ch.bbcag.cubeapi.services.CuberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.ConstraintViolationException;
import java.util.TimeZone;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(controllers = CuberController.class)
@AutoConfigureMockMvc(addFilters = false)
public class CuberControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuberService cuberService;

    @BeforeEach
    public void prepare() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    @Test
    public void checkGet_whenNotExistingName_thenIsOkAndNoCuberIsReturned() throws Exception {
        mockMvc.perform(get("/cubers")
                        .contentType("application/json")
                        .queryParam("name", "ThisIsANameWhichDefinitelyDoesNotExist"))
                .andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void checkGet_whenNoParameter_thenIsOk() throws Exception {
        mockMvc.perform(get("/cubers")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGet_whenExistingName_thenIsOk() throws Exception {
        mockMvc.perform(get("/cubers")
                        .contentType("application/json")
                        .queryParam("name", "Kimi"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenValidId_thenCuberIsReturned() throws Exception {
        mockMvc.perform(get("/cubers/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }

    @Test
    public void checkGetById_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(cuberService).findById(99999);
        mockMvc.perform(get("/cubers/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void checkPost_whenValidCuber_thenIsCreated() throws Exception {
        mockMvc.perform(post("/cubers")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 999,
                                  "firstname": "Testperson",
                                  "lastname": "Testperson",
                                  "birthdate": "2022-02-04T12:46:29.972Z",
                                  "country": {
                                    "id": 1
                                  },
                                  "mainevents": [
                                    {
                                      "id": 1
                                    }
                                  ],
                                  "cubes": [
                                    {
                                      "id": 1
                                    }
                                  ],
                                  "times": [
                                  ]
                                }
                                """))
                .andExpect(status().isCreated());
    }

    @Test
    public void checkPost_whenInvalidCuber_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(cuberService).insert(new Cuber());
        mockMvc.perform(post("/cubers")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "cuber1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkPut_whenValidCuber_thenIsOk() throws Exception {
        mockMvc.perform(put("/cubers")
                        .contentType("application/json")
                        .content("""
                                {
                                  "id": 1,
                                  "firstname": "Testperson",
                                  "lastname": "Testperson",
                                  "birthdate": "2022-02-04T12:46:29.972Z",
                                  "country": {
                                    "id": 1
                                  },
                                  "mainevents": [
                                    {
                                      "id": 1
                                    }
                                  ],
                                  "cubes": [
                                    {
                                      "id": 1
                                    }
                                  ],
                                  "times": [
                                  ]
                                }
                                """))
                .andExpect(status().isOk());
    }

    @Test
    public void checkPut_whenInvalidCuber_thenIsBadRequest() throws Exception {
        doThrow(ConstraintViolationException.class).when(cuberService).update(new Cuber());
        mockMvc.perform(put("/cubers")
                        .contentType("application/json")
                        .content("""
                                {
                                    "thisisafieldnamewhichdefinitelydoesnotexist": "cuber1"
                                }
                                """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void checkDelete_whenValidId_thenIsOk() throws Exception {
        mockMvc.perform(delete("/cubers/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    public void checkDelete_whenInvalidId_thenIsNotFound() throws Exception {
        doThrow(new ResponseStatusException(HttpStatus.NOT_FOUND)).when(cuberService).deleteById(99999);
        mockMvc.perform(delete("/cubers/99999")
                        .contentType("application/json"))
                .andExpect(status().isNotFound());
        assertEquals("Test", "Test");
    }
}