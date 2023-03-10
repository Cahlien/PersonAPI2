package dev.crowell.personapi2.controllers;

import dev.crowell.personapi2.dtos.CognomenDto;
import dev.crowell.personapi2.models.CognomenModificationRequest;
import dev.crowell.personapi2.services.CognomenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
@RequestMapping("/api/v1/cognomens")
@Slf4j
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class CognomenController {
    private final CognomenService cognomenService;

    @GetMapping()
    @Operation(summary = "Get all cognomens", description = "Get all cognomens in the database")
    public ResponseEntity<Page<CognomenDto>> getCognomens(@ParameterObject Pageable page) {
        return new ResponseEntity<>(cognomenService.getAllCognomens(page), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get cognomen by id", description = "Get cognomen by id")
    @Parameter(name = "id", description = "The id of the cognomen to get")
    public ResponseEntity<CognomenDto> getCognomenById(@PathVariable(name = "id") Long id) {
        var cognomen = cognomenService.getCognomenById(id);
        return cognomen.map(cognomenDto ->
                        new ResponseEntity<>(cognomenDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping(path = "/{id}")
    @Operation(summary = "Replace cognomen", description = "Replace an existing cognomen")
    @Parameter(name = "id", description = "The id of the cognomen to replace")
    public ResponseEntity<CognomenDto> replaceCognomen(@PathVariable(name = "id") Long id,
                                                       @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cognomen modification request",
                                                               required = true, content = @Content(schema = @Schema(implementation = CognomenModificationRequest.class)))
                                                       @RequestBody CognomenModificationRequest request) {
        request.setId(Optional.of(id));

        var cognomen = cognomenService.creatOrUpdateCognomen(request);

        return cognomen.map(cognomenDto ->
                        new ResponseEntity<>(cognomenDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PostMapping()
    @Operation(summary = "Create cognomen", description = "Create a new cognomen")
    public ResponseEntity<CognomenDto> createCognomen(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cognomen modification request",
            required = true, content = @Content(schema = @Schema(implementation = CognomenModificationRequest.class)))
                                                      @RequestBody CognomenModificationRequest request) {
        request.setId(Optional.of(0L));

        var cognomen = cognomenService.creatOrUpdateCognomen(request);

        return cognomen.map(cognomenDto ->
                        new ResponseEntity<>(cognomenDto, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PatchMapping(path = "/{id}")
    @Operation(summary = "Update cognomen", description = "Update an existing cognomen")
    @Parameter(name = "id", description = "The id of the cognomen to update")
    public ResponseEntity<CognomenDto> updateCognomen(@PathVariable(name = "id") Long id,
                                                      @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Cognomen modification request",
                                                              required = true, content = @Content(schema = @Schema(implementation = CognomenModificationRequest.class)))
                                                      @RequestBody CognomenModificationRequest request) {
        request.setId(Optional.of(id));

        var cognomen = cognomenService.creatOrUpdateCognomen(request);

        return cognomen.map(cognomenDto ->
                        new ResponseEntity<>(cognomenDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete cognomen", description = "Delete an existing cognomen")
    @Parameter(name = "id", description = "The id of the cognomen to delete")
    public ResponseEntity<Void> deleteCognomen(@PathVariable(name = "id") Long id) {
        cognomenService.deleteCognomen(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
