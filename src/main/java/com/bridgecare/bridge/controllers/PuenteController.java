package com.bridgecare.bridge.controllers;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bridgecare.bridge.services.PuenteService;
import com.bridgecare.common.models.entities.Puente;

@RestController
@RequestMapping("/api/puentes")
public class PuenteController {
    @Autowired
    private PuenteService service;

    @GetMapping("/all")
    public ResponseEntity<List<Puente>> getAllPuentes() {
        return new ResponseEntity<>(service.getAllPuentes(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Puente> getPuente(@PathVariable Long id) {
        Puente puente = service.getPuente(id);
        if (puente != null) {
            return new ResponseEntity<>(puente, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addPuente(@RequestPart Puente puente, @RequestPart MultipartFile imageFile) {
        try {
            Puente puente1 = service.createPuente(puente);
            return new ResponseEntity<>(puente1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    // @GetMapping("/puente/{puenteId}/image")
    // public ResponseEntity<byte[]> getImageByPuenteId(@PathVariable Long puenteId) {
    //     Puente puente = service.getPuente(puenteId);
    //     byte[] imageFile = puente.getImageData();

    //     return ResponseEntity.ok().contentType(MediaType.valueOf(puente.getImageType())).body(imageFile);
    // }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePuente(@PathVariable Long id, @RequestPart Puente puente, @RequestPart MultipartFile imageFile) {
        Puente puente1 = null;
        try {
            puente1 = service.updatePuente(id, puente);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
        if (puente1 != null) {
            return new ResponseEntity<>("updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePuente(@PathVariable Long id) {
        Puente puente = service.getPuente(id);
        if (puente != null) {
            service.deletePuente(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Puente not found", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Puente>> searchPuentes(@RequestParam String keyword) {
        List<Puente> puentes = service.searchPuentes(keyword);
        return new ResponseEntity<>(puentes, HttpStatus.OK);
    }
}
