package pl.mh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.net.URI;

@Service
public class WeatherService {
    private static final String CURRENT_WEATHER_URL = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={key}";

    private static final String FORECAST_URL = "http://api.openweathermap.org/data/2.5/forecast/daily?q={city}&cnt={count}&appid={key}";

    private final static Logger logger = LoggerFactory.getLogger(WeatherService.class);

    private final RestTemplate restTemplate;

    private final String key;

    public WeatherService(RestTemplateBuilder restTemplate, AppProperties appProperties) {
        this.restTemplate = restTemplate.build();
        this.key = appProperties.getApi().getKey();
    }

    public CurrentWeather getWeather(String city){
        logger.info("Getting weather for " + city);
        URI uri = new UriTemplate(CURRENT_WEATHER_URL).expand(city, this.key);
        return query(uri, CurrentWeather.class);
    }

    public WeatherForecast getWeather(String city, Integer count) {
        logger.info("Getting " + count + " day weather for " + city);
        URI uri = new UriTemplate(FORECAST_URL).expand(city, count, this.key);
        return query(uri, WeatherForecast.class);
    }

    private <T> T query(URI uri, Class<T> responseType){
        RequestEntity<?> requestEntity = RequestEntity.get(uri).accept(MediaType.APPLICATION_JSON).build();
        ResponseEntity<T> responseEntity = this.restTemplate.exchange(requestEntity, responseType);
        return responseEntity.getBody();
    }
}
