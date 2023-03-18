package dev.crowell.personapi2.controllers;

import dev.crowell.personapi2.dtos.PersonDto;
import dev.crowell.personapi2.models.PersonModificationRequest;
import dev.crowell.personapi2.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/people")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Cacheable(value = "people")
public class PersonController {
    private final PersonService personService;
    private final CacheManager cacheManager;


    @GetMapping()
    @Operation(summary = "Get all people", description = "Get all people in the database")
    public ResponseEntity<Page<PersonDto>> getPeople(@ParameterObject Pageable page, @Param("invalidate") Optional<String> invalidate) {
        if(invalidate.isPresent() && invalidate.get().equals("true")){
            Objects.requireNonNull(cacheManager.getCache("people"), "Cache of people is null").clear();
        }
        return new ResponseEntity<>(personService.getAllPeople(page), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get a person", description = "Get a person in the database")
    @Parameter(name = "id", description = "The id of the person to get")
    public ResponseEntity<PersonDto> getPersonById(@PathVariable(name = "id") Long id, @Param("invalidate") Optional<String> invalidate) {
        if(invalidate.isPresent() && invalidate.get().equals("true")){
            Objects.requireNonNull(cacheManager.getCache("people"), "Cache of people is null").clear();
        }
        var person = personService.getPersonById(id);
        return person.map(personDto -> new ResponseEntity<>(personDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Update a person", description = "Update a person in the database")
    @Parameter(name = "id", description = "The id of the person to update")
    public ResponseEntity<PersonDto> updatePerson(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The update person request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PersonModificationRequest.class))) @RequestBody PersonModificationRequest request) {
        request.setId(Optional.of(id));

        var person = personService.createOrUpdatePerson(request);
        return person.map(personDto -> new ResponseEntity<>(personDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace a person", description = "Replace a person in the database")
    @Parameter(name = "id", description = "The id of the person to replace")
    public ResponseEntity<PersonDto> replacePerson(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The replace person request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PersonModificationRequest.class))) @RequestBody PersonModificationRequest request) {
        request.setId(Optional.of(id));

        var person = personService.createOrUpdatePerson(request);
        return person.map(personDto -> new ResponseEntity<>(personDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @Operation(summary = "Create a person", description = "Create a person in the database")
    public ResponseEntity<PersonDto> createPerson(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The create person request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PersonModificationRequest.class))) @RequestBody PersonModificationRequest request) {
        request.setId(Optional.of(0L));
        var person = personService.createOrUpdatePerson(request);
        return person.map(personDto -> new ResponseEntity<>(personDto, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a person", description = "Delete a person in the database")
    @Parameter(name = "id", description = "The id of the person to delete")
    public ResponseEntity<Void> deletePerson(@PathVariable(name = "id") Long id) {
        personService.deletePerson(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
