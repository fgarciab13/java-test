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
public class GlucoseResult implements Serializable {
    @JsonProperty("bloodSugarLevel")
    private ResultData bloodSugarLevel = null;

    public static GlucoseResult getForReturn() {
        return new GlucoseResult(ResultData.NO_INFO);
    }

    public static GlucoseResult getForReturn(ResultData bloodSugarLevel) {
        return new GlucoseResult(bloodSugarLevel);
    }
}
