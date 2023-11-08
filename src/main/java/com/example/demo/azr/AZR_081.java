package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public class AZR_081 implements AZR_Scanner{
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

    public JsonNode filteredObjects = null;



    public static void main(String[] args) {
        AZR_081 azr081 = new AZR_081();
        azr081.allCommand();
    }



    @Override
    public void allCommand() {
        setName("AZR_081");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("보안솔루션 (삭제) 이벤트 작업로그 경고 설정");
        setCategory("Monitoring");

        /*
        az monitor activity-log alert list
        --resource-group kisia-astron
        --query "[?condition.allOf[?equals == 'Microsoft.Security/securitySolutions/delete']]"

        점검 방법: az resource show --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/microsoft.insights/activitylogalerts/AZR_081 --query properties.enabled
        보안 설정 방법: az resource update --ids  "/subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/resourceGroups/kisia-astron/providers/microsoft.insights/activitylogalerts/AZR_081" --set properties.enabled=false
        바꿔보기
         */

        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.MONITOR);
        commandAndArgs.add(AZRCommand.ACTIVITY_LOG);
        commandAndArgs.add(AZRCommand.ALERT);
        commandAndArgs.add(AZRCommand.LIST);
        commandAndArgs.add(AZRCommand.RESOURCE_GROUP);
        //commandAndArgs.add(AZRCommand.inputResourceGroupName);
        commandAndArgs.add("kisia-astron");

        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);

        //delete정책이 없으면 [] null값이 나오기 때문에 '취약', enabled가 false 면 비활성화 되어있기 때문에 '취약'

        String equalValue = "Microsoft.Security/securitySolutions/delete";

        filteredObjects = filterJsonObjectsByValue(jsonOutput, equalValue);

        //System.out.println(":" + filteredObjects);

        setResult(azrScan());


        if (filteredObjects != null){
            AZRCommand.idValue = filteredObjects.get("id").asText();
            ((ObjectNode) filteredObjects).put("dgnssEntityKey", idValue);
            ((ObjectNode) filteredObjects).put("dgnssEntityStatus", getResult());

            String[] targetFields = {"enabled"};
            String valueAndKey = extractFields(filteredObjects, targetFields);
            ((ObjectNode) filteredObjects).put("dgnssResultByValueAndKey", valueAndKey);

            ((ObjectNode) filteredObjects).put("dgnssCheckCmd", vulCheck());
            ((ObjectNode) filteredObjects).put("dgnssSecSettingCmd", secSetting());

            jsonOutputList.add(filteredObjects.deepCopy());
        }else {

            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode filteredObjectNode = objectMapper.createObjectNode(); // 새로운 JSON 객체 생성

            AZRCommand.idValue = "생성규칙 없음";
            filteredObjectNode.put("dgnssEntityKey", AZRCommand.idValue);
            filteredObjectNode.put("dgnssEntityStatus", getResult());

            String[] targetFields = {"enabled"};
            //String valueAndKey = extractFields(filteredObjectNode, targetFields);
            filteredObjectNode.put("dgnssResultByValueAndKey", "null");

            filteredObjectNode.put("dgnssCheckCmd", vulCheck());
            filteredObjectNode.put("dgnssSecSettingCmd", secSetting());

            jsonOutputList.add(filteredObjectNode); // 리스트에 JSON 객체 추가

        }
        String result = jsonOutputList.toString();
        //System.out.println("jsonOutputlist : " + result);
    }


    @Override
    public Boolean azrScan(){
        if (filteredObjects != null) {
            JsonNode conditionNode = filteredObjects.path("condition");
            if (conditionNode != null && conditionNode.isObject()) {
                JsonNode allOfNode = conditionNode.path("allOf");
                if (allOfNode.isNull() || allOfNode.isEmpty() || allOfNode.asText().equals("null")) {
                    // "allOf"의 값이 null이거나 비어있을 경우 취약으로 판단
                    return false;
                }
            }
            // "allOf"의 값이 null이 아니거나 비어있지 않을 경우 "enabled" 값에 따라 판단
            boolean value = parseAndResult_1key_1value(filteredObjects,"enabled", "true");
            //System.out.println("enabled :" + value);
            return value;
        }
        // filteredObjects가 null인 경우 취약으로 판단
        System.out.println("enabled: null,취약");
        return false;
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
                +AZRCommand.ENABLED;
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
                +AZRCommand.ENABLED
                +AZRCommand.EQUAL
                +AZRCommand.TRUE;
    }

}