package com.example.demo.service;

import com.example.demo.entity.Compliance;
import com.example.demo.repository.CompliaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComplianceService {

    private final CompliaceRepository compliaceRepository;

    public Compliance findByName(String name) {
        return compliaceRepository.findByName(name);
    }
}
