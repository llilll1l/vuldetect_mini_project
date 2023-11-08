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
public class AZR_029 implements AZR_Scanner{
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
        AZR_029 azr029 = new AZR_029();
        azr029.allCommand();
    }


    @Override
    public void allCommand() {
        setName("AZR_029");
        setVulnerabilityGrade("M");
        setVulnerabilityLevel(2);
        setDescription("컨테이너 레지스트리용 MS Defender가 활성화되어 있는 경우");
        setCategory("Security Solutions");

        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.SECURITY);
        commandAndArgs.add(AZRCommand.PRICING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.CONTAINER_REGISTRY);

        //API: az security pricing show -n containerregistry
        //RES: “pricingTier” : “Standard”면 양호, Free 면 취약
        //점검 방법: az security pricing show -n containerregistry --query pricingTier
        //보안 설정 방법:


        //networkRulet 노드의 defaultAction 키의 밸류값이 Allow면 취약 Deny면 양호
        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);

        setResult(azrScan());
        AZRCommand.idValue = jsonOutput.get("id").asText();
        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"pricingTier"};
        String valueAndKey = extractFields(jsonOutput, targetFields);
        ((ObjectNode) jsonOutput).put("dgnssResultByValueAndKey", valueAndKey);

        ((ObjectNode) jsonOutput).put("dgnssCheckCmd", vulCheck());
        ((ObjectNode) jsonOutput).put("dgnssSecSettingCmd", secSetting());

        jsonOutputList.add(jsonOutput.deepCopy());

    }


    @Override
    public Boolean azrScan(){
        return parseAndResult_1key_1value(jsonOutput,"pricingTier", "Standard");
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
                +AZRCommand.PRICING_TIER;
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
                +AZRCommand.PRICING_TIER
                +AZRCommand.EQUAL
                +AZRCommand.STANDARD;
    }

}