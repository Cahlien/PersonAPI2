package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.PraenomenDto;
import dev.crowell.personapi2.models.PraenomenModificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PraenomenService {
    Optional<PraenomenDto> getPraenomenById(Long id);

    Optional<PraenomenDto> createOrUpdatePraenomen(PraenomenModificationRequest request);

    void deletePraenomen(Long id);

    Page<PraenomenDto> getAllPraenomens(Pageable page);
}
