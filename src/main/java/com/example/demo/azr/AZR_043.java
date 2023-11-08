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
import static com.example.demo.azr.CommonMethod.*;

@Component
@Getter
@Setter
public class AZR_043 implements AZR_Scanner{

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
    public static void main(String[] args) throws IOException {

    AZR_043 azr043 = new AZR_043();
    azr043.allCommand();

    }

    public void allCommand() throws IOException {

        setName("AZR_043");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("스토리지 내 일시 삭제(Soft Delete) 기능 활성화 확인");
        setCategory("ETC");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.BLOB);
        commandAndArgs.add(AZRCommand.SERVICE_PROPERTIES);
        commandAndArgs.add(AZRCommand.DELETE_POLICY);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.ACCOUNT_NAME);
        commandAndArgs.add(AZRCommand.inputAccountName);
        //commandAndArgs.add("kisiaastronaccount");
        commandAndArgs.add(AZRCommand.OUTPUT);
        commandAndArgs.add(AZRCommand.JSON);

        //API: az storage blob service-properties delete-policy show --account-name <StorageAccountName>
        //RES: “enabled” : “true” 일시 “양호” / false 취약
        //점검 방법: az storage blob service-properties delete-policy show --account-name kisiaastronaccount --auth-mode login --query enabled
        //보안 설정 방법:  az storage blob service-properties delete-policy update --account-name <myAccountName> --enable true (더이상 ids값으로 update를 지원하지 않는다.)

        //enabled 키의 밸류값이 false면 취약 true면 양호
        jsonOutput = executeProcessAndGetJsonOutput_3(commandAndArgs);

        //setResourceGroup(findStorageResource());
        setResult(azrScan());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"enabled"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);

    }

    public Boolean azrScan() throws IOException {
        return parseAndResult_1key(jsonOutput, "enabled", "false", "true");

    }

    @Override
    public String vulCheck() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.BLOB
                +AZRCommand.SPACE
                +AZRCommand.SERVICE_PROPERTIES
                +AZRCommand.SPACE
                +AZRCommand.DELETE_POLICY
                +AZRCommand.SPACE
                +AZRCommand.SHOW
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.AUTH_MODE
                +AZRCommand.SPACE
                +AZRCommand.LOGIN
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.SPACE
                +AZRCommand.ENABLED;
    }

    @Override
    public String secSetting() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.BLOB
                +AZRCommand.SPACE
                +AZRCommand.SERVICE_PROPERTIES
                +AZRCommand.SPACE
                +AZRCommand.DELETE_POLICY
                +AZRCommand.SPACE
                +AZRCommand.UPDATE
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.AUTH_MODE
                +AZRCommand.SPACE
                +AZRCommand.LOGIN
                +AZRCommand.SPACE
                +AZRCommand.DAYS_RETAINED
                +AZRCommand.SPACE
                +AZRCommand.DAYS30
                +AZRCommand.SPACE
                +AZRCommand.ENABLE
                +AZRCommand.SPACE
                +AZRCommand.TRUE;
    }

}
