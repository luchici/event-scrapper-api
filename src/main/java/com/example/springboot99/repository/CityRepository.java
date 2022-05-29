package com.example.springboot99.repository;

import com.example.springboot99.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CityRepository extends JpaRepository<City, Long> {

    City findByCityNameIgnoreCase(String cityName);

    List<City> findTop6ByOrderByAccessDateTimeDesc();
}
