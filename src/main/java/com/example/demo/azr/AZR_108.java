package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.extractFields;
import static com.example.demo.azr.CommonMethod.parseAndResult_1key;

@Component
@Getter
@Setter
public class AZR_108 implements AZR_Scanner {
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
    public static void main(String[] args) throws IOException, InterruptedException {
        AZR_108 azr108 = new AZR_108();
        azr108.allCommand();
    }

    public void allCommand() throws IOException {
        setName("AZR_108");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("웹/앱 클라이언트 인증서(수신측) 상태 확인");
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
        //점검 방법: az resource show --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Web/sites/kisia-astron-appservice --query properties.clientCertEnabled
        //양호 값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Web/sites/kisia-astron-appservice --set properties.clientCertEnabled=true

        //clientCertEnabled : false 취약
        //clientCertEnabled : true 양호
        jsonOutput = CommonMethod.executeProcessAndGetJsonOutput_2(commandAndArgs);
        //System.out.println(jsonOutput.toPrettyString());

        setResult(azrScan());
        //setResourceGroup(findWebAppResourceId());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"clientCertEnabled\""};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);

    }

    public  Boolean azrScan() throws IOException {
        return parseAndResult_1key(jsonOutput, "clientCertEnabled","false", "true" );

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
                +AZRCommand.PROPERTIES
                +AZRCommand.DOT
                +AZRCommand.CLIENT_CERT_ENABLE;
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
                +AZRCommand.CLIENT_CERT_ENABLE
                +AZRCommand.EQUAL
                +AZRCommand.TRUE;
    }



}
