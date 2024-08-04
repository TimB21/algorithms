package com.application.algorithms.users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

     private static final Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<User> findAll() {
        return jdbcTemplate.query("SELECT * FROM users", new UserRowMapper());
    }

    public User findByUsername(String username) {
        String sql = "SELECT * FROM users WHERE username = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), username);
        return users.stream().findFirst().orElse(null);
    }

    public Optional<User> findById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        List<User> users = jdbcTemplate.query(sql, new UserRowMapper(), id);
        return users.stream().findFirst();
    }

    public void createUser(User user) {
        log.info("Inserting user: " + user);
        int rowsAffected = jdbcTemplate.update(
            "INSERT INTO users (username, password_hash) VALUES (?, ?)",
            user.getUsername(), user.getPassword()
        );
    
        if (rowsAffected != 1) {
            throw new RuntimeException("Failed to insert user");
        }
    }    

    public void updateUser(User user, int id) {
        int rowsAffected = jdbcTemplate.update(
            "UPDATE users SET username = ?, password = ? WHERE id = ?",
            user.getUsername(), user.getPassword(), id
        );

        assert rowsAffected == 1 : "Failed to update user with ID: " + id;
    }

    public void deleteUser(int id) {
        int rowsAffected = jdbcTemplate.update("DELETE FROM users WHERE id = ?", id);

        assert rowsAffected > 0 : "No user found with ID: " + id;
    }

    public int count() {
        String sql = "SELECT COUNT(*) FROM users";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    public void saveAll(List<User> users) {
        users.stream().forEach(this::createUser);
    }

    private static class UserRowMapper implements RowMapper<User> {
        @Override
        public User mapRow(ResultSet rs, int rowNum) throws SQLException {
            int id = rs.getInt("id");
            String username = rs.getString("username");
            String password = rs.getString("password");
            return new User(id, username, password);
        }
    }
}
