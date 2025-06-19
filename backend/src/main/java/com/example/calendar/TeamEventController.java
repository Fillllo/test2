package com.example.calendar;

import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/events")
@CrossOrigin(origins = "*")
public class TeamEventController {

    private final TeamEventRepository repository;

    public TeamEventController(TeamEventRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public Iterable<TeamEvent> all() {
        return repository.findAll();
    }

    @PostMapping
    public TeamEvent create(@RequestBody TeamEvent event) {
        return repository.save(event);
    }

    @GetMapping("/{id}")
    public Optional<TeamEvent> get(@PathVariable Long id) {
        return repository.findById(id);
    }

    @PutMapping("/{id}")
    public TeamEvent update(@PathVariable Long id, @RequestBody TeamEvent event) {
        event.setId(id);
        return repository.save(event);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        repository.deleteById(id);
    }
}
