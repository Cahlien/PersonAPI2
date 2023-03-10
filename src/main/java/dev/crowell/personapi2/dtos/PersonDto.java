package dev.crowell.personapi2.dtos;

import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonDto {
    @Embedded
    private PersonalNameDto personalName;
    private AddressDto address;
}
