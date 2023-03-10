package dev.crowell.personapi2.repositories;

import dev.crowell.personapi2.entities.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PersonEntity p WHERE ?1 = p.address.id")
    Boolean isInUse(Long id);
}
