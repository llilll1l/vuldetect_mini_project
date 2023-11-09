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
public class AZR_041 implements AZR_Scanner{
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
        AZR_041 azr041 = new AZR_041();
        azr041.allCommand();
    }


    @Override
    public void allCommand() {
        setName("AZR_041");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("스토리지 계정 내 기본 네트워크 액세스 제한 설정");
        setCategory("Access Control");



        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.ACCOUNT);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.NAME);
        //commandAndArgs.add("--name");
        commandAndArgs.add(AZRCommand.inputAccountName);
        //commandAndArgs.add("kisiaastronaccount");

        //networkacls과 networkruleset의 차이점이 무엇이죠..?
        //networkruleset은 update가 안되어 networkalcs로 update하는 방법사용
        //API: az storage account show --name <MyStorageAccount>
        //API: az storage account show --name kisiaastronaccount
        //RES: “defaultAction” : “Allow” 일시 "양호”
        //점검 방법: az storage account show --name <MyStorageAccount> --query networkRuleSet.defaultAction
        //az storage account show --name kisiaastronaccount --query networkRuleSet.defaultAction
        //보안 설정 방법: az resource update --ids /subscriptions/{SubID}/resourceGroups/{ResourceGroup}/providers/Microsoft.Storage/storageAccounts/{StorageAccount} --set properties.networkAcls.defaultAction=Allow
        //networkRulset 노드의 defaultAction 키의 밸류값이 Allow면 취약 Deny면 양호

        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        System.out.println(getName());
        System.out.println(jsonOutput.toPrettyString());


        //setResourceGroup(findStorageResource()); //setresourceGroup 해주기
        setResult(azrScan());

        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"defaultAction"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput.deepCopy());


        //이제 얘네 필요없음
        //String jsonrspns = jsonOutput.toPrettyString(); //맨처음 response 받은 json값
        //String jsonR =  jsonrspns.replace("[", "").replace("]", "");
        //setJsonResponse(jsonR); //처음 저장된 response값


    }


    @Override
    public Boolean azrScan(){
        return parseAndResult_2key(jsonOutput,"networkRuleSet", "defaultAction", "Allow", "Deny");
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
                +AZRCommand.DEFAULTACTION;
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
                +AZRCommand.DEFAULTACTION
                +AZRCommand.EQUAL
                +AZRCommand.DENY;
    }


}







