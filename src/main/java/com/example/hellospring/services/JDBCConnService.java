package com.example.hellospring.services;

import com.example.hellospring.config.ClickhouseDataSourceConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Slf4j
@Service
@RequiredArgsConstructor
public class JDBCConnService {

    private final ClickhouseDataSourceConfig config;
    private Connection conn;

    @PostConstruct
    public void initConn(){
        try {
            conn = DriverManager.getConnection(config.getUrl()+"/"+config.getDatabase(),
                    config.getUsername(), config.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConn(){
        return conn;
    }

    public ClickhouseDataSourceConfig getConfig(){
        return config;
    }
}
