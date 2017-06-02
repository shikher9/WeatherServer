package com.pivotusventures.weatherserver.controller;

import com.pivotusventures.weatherserver.service.WeatherDataService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Unit tests which test the functionality of different modules.
 * Basically there are three modules - calling external api, calculate average and calculate standard deviation.
 * I have created tests for calculate average and calculate standard deviation. Creating tests for
 * external api does not make sense as it out of our control.
 */


@RunWith(SpringRunner.class)
@SpringBootTest
public class WeatherApiControllerTest {


    @Autowired
    private WeatherDataService weatherDataService;


    Double[] data1 = null;
    Double[] data2 = null;

    @Before
    public void setup() {
        data1 = new Double[]{76.08, 64.8};
        data2 = new Double[]{83.62, 86.9};
    }

    @org.junit.Test
    public void testCalculateAverage1() {
        double delta = 10E-1;
        double average = 70.44;
        Assert.assertEquals(average, weatherDataService.calculateAverage(data1), delta);
    }

    @org.junit.Test
    public void testCalculateStandardDeviation1() {
        double std = 5.640000000000001;
        double average = 70.44;
        double delta = 10E-1;
        Assert.assertEquals(std, weatherDataService.getStandardDeviation(data1, average), delta);
    }

    @org.junit.Test
    public void testCalculateAverage2() {
        double delta = 10E-1;
        double average = 85.26;
        Assert.assertEquals(average, weatherDataService.calculateAverage(data2), delta);
    }

    @org.junit.Test
    public void testCalculateStandardDeviation2() {
        double std = 1.6400000000000006;
        double average = 85.26;
        double delta = 10E-1;
        Assert.assertEquals(std, weatherDataService.getStandardDeviation(data2, average), delta);
    }


}
