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
public class AZR_042 implements AZR_Scanner {
    private JsonNode jsonOutput;

    public String name;

    public String VulnerabilityGrade;

    public int vulnerabilityLevel;

    public String description;

    public String category;

    public Boolean Result;

    public String ResourceGroup;

    public String jsonArray;

    public List<JsonNode> jsonOutputList = new ArrayList<>();


    public static void main(String[] args) {
        AZR_042 azr042 = new AZR_042();
        azr042.allCommand();

    }



    public void allCommand() {
        setName("AZR_042");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("스토리지 계정 내 서비스 액세스 제한 설정");
        setCategory("Access Control");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.ACCOUNT);
        //commandAndArgs.add(AZRCommand.LIST);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.NAME);
        commandAndArgs.add(AZRCommand.inputAccountName);

        //Azure Storage Account의 networkRuleSet.bypass 속성은 Azure Blob Storage 또는 파일 서비스의 네트워크 규칙을 우회하는 규칙을 설정하는 데 사용.
        //networkRulset 노드의 bypass 키의 밸류값이 AzureServices면 양호 None이면 취약
        //API : az storage account show --name <MyStorageAccount>
        //az storage account show --name kisiaastronaccount
        //RES : ”bypass” : “AzureServices” 일시 “양호”
        //점검 방법 : az storage account show --name <MyStorageAccount> --query networkRuleSet.bypass
        //양호값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Storage/storageAccounts/kisiaastronaccount --set properties.networkAcls.bypass=Azureservices
        //취약값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/Microsoft.Storage/storageAccounts/kisiaastronaccount --set properties.networkAcls.bypass=None
        //취약값 안됨.... 더 알아보자

        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        System.out.println(getName());
        System.out.println(jsonOutput.toPrettyString());

        //setResourceGroup(findStorageResource());
        setResult(azrScan());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());


        String[] targetFields = {"bypass"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());


        jsonOutputList.add(jsonOutput);

    }


    public Boolean azrScan() {
        return parseAndResult_2key(jsonOutput, "networkRuleSet", "bypass", "None", "AzureServices");
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
                +AZRCommand.NETWORKACLS
                +AZRCommand.DOT
                +AZRCommand.BYPASS;
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
                +AZRCommand.NETWORKACLS
                +AZRCommand.DOT
                +AZRCommand.BYPASS
                +AZRCommand.EQUAL
                +AZRCommand.AZURESERVICES;
    }


}
