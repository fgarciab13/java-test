package com.hrs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.hrs.enums.ResultData;
import com.hrs.model.readings.Reading;
import com.hrs.model.readings.Values;
import com.hrs.model.result.BloodPressureResult;
import com.hrs.model.result.GlucoseResult;
import com.hrs.model.result.Result;
import com.hrs.model.result.WeightResult;
import com.hrs.model.rules.BloodPressure;
import com.hrs.model.rules.Glucose;
import com.hrs.model.rules.Rule;
import com.hrs.model.rules.Weight;
import com.hrs.utils.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class Test {

    ObjectMapper objectMapper;

    public static void main(String[] args) throws InterruptedException {
        try {
            String readingsFile = args.length >= 1 && !args[0].isEmpty() ? args[0] : "/code/readings.json";
            String rulesFiles = args.length >= 2 && !args[1].isEmpty() ? args[1] : "/code/rules.json";
            new Test(rulesFiles, readingsFile);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        Thread.sleep(10);
    }

    public Test(String rulesFile, String readingsFile) throws IOException {
        if (readingsFile != null && rulesFile != null) {
            // Read resource source file for rules
            log.info("rulePath {}", rulesFile);
            // Read resource source file for values
            log.info("readingsPath {}", readingsFile);
            // See which values trigger the rules
            log.info("Calculate values");
            Reading[] readings = transformFile(readingsFile, Reading[].class);
            List<Result> results = calc(transformFile(rulesFile, Rule.class), (readings != null ? Arrays.asList(readings) : null));
            if (results != null && !results.isEmpty()) {
                // Report the findings to another server
                //     (don't need to make the actual http request but code should lead up until that final moment)
                // Uncomment when ready
                log.info("sendingResults");
                //sendFindings(results, "someUrl");
                // Write findings as json file to /code/results.json
                log.info("savingResults");
                saveJsonFile(results);
            } else {
                log.info("noResultsFound");
            }
        } else {
            log.error("No ReadingsFile or RulesFile");
        }
        // Report the findings to another server
        //     (don't need to make the actual http request but code should lead up until that final moment)

    }

    private ResultData getResultData(List<String> values, Integer comparableValue) {
        if (values == null || comparableValue == null) {
            return ResultData.NO_INFO;
        }
        return Utilities.operatorMap.get(values.get(0)).compare(comparableValue, Integer.parseInt(values.get(1))) ? ResultData.NORMAL : ResultData.OUT_RANGE;
    }

    private BloodPressureResult getBloodPressureResult(Values values, BloodPressure rule) {
        if (values != null)
            return BloodPressureResult.getForReturn(getResultData(rule.getSystolic(), values.getSystolic()), getResultData(rule.getDiastolic(), values.getDiastolic()), getResultData(rule.getHeartRate(), values.getHeartRate()));
        else
            return BloodPressureResult.getForReturn();
    }

    private WeightResult getWeightResult(Values values, Weight rule) {
        if (values != null)
            return WeightResult.getForReturn(getResultData(rule.getWeight(), values.getWeight()));
        else
            return WeightResult.getForReturn();
    }

    private GlucoseResult getGlucoseResult(Values values, Glucose rule) {
        if (values != null)
            return GlucoseResult.getForReturn(getResultData(rule.getBloodSugarLevel(), values.getBloodSugarLevel()));
        else
            return GlucoseResult.getForReturn();
    }

    private Values getValue(List<Values> values, String searchValue) {
        return values.stream().filter(v -> searchValue.equals(v.getType())).findAny().orElse(null);
    }

    private List<Result> calc(Rule rule, List<Reading> readings) {
        List<Result> results = null;
        if (rule != null && readings != null && !readings.isEmpty()) {
            results = new ArrayList<>();
            Result result;
            for (Reading reading : readings) {
                if (reading.getValues() != null) {
                    result = new Result();
                    result.setId(reading.getId());
                    result.setBloodPressure(getBloodPressureResult(getValue(reading.getValues(), "bloodPressure"), rule.getBloodPressure()));
                    result.setWeight(getWeightResult(getValue(reading.getValues(), "weight"), rule.getWeight()));
                    result.setGlucose(getGlucoseResult(getValue(reading.getValues(), "glucose"), rule.getGlucose()));
                    results.add(result);
                }
            }
        }
        return results;
    }

    private <T> T transformFile(String file, Class<T> t) throws IOException {
        String ext = getExtension(file);
        if (ext != null && !ext.isEmpty() && Utilities.availableExtensions.contains(ext)) {
            switch (ext) {
                case "yaml":
                case "yml":
                    objectMapper = new ObjectMapper(new YAMLFactory());
                    objectMapper.findAndRegisterModules();
                    break;
                default:
                    objectMapper = new ObjectMapper();
            }
            return objectMapper.readValue(new BufferedReader(new FileReader(file)), t);
        }
        return null;
    }

    private void saveJsonFile(List<Result> results) throws IOException {
        File f = new File("result.json");
        if (f.createNewFile()) {
            objectMapper = new ObjectMapper();
            objectMapper.writeValue(new File("result.json"), results);
        }
    }

    private void sendFindings(List<Result> listResults, String serverHost) throws IOException {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            objectMapper = new ObjectMapper();
            HttpPost httpPost = new HttpPost(serverHost);
            StringEntity entity = new StringEntity(objectMapper.writeValueAsString(listResults));
            httpPost.setEntity(entity);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            client.execute(httpPost);
        }
    }


    private String getExtension(String filename) {
        return FilenameUtils.getExtension(filename);
    }

}
