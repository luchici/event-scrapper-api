package com.example.springboot99.controlers;

import com.example.springboot99.config.NotionConfigProperties;
import com.example.springboot99.entity.City;
import com.example.springboot99.exception.CityNotFoundException;
import com.example.springboot99.services.CityService;
import com.example.springboot99.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApiRestController {

    private WeatherService theWeatherService;
    private CityService theCityService;

    private NotionConfigProperties notionConfigProperties;
    @GetMapping("/test")
    public String test(){
        return "1111  " + notionConfigProperties.openWeatherApiKey();
    }


    //    GET CITY
    @GetMapping("/city/{cityName}")
    public City getCityByCityName(@PathVariable String cityName){
        cityName = cityName.trim();
        if (!theCityService.checkCityName(cityName).equalsIgnoreCase("")) {

            throw new CityNotFoundException(theCityService.checkCityName(cityName));
        }
        else if (theCityService.getCityByCityName(cityName) == null) {
            throw new CityNotFoundException(theCityService.checkCityName(cityName));
        }
        return theCityService.getCityByCityName(cityName);
    }

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return theCityService.getAllCtities();
    }

    @GetMapping("/weather/{cityName}")
    public City getWeatherByCityName(@PathVariable String cityName){
        City localCity = theCityService.getCityByCityName(cityName);
        theWeatherService.updateWeatherByCity(localCity);
        return theCityService.getCityByCityName(cityName);
    }

//    TODO: add delete city controler
    @DeleteMapping("/city/{cityName}")
    public City deleteCityByCityName(@PathVariable String cityName){
        return theCityService.deleteCityByCityName(cityName);
    }

}
