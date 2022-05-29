package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.repository.WeatherRepository;
import com.example.springboot99.utility.HttpRequestOpenWeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class WeatherServiceImpl implements WeatherService{

    private static LocalDate date = LocalDate.now();

    private WeatherRepository weatherRepository;
    private CityRepository cityRepository;

    public static void updateDate(){
        date = LocalDate.now();
    }

    @PostConstruct
    private void intialUpdate(){
        updateWeatherAllCities();
    }

    @Override
    public Weather updateWeatherByCityName(String cityName){
        City city = cityRepository.findByCityNameIgnoreCase(cityName);
        return updateWeatherByCity(city);
    }

    @Override
    public Weather updateWeatherByCity(City city){
        updateDate();
        Weather weatherToday = city.getWeather();
        if(weatherToday == null || !weatherToday.getLocalDate().equals(date)){
            weatherToday = HttpRequestOpenWeatherAPI.getTheWeather(city);
            city.setWeather(weatherToday);
            cityRepository.save(city);}
        return weatherToday;
    }

    @Override
    public void updateWeatherAllCities(){
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            updateWeatherByCity(city);
        }
    }
    }
