package com.example.demo.service;

import com.example.demo.entity.Diagnosis;
import com.example.demo.entity.Project;
import com.example.demo.repository.DiagnosisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisService {


    private  final DiagnosisItemService diagnosisItemService;
    private  final DiagnosisRepository diagnosisRepository;

    public void createDiagnosis(Project project, int round) {
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setProject(project);
        diagnosis.saveRound();
        diagnosis.setDiagnosisItemList(diagnosisItemService.findByListByProjectAndRound(project, round)); //바꾸기
        diagnosis.calResult();
        diagnosisRepository.save(diagnosis);
    }

    public List<Integer> findRoundByProjectId(Long projectId) {
        return diagnosisRepository.findRoundByProjectId(projectId);
    }

    public List<Diagnosis> findByProjectId(Long projectId) {
        return diagnosisRepository.findByProjectId(projectId);
    }

    public int findDiagnosisResultByProjectIdAndRoundsService(Project project, int round){
        return diagnosisRepository.findDiagnosisResultByProjectIdAndRound(project, round);
    }

    public Diagnosis findDiagnosisByProjectIdAndRoundsService(Project project, int round){
        return diagnosisRepository.findDiagnosisByProjectIdAndRound(project, round);
    }

}
