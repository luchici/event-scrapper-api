package com.example.springboot99.services;

import com.example.springboot99.entity.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CityService {

    City getCityByCityName(String cityName);

    List<City> getAllCtities();
}
