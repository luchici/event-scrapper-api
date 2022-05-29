package com.example.springboot99.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
@Entity(name = "cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @JsonProperty("city_name")
    private String cityName;
    private double lat;
    private double lon;

    private LocalDateTime creationDateTime = LocalDateTime.now();
    private LocalDateTime accessDateTime = LocalDateTime.now();;

    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE})
    @JoinColumn(name= "weather_id")
    private Weather weather;

    @JsonManagedReference
    public Weather getWeather() {
        return weather;
    }

    public void setWeather(Weather weather) {
        this.weather = weather;
        weather.setCity(this);
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", lat=" + lat +
                ", lon=" + lon +
                ", creationDateTime=" + creationDateTime +
                ", weather=" + weather.getDescription() +
                '}';
    }
}
