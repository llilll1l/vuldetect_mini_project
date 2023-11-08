package com.example.demo.controller;

import com.example.demo.azr.*;
import com.example.demo.dto.DiagnosisItem_DTO;
import com.example.demo.dto.VulnerabilityAndPassCount_DTO;
import com.example.demo.entity.*;
import com.example.demo.repository.AZRDescriptionRepository;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.CommonMethod.findStorageResource;
import static com.example.demo.azr.CommonMethod.findWebAppResourceId;

@Controller
@RequiredArgsConstructor
public class ProjectController {

    private  final ProjectService projectService;
    private  final ComplianceService complianceService;
    private  final UserService userService;
    private final DiagnosisItemService diagnosisItemService;
    private final DiagnosisService diagnosisService;
    private  final AZRDescriptionRepository azrDescriptionRepository;


    @GetMapping("projectManage")
    public String getProjects(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projectManage";
    }

    @GetMapping("addProject")
    public String addProjectForm(Model model) {
        List<Compliance> complianceList = projectService.findComplianceList();
        model.addAttribute("complianceList", complianceList);
        return "addProject";
    }


    @PostMapping("addProject")
    public String addProject(@RequestParam(name = "name") String name,
                             @RequestParam(name = "compliance") String compliance,
                             Model model) {
        Project project = new Project();
        project.setName(name);
        Compliance compliance1 = complianceService.findByName(compliance);
        project.setCompliance(compliance1);
        //user는 setting 해줌......
        User user =  userService.findUserByName("mj");
        project.setUser(user);
        projectService.createAndSaveProject(project);

        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "projectManage";
    }

    @GetMapping("/deleteProject/{projectId}")
    public String deleteProject(@PathVariable Long projectId, Model model) {

        projectService.deleteProjectById(projectId);

        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        return "redirect:/projectManage";  // 프로젝트 관리 페이지로 리다이렉트
    }

    @GetMapping("/updateProject/{projectId}")
    public String updateProject(@PathVariable Long projectId, Model model) {

        Project project = projectService.findById(projectId);

        List<Compliance> complianceList = projectService.findComplianceList();
        model.addAttribute("complianceList", complianceList);
        // 프로젝트 정보를 addProject 페이지로 전달합니다.
        model.addAttribute("project", project);

        return "updateProject";
    }


    //
    @PostMapping("/updateProject/{projectId}")
    public String updateProjectComplete(@RequestParam(name = "name") String name,
                                        @RequestParam(name = "compliance") String compliance,
                                        @RequestParam(name = "projectId") Long projectId,
                                        Model model) {
        Project project = projectService.findById(projectId);
        project.setName(name);
        Compliance compliance1 = complianceService.findByName(compliance);
        project.setCompliance(compliance1);
        //user는  수동으로 setting ......
        User user =  userService.findUserByName("mj");
        project.setUser(user);
        projectService.updateProject(project);
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        return "redirect:/projectManage";

    }

    @GetMapping("/diagnosisProject/{projectId}")
    public String diagnosisProject(@PathVariable Long projectId,
                                   Model model) throws IOException {

        //다 서비스로 보내버리기
        Project project = projectService.findById(projectId);
        project.incrementRound();


        diagnosisItemService.addDiagnosisItem2(new AZR_024(), project, azrDescriptionRepository.findByName("AZR_024"));
        diagnosisItemService.addDiagnosisItem2(new AZR_026(), project, azrDescriptionRepository.findByName("AZR_026"));
        diagnosisItemService.addDiagnosisItem2(new AZR_029(), project, azrDescriptionRepository.findByName("AZR_029"));
        diagnosisItemService.addDiagnosisItem2(new AZR_031(), project, azrDescriptionRepository.findByName("AZR_031"));
        diagnosisItemService.addDiagnosisItem2(new AZR_033(), project, azrDescriptionRepository.findByName("AZR_033"));

        AZRCommand.storageAccountList = findStorageResource();
        diagnosisItemService.addDiagnosisItem(new AZR_041(), project, azrDescriptionRepository.findByName("AZR_041"));
        diagnosisItemService.addDiagnosisItem(new AZR_042(), project, azrDescriptionRepository.findByName("AZR_042"));
        diagnosisItemService.addDiagnosisItem(new AZR_043(), project, azrDescriptionRepository.findByName("AZR_043"));
        diagnosisItemService.addDiagnosisItem(new AZR_044(), project, azrDescriptionRepository.findByName("AZR_044"));
        diagnosisItemService.addDiagnosisItem(new AZR_045(), project, azrDescriptionRepository.findByName("AZR_045"));
        diagnosisItemService.addDiagnosisItem(new AZR_047(), project, azrDescriptionRepository.findByName("AZR_047"));

        diagnosisItemService.addDiagnosisItem2(new AZR_075(), project, azrDescriptionRepository.findByName("AZR_075"));
        diagnosisItemService.addDiagnosisItem2(new AZR_077(), project, azrDescriptionRepository.findByName("AZR_077"));
        diagnosisItemService.addDiagnosisItem2(new AZR_079(), project, azrDescriptionRepository.findByName("AZR_079"));
        diagnosisItemService.addDiagnosisItem2(new AZR_081(), project, azrDescriptionRepository.findByName("AZR_081"));

        AZRCommand.storageAccountList = findWebAppResourceId();
        diagnosisItemService.addDiagnosisItem(new AZR_105(), project, azrDescriptionRepository.findByName("AZR_105"));
        diagnosisItemService.addDiagnosisItem(new AZR_106(), project, azrDescriptionRepository.findByName("AZR_106"));
        diagnosisItemService.addDiagnosisItem(new AZR_107(), project, azrDescriptionRepository.findByName("AZR_107"));
        diagnosisItemService.addDiagnosisItem(new AZR_108(), project, azrDescriptionRepository.findByName("AZR_108"));

        diagnosisService.createDiagnosis(project, project.getRound());
        model.addAttribute(project);

        Diagnosis diagnosis = diagnosisService.findDiagnosisByProjectIdAndRoundsService(project, project.getRound());
        List<DiagnosisItem> diagnosisItemList = diagnosisItemService.findByListByProjectAndRound(project, project.getRound());

        for (DiagnosisItem diagnosisItem : diagnosisItemList) {
            diagnosisItem.setDiagnosis(diagnosis);
            diagnosisItemService.save(diagnosisItem);
        }


        return "redirect:/projectManage";
    }




//project 이름 같으면 안됨

    /*
    dashboard 페이지
     */

    @GetMapping("/dashBoard")
    public String initialDashboard(Model model) {
        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);
        //model.addAttribute("path", "/dashBoard");
        // 서버에서 diagnosisResult 값을 설정할 때 기본값 설정
        model.addAttribute("diagnosisResult", 0 + "%");
        model.addAttribute("complianceName", " ");

        return "dashBoard";
    }


    @GetMapping("/dashBoard/{projectId}")
    public String loadRound(@PathVariable Long projectId,
                                    Model model) {
        //Project selectProject = projectService.findById(projectId);
        model.addAttribute("selectedProjectId", projectId);

        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);


        List<Integer> roundList = diagnosisService.findRoundByProjectId(projectId);
        model.addAttribute("roundList", roundList);

        return "dashBoard";

    }



    //select 된 값 보이게 만들기
    @GetMapping("/dashBoard/{projectId}/{roundSelect}")
    public String loadDiagnosis(@PathVariable Long projectId,
                                @PathVariable Integer roundSelect,
                                Model model) {

        List<Project> projects = projectService.findAll();
        model.addAttribute("projects", projects);

        List<Integer> roundList = diagnosisService.findRoundByProjectId(projectId);
        model.addAttribute("roundList", roundList);

        Project project = projectService.findById(projectId);
        model.addAttribute("project", project);

        List<DiagnosisItem> diagnosisItemList = diagnosisItemService.findListByProjectId(project);


        List<DiagnosisItem> diagnosisItems = diagnosisItemService.findByListByProjectAndRound(project, roundSelect);
        model.addAttribute("diagnosisItems", diagnosisItems);

        Integer  diagnosisResult = diagnosisService.findDiagnosisResultByProjectIdAndRoundsService(project, roundSelect);
        model.addAttribute("diagnosisResult", diagnosisResult);

        model.addAttribute("selectedProjectId", projectId);
        model.addAttribute("selectedRoundvalue", roundSelect);


        // JsonNodeDTO 객체를 담을 리스트 생성
        List<AZR_DTO> jsonNodeDTOs = new AZR_DTO().azrDto(diagnosisItems);
        model.addAttribute("jsonNodeDTOs", jsonNodeDTOs);

        //project의 모든 회차 diagnosis가져가기 전 회차 차트 그리기에 사용.
        List<DiagnosisItem_DTO> diagnosisItemDtos = new DiagnosisItem_DTO().diagnosisItemDtoList(diagnosisItemList);
        model.addAttribute("diagnosisItemDTOs", diagnosisItemDtos);


        VulnerabilityAndPassCount_DTO vulnerabilityAndPassCountDto = new VulnerabilityAndPassCount_DTO(diagnosisItems);
        model.addAttribute("vulnerabilityAndPassCountDto", vulnerabilityAndPassCountDto);

        return "dashBoard";
    }
}
