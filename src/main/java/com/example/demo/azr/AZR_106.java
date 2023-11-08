package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.*;


@Component
@Getter
@Setter
public class AZR_106 implements AZR_Scanner{
    private JsonNode jsonOutput;

    public String name;

    public String VulnerabilityGrade;

    public int vulnerabilityLevel;

    public String description;

    public String category;

    public Boolean Result;

    public String resourceGroup;

    public String jsonArray;

    public List<JsonNode> jsonOutputList = new ArrayList<>();
    public static void main(String[] args) {
       AZR_106 azr106 = new AZR_106();
       azr106.allCommand();

    }

    public void allCommand() {

        setName("AZR_106");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("Azure 앱 서비스 내 웹/앱 HTTPS 리다이렉트 사용 확인");
        setCategory("ETC");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.WEBAPP);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.G);
        commandAndArgs.add(AZRCommand.inputResourceGroupName);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.inputAccountName);

        //az webapp show -g kisia-astron -n kisia-astron-appservice
        //az webapp show -g kisia-astron -n kisia-astron-appservice-second
        //점검 방법: az resource show --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Web/sites/kisia-astron-appservice --query properties.httpsOnly
        //양호 값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Web/sites/kisia-astron-appservice --set properties.httpsOnly=true


        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        //System.out.println(jsonOutput.toPrettyString());

        //httpsOnly값이 true일 시에만 "양호" 그 외에는 "취약"(false)

        setResult(azrScan());
        //setResourceGroup(findWebAppResourceId());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"httpsOnly\""};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);
    }
    public Boolean azrScan(){return parseAndResult_1key_1value(jsonOutput, "httpsOnly", "true" );}

    @Override
    public String vulCheck() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.RESOURCE
                +AZRCommand.SPACE
                +AZRCommand.SHOW
                +AZRCommand.SPACE
                +AZRCommand.IDS
                +AZRCommand.SPACE
                +idValue
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.SPACE
                +AZRCommand.PROPERTIES
                +AZRCommand.DOT
                +AZRCommand.HTTPS_ONLY;
    }

    @Override
    public String secSetting() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.RESOURCE
                +AZRCommand.SPACE
                +AZRCommand.UPDATE
                +AZRCommand.SPACE
                +AZRCommand.IDS
                +AZRCommand.SPACE
                +idValue
                +AZRCommand.SPACE
                +AZRCommand.SET
                +AZRCommand.SPACE
                +AZRCommand.PROPERTIES
                +AZRCommand.DOT
                +AZRCommand.HTTPS_ONLY
                +AZRCommand.EQUAL
                +AZRCommand.TRUE;
    }
}
