package com.example.demo.repository;

import com.example.demo.entity.Project;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class ProjectRepositoryTest {
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CompliaceRepository compliaceRepository;

    @Test
    @Rollback(value = false)
    public void createProject() {
        Project project1 = new Project();
        project1.setName("pro1");
        project1.setUser(userRepository.findOne(1L));
        project1.setCompliance(compliaceRepository.findByName("Azure best practice"));
        projectRepository.save(project1);

    }


    @Test
    @Rollback(value = false)
    public void deleteProject() {
        Project project = projectRepository.findById(152L);
        projectRepository.remove(project);
    }
}