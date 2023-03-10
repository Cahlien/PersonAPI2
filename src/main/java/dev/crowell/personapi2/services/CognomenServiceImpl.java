package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.CognomenDto;
import dev.crowell.personapi2.entities.CognomenEntity;
import dev.crowell.personapi2.models.CognomenModificationRequest;
import dev.crowell.personapi2.repositories.CognomenRepository;
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
public class CognomenServiceImpl implements CognomenService {
    private final CognomenRepository cognomenRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<CognomenDto> getCognomenById(Long id) {
        return Optional.of(modelMapper.map(cognomenRepository.findById(id), CognomenDto.class));
    }

    @Override
    public Optional<CognomenDto> creatOrUpdateCognomen(CognomenModificationRequest request) {
        if (request.getValue().isEmpty()) return Optional.empty();

        Optional<CognomenEntity> cognomen;

        if (request.getId().isPresent() && request.getId().get() != 0) {
            cognomen = cognomenRepository.findById(request.getId().get());
            if (cognomen.isEmpty()) return Optional.empty();

            cognomen.get().setValue(request.getValue().get());
        } else {
            cognomen = Optional.of(CognomenEntity.builder().value(request.getValue().get()).build());
        }

        return Optional.of(modelMapper.map(cognomenRepository.save(cognomen.get()), CognomenDto.class));
    }

    @Override
    public void deleteCognomen(Long id) {
        try {
            if(cognomenRepository.isCognomenInUse(id)) throw new Exception("Cognomen is in use");
            cognomenRepository.deleteById(id);
        } catch (Exception e) {
            log.error(String.format("Error deleting cognomen with id %d: %s", id, e));
        }
    }

    @Override
    public Page<CognomenDto> getAllCognomens(Pageable page) {
        return cognomenRepository.findAll(page).map(cognomenEntity -> modelMapper.map(cognomenEntity, CognomenDto.class));
    }
}
