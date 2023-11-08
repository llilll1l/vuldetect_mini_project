package com.example.demo.repository;

import com.example.demo.azr.AZR_Scanner;
import com.example.demo.entity.DiagnosisItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class DiagnosisItemRepositoryTest {


    //private static final AZR_Scanner AZR_041 = new AZR_041();

    @Autowired
    private DiagnosisItemRepository diagnosisItemRepository;

    @Autowired
    private AZR_Scanner AZR_041;


    @Test
    @Rollback(value = false)
    public void addDiagnosisItemTest(){
        //AZR_041 azr041 = new AZR_041();
        DiagnosisItem azure041 = new DiagnosisItem();
        //azure041.addDiagnosisItem(AZR_041, project);
       // System.out.println(azure041);
        diagnosisItemRepository.save(azure041);

    }




}