package com.example.reddit;

import java.util.Collection;
import java.util.UUID;
import org.springframework.data.repository.Repository;

interface PersonRepository extends Repository<Person, UUID> {
    NamesOnly findByLastName(String lastName);

  // PersonSummary findByLastName(String lastName);
}
