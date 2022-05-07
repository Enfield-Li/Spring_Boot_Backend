package com.example.reddit.person;

import java.util.Collection;
import java.util.UUID;

import com.example.reddit.person.dto.NamesOnly;

import org.springframework.data.repository.Repository;

public interface PersonRepository extends Repository<Person, UUID> {
    NamesOnly findByLastName(String lastName);

  // PersonSummary findByLastName(String lastName);
}
