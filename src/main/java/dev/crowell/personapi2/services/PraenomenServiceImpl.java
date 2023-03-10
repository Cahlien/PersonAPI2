package dev.crowell.personapi2.services;

import dev.crowell.personapi2.dtos.PraenomenDto;
import dev.crowell.personapi2.entities.PraenomenEntity;
import dev.crowell.personapi2.models.PraenomenModificationRequest;
import dev.crowell.personapi2.repositories.PraenomenRepository;
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
public class PraenomenServiceImpl implements PraenomenService {
    private final PraenomenRepository praenomenRepository;
    private final ModelMapper modelMapper;

    @Override
    public Optional<PraenomenDto> getPraenomenById(Long id) {
        return Optional.of(modelMapper.map(praenomenRepository.findById(id), PraenomenDto.class));
    }

    @Override
    public Optional<PraenomenDto> createOrUpdatePraenomen(PraenomenModificationRequest request) {
        // find out if name is in praenomenRepository
        PraenomenEntity praenomen;

        if (request.getId().isPresent() && request.getId().get() != 0) {
            praenomen = praenomenRepository.findById(request.getId().get()).orElse(null);
            if (praenomen == null) return Optional.empty();

            praenomen.setValue(request.getValue().orElse(praenomen.getValue()));
        } else {
            if (request.getValue().isEmpty()) return Optional.empty();

            praenomen = new PraenomenEntity();
            praenomen.setValue(request.getValue().get());
        }

        return Optional.of(modelMapper.map(praenomenRepository.save(praenomen), PraenomenDto.class));
    }

    @Override
    public void deletePraenomen(Long id) {
        try {
            if(praenomenRepository.isPraenomenInUse(id)) throw new Exception("Praenomen is in use");
            praenomenRepository.deleteById(id);
        } catch (Exception e) {
            log.error(String.format("Error deleting praenomen with id %d: %s", id, e));
        }
    }

    @Override
    public Page<PraenomenDto> getAllPraenomens(Pageable page) {
        return praenomenRepository.findAll(page).map(praenomenEntity -> modelMapper.map(praenomenEntity, PraenomenDto.class));
    }
}
