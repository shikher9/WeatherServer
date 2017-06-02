package com.pivotusventures.weatherserver.model;

/**
 * Created by shikh on 31-May-17.
 * <p>
 * Response object for the final api response
 */

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "temp",
        "std",
        "all"
})
public class WeatherDataResponse {

    @JsonProperty("temp")
    private Double temp;
    @JsonProperty("std")
    private Double std;
    @JsonProperty("all")
    private List<Double> all = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("temp")
    public Double getTemp() {
        return temp;
    }

    @JsonProperty("temp")
    public void setTemp(Double temp) {
        this.temp = temp;
    }

    @JsonProperty("std")
    public Double getStd() {
        return std;
    }

    @JsonProperty("std")
    public void setStd(Double std) {
        this.std = std;
    }

    @JsonProperty("all")
    public List<Double> getAll() {
        return all;
    }

    @JsonProperty("all")
    public void setAll(List<Double> all) {
        this.all = all;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}

