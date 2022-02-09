package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Manufacture;
import ch.bbcag.cubeapi.services.ManufactureService;
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
@RequestMapping("/manufactures")
public class ManufactureController {

    @Autowired
    private ManufactureService manufactureService;

    public ManufactureController(ManufactureService manufactureService) {
        this.manufactureService = manufactureService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a manufacture by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manufacture found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Manufacture.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No manufacture with this id found.")
    })
    public Manufacture findById(@PathVariable Integer id) {
        return manufactureService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get every manufacture")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manufactures found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Manufacture.class)
                    )})
    })
    public Iterable<Manufacture> findAll() {
        return manufactureService.findAll();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing manufacture")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Manufacture successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This manufacture does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        manufactureService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new manufacture.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Manufacture successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the manufacture"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new manufacture")
    })
    public void insert(@RequestBody @Valid Manufacture manufacture) {
        manufactureService.insert(manufacture);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing manufacture.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Manufacture successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the manufacture"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the Manufacture")
    })
    public void update(@RequestBody @Valid Manufacture manufacture) {
        manufactureService.update(manufacture);
    }
}