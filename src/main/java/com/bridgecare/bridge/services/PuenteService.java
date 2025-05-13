package com.bridgecare.bridge.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.bridgecare.bridge.repositories.PuenteRepository;
import com.bridgecare.common.models.entities.Puente;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PuenteService {
    @Autowired
    private PuenteRepository repo;

    @Autowired
    private RestTemplate restTemplate;

    private final String INVENTARIO_SERVICE_URL = "http://localhost:8082/api/inventario/delete/by-puente/";
    private final String INSPECCION_SERVICE_URL = "http://localhost:8083/api/inspeccion/delete/by-puente/";

    public void deletePuenteCascada(Long puenteId) {
    System.out.println("🔁 Iniciando eliminación en cascada del puente ID: " + puenteId);

    // Eliminar inspección
    try {
        System.out.println("📡 Llamando a inspeccion service para eliminar inspecciones...");
        restTemplate.exchange(
            INSPECCION_SERVICE_URL + puenteId,
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            String.class
        );
        System.out.println("✅ Inspección eliminada (si existía)");
    } catch (Exception e) {
        System.out.println("❌ Error al eliminar inspección: " + e.getMessage());
    }

    // Eliminar inventario
    try {
        System.out.println("📡 Llamando a inventario service para eliminar inventarios...");
        restTemplate.exchange(
            INVENTARIO_SERVICE_URL + puenteId,
            HttpMethod.DELETE,
            HttpEntity.EMPTY,
            String.class
        );
        System.out.println("✅ Inventario eliminado (si existía)");
    } catch (Exception e) {
        System.out.println("❌ Error al eliminar inventario: " + e.getMessage());
    }

    // Eliminar el puente
    try {
        System.out.println("🗑️ Eliminando puente desde PuenteService...");
        repo.deleteById(puenteId);
        System.out.println("✅ Puente eliminado");
    } catch (Exception e) {
        System.out.println("❌ Error al eliminar puente: " + e.getMessage());
        throw new RuntimeException("Error eliminando el puente: " + e.getMessage());
    }
}


    public List<Puente> getAllPuentes() {
        return repo.findAll();
    }

    public Puente getPuente(Long id) {
        return repo.findById(id).orElse(null);
    }

    public Puente createPuente(Puente puente) throws IOException {
        return repo.save(puente);
    }

    public Puente updatePuente(Long id, Puente puente) throws IOException {
        Optional<Puente> existingPuente = repo.findById(id);
        if (!existingPuente.isPresent()) {
            throw new EntityNotFoundException("Puente with ID " + id + " not found.");
        }

        Puente puenteToUpdate = existingPuente.get();
        puenteToUpdate.setNombre(puente.getNombre());
        puenteToUpdate.setIdentif(puente.getIdentif());
        puenteToUpdate.setCarretera(puente.getCarretera());
        puenteToUpdate.setPr(puente.getPr());
        puenteToUpdate.setRegional(puente.getRegional());

        return repo.save(puenteToUpdate);
    }

    public void deletePuente(Long id) {
        repo.deleteById(id);
    }


    public List<Puente> searchPuentes(String keyword) {
        return repo.searchPuentes(keyword);
    }
}
