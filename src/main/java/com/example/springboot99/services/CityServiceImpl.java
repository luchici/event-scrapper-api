package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import com.example.springboot99.entity.Weather;
import com.example.springboot99.repository.CityRepository;
import com.example.springboot99.repository.WeatherRepository;
import com.example.springboot99.utility.HttpRequestWeatherAPI;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
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
    public City getCityByCityName(String cityName){

        if(theCityRepository.findByCityNameIgnoreCase(cityName).isEmpty()) {
            City localCity = HttpRequestWeatherAPI.getTheCity(cityName);
            Weather weatherToday = HttpRequestWeatherAPI.getTheWeather(localCity);
            weatherToday.setCity(localCity);
            weatherRepository.save(weatherToday);
            System.out.println(localCity);
//            System.out.println(theCityRepository.save(localCity));
            return localCity;}
        return theCityRepository.findByCityNameIgnoreCase(cityName).get(0);
    }

    @Override
    public List<City> getAllCtities() {
        return theCityRepository.findAll();
    }

    @Override
    public List<City> getLastTenCities() {
        List<City> list = theCityRepository.findAll();
        System.out.println(list.size());
        List<City> tenCities = new ArrayList<>();
        if (list.size() > 5){
            int count=0;
            for(int i=list.size()-1;count < 5;i--, count++)
                tenCities.add(list.get(i));}
        else return list;
        return tenCities;
    }
}