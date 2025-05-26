package com.bridgecare.bridge.services;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.bridgecare.bridge.repositories.PuenteRepository;
import com.bridgecare.common.models.entities.Puente;

import jakarta.persistence.EntityNotFoundException;

@Service
public class PuenteService {
    @Autowired
    private PuenteRepository repo;

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
