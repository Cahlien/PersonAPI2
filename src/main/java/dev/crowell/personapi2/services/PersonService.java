package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.PersonDto;
import dev.crowell.personapi2.models.PersonModificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PersonService {

    Optional<PersonDto> getPersonById(Long id);

    Optional<PersonDto> createOrUpdatePerson(PersonModificationRequest request);

    void deletePerson(Long id);

    Page<PersonDto> getAllPeople(Pageable page);
}
