package com.hrs.model.result;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Result implements Serializable {
    @JsonProperty("id")
    public Integer id;
    @JsonProperty("bloodPressure")
    public BloodPressureResult bloodPressure;
    @JsonProperty("glucose")
    public GlucoseResult glucose;
    @JsonProperty("weight")
    public WeightResult weight;
}
