package com.application.algorithms.lens;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class LensRepository {

    private List<Lens> lenses = new ArrayList<>();

    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Lens> findAll() {
        return jdbcTemplate.query("SELECT * FROM lens", new LensRowMapper());
    }

    public Optional<Lens> findById(int id) {
        String sql = "SELECT * FROM lens WHERE id = ?";
        List<Lens> lenses = jdbcTemplate.query(sql, new LensRowMapper(), id);
        return lenses.stream().findFirst();
    }

    public void createLens(Lens len) {
        int rowsAffected = jdbcTemplate.update(
            "INSERT INTO lens (id, name, transmissions, absorption_spectrum) VALUES (?, ?, ?, ?)",
            len.getId(), len.getName(),
            String.join(",", Arrays.stream(len.getTransmissions()).mapToObj(String::valueOf).toArray(String[]::new)),
            String.join(",", Arrays.stream(len.getAbsorptionSpectrum()).mapToObj(String::valueOf).toArray(String[]::new))
        );
    
        assert rowsAffected == 1 : "Failed to insert lens with ID: " + len.getId();
    }

    public void updateLens(Lens len, int id) {
        int rowsAffected = jdbcTemplate.update(
            "UPDATE lens SET name = ?, transmissions = ?, absorption_spectrum = ? WHERE id = ?",
            len.getName(),
            String.join(",", Arrays.stream(len.getTransmissions()).mapToObj(String::valueOf).toArray(String[]::new)),
            String.join(",", Arrays.stream(len.getAbsorptionSpectrum()).mapToObj(String::valueOf).toArray(String[]::new)),
            id
        );
    
        assert rowsAffected == 1 : "Failed to update lens with ID: " + id;
    }

    public void deleteLens(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM lens WHERE id = ?", id);
    
        assert rowsAffected > 0 : "No lens found with ID: " + id;
    }

    @SuppressWarnings("null")
    public int count() {
        String sql = "SELECT COUNT(*) FROM lens;";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
    
    

    public void saveAll(List<Lens> lens) {
        lens.stream().forEach(this::createLens);
    }


    

    private static class LensRowMapper implements RowMapper<Lens> {
        @Override
        public Lens mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String name = rs.getString("name");
            double[] transmissions = Arrays.stream(rs.getString("transmissions").split(",")).mapToDouble(Double::parseDouble).toArray();
            double[] absorptionSpectrum = Arrays.stream(rs.getString("absorption_spectrum").split(",")).mapToDouble(Double::parseDouble).toArray();
            return new Lens(id, name, transmissions, absorptionSpectrum);
        }
    }

    // public Optional<Lens> findById(int id) {
    //     String sql = "SELECT * FROM lens WHERE id = ?";
    //     List<Lens> lenses = jdbcTemplate.query(sql, new LensRowMapper(), id);
    //     return lenses.stream().findFirst();
    // }

    // public void createLens(Lens lens) {
    //     String sql = "INSERT INTO lens (id, name, transmission_avg, transmissions, absorption_spectrum, eye_protection_factor, melatonin_production_factor, glare_reduction_factor, r_squared) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    //     jdbcTemplate.update(sql, lens.getId(), lens.getName(), lens.getTransmissionAvg(),
    //             arrayToString(lens.getTransmissions()), arrayToString(lens.getAbsorptionSpectrum()),
    //             lens.getEyeProtectionFactor(), lens.getMelatoninProductionFactor(),
    //             lens.getGlareReductionFactor(), lens.getRSquared());
    // }

    // public void updateLens(Lens lens, int id) {
    //     String sql = "UPDATE lens SET name = ?, transmission_avg = ?, transmissions = ?, absorption_spectrum = ?, eye_protection_factor = ?, melatonin_production_factor = ?, glare_reduction_factor = ?, r_squared = ? WHERE id = ?";
    //     jdbcTemplate.update(sql, lens.getName(), lens.getTransmissionAvg(),
    //             arrayToString(lens.getTransmissions()), arrayToString(lens.getAbsorptionSpectrum()),
    //             lens.getEyeProtectionFactor(), lens.getMelatoninProductionFactor(),
    //             lens.getGlareReductionFactor(), lens.getRSquared(), id);
    // }

    // public void deleteLens(int id) {
    //     String sql = "DELETE FROM lens WHERE id = ?";
    //     jdbcTemplate.update(sql, id);
    // }

    // private String arrayToString(double[] array) {
    //     StringBuilder sb = new StringBuilder();
    //     for (double value : array) {
    //         sb.append(value).append(",");
    //     }
    //     return sb.length() > 0 ? sb.substring(0, sb.length() - 1) : "";
    // }

    // List<Lens> findAll() {
    //     return lenses;
    // }

    // Optional<Lens> findById(double id){
    //     return lenses.stream().filter(len -> len.getId() == id).findFirst();
    // }

    // void createLens(Lens len){
    //     lenses.add(len);
    // }

    // void updateLens(Lens len, double id) {
    //     Optional<Lens> existingLens = findById(id);
    //     if(existingLens.isPresent()) {
    //         lenses.set(lenses.indexOf(existingLens.get()), len);
    //     }
    // }

    // void deleteLens(double id){
    //     lenses.removeIf(len -> len.getId() == id);
    // }

    // @PostConstruct
    // private void init(){
    //     // Initialize transmissions and absorption spectrum arrays
    //     double[] transmissions = {
    //         0.1, 0.1, 0.1, 0.1, 0.67, 1.86, 4.23, 7.85, 13.87, 21.28, 28.05, 37.84,
    //         47.75, 52.72, 59.57, 69.18, 75.51, 79.8, 84.72, 87.5, 87.5, 87.9, 90.16,
    //         92.26, 92.04, 90.99, 91.2, 92.47, 93.76, 93.76, 93.33, 92.47, 92.47, 92.68,
    //         93.11, 93.33, 93.11, 92.47
    //     };

    //     double[] absorptionSpectrum = {
    //         3.0, 3.0, 3.0, 3.0, 2.175, 1.731, 1.374, 1.105, 0.858, 0.672, 0.552, 0.422,
    //         0.321, 0.278, 0.225, 0.16, 0.122, 0.098, 0.072, 0.058, 0.058, 0.056, 0.045,
    //         0.035, 0.036, 0.041, 0.04, 0.034, 0.028, 0.028, 0.03, 0.034, 0.034, 0.033,
    //         0.031, 0.03, 0.031, 0.034
    //     };
    //     lenses.add(new Lens(1, "Cyxus", transmissions, absorptionSpectrum));

    //     double[] absorption1 = {
    //         3.0, 3.0, 3.0, 2.141, 1.672, 1.358, 1.085, 0.849, 0.647, 0.499, 0.386, 0.288,
    //         0.22, 0.185, 0.146, 0.102, 0.076, 0.064, 0.053, 0.045, 0.045, 0.046, 0.043,
    //         0.038, 0.037, 0.041, 0.041, 0.037, 0.031, 0.029, 0.033, 0.035, 0.035, 0.032,
    //         0.029, 0.028, 0.029, 0.032
    //     };
        
    //     double[] transmission1 = {
    //         0.1, 0.1, 0.1, 0.72, 2.13, 4.39, 8.22, 14.16, 22.54, 31.7, 41.11, 51.52,
    //         60.26, 65.31, 71.45, 79.07, 83.95, 86.3, 88.51, 90.16, 90.16, 89.95, 90.57,
    //         91.62, 91.83, 90.99, 90.99, 91.83, 93.11, 93.54, 92.68, 92.26, 92.26, 92.9,
    //         93.54, 93.76, 93.54, 92.9
    //     };

    //     lenses.add(new Lens(2, "GYSnail", transmission1, absorption1));
   
    // }
}
