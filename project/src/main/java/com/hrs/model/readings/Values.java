package com.hrs.model.readings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Values implements Serializable {

    @JsonProperty("type")
    public String type;
    @JsonProperty("systolic")
    public Integer systolic;
    @JsonProperty("diastolic")
    public Integer diastolic;
    @JsonProperty("heartRate")
    public Integer heartRate;
    @JsonProperty("bloodSugarLevel")
    public Integer bloodSugarLevel;
    @JsonProperty("weight")
    public Integer weight;

}
