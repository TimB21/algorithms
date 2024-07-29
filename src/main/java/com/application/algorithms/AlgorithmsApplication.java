package com.application.algorithms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.application.algorithms.users")
@EntityScan(basePackages = "com.application.algorithms.users")
public class AlgorithmsApplication {

    private static final Logger log = LoggerFactory.getLogger(AlgorithmsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(AlgorithmsApplication.class, args);

        System.out.println("Welcome to Maven");

        log.info("Logging looking good");

        // // Initialize transmissions and absorption spectrum arrays
        // double[] transmissions = {
        //     0.1, 0.1, 0.1, 0.1, 0.67, 1.86, 4.23, 7.85, 13.87, 21.28, 28.05, 37.84,
        //     47.75, 52.72, 59.57, 69.18, 75.51, 79.8, 84.72, 87.5, 87.5, 87.9, 90.16,
        //     92.26, 92.04, 90.99, 91.2, 92.47, 93.76, 93.76, 93.33, 92.47, 92.47, 92.68,
        //     93.11, 93.33, 93.11, 92.47
        // };

        // double[] absorptionSpectrum = {
        //     3.0, 3.0, 3.0, 3.0, 2.175, 1.731, 1.374, 1.105, 0.858, 0.672, 0.552, 0.422,
        //     0.321, 0.278, 0.225, 0.16, 0.122, 0.098, 0.072, 0.058, 0.058, 0.056, 0.045,
        //     0.035, 0.036, 0.041, 0.04, 0.034, 0.028, 0.028, 0.03, 0.034, 0.034, 0.033,
        //     0.031, 0.03, 0.031, 0.034
        // };

        // // // Print lengths of transmissions and absorption spectrum arrays
        // // System.out.println("Length of transmissions array: " + transmissions.length);
        // // System.out.println("Length of absorption spectrum array: " + absorptionSpectrum.length);

        // // Create a Lens object using the constructor
        // Lens lens = new Lens(
        //     1,                     // id
        //     "Testing",
        //     transmissions,         // transmissions array
        //     absorptionSpectrum  // absorptionSpectrum array
        // );

        
        // // Output lens details to verify initialization
        // System.out.println(lens);
    }

    // @Bean 
    // CommandLineRunner runner(LensRepository lensRepository) {
    //     return args -> {
    //         // Initialize transmissions and absorption spectrum arrays
    //         double[] transmissions = {
    //             0.1, 0.1, 0.1, 0.1, 0.67, 1.86, 4.23, 7.85, 13.87, 21.28, 28.05, 37.84,
    //             47.75, 52.72, 59.57, 69.18, 75.51, 79.8, 84.72, 87.5, 87.5, 87.9, 90.16,
    //             92.26, 92.04, 90.99, 91.2, 92.47, 93.76, 93.76, 93.33, 92.47, 92.47, 92.68,
    //             93.11, 93.33, 93.11, 92.47
    //         };

    //         double[] absorptionSpectrum = {
    //             3.0, 3.0, 3.0, 3.0, 2.175, 1.731, 1.374, 1.105, 0.858, 0.672, 0.552, 0.422,
    //             0.321, 0.278, 0.225, 0.16, 0.122, 0.098, 0.072, 0.058, 0.058, 0.056, 0.045,
    //             0.035, 0.036, 0.041, 0.04, 0.034, 0.028, 0.028, 0.03, 0.034, 0.034, 0.033,
    //             0.031, 0.03, 0.031, 0.034
    //         };

    //         // // Print lengths of transmissions and absorption spectrum arrays
    //         // System.out.println("Length of transmissions array: " + transmissions.length);
    //         // System.out.println("Length of absorption spectrum array: " + absorptionSpectrum.length);

    //         // Create a Lens object using the constructor
    //         Lens lens = new Lens(
    //             1,                     // id
    //             "Runner",
    //             transmissions,         // transmissions array
    //             absorptionSpectrum  // absorptionSpectrum array
    //         );
    //         lensRepository.createLens(lens);
    //     };
    // }

}
