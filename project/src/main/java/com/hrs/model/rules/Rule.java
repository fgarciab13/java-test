package com.hrs.model.rules;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Rule implements Serializable {
    @JsonProperty("bloodPressure")
    public BloodPressure bloodPressure;
    @JsonProperty("glucose")
    public Glucose glucose;
    @JsonProperty("weight")
    public Weight weight;

}
