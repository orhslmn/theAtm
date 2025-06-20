package com.atm.atmapp;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;


@Component

public class OracleConnectionTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void testConnection() {
        try {
            String result = jdbcTemplate.queryForObject("SELECT 'Connected to Oracle!' FROM DUAL", String.class);
            System.out.println(result);
        } catch (Exception e) {
            System.err.println("Oracle bağlantısı kurulamadı: " + e.getMessage());
        }
    }
}
