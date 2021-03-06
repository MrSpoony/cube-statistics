package ch.bbcag.cubeapi.controllers.rest;


import ch.bbcag.cubeapi.models.Cuber;
import ch.bbcag.cubeapi.services.CuberService;
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
@RequestMapping(path = "/cubers")
public class CuberController {
    @Autowired
    private CuberService cuberService;

    public CuberController(CuberService cuberService) {
        this.cuberService = cuberService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a cuber by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuber found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Cuber.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No cuber with this id found.")
    })
    public Cuber findById(@PathVariable Integer id) {
        return cuberService.findById(id);
    }

    @GetMapping
    @Operation(summary = """
            Find Cubers by their first-
            and/or lastname,
            and/or their maincube,
            and/or their mainevent
            and/or their country.
            Use multiple arguments are to specify your search.
            If no argument is given returns all cubers.""")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuber(s) found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Cuber.class))}),
            @ApiResponse(
                    responseCode = "404",
                    description = "No cuber found with given Argument(s)")
    })
    public Iterable<Cuber> findByName(
            @Parameter(description = """
                    Name is seperated by space character
                    if there is no space in the name, search starts for first- and lastnames,
                    if there is a space search starts for the firstname
                    with the part before the last space
                    if argument is not given return all cubers.
                    """) @RequestParam(required = false) String name,
            @Parameter(description = """
                    Name of the main cube from the cuber you want to search for.
                    """) @RequestParam(required = false) String maincube,
            @Parameter(description = """
                    Name of the main event from the cuber you want to search for.
                    """) @RequestParam(required = false) String mainevent,
            @Parameter(description = """
                    Name of the country from the cuber you want to search for.
                    """) @RequestParam(required = false) String country) {
        return cuberService.findCubers(name, maincube, mainevent, country);
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing cuber")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cuber successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This cuber does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        cuberService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new cuber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cuber successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the cuber"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new cuber")
    })
    public void insert(@RequestBody @Valid Cuber cuber) {
        cuberService.insert(cuber);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing cuber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuber successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the cuber"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the cuber")
    })
    public void update(@RequestBody @Valid Cuber cuber) {
        cuberService.update(cuber);
    }
}