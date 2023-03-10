package dev.crowell.personapi2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PersonModificationRequest {
    private Optional<Long> id = Optional.empty();
    private Optional<List<String>> praenomens = Optional.empty();
    private Optional<String> cognomen = Optional.empty();
    private Optional<String> number = Optional.empty();
    private Optional<String> street = Optional.empty();
    private Optional<String> city = Optional.empty();
    private Optional<String> state = Optional.empty();
    private Optional<String> zip = Optional.empty();
}
