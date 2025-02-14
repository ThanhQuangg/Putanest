package com.ntq.putanest.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.sql.*;

@RestController
public class test_db {

    @GetMapping("/test-db")
    public ResponseEntity<String> testDbConnection() {
        try (Connection conn = DriverManager.getConnection(
                "jdbc:mysql://monorail.proxy.rlwy.net:24398/railway",
                "root",
                "boCPVJZDJOkEkyXqlaMQeqZzDaDnfUFr")) {
            return ResponseEntity.ok("✅ Connected to DB!");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ DB Error: " + e.getMessage());
        }
    }
}
