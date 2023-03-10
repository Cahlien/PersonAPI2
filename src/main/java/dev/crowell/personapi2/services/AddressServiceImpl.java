package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.AddressDto;
import dev.crowell.personapi2.entities.AddressEntity;
import dev.crowell.personapi2.models.AddressModificationRequest;
import dev.crowell.personapi2.repositories.AddressRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {
    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;

    @Override
    public Page<AddressDto> getAllAddresses(Pageable page) {
        var address = addressRepository.findAll(page);
        return address.map(a -> modelMapper.map(a, AddressDto.class));
    }

    @Override
    public Optional<AddressDto> getAddressById(Long id) {
        return Optional.of(modelMapper.map(addressRepository.findById(id), AddressDto.class));
    }

    @Override
    public Optional<AddressDto> createOrUpdateAddress(AddressModificationRequest request) {
        AddressEntity address;

        if (request.getId().isPresent() && request.getId().get() != 0) {
            address = addressRepository.findById(request.getId().get()).orElse(null);
            if (address == null) return Optional.empty();

            address.setNumber(request.getNumber().orElse(address.getNumber()));
            address.setStreet(request.getStreet().orElse(address.getStreet()));
            address.setCity(request.getCity().orElse(address.getCity()));
            address.setState(request.getState().orElse(address.getState()));
            address.setZip(request.getZip().orElse(address.getZip()));
        } else {
            if (request.getNumber().isEmpty() || request.getStreet().isEmpty() || request.getCity().isEmpty() || request.getState().isEmpty() || request.getZip().isEmpty())
                return Optional.empty();

            address = new AddressEntity();

            address.setNumber(request.getNumber().get());
            address.setStreet(request.getStreet().get());
            address.setCity(request.getCity().get());
            address.setState(request.getState().get());
            address.setZip(request.getZip().get());
        }

        return Optional.of(modelMapper.map(addressRepository.save(address), AddressDto.class));
    }

    @Override
    public void deleteAddress(Long id) {
        try {
            if(addressRepository.isInUse(id)) throw new RuntimeException("Address is in use");
            addressRepository.deleteById(id);
        } catch (Exception e) {
            log.error(String.format("Error deleting address with id %d: %s", id, e));
        }
    }
}
