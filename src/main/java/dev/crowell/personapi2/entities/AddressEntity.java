package dev.crowell.personapi2.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;
    @OneToOne(mappedBy = "address", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private PersonEntity person;
}
