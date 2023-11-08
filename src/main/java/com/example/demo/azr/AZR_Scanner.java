package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public interface AZR_Scanner {

    String getName();
    String getVulnerabilityGrade();
    int getVulnerabilityLevel();
    String getDescription();

    String getCategory();
    public Boolean azrScan() throws IOException; //바꿈

    public String vulCheck();
    public String secSetting();

    Boolean getResult();

    //이건 우선 적용해본거
    public void allCommand() throws IOException;

    public String getResourceGroup();

    public String getJsonArray();

    public List<JsonNode> getJsonOutputList();

    //AZR_041에만 우선 적용.

}
