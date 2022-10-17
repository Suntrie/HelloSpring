package com.example.hellospring.services;

import com.example.hellospring.domain.entities.Vet;
import com.example.hellospring.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class VetService {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final VetRepository vetRepository;
    private final JDBCConnService connService;

    private List<Vet> modelVets;

    {
        try {
            modelVets = new ArrayList<>(Arrays.asList(Vet.builder()
                    .animalKind("cat")
                    .customerId(76L)
                    .animalPassportNumber(72L)
                    .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
                    .animalName("hh43-113-joly1")
                    .build(), Vet.builder()
                    .animalKind("cat")
                    .customerId(90L)
                    .animalPassportNumber(80L)
                    .timestamp(dateFormat.parse("2020-01-01").getTime() * 1000)
                    .animalName("hh43-113-joly2")
                    .build(), Vet.builder()
                    .animalKind("cat")
                    .customerId(47L)
                    .animalPassportNumber(74L)
                    .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
                    .animalName("hh43-113-joly3")
                    .build(), Vet.builder()
                    .animalKind("cat")
                    .customerId(7999L)
                    .animalPassportNumber(87L)
                    .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
                    .animalName("hh43-113-joly4")
                    .build(), Vet.builder()
                    .animalKind("cat")
                    .customerId(19L)
                    .animalPassportNumber(87L)
                    .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
                    .animalName("hh43-113-joly5")
                    .build()));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional(rollbackFor = Exception.class) // no real rollback
    public Long generate100kVetByJavaSpec() throws Exception {

        // transactions are not fully supported at all, and here it's just a 'fake transaction'
        // "INSERT into one partition in one table of MergeTree family up to max_insert_size rows is transactional (ACID)"
        // todo: insert speed measurement
        // replacing tree delete?

        var modelVetsSize = modelVets.size();
        List<Vet> vets = new ArrayList<>();

        for (int i = 0; i < 100000; i += modelVetsSize) {
            Long number = (long) i;
            AtomicInteger index = new AtomicInteger();
            var partBatch = modelVets.stream().map(it -> Vet.builder()
                    .animalName(it.getAnimalName())
                    .animalKind(it.getAnimalKind())
                    .timestamp(it.getTimestamp())
                    .customerId(number + index.get())
                    .animalPassportNumber(number + index.getAndIncrement())
                    .build()).toList();
            vets.addAll(partBatch);
        }

        Long startMillis = System.currentTimeMillis();

        // A different object with the same identifier value was already associated with the session
        // Is it possible to have more than once the same row within the same data batch?
        vetRepository.save(vets); // 1 statement (select) + 1 batch
        Long endMillis = System.currentTimeMillis();

        Long delta = endMillis - startMillis;
        log.info("Batch insert time: {}", delta);

        //vetRepository.saveAll(vets); // 6 statements (select) + 1 batch

        return delta;
        //var actualVets = vetRepository.findAll();
        //throw new Exception("e");
    }

    @Transactional(rollbackFor = Exception.class) // no real rollback (checked for jdbc sepately)
    public Long generate100kVetByJDBC() throws Exception {
        PreparedStatement preparedStatement;

        Connection connection = connService.getConn();
        connection.setAutoCommit(true);

        String CUSTOMER_ID = "customerId";
        String TIMESTAMP = "timestamp";
        String ANIMAL_NAME = "animalName";
        String ANIMAL_KIND = "animalKind";
        String ANIMAL_PASSPORT_NUMBER = "animalPassportNumber";

        String compiledQuery = String.format("INSERT INTO tutorial.vet(%s, " +
                        "%s, %s, %s, %s)" +
                        " VALUES" + "(?, ?, ?, ?, ?)", CUSTOMER_ID, TIMESTAMP, ANIMAL_NAME, ANIMAL_KIND,
                ANIMAL_PASSPORT_NUMBER);
        preparedStatement = connection.prepareStatement(compiledQuery);

        for (int i = 1; i <= 100000; i += modelVets.size()) {

            Long number = (long) i;
            AtomicInteger index = new AtomicInteger();

            modelVets.forEach(it -> {
                try {
                    preparedStatement.setLong(1, number + index.get());
                    preparedStatement.setLong(2, it.getTimestamp());
                    preparedStatement.setString(3, it.getAnimalName());
                    preparedStatement.setString(4, it.getAnimalKind());
                    preparedStatement.setLong(5, number + index.getAndIncrement());
                    preparedStatement.addBatch();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        long startMillis = System.currentTimeMillis();
        preparedStatement.executeBatch();
        //throw  new Exception("s");
        long endMillis = System.currentTimeMillis();

        Long delta = endMillis - startMillis;
        log.info("Batch insert time: {}", delta);

        preparedStatement.close();
        //connection.close();

        return delta;

    }

    public String getAllVets() {
        List<Vet> allVets = vetRepository.findAll();
        return "Alles klar";
    }
}
