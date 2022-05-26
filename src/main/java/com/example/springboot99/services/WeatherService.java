package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {

    void updateWeatherAllCities();
    Weather updateWeatherByCityName(City city);

    Weather updateWeatherByCityName(String cityName);

//    List<Weather> getWeatherByTempValueAndDate(Integer tempValue, String date);

//    public List<Weather> getWeatherByTempValueAndDate(Integer tempValue, String date);
//    public List<Weather> getWeatherByTempValue(Integer tempValue);
//    public List<Weather> getWeatherByDescriptionAndLocalDate(String description, String date);
//    public List<Weather> getWeatherByDescription(String description);

}
