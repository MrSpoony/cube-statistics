package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Location;
import ch.bbcag.cubeapi.services.LocationService;
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
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a location by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Location.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No location with this id found.")
    })
    public Location findById(@PathVariable Integer id) {
        return locationService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get every location")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Locations found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Location.class)
                    )})
    })
    public Iterable<Location> findAll() {
        return locationService.findAll();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing location")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Location successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This location does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        locationService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the location"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new location")
    })
    public void insert(@RequestBody @Valid Location location) {
        locationService.insert(location);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing location.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the location"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the location")
    })
    public void update(@RequestBody @Valid Location location) {
        locationService.update(location);
    }
}