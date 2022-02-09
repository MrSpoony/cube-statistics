package ch.bbcag.cubeapi.controllers.rest;

import ch.bbcag.cubeapi.models.Event;
import ch.bbcag.cubeapi.services.EventService;
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
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @GetMapping(path = "{id}")
    @Operation(summary = "Find a event by it's id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)
                    )}),
            @ApiResponse(responseCode = "404", description = "No event with this id found.")
    })
    public Event findById(@PathVariable Integer id) {
        return eventService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Get every event")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Events found!",
                    content = {@Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Event.class)
                    )})
    })
    public Iterable<Event> findAll() {
        return eventService.findAll();
    }

    @DeleteMapping(path = "{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Delete an existing event")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Event successfully deleted"),
            @ApiResponse(
                    responseCode = "404",
                    description = "This event does not exist.")
    })
    public void deleteById(@PathVariable Integer id) {
        eventService.deleteById(id);
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Add a new event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event successfully added"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while adding the event"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while adding the new event")
    })
    public void insert(@RequestBody @Valid Event event) {
        eventService.insert(event);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Update an existing event.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event successfully updated"),
            @ApiResponse(responseCode = "400", description = "Something went wrong while updating the event"),
            @ApiResponse(responseCode = "409", description = "There was a conflict while updating the event")
    })
    public void update(@RequestBody @Valid Event event) {
        eventService.update(event);
    }
}