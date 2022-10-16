package com.example.hellospring.services;

import com.clickhouse.client.*;
import com.example.hellospring.domain.dto.condition.RecipeConditionDTO;
import com.example.hellospring.domain.entities.Recipe_;
import com.example.hellospring.repository.RecipeRepository;
import com.example.hellospring.repository.spec.RecipeSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final JDBCConnService jdbcConnService;

    private final String explorationQuery = " select recipe0_.title as col_0_0_, " +
            " count(recipe0_.title)*1000 as col_1_0_ " +
            " from recipes recipe0_ " +
            " where recipe0_.title like ? " +
            " group by recipe0_.title " +
            " having count(recipe0_.title)*1000>1000 " +
            " order by case when recipe0_.title like ? then 1 else 0 end asc";

    public Long getAggregatesByJDBC() throws SQLException {

        Map<String, Long> map = new HashMap<>();

        String query = explorationQuery;

        long startMillis;
        long endMillis;
        long duration;

        try (PreparedStatement statement = jdbcConnService.getConn().prepareStatement(query)) {
            statement.setString(1, "%a%");
            statement.setString(2, "%e%");
            startMillis = System.currentTimeMillis();
            try (ResultSet rs = statement.executeQuery()) {
                endMillis = System.currentTimeMillis();
                duration = endMillis - startMillis;

                log.info("JDBC time: {}", duration);

                while (rs.next()) {
                    map.put(rs.getString("col_0_0_"), rs.getLong("col_1_0_"));
                }

                log.info("JDBC size: {}", map.size());
            }
        }

        return duration;
    }

    public Long getAggregatesByHttpClickhouse() {

        Map<String, Long> map = new HashMap<>();

        var config = jdbcConnService.getConfig();

        ClickHouseNode endpoint = ClickHouseNode.of("localhost", ClickHouseProtocol.ANY,
                8123, config.getDatabase());

        long startMillis = System.currentTimeMillis();
        long endMillis;
        long duration;

        try (ClickHouseClient client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
             ClickHouseResponse response = client.connect(endpoint)
                     .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                     .query(explorationQuery.replace("?",":param"))
                     .params("\'%a%\'", "\'%e%\'").execute().get()) {

            endMillis = System.currentTimeMillis();
            duration = endMillis - startMillis;

            log.info("Http ClickHouse time: {}", duration);

            for (ClickHouseRecord r : response.records()) {
                map.put(r.getValue(0).asString(), r.getValue(1).asLong());
            }

            log.info("Http ClickHouse size: {}", map.size());

        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
        return duration;
    }

    public Long getAggregatesByJavaSpec() {
        var result = getAggregatesMapByJavaSpec();
        return result.getRight();
    }

    private Pair<Map<String, Long>, Long> getAggregatesMapByJavaSpec() {
        return recipeRepository.groupAndCountHaving(Recipe_.title, RecipeSpecification.createSpecificationByParams
                (RecipeConditionDTO.builder().nameLikePattern("a").build()));
    }


    public String findByTitle() {
        return recipeRepository.findByTitle("Burgoo").toString();
    }
}
