package com.example.weather_service.weather_service.controller;

import com.example.weather_service.weather_service.entity.Weather;
import com.example.weather_service.weather_service.repository.WeatherRepository;
import com.example.weather_service.weather_service.service.CacheInspectionService;
import com.example.weather_service.weather_service.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @Autowired
    private WeatherRepository weatherRepository;

    @Autowired
    private CacheInspectionService cacheInspectionService;

    @GetMapping
    @PreAuthorize("hasAuthority('WEATHER_READ')")
//    @PreAuthorize("hasRole('ADMIN')")
//    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public String getWeather(@RequestParam String city){
        return weatherService.getWeatherByCity(city);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('WEATHER_WRITE')")
    public Weather addWeather(@RequestBody Weather weather){
        return weatherRepository.save(weather);
    }

    @GetMapping("/all")
    public List<Weather> getAllWeather(){
        return weatherRepository.findAll();
    }

    @GetMapping("/cacheData")
    public void getCacheData(){
        cacheInspectionService.printCacheContents("weather");
    }

    @PutMapping("/{city}")
    public String updateWeather(@PathVariable String city, @RequestParam String weatherUpdate){
        return weatherService.updateWeather(city, weatherUpdate);
    }

    @DeleteMapping("/{city}")
    @PreAuthorize("hasAuthority('WEATHER_DELETE')")
    public String deleteWeather(@PathVariable String city){
        weatherService.deleteWeather(city);
        return "Weather data for " + city + " has been deleted and cache evicted.";
    }

    @GetMapping("/health")
    public String getHealth(){
        return "Healthy";
    }

}
