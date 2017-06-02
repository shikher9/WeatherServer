package com.pivotusventures.weatherserver.controller;

import com.pivotusventures.weatherserver.model.ExternalWeatherApi;
import com.pivotusventures.weatherserver.model.WeatherDataResponse;
import com.pivotusventures.weatherserver.service.WeatherDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * Created by shikh on 31-May-17.
 * <p>
 * Spring boot is used for the project. The properties are maintained in application.properties
 * and exceptions, errors are maintained in application.log. The getWeatherData() method is mapped to the main
 * url
 */

@RestController
public class WeatherApiController implements ErrorController {

    private static final String PATH = "/error";


    @Autowired
    WeatherDataService weatherDataService;


    @RequestMapping(value = {"/api/weather"}, method = RequestMethod.GET, produces = "application/json")
    public
    @ResponseBody
    WeatherDataResponse getWeatherData(@RequestParam("city") String city) {
        ExternalWeatherApi[] externalWeatherApis = weatherDataService.setupExternalApi();
        Double[] data = weatherDataService.getData(4, externalWeatherApis, city);
        double average = weatherDataService.calculateAverage(data);
        double sd = weatherDataService.getStandardDeviation(data, average);
        WeatherDataResponse weatherDataResponse = new WeatherDataResponse();
        weatherDataResponse.setTemp(average);
        weatherDataResponse.setStd(sd);
        weatherDataResponse.setAll(Arrays.asList(data));
        return weatherDataResponse;
    }


    @RequestMapping(value = {PATH}, produces = "application/json")
    public
    @ResponseBody
    Error errorProcessing() {
        return new Error("Error - check log");
    }


    @Override
    public String getErrorPath() {
        return PATH;
    }
}
