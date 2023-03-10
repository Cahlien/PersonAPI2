package dev.crowell.personapi2.repositories;

import dev.crowell.personapi2.entities.PraenomenEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PraenomenRepository extends JpaRepository<PraenomenEntity, Long> {
    Optional<PraenomenEntity> findByValue(String value);

    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PersonEntity p WHERE ?1 IN (SELECT n.id FROM p.name.givenNames n)")
    Boolean isPraenomenInUse(Long id);
}
