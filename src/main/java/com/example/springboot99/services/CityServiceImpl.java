package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.example.springboot99.exception.CityNotFoundException;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.repository.WeatherRepository;
import com.example.springboot99.utility.HttpRequestOpenWeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CityServiceImpl implements CityService {

    private CityRepository theCityRepository;
    private WeatherRepository weatherRepository;

    @PostConstruct
    public void intialUpdate(){
        if (theCityRepository.findAll().isEmpty() || theCityRepository.findAll().size() < 3) {
            getCityByCityName("bucuresti");
            getCityByCityName("iasi");
            getCityByCityName("craiova");
        }
    }

    @Override
    public String checkCityName(String cityName){
        char[] cityNameCharArray = cityName.toCharArray();
        int cityNameCharArraySieze = cityNameCharArray.length-1;
        String message = "";
        if (Character.compare(cityNameCharArray[0],'-') == 0 ||
                Character.compare(cityNameCharArray[cityNameCharArraySieze],'-') == 0)
            return "City name format is not right, can't start or end with hyphen";
        else if(cityName.contains(" ")) return "Please replace the space with hyphen";
        else if (StringUtils.countOccurrencesOf(cityName,"-")>1 )
            return "Just 1 hyphen is allowed";
        else {
            for (char ch : cityNameCharArray)
                if (!Character.isLetter(ch) && Character.compare(ch,'-') != 0)
                    return "No special characters or digits are allowed, just letters and hyphen";
        }
        if (getCityByCityName(cityName) == null)
            return cityName + " was not found in romanian database";
        return message;
    }

    @Override
    public City getCityByCityName(String cityName) {
        City theCity;
        Weather weather;
        if (theCityRepository.findByCityNameIgnoreCase(cityName) == null) {
            theCity = HttpRequestOpenWeatherAPI.getTheCity(cityName);
            if (theCity == null) return null;
            else {
                weather = HttpRequestOpenWeatherAPI.getTheWeather(theCity);
                theCity.setWeather(weather);
                weatherRepository.save(weather);
            }
        } else {
            theCity = theCityRepository.findByCityNameIgnoreCase(cityName);
            theCity.setAccessDateTime(LocalDateTime.now());
            if (!theCity.getWeather().getLocalDate().equals(LocalDate.now())) {
                weather = HttpRequestOpenWeatherAPI.getTheWeather(theCity);
                theCity.setWeather(weather);
                weatherRepository.save(weather);
            }
            theCityRepository.save(theCity);
        }
        return theCity;
    }

    @Override
    public List<City> getAllCtities() {
        return theCityRepository.findAll();
    }

    @Override
    public List<City> getLastCities() {
        List<City> lastCitiesList = theCityRepository.findTop6ByOrderByAccessDateTimeDesc();
        return lastCitiesList;
    }

    @Override
    public City deleteCityByCityName(String cityName) {
        City city = null;
        if (checkCityName(cityName).isEmpty()) {
            city = theCityRepository.findByCityNameIgnoreCase(cityName);
            theCityRepository.delete(city);
        } else throw new CityNotFoundException(checkCityName(cityName));
        return city;
    }
}