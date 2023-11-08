package com.example.demo.repository;

import com.example.demo.entity.Compliance;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CompliaceRepositoryTest {

    @Autowired
    private CompliaceRepository compliaceRepository;

    @Test @Rollback(value = false)
    public void createCompliance() {
        Compliance compliance1 = new Compliance();
        compliance1.setName("Azure CIS Benchmark");
        compliaceRepository.save(compliance1);

        Compliance compliance2 = new Compliance();
        compliance2.setName("Aws CIS Benchmark");
        compliaceRepository.save(compliance2);
    }

}