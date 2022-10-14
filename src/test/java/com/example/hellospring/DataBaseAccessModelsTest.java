/*
package com.example.hellospring;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE) //to run against a real database
@AutoConfigureMockMvc
public class DataBaseAccessModelsTest {
    @Autowired
    protected MockMvc mockMvc;

    @Test
    public void calcAvgAggregationTimeHibernate() throws Exception {

        var t= mockMvc.perform(get("/api/hibernate/load/java-spec/aggregates"));
        System.out.println();

    }


}
*/
