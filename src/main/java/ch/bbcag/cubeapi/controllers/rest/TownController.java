package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Town;
import ch.bbcag.cubeapi.services.TownService;
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
@RequestMapping("/towns")
public class TownController {

    @Autowired
    private TownService townService;

    public TownController(TownService townService) {
        this.townService = townService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a town by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Town found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Town.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No town with this id found.")
    })
    public Town findById(@PathVariable Integer id) {
        return townService.findById(id);
    }


    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing town")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Town successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This town does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        townService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new town.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Town successfully added"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new Town")
    })
    public void insert(@RequestBody @Valid Town town) {
        townService.insert(town);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing town.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Town successfully updated"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Town")
    })
    public void update(@RequestBody @Valid Town town) {
        townService.update(town);
    }
}