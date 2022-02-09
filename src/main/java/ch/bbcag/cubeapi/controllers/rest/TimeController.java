package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Time;
import ch.bbcag.cubeapi.services.TimeService;
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
@RequestMapping("/times")
public class TimeController {

    @Autowired
    private TimeService timeService;

    public TimeController(TimeService timeService) {
        this.timeService = timeService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a time by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Time.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No time with this id found.")
    })
    public Time findById(@PathVariable Integer id) {
        return timeService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get every time")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Times found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Time.class)
                    )})
    })
    public Iterable<Time> findAll() {
        return timeService.findAll();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing time")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Time successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This time does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        timeService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Time successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the time"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new time")
    })
    public void insert(@RequestBody @Valid Time time) {
        timeService.insert(time);
    }


    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing time.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Time successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the time"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the time")
    })
    public void update(@RequestBody @Valid Time time) {
        timeService.update(time);
    }
}