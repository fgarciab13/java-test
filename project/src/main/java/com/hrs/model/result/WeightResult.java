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
public class WeightResult implements Serializable {
    @JsonProperty("weight")
    private ResultData weight;

    public static WeightResult getForReturn() {
        return new WeightResult(ResultData.NO_INFO);
    }

    public static WeightResult getForReturn(ResultData weight) {
        return new WeightResult(weight);
    }
}
