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
public class AZR_045 implements AZR_Scanner{

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
        AZR_045 azr045 = new AZR_045();
        azr045.allCommand();

    }

    public void allCommand() {
        setName("AZR_045");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("테이블 서비스의 읽기/쓰기/삭제  스토리지 로깅 설정");
        setCategory("Logging");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.LOGGING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.SERVICES);
        commandAndArgs.add(AZRCommand.T);
        commandAndArgs.add(AZRCommand.ACCOUNT_NAME);
        commandAndArgs.add(AZRCommand.inputAccountName);

        //API: az storage logging show --services t --account-name <myAccountName> --query table
        //RES: “delete”: “true” , “read”: “true”, “write”: “true” 일시 “양호”
        //점검 방법:
        //  az storage logging show --services t --account-name <myAccountName> --query table.delete
        //  az storage logging show --services t --account-name <myAccountName> --query table.write
        //  az storage logging show --services t --account-name <myAccountName> --query table.read
        //보안 설정 방법: az storage logging update --account-name kisiaastronaccount --account-key KWPDhbN6u1OS0ZigyfFV5DYNhzm4NbUy9cyy/K0MvF4yhio/7d6ZNwyhowcJixsKwOCsD4hF/zrh+AStNXAxiw== --services t --log rwd --retention 90

        //table 노드의 delete 키의 밸류값이 false면 취약 true면 양호
        jsonOutput = executeProcessAndGetJsonOutput_3(commandAndArgs);

        setResult(azrScan());

        //setResourceGroup(findStorageResourceId());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"delete", "read", "write"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);


    }

    public Boolean azrScan() {
       Boolean result1 =  parseAndResult_2key(jsonOutput, "table", "delete", "false", "true");
       Boolean result2 =  parseAndResult_2key(jsonOutput, "table", "read", "false", "true");
       Boolean result3 =  parseAndResult_2key(jsonOutput, "table", "write", "false", "true");

        return (result1 && result2 && result3);
    }

    @Override
    public String vulCheck() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.LOGGING
                +AZRCommand.SPACE
                +AZRCommand.SHOW
                +AZRCommand.SPACE
                +AZRCommand.SERVICES
                +AZRCommand.SPACE
                +AZRCommand.T
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.SPACE
                +AZRCommand.TABLE;
    }

    @Override
    public String secSetting() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.LOGGING
                +AZRCommand.SPACE
                +AZRCommand.UPDATE
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_KEY
                +AZRCommand.SPACE
                +"<MyAccountKey>입력"
                +AZRCommand.SPACE
                +AZRCommand.SERVICES
                +AZRCommand.SPACE
                +AZRCommand.T
                +AZRCommand.SPACE
                +AZRCommand.LOG
                +AZRCommand.SPACE
                +AZRCommand.RWD
                +AZRCommand.SPACE
                +AZRCommand.RETENTION
                +AZRCommand.SPACE
                +AZRCommand.DAYS90;
    }


}









