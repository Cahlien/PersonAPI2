package dev.crowell.personapi2.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PraenomenModificationRequest {
    private Optional<Long> id = Optional.empty();
    private Optional<String> value = Optional.empty();
}
