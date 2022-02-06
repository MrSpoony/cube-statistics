package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Cube;
import ch.bbcag.cubeapi.services.CubeService;
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
@RequestMapping("/cubes")
public class CubeController {

    @Autowired
    private CubeService cubeService;

    public CubeController(CubeService cubeService) {
        this.cubeService = cubeService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a cube by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cube found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Cube.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No cube with this id found.")
    })
    public Cube findById(@PathVariable Integer id) {
        return cubeService.findById(id);
    }


    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing cube")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Cube successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This cube does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        cubeService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new cube.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cube successfully added"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new Cube")
    })
    public void insert(@RequestBody @Valid Cube cube) {
        cubeService.insert(cube);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing cube.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cube successfully updated"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Cube")
    })
    public void update(@RequestBody @Valid Cube cube) {
        cubeService.update(cube);
    }
}