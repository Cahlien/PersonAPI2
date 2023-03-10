package dev.crowell.personapi2.services;


import dev.crowell.personapi2.dtos.PersonDto;
import dev.crowell.personapi2.entities.AddressEntity;
import dev.crowell.personapi2.entities.CognomenEntity;
import dev.crowell.personapi2.entities.PersonEntity;
import dev.crowell.personapi2.entities.PraenomenEntity;
import dev.crowell.personapi2.models.PersonModificationRequest;
import dev.crowell.personapi2.models.PersonalNameModel;
import dev.crowell.personapi2.repositories.AddressRepository;
import dev.crowell.personapi2.repositories.CognomenRepository;
import dev.crowell.personapi2.repositories.PersonRepository;
import dev.crowell.personapi2.repositories.PraenomenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {
    private final AddressRepository addressRepository;
    private final PersonRepository personRepository;
    private final CognomenRepository cognomenRepository;
    private final PraenomenRepository praenomenRepository;

    private final ModelMapper modelMapper;

    private static void updateAddress(PersonModificationRequest request, Optional<PersonEntity> person) {
        if (person.isEmpty()) return;

        if (request.getNumber().isPresent()) {
            person.get().getAddress().setNumber(request.getNumber().get());
        }

        if (request.getStreet().isPresent()) {
            person.get().getAddress().setStreet(request.getStreet().get());
        }

        if (request.getCity().isPresent()) {
            person.get().getAddress().setCity(request.getCity().get());
        }

        if (request.getState().isPresent()) {
            person.get().getAddress().setState(request.getState().get());
        }

        if (request.getZip().isPresent()) {
            person.get().getAddress().setZip(request.getZip().get());
        }
    }

    private void updateName(PersonModificationRequest request, Optional<PersonEntity> person) {
        if (person.isEmpty()) return;

        if (request.getPraenomens().isPresent()) {
            var praenomens = new ArrayList<PraenomenEntity>();
            request.getPraenomens().get().forEach(praenomen -> {
                praenomens.add(praenomenRepository.findByValue(praenomen).orElse(PraenomenEntity.builder().value(praenomen).build()));
            });
            person.get().getName().setGivenNames(praenomens);
        }

        if (request.getCognomen().isPresent()) {
            var cognomen = cognomenRepository.findByValue(request.getCognomen().get());
            person.get().getName().setSurname(cognomen.orElse(CognomenEntity.builder().value(request.getCognomen().get()).build()));
        }
    }

    @Override
    public Optional<PersonDto> getPersonById(Long id) {
        return Optional.of(modelMapper.map(personRepository.findById(id), PersonDto.class));
    }

    @Override
    public Optional<PersonDto> createOrUpdatePerson(PersonModificationRequest request) {
        Optional<PersonEntity> person;

        if (request.getId().isPresent() && request.getId().get() != 0) {
            person = personRepository.findById(request.getId().get());
            if (person.isEmpty()) return Optional.empty();
        } else {
            person = Optional.of(PersonEntity.builder().name(new PersonalNameModel()).address(new AddressEntity()).build());
        }

        updateName(request, person);
        updateAddress(request, person);

        return Optional.of(modelMapper.map(personRepository.save(person.get()), PersonDto.class));
    }

    @Override
    public void deletePerson(Long id) {
        try {
            personRepository.deleteById(id);
        } catch (Exception e) {
            log.error(String.format("Error deleting person with id %d: %s", id, e));
        }
    }

    @Override
    public Page<PersonDto> getAllPeople(Pageable page) {
        log.info("Getting all people");
        var people = personRepository.findAll(page);
        log.info("Found {} people", people.getTotalElements());
        return people.map(person -> modelMapper.map(person, PersonDto.class));
    }
}
