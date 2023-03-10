package dev.crowell.personapi2.controllers;

import dev.crowell.personapi2.dtos.AddressDto;
import dev.crowell.personapi2.models.AddressModificationRequest;
import dev.crowell.personapi2.services.AddressService;
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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
@Slf4j
@CrossOrigin(origins = "*")
public class AddressController {
    private final AddressService addressService;

    @GetMapping()
    @Operation(summary = "Get all addresses", description = "Get all addresses in the database")
    public ResponseEntity<Page<AddressDto>> getAddresses(@ParameterObject Pageable page) {
        return new ResponseEntity<>(addressService.getAllAddresses(page), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    @Operation(summary = "Get an address by id", description = "Get an address by id from the database")
    @Parameter(name = "id", description = "The id of the address to get", required = true)
    public ResponseEntity<AddressDto> getAddressById(@PathVariable(name = "id") Long id) {
        var address = addressService.getAddressById(id);
        return address.map(addressDto -> new ResponseEntity<>(addressDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @PostMapping()
    @Operation(summary = "Create a new address", description = "Create a new address in the database")
    public ResponseEntity<AddressDto> createOrUpdateAddress(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The address modification request", required = true, content = @Content(schema = @Schema(implementation = AddressModificationRequest.class))) @RequestBody AddressModificationRequest request) {
        var address = addressService.createOrUpdateAddress(request);

        return address.map(addressDto -> new ResponseEntity<>(addressDto, HttpStatus.CREATED)).orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }

    @PutMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Partially update an address", description = "Replace an address in the database")
    @Parameter(name = "id", description = "the id of the address to replace", required = true)
    public ResponseEntity<AddressDto> partiallyUpdateAddress(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The address modification request", required = true, content = @Content(schema = @Schema(implementation = AddressModificationRequest.class))) @RequestBody AddressModificationRequest request) {
        request.setId(Optional.of(id));
        var address = addressService.createOrUpdateAddress(request);
        return address.map(addressDto -> new ResponseEntity<>(addressDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PatchMapping(path = "/{id}", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, "*/*"}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    @Operation(summary = "Update an address", description = "Update an address in the database")
    @Parameter(name = "id", description = "The id of the address to update", required = true)
    public ResponseEntity<AddressDto> updateAddress(@PathVariable(name = "id") Long id, @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "The address modification request", required = true, content = @Content(schema = @Schema(implementation = AddressModificationRequest.class))) @RequestBody AddressModificationRequest request) {
        request.setId(Optional.of(id));
        var address = addressService.createOrUpdateAddress(request);
        return address.map(addressDto -> new ResponseEntity<>(addressDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @DeleteMapping(path = "/{id}")
    @Operation(summary = "Delete an address", description = "Delete an address from the database")
    @Parameter(name = "id", description = "The id of the address to delete", required = true)
    public ResponseEntity<Void> deleteAddress(@PathVariable(name = "id") Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
