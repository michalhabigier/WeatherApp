package pl.mh;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class CurrentWeather extends Weather{
    private String name;
    private double temperature;
    private Integer humidity;

    @JsonIgnore
    private double minTemp;
    @JsonIgnore
    private double maxTemp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    @JsonProperty("wind")
    public void setWind(Map<String, Object> wind){
        setWindSpeed(Double.parseDouble(wind.get("speed").toString()));
    }


    @JsonProperty("main")
    public void setMain(Map<String, Object> main){
        setTemperature(Double.parseDouble(main.get("temp").toString()));
        setPressure(Double.parseDouble(main.get("pressure").toString()));
        setHumidity(Integer.parseInt(main.get("humidity").toString()));
    }
}
