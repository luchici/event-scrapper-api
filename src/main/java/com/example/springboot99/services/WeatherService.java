package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import org.springframework.stereotype.Service;

@Service
public interface WeatherService {

    void updateWeatherAllCities();
    Weather updateWeatherByCity(City city);

    Weather updateWeatherByCityName(String cityName);

}
