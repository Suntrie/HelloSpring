package com.example.hellospring.controllers;

import com.example.hellospring.services.KatzeJDBCConnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/jdbc")
@RequiredArgsConstructor
public class JDBCConnController {

    private final KatzeJDBCConnService katzeJDBCConnService;

    @GetMapping("/katzen")
    @ResponseStatus(HttpStatus.OK)
    public String getKatzen() throws SQLException {
        return katzeJDBCConnService.getKatzen();
    }
}
