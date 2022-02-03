package ch.bbcag.cubeapi.controllers;


import ch.bbcag.cubeapi.models.Cuber;
import ch.bbcag.cubeapi.repositories.CuberRepository;
import ch.bbcag.cubeapi.services.CuberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    @Operation(summary = "Find Cubers by their name " +
            "and the lastname, " +
            "if argument is not given return all Cubers.")
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
    public Iterable<Cuber> findByName(@Parameter(description = """
            Name is seperated by space character
            if there is no space in the name, search starts for first- and lastnames,
            if there is a space search starts for the firstname with the part before the first space
            if argument is not given return all Cubers.""") @RequestParam(required = false) String name) {
        return cuberService.findByName(name);
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
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new Cuber")
    })
    public void insert(@RequestBody @Valid Cuber cuber) {
        cuberService.insert(cuber);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing cuber.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cuber successfully updated"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Cuber")
    })
    public void update(@RequestBody @Valid Cuber cuber) {
        cuberService.update(cuber);
    }
}