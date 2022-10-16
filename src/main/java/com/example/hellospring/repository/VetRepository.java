package com.example.hellospring.repository;

import com.example.hellospring.domain.entities.Vet;
import com.example.hellospring.domain.entities.helpers.VetId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
@Component
@Repository
public class VetRepository extends SimpleJpaRepository<Vet, VetId> {

    @Autowired
    private final EntityManager entityManager;

    public VetRepository(EntityManager entityManager) {
        super(Vet.class, entityManager);
        this.entityManager = entityManager;
    }

    @Transactional
    public List<Vet> save(List<Vet> things) {
        things.forEach(entityManager::persist);
        return things;
    }
}
