package com.example.springboot99.controlers;

import com.example.springboot99.entity.City;
import com.example.springboot99.services.CityService;
import com.example.springboot99.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainRestController {

    private WeatherService theWeatherService;
    private CityService theCityService;

    //    GET CITY
    @GetMapping("/city/{cityName}")
    public City getCityByCityName(@PathVariable String cityName){
        return theCityService.getCityByCityName(cityName);
    }

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return theCityService.getAllCtities();
    }

    @GetMapping("/weather/{cityName}")
    public City getWeatherByCityName(@PathVariable String cityName){
        theWeatherService.updateWeatherByCityName(theCityService.getCityByCityName(cityName));
        return theCityService.getCityByCityName(cityName);
    }

}
