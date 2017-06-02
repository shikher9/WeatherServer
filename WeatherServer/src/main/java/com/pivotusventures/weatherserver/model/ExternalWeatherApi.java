package com.pivotusventures.weatherserver.model;

/**
 * Created by shikh on 31-May-17.
 * <p>
 * Model for storing the details of an external api.
 * <p>
 * It stores he url, the api key and an identifier for identifying the class object during rest call.
 */
public class ExternalWeatherApi {

    private String url;
    private String apikey;
    private String apiIdentifier;

    public ExternalWeatherApi() {
    }

    public ExternalWeatherApi(String url, String apikey, String apiIdentifier) {
        this.url = url;
        this.apikey = apikey;
        this.apiIdentifier = apiIdentifier;
    }

    public String getUrl() {
        return url;
    }

    public String getApikey() {
        return apikey;
    }

    public String getApiIdentifier() {
        return apiIdentifier;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setApikey(String apikey) {
        this.apikey = apikey;
    }

    public void setApiIdentifier(String apiIdentifier) {
        this.apiIdentifier = apiIdentifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ExternalWeatherApi that = (ExternalWeatherApi) o;

        if (url != null ? !url.equals(that.url) : that.url != null) return false;
        return apikey != null ? apikey.equals(that.apikey) : that.apikey == null;

    }

    @Override
    public int hashCode() {
        int result = url != null ? url.hashCode() : 0;
        result = 31 * result + (apikey != null ? apikey.hashCode() : 0);
        return result;
    }
}
