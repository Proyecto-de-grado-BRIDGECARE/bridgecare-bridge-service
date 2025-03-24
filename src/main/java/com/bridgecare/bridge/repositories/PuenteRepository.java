package com.bridgecare.bridge.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgecare.common.models.entities.Puente;

public interface PuenteRepository extends JpaRepository<Puente, Long> {
    @Query("SELECT p from Puente p WHERE " +
            "LOWER(p.nombre) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.identif) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.carretera) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.pr) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.regional) LIKE LOWER(CONCAT('%', :keyword, '%'))")
    List<Puente> searchPuentes(String keyword);
}
