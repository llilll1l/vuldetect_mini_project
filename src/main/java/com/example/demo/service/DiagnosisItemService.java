package com.example.demo.service;

import com.example.demo.azr.AZR_Scanner;
import com.example.demo.entity.AZRDescription;
import com.example.demo.entity.DiagnosisItem;
import com.example.demo.entity.Project;
import com.example.demo.repository.DiagnosisItemRepository;
import com.example.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DiagnosisItemService {

    private final DiagnosisItemRepository diagnosisItemRepository;
    private final ProjectRepository projectRepository;

    public void save (DiagnosisItem diagnosisItem) {diagnosisItemRepository.save(diagnosisItem);}

    public void addDiagnosisItem(AZR_Scanner azrScanner, Project project, AZRDescription azrDescription) throws IOException {
        DiagnosisItem diagnosisItem = new DiagnosisItem();
        diagnosisItem.addDiagnosisItem(azrScanner, project, azrDescription);
        diagnosisItemRepository.save(diagnosisItem);
    }

    public void addDiagnosisItem2(AZR_Scanner azrScanner, Project project, AZRDescription azrDescription) throws IOException {
        DiagnosisItem diagnosisItem = new DiagnosisItem();
        diagnosisItem.addDiagnosisItem2(azrScanner, project, azrDescription);
        diagnosisItemRepository.save(diagnosisItem);
    }

    public List<DiagnosisItem> findAll(){
        return diagnosisItemRepository.findAll();
    }

    public void removeAll() {
        List<DiagnosisItem> diagnosisItemList = diagnosisItemRepository.findAll();
        for (DiagnosisItem diagnosis: diagnosisItemList) {
            diagnosisItemRepository.remove(diagnosis);
        }
    }


    public DiagnosisItem findByName(String name) {
        return diagnosisItemRepository.findByName(name);
    }

    public List<DiagnosisItem> findByListByProjectAndRound(Project project, int round){
        List<DiagnosisItem> diagnosisItemList = diagnosisItemRepository.findListByProjectIdAndRound(project, round);
        return  diagnosisItemList;

    }

    public List<DiagnosisItem> findListByProjectId(Project project){
        return diagnosisItemRepository.findListByProjectId(project);
    }


}



