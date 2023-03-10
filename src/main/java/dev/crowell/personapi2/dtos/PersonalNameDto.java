package dev.crowell.personapi2.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonalNameDto {
    List<PraenomenDto> givenNames;
    CognomenDto surname;
}
