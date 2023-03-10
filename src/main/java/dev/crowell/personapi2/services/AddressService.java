package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.AddressDto;
import dev.crowell.personapi2.models.AddressModificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AddressService {
    Page<AddressDto> getAllAddresses(Pageable page);

    Optional<AddressDto> getAddressById(Long id);

    Optional<AddressDto> createOrUpdateAddress(AddressModificationRequest request);

    void deleteAddress(Long id);
}
