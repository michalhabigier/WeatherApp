package pl.mh;

import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(WeatherController.class)
public class WeatherControllerTests {

    @MockBean
    private WeatherService weatherService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void getWeather() throws Exception{
        CurrentWeather currentWeather = new CurrentWeather();
        currentWeather.setName("Cracow");
        currentWeather.setTemperature(22.1);
        currentWeather.setHumidity(77);
        setWeather(currentWeather, 200, "cloudy", "cloudy", "01d", 1013.25,
                2.22);
        Mockito.when(this.weatherService.getWeather("Cracow")).thenReturn(currentWeather);
        this.mockMvc.perform(get("/Cracow"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Cracow")))
                .andExpect(jsonPath("$.weatherId", is(200)))
                .andExpect(jsonPath("$.mainDesc", is("cloudy")))
                .andExpect(jsonPath("$.description", is("cloudy")))
                .andExpect(jsonPath("$.weatherIcon", is("01d")))
                .andExpect(jsonPath("$.pressure", is(1013.25)))
                .andExpect(jsonPath("$.speed", is(2.22)))
                .andExpect(jsonPath("$.temperature", is(22.1)))
                .andExpect(jsonPath("$.humidity", is(77)));
        verify(this.weatherService).getWeather("Cracow");
    }

    @Test
    public void getWeatherForecast() throws Exception{
        WeatherForecast weatherForecast = new WeatherForecast();
        weatherForecast.setName("Warsaw");
        weatherForecast.getEntries().add(createWeatherForecast(1, "cloudy", "cloudy", "01d",
                1002.25, 3.11, 17.22, 25.43));
        weatherForecast.getEntries().add(createWeatherForecast(1, "cloudy", "cloudy", "01d",
                1013.25, 2.44, 15.22, 22.43));

        Mockito.when(this.weatherService.getWeather("Warsaw", 2)).thenReturn(weatherForecast);
        this.mockMvc.perform(get("/Warsaw/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Warsaw")))
                .andExpect(jsonPath("$.entries[0].speed", is(3.11)))
                .andExpect(jsonPath("$.entries[0].mainDesc", is("cloudy")))
                .andExpect(jsonPath("$.entries[1].speed", is(2.44)))
                .andExpect(jsonPath("$.entries[1].mainDesc", is("cloudy")));
        verify(this.weatherService).getWeather("Warsaw", 2);
    }

    private static Weather createWeatherForecast(Integer weatherId, String mainDesc, String description,
                                                 String weatherIcon, double pressure, double speed, double minTemp, double maxTemp){
        Weather weather = new Weather();
        setWeather(weather, weatherId, mainDesc, description, weatherIcon, pressure, speed);
        weather.setMinTemp(minTemp);
        weather.setMaxTemp(maxTemp);
        return weather;
    }

    private static void setWeather(Weather weather, Integer weatherId, String mainDesc, String description,
                                 String weatherIcon, double pressure, double speed){
         weather.setWeatherId(weatherId);
         weather.setMainDesc(mainDesc);
         weather.setDescription(description);
         weather.setWeatherIcon(weatherIcon);
         weather.setPressure(pressure);
         weather.setWindSpeed(speed);
    }
}
