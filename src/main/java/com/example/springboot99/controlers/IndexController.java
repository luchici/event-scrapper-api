package com.example.springboot99.controlers;

import com.example.springboot99.entity.City;
import com.example.springboot99.services.CityService;
import com.example.springboot99.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    private CityService cityService;
    private WeatherService weatherService;

    @GetMapping({"/","/{cityName}"})
    public String getAllCities(@PathVariable(required = false) String cityName, Model model){
        List<City> cityList = cityService.getLastTenCities();
        model.addAttribute("cityList", cityList);
        int cityListSize = cityList.size()-1;
        if(cityName == null) {
            model.addAttribute("theCity", cityList.get(cityListSize));
            model.addAttribute("theWeather", cityList.get(cityListSize).getWeather());
        } else {
            City theCity = cityService.getCityByCityName(cityName);
            weatherService.updateWeatherByCityName(cityName);
            model.addAttribute("theCity", theCity);
            model.addAttribute("theWeather", theCity.getWeather());

        }
        return "homepage";
    }

    @PostMapping({"/","/{cityName}"})
    public String getCityName(@RequestParam String cityName, Model model) {
        City theCity = cityService.getCityByCityName(cityName);
        weatherService.updateWeatherByCityName(cityName);
        model.addAttribute("theCity", theCity);
        model.addAttribute("theWeather", theCity.getWeather());
//        System.out.println("--------------------------------------");
//        System.out.println(theCity.getWeather());

        List<City> cityList = cityService.getLastTenCities();
        model.addAttribute("cityList", cityList);
        return "homepage";
    }

    @GetMapping("/api")
    public String getApiPage() {
        return "api";
    }

}
