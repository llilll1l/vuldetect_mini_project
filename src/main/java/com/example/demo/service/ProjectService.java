package com.example.demo.service;

import com.example.demo.entity.Compliance;
import com.example.demo.entity.Project;
import com.example.demo.repository.CompliaceRepository;
import com.example.demo.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final CompliaceRepository compliaceRepository;

    public List<Project> findAll() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    public List<Compliance> findComplianceList() {
        List<Compliance> complianceList =  compliaceRepository.findAll();
        return complianceList;
    }

    //@Transactional
    public void createAndSaveProject(Project project) {
        projectRepository.save(project);
    }

    public void removeProject(Project project) {
        projectRepository.remove(project);
    }

   // @Transactional
    public void updateProject(Project project) {
        projectRepository.update(project);
    }

   // @Transactional
    public void deleteProjectById(Long projectId) {
        projectRepository.remove(projectRepository.findById(projectId));
    }

    public Project findById(Long projectId) {
        Project project = projectRepository.findById(projectId);
        return project;
    }
}
