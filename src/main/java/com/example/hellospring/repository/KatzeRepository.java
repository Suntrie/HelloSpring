package com.example.hellospring.repository;

import com.example.hellospring.domain.entities.Katze;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KatzeRepository extends JpaRepository<Katze, Integer> {
    Katze findByCatId(Integer id); // Маппинг чисел реальный
                                   // Запросы с группировкой (прикинуть схему и запросы на данные)
                                   // Насколько маппинг - надежный (почитать код + сформировать вопросы)
                                   // Параметры создания табл - почитать, обсудить

}
