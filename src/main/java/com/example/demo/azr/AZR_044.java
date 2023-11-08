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
@Getter @Setter
public class AZR_044 implements AZR_Scanner {
    private JsonNode jsonOutput;

    public String name;

    public String VulnerabilityGrade;

    public int vulnerabilityLevel;

    public String description;

    public String category;

    public  Boolean Result;

    public String ResourceGroup;

    public String jsonArray;

    public List<JsonNode> jsonOutputList = new ArrayList<>();


    public static void main(String[] args) throws IOException {
        AZR_044 azr044 = new AZR_044();
        azr044.allCommand();
    }

    public void allCommand() throws IOException {
        setName("AZR_044");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("Blob 서비스의 읽기/쓰기/삭제 스토리지 로깅 설정");
        setCategory("Logging");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.LOGGING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.SERVICES);
        commandAndArgs.add(AZRCommand.B);
        commandAndArgs.add(AZRCommand.ACCOUNT_NAME);
        commandAndArgs.add(AZRCommand.inputAccountName);
        //commandAndArgs.add("kisiaastronaccount");
        commandAndArgs.add(AZRCommand.QUERY);
        commandAndArgs.add(AZRCommand.BLOB);

        //API: az storage logging show --services b --account-name <StorageAccountName> --query blob
        //RES: “delete”: “true” , “read”: “true”, “write”: “true” 일시 “양호”
        //점검 방법: az storage logging show --services b --account-name kisiaastronaccount --query blob.delete
        //          az storage logging show --services b --account-name kisiaastronaccount --query blob.read
        //          az storage logging show --services b --account-name kisiaastronaccount --query blob.write
        //보안 설정 방법: az storage logging update --account-name kisiaastronaccount --account-key KWPDhbN6u1OS0ZigyfFV5DYNhzm4NbUy9cyy/K0MvF4yhio/7d6ZNwyhowcJixsKwOCsD4hF/zrh+AStNXAxiw== --services b --log rwd --retention 90
        //Azure Storage 계정의 Blob 서비스에 대한 로깅을 활성화하고, 읽기(read), 쓰기(write), 삭제(delete) 작업에 대한 로깅을 활성화하며, 로그를 90일 동안 보관하도록 설정

        jsonOutput = executeProcessAndGetJsonOutput_3(commandAndArgs);
        System.out.println(jsonOutput.toPrettyString());
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


        //networkRulet 노드의 delete,read,write 키의 밸류값이 false면 취약 true면 양호
        //각각의 결과 중 하나라도 취약하면 취약하게 만들 생각
        //하나라도 취약하면 취약한것.

        }


    public Boolean azrScan() throws IOException {
        Boolean result1 = parseAndResult_1key(jsonOutput, "delete", "false", "true");
        Boolean result2 = parseAndResult_1key(jsonOutput, "read", "false", "true");
        Boolean result3 = parseAndResult_1key(jsonOutput, "write", "false", "true");
        return (result1 && result2 && result3);
        // result1, result2, result3 중 하나라도 "취약"이면 "취약" 반환함
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
                +AZRCommand.B
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.QUERY
                +AZRCommand.SPACE
                +AZRCommand.BLOB;
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
                +"MyAccountKey입력"
                +AZRCommand.SPACE
                +AZRCommand.SERVICES
                +AZRCommand.SPACE
                +AZRCommand.B
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
