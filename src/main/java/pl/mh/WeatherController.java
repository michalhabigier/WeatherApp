package pl.mh;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    private WeatherService weatherService;

    @Autowired
    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/{city}")
    public CurrentWeather getWeather(@PathVariable String city){
        return weatherService.getWeather(city);
    }

    @GetMapping("/{city}/{days}")
    public WeatherForecast getWeather(@PathVariable String city, @PathVariable Integer days){
        return weatherService.getWeather(city, days);
    }

}
