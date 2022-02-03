package ch.bbcag.cubeapi.controllers;


import ch.bbcag.cubeapi.models.Cuber;
import ch.bbcag.cubeapi.repositories.CuberRepository;
import ch.bbcag.cubeapi.services.CuberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/cubers")
public class CuberController {

    private CuberRepository cuberRepository;

    @Bean
    public CuberService cuberService() {
        return new CuberService((cuberRepository));
    }

    @Autowired
    private CuberService cuberService;


    @GetMapping(path = "{id}")
    public Cuber findById(@PathVariable Integer id) {
        return cuberService.findById(id);
    }

    @GetMapping
    public Iterable<Cuber> findByName(@RequestParam(required = false) String name) {
        return cuberService.findByName(name);
    }

    @DeleteMapping(path = "{id}")
    public void deleteById(@PathVariable Integer id) {
        cuberService.deleteById(id);
    }

    @PostMapping()
    public void insert(@RequestBody Cuber cuber) {
        cuberService.insert(cuber);
    }

    @PutMapping
    public void update(@RequestBody Cuber cuber) {
        cuberService.update(cuber);
    }
}