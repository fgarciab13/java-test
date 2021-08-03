package com.hrs.model.rules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hrs.model.readings.Values;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BloodPressure implements Serializable {
    @JsonProperty("systolic")
    private List<String> systolic = null;
    @JsonProperty("diastolic")
    private List<String> diastolic = null;
    @JsonProperty("heartRate")
    private List<String> heartRate = null;
}
