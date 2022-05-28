package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.repository.WeatherRepository;
import com.example.springboot99.utility.HttpRequestWeatherAPI;
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

//    TODO: Update all the cities, need to run daily at the same hour

    @PostConstruct
    private void intialUpdate(){
        updateWeatherAllCities();
    }

    @Override
    public Weather updateWeatherByCityName(String cityName){
        City city = cityRepository.findByCityNameIgnoreCase(cityName).get(0);
        return updateWeatherByCity(city);
    }

    @Override
    public Weather updateWeatherByCity(City city){
        updateDate();
        Weather weatherToday = city.getWeather();
        if(!weatherToday.getLocalDate().equals(date)){
            weatherToday = HttpRequestWeatherAPI.getTheWeather(city);
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
