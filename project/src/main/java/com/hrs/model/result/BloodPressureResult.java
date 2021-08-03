package com.hrs.model.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrs.enums.ResultData;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class BloodPressureResult implements Serializable {
    @JsonProperty("systolic")
    private ResultData systolic = null;
    @JsonProperty("diastolic")
    private ResultData diastolic = null;
    @JsonProperty("heartRate")
    private ResultData heartRate = null;


    public static BloodPressureResult getForReturn() {
        return new BloodPressureResult(ResultData.NO_INFO, ResultData.NO_INFO, ResultData.NO_INFO);
    }

    public static BloodPressureResult getForReturn(ResultData systolic, ResultData diastolic, ResultData heartRate) {
        return new BloodPressureResult(systolic, diastolic, heartRate);
    }
}
