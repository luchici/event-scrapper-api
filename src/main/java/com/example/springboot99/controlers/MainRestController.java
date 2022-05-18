package com.example.springboot99.controlers;

import com.example.springboot99.entity.City;
import com.example.springboot99.services.CityService;
import com.example.springboot99.services.WeatherService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class MainRestController {

    private WeatherService theWeatherService;
    private CityService theCityService;
    private HttpServletRequest httpServletRequest;

    @GetMapping()
    public String test() {
        return "This is a test   " + httpServletRequest.getRemoteAddr() +
                "  ----------   " + httpServletRequest.getRemoteHost();
    }

    //    GET CITY
    @GetMapping("/city/{cityName}")
    public City getCityByCityName(@PathVariable String cityName){
        return theCityService.getCityByCityName(cityName);
    }

    @GetMapping("/cities")
    public List<City> getAllCities() {
        return theCityService.getAllCtities();
    }

// WEATHER
    @GetMapping("/weather/updateAll")
    public List<City> updateAllCities(){
        theWeatherService.updateWeatherAllCities();
        return theCityService.getAllCtities();
    }

    @GetMapping("/weather/{cityName}")
    public City getWeatherByCityName(@PathVariable String cityName){
        theWeatherService.updateWeatherByCityName(theCityService.getCityByCityName(cityName));
        return theCityService.getCityByCityName(cityName);
    }

// Date need to be day.month
//    @GetMapping("/weatherTemp/{tempValue}&{date}")
//    public List<Weather> getWeatherByTempValueAndDate
//            (@PathVariable Integer tempValue, @PathVariable(required = false) String date){
//        System.out.println(date+"++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
//        if(date == null) return theWeatherService.getWeatherByTempValue(tempValue);
//        else return theWeatherService.getWeatherByTempValueAndDate(tempValue, date);
//    }
//
//    @GetMapping("/weather/{Description}&{date}")
//    public List<Weather> getWeatherByDescription(@PathVariable String description, @PathVariable(required = false) String date){
//        if (date == null) return theWeatherService.getWeatherByDescription(description);
//        else return theWeatherService.getWeatherByDescriptionAndLocalDate(description, date);
//    }


}
