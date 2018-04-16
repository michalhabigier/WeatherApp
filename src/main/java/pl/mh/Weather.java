package pl.mh;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class Weather {
    private Integer weatherId;
    private String mainDesc;
    private String description;
    private String weatherIcon;
    private double pressure;
    private double speed;
    private double minTemp;
    private double maxTemp;

    public double getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(double minTemp) {
        this.minTemp = minTemp;
    }

    public double getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(double maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Integer getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(Integer weatherId) {
        this.weatherId = weatherId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMainDesc() {
        return mainDesc;
    }

    public void setMainDesc(String mainDesc) {
        this.mainDesc = mainDesc;
    }

    public String getWeatherIcon() {
        return weatherIcon;
    }

    public void setWeatherIcon(String weatherIcon) {
        this.weatherIcon = weatherIcon;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getSpeed() {
        return speed;
    }

    public void setWindSpeed(double wind) {
        this.speed = wind;
    }

    @JsonProperty("weather")
    public void setWeather(List<Map<String, Object>> weatherEntries){
        Map<String, Object> weather = weatherEntries.get(0);
        setWeatherId((Integer)weather.get("id"));
        setWeatherIcon((String)weather.get("icon"));
        setMainDesc((String)weather.get("main"));
        setDescription((String)weather.get("description"));

    }

    @JsonProperty("temp")
    public void setMain(Map<String, Object> main){
        setMinTemp(Double.parseDouble(main.get("min").toString()));
        setMaxTemp(Double.parseDouble(main.get("max").toString()));
    }
}
