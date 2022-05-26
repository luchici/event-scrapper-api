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

    public static LocalDate updateDate(){
        date = LocalDate.now();
        return date;
    }

//    TODO: Update all the cities, need to run daily at the same hour

    @PostConstruct
    private void intialUpdate(){
        updateWeatherAllCities();
    }

    @Override
    public Weather updateWeatherByCityName(String cityName){
       updateDate();
        City localCity = cityRepository.findByCityNameIgnoreCase(cityName).get(0);
        Weather weatherToday = localCity.getWeather();
        if(!(weatherToday.getLocalDate().equals(date))){
            System.out.println(weatherToday.equals(date));
            System.out.println("City name is " + localCity.getCityName() + " weather date is " + localCity.getWeather().getLocalDate());
            System.out.println("System Local date is " + date);
            System.out.println("----------------------------------We are in updateWeatherByCityName ------------------------------------------");
            System.out.println("----------------------------------We are in updateWeatherByCityName ------------------------------------------");
            weatherToday = HttpRequestWeatherAPI.getTheWeather(localCity);
            localCity.setWeather(weatherToday);
            weatherRepository.saveAndFlush(weatherToday);}
        return weatherToday;
    }

    @Override
    public Weather updateWeatherByCityName(City city){
        updateDate();
        Weather weatherToday = city.getWeather();

        if(!weatherToday.getLocalDate().equals(date)){
            System.out.println(weatherToday.equals(date));
            System.out.println("City name is " + city.getCityName() + " weather date is " + city.getWeather().getLocalDate());
            System.out.println("System Local date is " + date);
            System.out.println("----------------------------We are in updateWeatherByCityName ------------------------------------------");
            System.out.println("----------------------------We are in updateWeatherByCityName ------------------------------------------");
            weatherToday = HttpRequestWeatherAPI.getTheWeather(city);
            city.setWeather(weatherToday);
            weatherRepository.saveAndFlush(weatherToday);}
        return weatherToday;
    }

    @Override
    public void updateWeatherAllCities(){
        List<City> cities = cityRepository.findAll();
        for (City city : cities) {
            updateWeatherByCityName(city);
        }
    }
    }
