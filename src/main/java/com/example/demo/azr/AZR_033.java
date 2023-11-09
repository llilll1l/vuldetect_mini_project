package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.SPACE;
import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.*;

@Component
@Getter
@Setter
public class AZR_033 implements AZR_Scanner{
    private JsonNode jsonOutput;

    public String name;

    public String VulnerabilityGrade;

    public int vulnerabilityLevel;

    public String description;

    public String category;

    public Boolean Result; //이것은 필요할것인가..?

    public String resourceGroup;

    public String jsonArray;

    public List<JsonNode> jsonOutputList = new ArrayList<>();

    public static void main(String[] args)  {
        AZR_033 azr033 = new AZR_033();
        azr033.allCommand();
    }


    @Override
    public void allCommand() {
        setName("AZR_033");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("자동 프로비저닝 On 설정 확인");
        setCategory("Security Solutions");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.SECURITY);
        commandAndArgs.add(AZRCommand.AUTO_PROVISIONING_SETTING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.DEFAULT);

        //API: az security auto-provisioning-setting show -n default
        //RES: autoProvision "On"일 시 양호
        //점검 방법: az security auto-provisioning-setting show -n default --query autoProvision
        //보안 설정 방법: az resource update --ids /subscriptions/{subscription-id}/providers/{resource-provider-namespace}/{resource-type}/{resource-name} --set properties.autoProvision=On


        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        System.out.println(getName());
        System.out.println(jsonOutput.toPrettyString());

        setResult(azrScan());
        AZRCommand.idValue = jsonOutput.get("id").asText();
        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"autoProvision\""};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput.deepCopy());

    }


    @Override
    public Boolean azrScan(){
        return parseAndResult_1key_1value(jsonOutput,"autoProvision", "On");
    }

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
                +AZRCommand.AUTOPROVISION;
    }

    @Override
    public String secSetting() {
        return AZRCommand.AZ
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
                +AZRCommand.AUTOPROVISION
                +AZRCommand.EQUAL
                +AZRCommand.ON;
    }

}