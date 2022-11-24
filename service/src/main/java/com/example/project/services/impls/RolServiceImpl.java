package com.example.project.services.impls;

import com.example.project.models.entities.Rol;
import com.example.project.repositories.RolRepository;
import com.example.project.services.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    RolRepository rolRepository;

    @Override
    @Async
    public Rol findById(Long id) {
        return rolRepository.findById(id).get();
    }
}
