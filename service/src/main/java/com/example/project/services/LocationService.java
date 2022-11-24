package com.example.project.services;

import com.example.project.models.entities.Location;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface LocationService {
    Location getLocationDetail(Long id);
    Location getLocationDetailByMapId(String id);
    Void saveLocation(Location location);
    List<Location> findAll();
    Location findById(Long id);
    String inversedGeo(Double lat, Double lon) throws JsonProcessingException;
}
