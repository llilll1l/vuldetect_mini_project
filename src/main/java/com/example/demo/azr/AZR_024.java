package com.example.demo.azr;

import com.fasterxml.jackson.core.JsonProcessingException;
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
public class AZR_024 implements AZR_Scanner{
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

    public static void main(String[] args) throws JsonProcessingException {
        AZR_024 azr024 = new AZR_024();
        azr024.allCommand();
    }


    @Override
    public void allCommand() throws JsonProcessingException {
        setName("AZR_024");
        setVulnerabilityGrade("H");
        setVulnerabilityLevel(3);
        setDescription("앱 서비스용 MS Defender On 설정 확인");
        setCategory("Security Solutions");


        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add(AZRCommand.POWERSHELL);
        commandAndArgs.add(AZRCommand.AZ);
        commandAndArgs.add(AZRCommand.SECURITY);
        commandAndArgs.add(AZRCommand.PRICING);
        commandAndArgs.add(AZRCommand.SHOW);
        commandAndArgs.add(AZRCommand.N);
        commandAndArgs.add(AZRCommand.APPSERVICES);

        //API : az security pricing show -n AppServices
        //점검 방법 : az security pricing show -n appservices --query pricingTier
        //보안 설정 방법 : az resource update --ids /subscriptions/{subscription-id}/resourceGroups/{resource-group-name}/providers/{resource-provider-namespace}/{resource-type}/{resource-name} --set properties.pricingTier=standard
        //az security pricing create -n Appservices --tier "Standard"
        //az security pricing create -n Appservices --tier "free"
        //az resource update --ids /subscriptions/{subscription-id}/resourceGroups/{resource-group-name}/providers/{resource-provider-namespace}/{resource-type}/{resource-name} --set tags.key=value
        //양호 값 update : az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/providers/Microsoft.Security/pricings/AppServices --set properties.pricingTier=standard
        //취약 값 update :az resource update --ids /subscriptions/65a2081f-ab92-4edc-84cd-1db788b315eb/providers/Microsoft.Security/pricings/AppServices --set properties.pricingTier=free


        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);



        setResult(azrScan());
        AZRCommand.idValue = jsonOutput.get("id").asText();
        ((ObjectNode) jsonOutput).put("dgnssEntityKey", idValue);
        ((ObjectNode) jsonOutput).put("dgnssEntityStatus", getResult());

        String[] targetFields = {"\"pricingTier\""};
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