package com.application.algorithms.lens;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException; 

import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class LensController { 

    private static final Logger log = LoggerFactory.getLogger(LensController.class);

    @Autowired
    private final LensRepository lensRepository;

    public LensController(LensRepository lensRepository) {
        this.lensRepository = lensRepository;
    }

    @PostMapping("/upload")
    public ResponseEntity<Lens> handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("fileName") String fileName) {
        if (file.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        
        if (fileName == null || fileName.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        try {
            Lens lens = processTextFile(file, fileName);
            lensRepository.createLens(lens);
            // Debug statement to verify JSON response
            System.out.println(new ObjectMapper().writeValueAsString(lens));
            return ResponseEntity.status(HttpStatus.CREATED).body(lens);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }



    private Lens processTextFile(MultipartFile file, String fileName) throws IOException {
        List<Double> transmissions = new ArrayList<>();
        List<Double> absorptionSpectrum = new ArrayList<>();
        
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] values = line.trim().split("\\s+");
                if (values.length == 3) {
                    transmissions.add(Double.valueOf(values[1]));
                    absorptionSpectrum.add(Double.valueOf(values[2]));
                }
            }
        }
        
        if (transmissions.size() != 38 || absorptionSpectrum.size() != 38) {
            throw new IllegalArgumentException("Text file does not contain exactly 38 rows of data.");
        }
    
        // Convert List<Double> to double[]
        double[] transmissionArray = transmissions.stream().mapToDouble(Double::doubleValue).toArray();
        double[] absorptionArray = absorptionSpectrum.stream().mapToDouble(Double::doubleValue).toArray();
        
        // Use the count method to generate the next ID
        int id = (int) lensRepository.count() + 1; 
        
        // Create and return the Lens object with the provided file name
        return new Lens(id, fileName != null ? fileName : "No File Name Provided", absorptionArray, transmissionArray);
    }
    

    @GetMapping("/lens/{id}")
    public Lens findById(@PathVariable int id) {
        Optional<Lens> lens = lensRepository.findById(id);
        if (lens.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return lens.get();
    }

    @GetMapping("/lens")
    List<Lens> findAll() {
        return lensRepository.findAll();
    }

    @PostMapping("/lens")
    public ResponseEntity<String> createLens(@RequestBody Lens len) {
    log.info("Received request to create lens: {}", len);

        try {
            validateLensArrays(len);
            lensRepository.createLens(len);
            log.info("Lens successfully passed to repository.");
            return ResponseEntity.status(HttpStatus.CREATED).body("Lens created successfully");
        } catch (Exception e) {
            log.error("Failed to create lens: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to create lens: " + e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/lens/{id}")
    void updateLens(@RequestBody Lens len, @PathVariable int id) {
        log.info("Received request to update lens with ID {}: {}", id, len);
        
        try {
            // Check if the lens with the given ID exists
            Optional<Lens> existingLens = lensRepository.findById(id);
            if (existingLens.isEmpty()) {
                log.error("Lens with ID {} not found. Update operation aborted.", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lens with ID " + id + " not found.");
            }
            
            validateLensArrays(len);
            lensRepository.updateLens(len, id);
            log.info("Lens with ID {} successfully updated.", id);
        } catch (Exception e) {
            log.error("Failed to update lens with ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update lens with ID " + id, e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/lens/{id}")
    void deleteLens(@PathVariable int id) {
        log.info("Received request to delete lens with ID {}", id);
        
        try {
            // Check if the lens with the given ID exists
            Optional<Lens> existingLens = lensRepository.findById(id);
            if (existingLens.isEmpty()) {
                log.error("Lens with ID {} not found. Update operation aborted.", id);
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lens with ID " + id + " not found.");
            }
            
            lensRepository.deleteLens(id);
            log.info("Lens with ID {} successfully deleted.", id);
        } catch (Exception e) {
            log.error("Failed to delete lens with ID {}: {}", id, e.getMessage());
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to delete lens with ID " + id, e);
        }
    }


    public void validateLensArrays(Lens lens) {
        if (lens.getTransmissions().length != 38) {
            int currentLength = lens.getTransmissions().length;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Transmission array length is " + currentLength + ", must be of length 38");
        }
        if (lens.getAbsorptionSpectrum().length != 38) {
            int currentLength = lens.getAbsorptionSpectrum().length;
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Absorption spectrum array length is " + currentLength + ", must be of length 38");
        }
    }
}
