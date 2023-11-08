package com.example.demo.azr;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.example.demo.azr.AZRCommand.idValue;
import static com.example.demo.azr.CommonMethod.executeProcessAndGetJsonOutput_2;
import static com.example.demo.azr.CommonMethod.extractFields;


@Component
@Getter
@Setter
public class AZR_047 implements AZR_Scanner{

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
    public static void main(String[] args) throws JsonProcessingException {

       AZR_047 azr047 = new AZR_047();
       azr047.allCommand();

    }

    public void allCommand() {
        setName("AZR_047");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("스토리지 액세스 키 재생성 주기 점검");
        setCategory("Access Control");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.STORAGE);
        commandAndArgs.add(AZRCommand.ACCOUNT);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.inputAccountName);
        //commandAndArgs.add("kisiaastronaccount");

        //API: az storage account show -n <StorageAccountName> -- query keyPolicy
        //RES: “keyPolicy” : 90 이하, “null” 아닐시 양호
        //점검 방법: az storage account show -n <myAccountName> --query keyPolicy.keyExpirationPeriodInDays
        //보안 설정 방법:
        //az storage account keys renew --resource-group <myResourceGroup> --account-name <myAccountName> --key key1
        //&&
        //az storage account update -g <resourceGroup> -n <myAccountName> --set keyPolicy.keyExpirationPeriodInDays=60


        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        //System.out.println(jsonOutput.toPrettyString());
        setResult(azrScan());

        //setResourceGroup(findStorageResourceId());
        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"keyPolicy\" : null" ,"keyExpirationPeriodInDays"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput);

        // keyPolicy : null 이 아니어야 하고, keyPolicy가 설정 되어있다면 keyPolicy: expirationDay가 90 이하이어야 한다.
        // 90일 이상은 모두 취약
        // null, 90 이상 : 취약
        // 90일 이하 : 양호

    }

    public Boolean azrScan() {
        JsonNode keyPolicyNode = jsonOutput.get("keyPolicy");
        String  keyPolicyNodeToString = jsonOutput.get("keyPolicy").asText();
        System.out.println(keyPolicyNode);
            if (!(keyPolicyNodeToString.equals("null"))) {
                int expirationDays = keyPolicyNode
                        .path("keyExpirationPeriodInDays")
                        .asInt();

                if (expirationDays <= 90) {
                    System.out.println("90일 이하 : 양호");
                    return true;
                } else {
                    System.out.println("90일 이상 : 취약");
                    return false;
                }
            } else {    //keyPolicyNode.equals("null")
                System.out.println("null : 취약");
                return false;
            }

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
                +AZRCommand.KEYPOLICY
                +AZRCommand.DOT
                +AZRCommand.KEY_EXPIRATION_PERIOD_IN_DAYS;
    }

    @Override
    public String secSetting() {
        return  AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT
                +AZRCommand.SPACE
                +AZRCommand.KEYS
                +AZRCommand.SPACE
                +AZRCommand.RENEW
                +AZRCommand.SPACE
                +AZRCommand.RESOURCE_GROUP
                +AZRCommand.SPACE
                +AZRCommand.inputResourceGroupName
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT_NAME
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.KEY
                +AZRCommand.SPACE
                +AZRCommand.KEY1
                +AZRCommand.SPACE
                +AZRCommand.SPACE
                +AZRCommand.SPACE
                +AZRCommand.SPACE
                +AZRCommand.SPACE
                +AZRCommand.SPACE
                +"<br>"
                +AZRCommand.SPACE
                +"<br>"
                +AZRCommand.SPACE
                +AZRCommand.AZ
                +AZRCommand.SPACE
                +AZRCommand.STORAGE
                +AZRCommand.SPACE
                +AZRCommand.ACCOUNT
                +AZRCommand.SPACE
                +AZRCommand.UPDATE
                +AZRCommand.SPACE
                +AZRCommand.G
                +AZRCommand.SPACE
                +AZRCommand.inputResourceGroupName
                +AZRCommand.SPACE
                +AZRCommand.N
                +AZRCommand.SPACE
                +AZRCommand.inputAccountName
                +AZRCommand.SPACE
                +AZRCommand.SET
                +AZRCommand.SPACE
                +AZRCommand.KEYPOLICY
                +AZRCommand.DOT
                +AZRCommand.KEY_EXPIRATION_PERIOD_IN_DAYS
                +AZRCommand.EQUAL
                +AZRCommand.KEY_EXPIRATION_PERIOD_IN_DAYS60;

    }



}
