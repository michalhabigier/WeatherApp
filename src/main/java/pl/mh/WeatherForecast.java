package pl.mh;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WeatherForecast {
    private String name;

    private List<Weather> entries = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("entries")
    public List<Weather> getEntries() {
        return this.entries;
    }

    @JsonSetter("list")
    public void setEntries(List<Weather> entries) {
        this.entries = entries;
    }

    @JsonProperty("city")
    public void setCity(Map<String, Object> city) {
        setName(city.get("name").toString());
    }

}
