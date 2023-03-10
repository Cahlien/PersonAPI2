package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.CognomenDto;
import dev.crowell.personapi2.models.CognomenModificationRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CognomenService {


    Page<CognomenDto> getAllCognomens(Pageable page);

    Optional<CognomenDto> getCognomenById(Long id);

    Optional<CognomenDto> creatOrUpdateCognomen(CognomenModificationRequest request);

    void deleteCognomen(Long id);
}
