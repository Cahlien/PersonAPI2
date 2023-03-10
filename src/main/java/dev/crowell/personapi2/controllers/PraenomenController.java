package dev.crowell.personapi2.controllers;

import dev.crowell.personapi2.dtos.PraenomenDto;
import dev.crowell.personapi2.models.PraenomenModificationRequest;
import dev.crowell.personapi2.services.PraenomenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/praenomens")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PraenomenController {
    private final PraenomenService praenomenService;

    @GetMapping()
    @Operation(summary = "Get all praenomens", description = "Get all praenomens in the database")
    public ResponseEntity<Page<PraenomenDto>> getPraenomens(@ParameterObject Pageable page) {
        return new ResponseEntity<>(praenomenService.getAllPraenomens(page), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get praenomen by id", description = "Get praenomen by id")
    @Parameter(name = "id", description = "The id of the praenomen to get")
    public ResponseEntity<PraenomenDto> getPraenomenById(@PathVariable(name = "id") Long id) {
        var praenomen = praenomenService.getPraenomenById(id);
        return praenomen.map(praenomenDto -> new ResponseEntity<>(praenomenDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    @Operation(summary = "Create a new praenomen", description = "Create a new praenomen")
    public ResponseEntity<PraenomenDto> createPraenomen(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "the create praenomen request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PraenomenModificationRequest.class))) @RequestBody PraenomenModificationRequest request) {
        request.setId(Optional.of(0L));
        var praenomen = praenomenService.createOrUpdatePraenomen(request);
        return praenomen.map(praenomenDto1 -> new ResponseEntity<>(praenomenDto1, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Update a praenomen", description = "Update a praenomen")
    @Parameter(name = "id", description = "The id of the praenomen to update")
    public ResponseEntity<PraenomenDto> updatePraenomen(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "the update praenomen request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PraenomenModificationRequest.class))) @RequestBody PraenomenModificationRequest request) {
        request.setId(Optional.of(id));
        var praenomen = praenomenService.createOrUpdatePraenomen(request);
        return praenomen.map(praenomenDto -> new ResponseEntity<>(praenomenDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace a praenomen", description = "Replace a praenomen")
    @Parameter(name = "id", description = "The id of the praenomen to replace")
    public ResponseEntity<PraenomenDto> replacePraenomen(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "the replace praenomen request", required = true, content = @io.swagger.v3.oas.annotations.media.Content(schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = PraenomenModificationRequest.class))) @RequestBody PraenomenModificationRequest request) {
        request.setId(Optional.of(id));
        var praenomen = praenomenService.createOrUpdatePraenomen(request);
        return praenomen.map(praenomenDto -> new ResponseEntity<>(praenomenDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a praenomen", description = "Delete a praenomen")
    @Parameter(name = "id", description = "The id of the praenomen to delete")
    public ResponseEntity<Void> deletePraenomen(@PathVariable(name = "id") Long id) {
        praenomenService.deletePraenomen(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
