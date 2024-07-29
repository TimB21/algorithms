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
}
