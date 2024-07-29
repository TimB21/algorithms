package com.application.algorithms.lens;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class LensJsonDataLoader implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LensJsonDataLoader.class);

    private final LensRepository lensRepository;

    private final ObjectMapper objectMapper;

    // Inject the LensRepository and ObjectMapper via constructor
    public LensJsonDataLoader(LensRepository lensRepository, ObjectMapper objectMapper) {
        this.lensRepository = lensRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (lensRepository.count() == 1) {
            try (InputStream inputStream = TypeReference.class.getResourceAsStream("/data/lenses.json")) {
                List<Lens> allLenses = objectMapper.readValue(inputStream, new TypeReference<List<Lens>>() {});
                log.info("Reading {} lenses from JSON data and saving to database", allLenses.size());

                // Save all lenses to the database
                lensRepository.saveAll(allLenses);

                log.info("Lens data loaded successfully from JSON file.");

            } catch (IOException e) {
                throw new RuntimeException("Failed to read JSON data", e);
            }

        } else {
            log.info("Lens data already exists in the database. Skipping data loading.");
            log.info("Current number of lenses in the database: {}", lensRepository.count());
        }
    }
}


