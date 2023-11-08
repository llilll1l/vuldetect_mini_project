package com.example.demo.repository;

import com.example.demo.entity.Diagnosis;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DiagnosisRepositoryTest {

    @Autowired
    private  DiagnosisRepository diagnosisRepository;
    @Autowired
    private  DiagnosisItemRepository diagnosisItemRepository;
    @Autowired
    private ProjectRepository projectRepository;


    @Test
    @Rollback(value = false)
    public void createDiagnosis() {
        Diagnosis diagnosis1 = new Diagnosis();
        diagnosis1.setDiagnosisItemList(diagnosisItemRepository.findAll());
        diagnosis1.setProject(projectRepository.findOne("2nd"));
        diagnosis1.getProject().incrementRound();
        diagnosis1.saveRound();
        diagnosis1.calResult();
        diagnosisRepository.save(diagnosis1);


    }
}