package com.example.demo.azr;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class CommonMethod {


    public static JsonNode executeProcessAndGetJsonOutput_2(List<String> commandAndArgs) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandAndArgs);
            processBuilder.redirectErrorStream(true); // 프로세스 실행 중 발생한 오류 메시지와 정상 출력을 함께 처리

            Process process = processBuilder.start();

            // 프로세스의 출력을 읽기
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            boolean jsonStarted = false;
            while ((line = reader.readLine()) != null) {
                    // JSON 데이터가 시작된 경우, 그대로 추가
                    outputBuilder.append(line);

            }

            // 프로세스의 종료를 기다리고 종료 코드 확인
            int exitCode = process.waitFor();
            reader.close();

            // 종료 코드가 0이 아닌 경우 오류 처리
            if (exitCode != 0) {
                System.err.println("Command execution failed with exit code: " + exitCode);
                return null;
            }

            // ObjectMapper를 사용하여 JSON 결과를 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(outputBuilder.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static JsonNode executeProcessAndGetJsonOutput_3(List<String> commandAndArgs) {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(commandAndArgs);
            processBuilder.redirectErrorStream(true); // 프로세스 실행 중 발생한 오류 메시지와 정상 출력을 함께 처리

            Process process = processBuilder.start();

            // 프로세스의 출력을 읽기
            InputStream inputStream = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder outputBuilder = new StringBuilder();
            String line;
            boolean jsonStarted = false;
            while ((line = reader.readLine()) != null) {
                if (jsonStarted) {
                    // JSON 데이터가 시작된 경우, 그대로 추가
                    outputBuilder.append(line);
                } else {
                    // JSON 데이터가 시작되기 전에 나온 텍스트를 무시
                    if (line.trim().isEmpty()) {
                        // 빈 줄은 무시
                        continue;
                    }
                    // JSON 데이터가 시작되었는지 확인
                    if (line.trim().startsWith("{")) {
                        jsonStarted = true;
                        outputBuilder.append(line);
                    }
                }
            }

            // 프로세스의 종료를 기다리고 종료 코드 확인
            int exitCode = process.waitFor();
            reader.close();

            // 종료 코드가 0이 아닌 경우 오류 처리
            if (exitCode != 0) {
                System.err.println("Command execution failed with exit code: " + exitCode);
                return null;
            }

            // ObjectMapper를 사용하여 JSON 결과를 파싱
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readTree(outputBuilder.toString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }



    //=----------------------------------parseAndResult_1key,2key는 이분법. 양호 값과 취약 값 모두 정해준다
    public static Boolean parseAndResult_1key(JsonNode jsonNode, String getKey1, String malValue, Object value) throws IOException {

        if (jsonNode.isArray()) {    //배열로 시작할때
            for (JsonNode accountNode : jsonNode) {
                JsonNode SetNode = accountNode.get(getKey1);
                String getValue = SetNode.asText();
                System.out.println(getKey1 + " : " + getValue);
                if (malValue.equals(getValue)) {
                    System.out.println("취약");
                    return false;
                } else if (value.equals(getValue)) {
                    System.out.println("양호");
                    return true;
                }
            }
        } else {
            JsonNode getValue = jsonNode.get(getKey1);
            System.out.println(getKey1 + " : " + getValue);
            if (malValue.equals(getValue.asText())) {
                System.out.println("취약");
                return false;
            } else if (value.equals(getValue.asText())) {
                System.out.println("양호");
                return true;
            }
        }
        return false;
    }


    public static Boolean parseAndResult_2key(JsonNode jsonNode, String getKey1, String getKey2, String malValue, String value) {
        //  System.out.println(jsonNode.toPrettyString());
        if (jsonNode.isArray()) {    //배열로 시작할때
            for (JsonNode accountNode : jsonNode) {
                JsonNode SetNode = accountNode.get(getKey1).get(getKey2);
                String getValue = SetNode.asText();
                System.out.println(getKey2 + " : " + getValue);
                if (malValue.equals(getValue)) {
                    System.out.println("취약");
                    return false;
                } else if (value.equals(getValue)) {
                    System.out.println("양호");
                    return true;
                }
            }
        } else {
            JsonNode getValue = jsonNode.get(getKey1).get(getKey2);
            System.out.println(getKey2 + " : " + getValue);
            if (malValue.equals(getValue.asText())) {
                System.out.println("취약");
                return false;
            } else if (value.equals(getValue.asText())) {
                System.out.println("양호");
                return true;
            }
        }
        return false;
    }

//----------------------------------------------------------

    //key2의 value값이 지정 value 값 아닐시 모두 취약함으로 판단.

    public static Boolean parseAndResult_2key_1value(JsonNode jsonNode, String getKey1, String getKey2, String value) {
        //System.out.println(jsonNode.toPrettyString());
        if (jsonNode.isArray()) {    //배열로 시작할때
            for (JsonNode accountNode : jsonNode) {
                JsonNode SetNode = accountNode.get(getKey1).get(getKey2);
                String getValue = SetNode.asText();
                System.out.println(getKey2 + " : " + getValue);
                if (value.equals(getValue)) {
                    System.out.println("양호");
                    return true;
                } else {
                    System.out.println("취약");
                    return false;
                }
            }
        }else {
            JsonNode getValue = jsonNode.get(getKey1).get(getKey2);
            System.out.println(getKey2 + " : " + getValue);
            if (value.equals(getValue.asText())) {
                System.out.println("양호");
                return true;
            } else if (value.equals(getValue.asText())) {
                System.out.println("취약");
                return false;
            }
        }
        return false;

    }


    public static Boolean parseAndResult_1key_1value(JsonNode jsonNode, String getKey1, String value) {
        //System.out.println(jsonNode.toPrettyString());
        if (jsonNode.isArray()) {    //배열로 시작할때
            for (JsonNode accountNode : jsonNode) {
                JsonNode SetNode = accountNode.get(getKey1);
                String getValue = SetNode.asText();
                System.out.println(getKey1 + " : " + getValue);
                if (value.equals(getValue)) {
                    System.out.println("양호");
                    return true;
                } else {
                    System.out.println("취약");
                    return false;
                }
            }
        }else {
            JsonNode getValue = jsonNode.get(getKey1);
            System.out.println(getKey1 + " : " + getValue);
            if (value.equals(getValue.asText())) {
                System.out.println("양호");
                return true;
            } else if (value.equals(getValue.asText())) {
                System.out.println("취약");
                return false;
            }
        }
        System.out.println("취약");
        return false;
        //양호의 조건이 아니면 무조건 취약입니다...
    }


    public static JsonNode findStorageResource(){
        JsonNode jsonOutput;

        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add("powershell.exe");
        commandAndArgs.add("az");
        commandAndArgs.add("storage");
        commandAndArgs.add("account");
        commandAndArgs.add("list");
        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        return jsonOutput;

    }

    public static JsonNode findWebAppResourceId(){
        JsonNode jsonOutput;

        List<String> commandAndArgs = new ArrayList<>();
        commandAndArgs.add("powershell.exe");
        commandAndArgs.add("az");
        commandAndArgs.add("webapp");
        commandAndArgs.add("list");
        jsonOutput = executeProcessAndGetJsonOutput_2(commandAndArgs);
        return jsonOutput;

    }

    //condition > allOf > equals 값이 인자값과 같은 것을 찾는다.
    public static JsonNode filterJsonObjectsByValue(JsonNode jsonOutput, String targetValue) {
        JsonNode filteredObject = null;

        for (JsonNode node : jsonOutput) {
            JsonNode conditionNode = node.path("condition");
            if (conditionNode != null && conditionNode.isObject()) {
                JsonNode allOfNode = conditionNode.path("allOf");
                if (allOfNode != null && allOfNode.isArray()) {
                    for (JsonNode equalsNode : allOfNode) {
                        JsonNode equalsValueNode = equalsNode.path("equals");
                        if (equalsValueNode != null && equalsValueNode.isTextual() &&
                                equalsValueNode.asText().equals(targetValue)) {
                            filteredObject = node;
                        }
                    }
                }
            }
        }

        return filteredObject;
    }


    public static String extractFields(JsonNode jsonNode, String[] targetFields) {
        String jsonOutput = jsonNode.toPrettyString();
        StringBuilder outputLine = new StringBuilder();
        try {
            String[] lines = jsonOutput.split("\\r?\\n");
            for (String line : lines) {
                boolean containsAnyField = false;
                for (String field : targetFields) {
                    if (line.contains(field)) {
                        containsAnyField = true;
                        break;
                    }
                }
                if (containsAnyField) {
                    line = line.replace(",", "<br>");
                    outputLine.append(line).append("\n");
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return outputLine.toString();
    }

}
