package dev.crowell.personapi2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private String number;
    private String street;
    private String city;
    private String state;
    private String zip;
}
