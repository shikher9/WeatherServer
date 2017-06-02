package com.pivotusventures.weatherserver.service;

import com.pivotusventures.weatherserver.model.ExternalWeatherApi;
import com.pivotusventures.weatherserver.model.external.Main;
import com.pivotusventures.weatherserver.model.external.OpenWeatherMapResponse;
import com.pivotusventures.weatherserver.model.external.WunderGroundResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.*;

/**
 * Created by shikh on 31-May-17.
 * <p>
 * The service object which contains all the logic. The setupExternalApi() and callApi() method would need to be changed depending upon the list of external
 * urls. Here there are only two, but since solution has to extensible, using generics would reduce the amount the code in the callApi() method. The setupExternalApi()
 * method will still require change. The way to make callAPI generic by storing the class object (.class) of the domain object (eg - OpenWeatherMapResponse) in
 * ExternalWeatherApi itself.
 * <p>
 * Regarding the performance, I used executor service which will divide the work among multiple threads. I am using a newFixedThreadPool for this.
 * Technically Java tries to map one processor core to one thread. Therefore using a Fixed Thread Pool would give reasonable performance.
 * <p>
 * There is one problem, with the current approach. The method getData() calls all the api and returns data when it gets results from all the api calls.
 * If any api call takes too much time, it would slow down the response a lot. A solution to this is to have timeouts for each call and calculate average and
 * standard deviation without considering them if they are taking too much time.
 */

@Service
@Scope("prototype")
public class WeatherDataService {

    Double[] data;

    /**
     * The methods sets up the external api information. This needs to be changed if you need to add or remove additional
     * external url, or modify the existing ones.
     *
     * @return
     */


    public ExternalWeatherApi[] setupExternalApi() {

        ExternalWeatherApi openWeatherMapApi = new ExternalWeatherApi();
        openWeatherMapApi.setUrl("http://api.openweathermap.org/data/2.5/weather?q={city}&APPID={key}&units=imperial");
        openWeatherMapApi.setApikey("40e80daa66514dfaab21c98a003d08e7");
        openWeatherMapApi.setApiIdentifier("openweathermap");

        ExternalWeatherApi wunderground = new ExternalWeatherApi();
        wunderground.setUrl("http://api.wunderground.com/api/{key}/conditions/q/CA/{city}.json");
        wunderground.setApikey("1e4ac434bb8e4f75");
        wunderground.setApiIdentifier("wunderground");

        ExternalWeatherApi[] externalWeatherApis = {openWeatherMapApi, wunderground};
        return externalWeatherApis;
    }


    /**
     * This creates an executor service and submits all the tasks. The total number of tasks will be the total
     * number of api calls be made.
     *
     * @param threads             - The total number of threads to be used for executor service. See setupExternalApi() method.
     * @param externalWeatherApis - an array containing ExternalWeatherApi objects.
     * @param city                - The city for which we need data.
     * @return
     */
    public Double[] getData(int threads, ExternalWeatherApi[] externalWeatherApis, String city) {

        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        data = new Double[externalWeatherApis.length];
        Future[] futurearr = new Future[externalWeatherApis.length];

        for (int i = 0; i < externalWeatherApis.length; i++) {
            ExternalWeatherApi externalWeatherApi = externalWeatherApis[i];
            Future<Double> future = executorService.submit(new Callable<Double>() {

                @Override
                public Double call() throws Exception {
                    return callApi(externalWeatherApi, city);
                }
            });
            futurearr[i] = future;
        }

        executorService.shutdown();

        for (int i = 0; i < futurearr.length; i++) {
            Future<Double> future = futurearr[i];
            try {
                data[i] = future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

    /**
     * This the the method which call the external api and will need to be changed depending upon the list of URLs.
     * I thought of using a generic method but avoided as even though I would reduce the code in if else conditions here,
     * I have write additional code in the setupExternalApi() method. This way makes the code more readable as well.
     *
     * @param api
     * @param city
     * @return
     */

    private Double callApi(ExternalWeatherApi api, String city) {

        String url = api.getUrl().replace("{key}", api.getApikey()).replace("{city}", city);
        RestTemplate restTemplate = new RestTemplate();
        String apiIdentifier = api.getApiIdentifier();
        if (apiIdentifier.equals("openweathermap")) {
            OpenWeatherMapResponse res = restTemplate.getForObject(url, OpenWeatherMapResponse.class);
            return res.getMain().getTemp();
        } else if (apiIdentifier.equals("wunderground")) {
            WunderGroundResponse res = restTemplate.getForObject(url, WunderGroundResponse.class);
            return res.getCurrentObservation().getTempF();
        }


        return null;
    }

    /**
     * calculates average, even we don't get data for an api call , we still take whole data array length of
     * calculating average.
     *
     * @param data
     * @return
     */

    public double calculateAverage(Double[] data) {
        double sum = 0;
        for (Double d : data) {
            if (d != null) {
                sum += d;
            }
        }
        return sum / data.length;
    }

    /**
     * calculates standard deviation, even we don't get data for an api call , we still take whole data array length of
     * calculating average and standard deviation.
     *
     * @param data
     * @param average
     * @return
     */
    public double getStandardDeviation(Double[] data, double average) {
        double std = 0;
        for (int i = 0; i < data.length; i++) {
            if (data[i] != null) {
                std += Math.pow((data[i] - average), 2);
            }
        }
        std = std / data.length;
        std = Math.sqrt(std);
        return std;
    }
}
