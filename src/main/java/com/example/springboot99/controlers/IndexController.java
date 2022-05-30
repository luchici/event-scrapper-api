package com.example.springboot99.controlers;

import com.example.springboot99.config.NotionConfigProperties;
import com.example.springboot99.entity.City;
import com.example.springboot99.exception.CityNotFoundException;
import com.example.springboot99.services.CityService;
import com.example.springboot99.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class IndexController {

    private CityService cityService;
    private WeatherService weatherService;
    private NotionConfigProperties notionConfigProperties;

    @GetMapping("/")
    public String getCityList(Model model){
        List<City> cityList = cityService.getLastCities();
        int cityListSize = cityList.size()-1;
        model.addAttribute("theCity", cityList.get(cityListSize));
        model.addAttribute("theWeather", cityList.get(cityListSize).getWeather());
        model.addAttribute("cityList", cityList);
        return "homepage";
    }

    @GetMapping("/{cityName}")
    public String getCityByCityName(@PathVariable String cityName, Model model) {
        City theCity = cityService.getCityByCityName(cityName);
        if(!cityService.checkCityName(cityName).isEmpty()) {
            model.addAttribute("message", cityService.checkCityName(cityName));
            return getCityList(model);
        }
        if (theCity == null) throw new CityNotFoundException("");
        weatherService.updateWeatherByCity(theCity);
        List<City> cityList = cityService.getLastCities();
        model.addAttribute("theCity", theCity);
        model.addAttribute("theWeather", theCity.getWeather());
        model.addAttribute("cityList", cityList);
        return "homepage";
    }

    @PostMapping("/{cityName}")
    public String postCityByCityName(@RequestParam String cityName, Model model) {
        cityName = cityName.trim();
        return getCityByCityName(cityName, model);
    }

    @GetMapping("/api")
    public String getApiPage() {
        return "api";
    }


}
