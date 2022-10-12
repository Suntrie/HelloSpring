package com.example.hellospring.services;

import com.example.hellospring.config.ClickhouseDataSourceConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.*;

@Service
@RequiredArgsConstructor
public class KatzeJDBCConnService {

    private final JDBCConnService jdbcConnService;

    public String getKatzen() throws SQLException {

        StringBuffer stringBuffer = new StringBuffer();
        String query = "SELECT * " +
                "FROM tutorial.katze LIMIT 2";

        try (PreparedStatement statement = jdbcConnService.getConn().prepareStatement(query)) {
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    stringBuffer.append(rs.getString("catName") + " " + rs.getInt("catId"));
                    stringBuffer.append("<br>");
                }
            }
        }

        return  stringBuffer.toString();
    }
}
