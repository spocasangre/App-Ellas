package com.example.project.services.impls;

import com.example.project.models.entities.Location;
import com.example.project.repositories.LocationRepository;
import com.example.project.services.LocationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.tools.jconsole.JConsoleContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class LocationServiceImpl implements LocationService {

    @Autowired
    LocationRepository locationRepository;

    @Override
    @Async
    public Location getLocationDetail(Long id) {
        return locationRepository.getLocationDetail(id);
    }

    @Override
    public Location getLocationDetailByMapId(String id) {
        return locationRepository.getLocationDetailByMapId(id);
    }

    @Override
    @Async
    public Void saveLocation(Location location) {
        locationRepository.save(location);
        return null;
    }

    @Override
    @Async
    public List<Location> findAll() {
        return locationRepository.findAll();
    }

    @Override
    @Async
    public Location findById(Long id) {
        Location location = locationRepository.findById(id).get();
        return location;
    }

    @Override
    @Async
    public String inversedGeo(Double lat, Double lon) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String streetResponse;
        String inversedGeoResourceUrl = "https://maps.googleapis.com/maps/api/geocode/json?latlng="+lat+","+lon+
                "&result_type=premise|subpremise&key=AIzaSyBB6hPAsu6ZghjrgYhAUFaGI3hGkRdqmY8";
        ResponseEntity<String> response
                = restTemplate.getForEntity(inversedGeoResourceUrl, String.class);
        System.out.println(response.getBody());
        ObjectMapper mapper = new ObjectMapper();

        JsonNode root = mapper.readTree(response.getBody());
        JsonNode addressComponents = root.at("/results/");

        /*JsonNode results = root.path("results");
        JsonNode getFirstResult = root.path(0);
        JsonNode address_components = root.path("address_components");*/
        //return addressComponents.get("formatted_address").asText();
        System.out.println(addressComponents.asText());
        return addressComponents.asText();
    }
}

