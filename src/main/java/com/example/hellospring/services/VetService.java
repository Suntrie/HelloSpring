package com.example.hellospring.services;

import com.example.hellospring.domain.entities.Vet;
import com.example.hellospring.repository.VetRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class VetService {

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final VetRepository vetRepository;

    @Transactional(rollbackFor=Exception.class) // no real rollback
    public void generate100kVet() throws Exception {

        var vets = Arrays.asList(Vet.builder()
                .animalKind("cat")
                .customerId(76L)
                .animalPassportNumber(72L)
                .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
                .animalName("hh43-113-joly1")
                .build(), Vet.builder()
                .animalKind("cat")
                .customerId(90L)
                .animalPassportNumber(80L)
                .timestamp(dateFormat.parse("2019-01-01").getTime() * 1000)
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
                .build());

        // transactions are not fully supported at all, and here it's just a 'fake transaction'
        // "INSERT into one partition in one table of MergeTree family up to max_insert_size rows is transactional (ACID)"
        // todo: try update + collapse manually -> insert speed measurement
        // replacing tree delete?
        vetRepository.save(vets); // 1 statement (select) + 1 batch
        //vetRepository.saveAll(vets); // 6 statements (select) + 1 batch

        var actualVets = vetRepository.findAll();
        throw new Exception("e");
    }

    public String getAllVets() {
        List<Vet> allVets = vetRepository.findAll();
        return "Alles klar";
    }
}
