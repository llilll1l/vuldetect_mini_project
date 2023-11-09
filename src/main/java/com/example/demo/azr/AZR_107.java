package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.AZ;
import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.*;

@Component
@Getter
@Setter
public class AZR_107 implements AZR_Scanner {

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
        AZR_107 azr107 = new AZR_107();
        azr107.allCommand();
    }

    public void allCommand() {

        setName("AZR_107");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("최신 버전 TLS 암호화 사용 여부 확인");
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
        //점검 방법: az webapp config show -g kisia-astron -n kisia-astron-appservice --query minTlsVersion
        //양호 값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Web/sites/kisia-astron-appservice --set properties.httpsOnly=true

        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        System.out.println(getName());
        System.out.println(jsonOutput.toPrettyString());

        setResult(azrScan()); //siteConfig 의 minTlsVersion 값이 "1.2" 가 아닐때 모두 취약
        //setResourceGroup(findWebAppResourceId());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"minTlsVersion\""};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);

    }

    public  Boolean azrScan() {
        return parseAndResult_2key_1value(jsonOutput,"siteConfig", "minTlsVersion", "1.2");
    }

    @Override
    public String vulCheck() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.WEBAPP
                +AZRCommand.SPACE
                +AZRCommand.SHOW
                +AZRCommand.SPACE
                +AZRCommand.G
                +AZRCommand.SPACE
                +AZRCommand.inputResourceGroupName
                +AZRCommand.SPACE
                +AZRCommand.N
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.MINTLSVERSION;
    }

    @Override
    public String secSetting() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.WEBAPP
                +AZRCommand.SPACE
                +AZRCommand.SET
                +AZRCommand.SPACE
                +AZRCommand.G
                +AZRCommand.SPACE
                +AZRCommand.inputResourceGroupName
                +AZRCommand.SPACE
                +AZRCommand.N
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.MIN_TLS_VERSION
                +AZRCommand.SPACE
                +AZRCommand.MIN_TLS_VERSION_STANDARD;

    }
}
