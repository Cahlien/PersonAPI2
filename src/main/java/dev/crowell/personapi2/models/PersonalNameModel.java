package dev.crowell.personapi2.models;

import dev.crowell.personapi2.entities.CognomenEntity;
import dev.crowell.personapi2.entities.PraenomenEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PersonalNameModel {
    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "person_praenomens",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "praenomen_id"))
    private List<PraenomenEntity> givenNames;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "surname_id")
    private CognomenEntity surname;
}
