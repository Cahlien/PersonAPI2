package dev.crowell.personapi2.models;

import lombok.*;

import java.util.Optional;

@Getter
@Setter

public class AddressModificationRequest {
    private Optional<Long> id = Optional.empty();
    private Optional<String> number = Optional.empty();
    private Optional<String> street = Optional.empty();
    private Optional<String> city = Optional.empty();
    private Optional<String> state = Optional.empty();
    private Optional<String> zip = Optional.empty();
}
