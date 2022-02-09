package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Country;
import ch.bbcag.cubeapi.services.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private CountryService countryService;

    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a country by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Country.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No country with this id found.")
    })
    public Country findById(@PathVariable Integer id) {
        return countryService.findById(id);
    }


    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing country")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Country successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This country does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        countryService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new country.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Country successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the country"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new country")
    })
    public void insert(@RequestBody @Valid Country country) {
        countryService.insert(country);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing country.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Country successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the country"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the country")
    })
    public void update(@RequestBody @Valid Country country) {
        countryService.update(country);
    }
}