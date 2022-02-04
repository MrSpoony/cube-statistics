package ch.bbcag.cubeapi.controllers;

import ch.bbcag.cubeapi.models.Competition;
import ch.bbcag.cubeapi.services.CompetitionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/competitions")
public class CompetitionController {

    @Autowired
    private CompetitionService competitionService;

    public CompetitionController(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a competition by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competition found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Competition.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No competition with this id found.")
    })
    public Competition findById(@PathVariable Integer id) {
        return competitionService.findById(id);
    }


    @GetMapping
    @Operation(summary = """
            Find Competitions by their name
            and/or their cubers that competed,
            and/or their country.
            Use multiple arguments are to specify your search.
            If no argument is given returns all Competitions.""")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Competition(s) found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Competition.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No cuber found with given Argument(s)")
    })
    public Iterable<Competition> findByName(
            @Parameter(description = """
                    Name is seperated by space character
                    if there is no space in the name, search starts for first- and lastnames,
                    if there is a space search starts for the firstname
                    with the part before the last space
                    if argument is not given return all Cubers.
                    """) @RequestParam(required = false) String name,
            @Parameter(description = """
                    Name of the main cube from the cuber you want to search for.
                    """) @RequestParam(required = false) String cuber,
            @Parameter(description = """
                    Name of the country from the cuber you want to search for.
                    """) @RequestParam(required = false) String country) {
        return competitionService.findCompetitions(name, cuber, country);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing competition")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Competition successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This competition does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        competitionService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new competition.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "competition successfully added"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new Competition")
    })
    public void insert(@RequestBody @Valid Competition competition) {
        competitionService.insert(competition);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing competition.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Competition successfully updated"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Competition")
    })
    public void update(@RequestBody @Valid Competition competition) {
        competitionService.update(competition);
    }
}