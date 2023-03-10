package dev.crowell.personapi2.repositories;

import dev.crowell.personapi2.dtos.CognomenDto;
import dev.crowell.personapi2.entities.CognomenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CognomenRepository extends JpaRepository<CognomenEntity, Long> {
    Optional<CognomenEntity> findByValue(String value);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PersonEntity p WHERE ?1 = p.name.surname.id")
    Boolean isCognomenInUse(Long id);
}
