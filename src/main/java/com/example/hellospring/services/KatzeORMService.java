package com.example.hellospring.services;

import com.example.hellospring.domain.entities.Katze;
import com.example.hellospring.repository.KatzeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KatzeORMService {

    private final KatzeRepository katzeRepository;

    public String getFirstKatzen() {
        Katze katze = katzeRepository.findByCatId(1);
        return katze.toString();
    }

}
